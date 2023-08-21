//
//  NativeWebsocket.swift
//  abacus.ios
//
//  Created by John Huang on 5/25/23.
//

import Foundation
import Abacus

final class NativeWebSocket: NSObject, Abacus.WebSocketProtocol {
    private var url: String?
    private var connected: ((KotlinBoolean) -> Void)?
    private var received: ((String) -> Void)?

    private var dataTask: URLSessionWebSocketTask? {
        didSet {
            if dataTask !== oldValue {
                oldValue?.cancel(with: .normalClosure, reason: nil)
                if let dataTask = dataTask {
                    dataTask.resume()
                    receiveData()
                }
            }
        }
    }

    func connect(url: String, connected: @escaping (KotlinBoolean) -> Void, received: @escaping (String) -> Void) {
        self.url = url
        self.connected = connected
        self.received = received
        connect()
    }

    func disconnect() {
        dataTask?.cancel(with: .normalClosure, reason: nil)
        dataTask = nil
    }

    func send(message: String) {
        guard let task = dataTask else {
//            Console.shared.log("AbacusWebSocketImp: WebSocket is not connected.")
            return
        }

        let message = URLSessionWebSocketTask.Message.string(message)
        task.send(message) { [weak self] error in
            if let error = error {
//                Console.shared.log("AbacusWebSocketImp: Error sending message: \(error)")
                self?.disconnect()
                self?.connect()
            }
        }
    }

    private func connect() {
        if let url = url, let url = URL(string: url) {
            let urlSession = URLSession(configuration: .default, delegate: self, delegateQueue: nil)
            let dataTask = urlSession.webSocketTask(with: url)
            dataTask.maximumMessageSize = 16 * 1024 * 1024
            self.dataTask = dataTask
        } else {
//            Console.shared.log("AbacusWebSocketImp: invalid url: \(String(describing: url))")
        }
    }

    private func receiveData() {
        guard let task = dataTask else { return }

        task.receive { [weak self] result in
            switch result {
            case .success(let message):
                switch message {
                case .data(let data):
                    if let text = String(data: data, encoding: .utf8) {
                        self?.dispatch(text: text)
                    } else {
//                        Console.shared.log("AbacusWebSocketImp: unable to decode message")
                    }

                case .string(let text):
                    self?.dispatch(text: text)

                @unknown default:
                    break
                }
                // Continue to receive more data
                self?.receiveData()

            case .failure(let error):
//                Console.shared.log("AbacusWebSocketImp: Error receiving message: \(error)")
                self?.dataTask = nil
            }
        }
    }

    private func dispatch(text: String) {
        DispatchQueue.main.async { [weak self] in
            self?.received?(text)
        }
    }
}

extension NativeWebSocket: URLSessionWebSocketDelegate {
    public func urlSession(_ session: URLSession, webSocketTask: URLSessionWebSocketTask, didOpenWithProtocol protocol: String?) {
        DispatchQueue.main.async {[weak self] in
            self?.connected?(true)
        }
    }

    public func urlSession(_ session: URLSession, webSocketTask: URLSessionWebSocketTask, didCloseWith closeCode: URLSessionWebSocketTask.CloseCode, reason: Data?) {
        DispatchQueue.main.async {[weak self] in
            self?.connected?(false)
        }
    }
}
