package exchange.dydx.abacus.state.modal

import exchange.dydx.abacus.state.changes.Changes
import exchange.dydx.abacus.state.changes.StateChanges
import exchange.dydx.abacus.utils.IMap
import kollections.iListOf
import kollections.toIList
import kollections.toIMap
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

internal fun TradingStateMachine.candles(payload: String): StateChanges {
    val json = Json.parseToJsonElement(payload).jsonObject.toIMap()
    return receivedCandles(json)
}

private fun TradingStateMachine.receivedCandles(payload: IMap<String, Any>): StateChanges {
    val markets = parser.asMap(payload["candles"])
    val marketIds = if (markets != null) markets.keys.toIList() else {
        val marketId = parser.asString(parser.value(payload, "candles.0.market")) ?: parser.asString(parser.value(payload, "candles.0.ticker"))
        if (marketId != null) iListOf(marketId) else null
    }
    return if (marketIds != null) {
        val size = parser.asList(payload["candles"])?.size ?: 0
        if (size > 0) {
            marketsSummary = marketsProcessor.receivedCandles(marketsSummary, payload)
            StateChanges(iListOf(Changes.candles), marketIds)
        } else {
            val size = parser.asMap(payload["candles"])?.size ?: 0
            if (size > 0) {
                marketsSummary = marketsProcessor.receivedCandles(marketsSummary, payload)
                StateChanges(iListOf(Changes.candles), marketIds)
            } else StateChanges(iListOf())
        }
    } else StateChanges(iListOf())
}

internal fun TradingStateMachine.sparklines(payload: String): StateChanges? {
    val json = Json.parseToJsonElement(payload).jsonObject.toIMap()
    return receivedSparklines(json)
}

private fun TradingStateMachine.receivedSparklines(payload: IMap<String, Any>): StateChanges {
    marketsSummary = marketsProcessor.receivedSparklines(marketsSummary, payload)
    return StateChanges(iListOf(Changes.sparklines, Changes.markets), null)
}
