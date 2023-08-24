//
//  ContentView.swift
//  abacus.ios
//
//  Created by Rui Huang on 7/1/22.
//

import Abacus
import SwiftUI

var stateManager: AbacusStateManager?
var state: PerpetualState?
var foregroundToken: NotificationToken?
var backgroundToken: NotificationToken?

var timer: Timer?
var timer2: Timer?


struct ContentView: View {
    var body: some View {
        Text("Hello, world!")
            .padding()
    }
    
    init() {
        stateManager = AbacusStateManager.shared
        stateManager?.asyncStateManager.readyToConnect = true
        
        timer = Timer.scheduledTimer(withTimeInterval: 5, repeats: false, block: { timer in
            stateManager?.asyncStateManager.market = "BTC-USD"
        })
        
        
        timer2 = Timer.scheduledTimer(withTimeInterval: 5, repeats: false, block: { timer in
            stateManager?.asyncStateManager.accountAddress = "dydx14zzueazeh0hj67cghhf9jypslcf9sh2n5k6art"
        })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
