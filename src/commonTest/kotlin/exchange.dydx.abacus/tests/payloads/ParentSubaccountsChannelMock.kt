package exchange.dydx.abacus.tests.payloads

import kollections.JsExport
import kotlinx.serialization.Serializable

@Suppress("PropertyName")
@JsExport
@Serializable
internal class ParentSubaccountsChannelMock {
    internal val subscribed = """
        {
           "type":"subscribed",
           "connection_id":"c5a28fa5-c257-4fb5-b68e-fe084c2768e5",
           "message_id":1,
           "channel":"v4_parent_subaccounts",
           "id":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4/0",
           "contents":{
              "subaccount":{
                 "address":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4",
                 "parentSubaccountNumber":0,
                 "equity":"100000000000.000000",
                 "freeCollateral":"100000000000.000000",
                 "childSubaccounts":[
                    {
                       "address":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4",
                       "subaccountNumber":0,
                       "equity":"100000000000.000000",
                       "freeCollateral":"100000000000.000000",
                       "openPerpetualPositions":{
                          "BTC-USD":{
                             "market":"BTC-USD",
                             "status":"OPEN",
                             "side":"SHORT",
                             "size":"-0.442371112",
                             "maxSize":"0.442388027",
                             "entryPrice":"0.000000",
                             "exitPrice":null,
                             "realizedPnl":"0.000000",
                             "unrealizedPnl":"0.000000",
                             "createdAt":"2022-12-02T16:14:15.883Z",
                             "createdAtHeight":"862",
                             "closedAt":null,
                             "sumOpen":"0",
                             "sumClose":"0",
                             "netFunding":"0"
                          }
                       },
                       "assetPositions":{
                          "USDC":{
                             "symbol":"USDC",
                             "side":"LONG",
                             "size":"100000",
                             "assetId":"0"
                          }
                       },
                       "marginEnabled":true
                    },
                    {
                       "address":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4",
                       "subaccountNumber":128,
                       "equity":"500.000000",
                       "freeCollateral":"500.000000",
                       "openPerpetualPositions":{
                          "RUNE-USD":{
                             "market":"RUNE-USD",
                             "status":"OPEN",
                             "side":"LONG",
                             "size":"120",
                             "maxSize":"12",
                             "entryPrice":"1.464000",
                             "exitPrice":"0.000000",
                             "unrealizedPnl":"0.729203",
                             "realizedPnl":"0.271316",
                             "createdAt":"2022-07-18T20:36:17.165Z",
                             "closedAt":null,
                             "sumOpen":"12",
                             "sumClose":"0",
                             "netFunding":"0.271316"
                          }
                       },
                       "assetPositions":{
                          "USDC":{
                             "symbol":"USDC",
                             "side":"LONG",
                             "size":"500",
                             "assetId":"0"
                          }
                       },
                       "marginEnabled":true
                    }
                 ],
                 "orders":[
                    {
                       "subaccountNumber":0,
                       "id":"string",
                       "subaccountId":"string",
                       "clientId":"string",
                       "clobPairId":"string",
                       "side":"BUY",
                       "size":"string",
                       "totalFilled":"string",
                       "price":"string",
                       "type":"LIMIT",
                       "reduceOnly":true,
                       "orderFlags":"string",
                       "goodTilBlock":"string",
                       "goodTilBlockTime":"string",
                       "createdAtHeight":"string",
                       "clientMetadata":"string",
                       "triggerPrice":"string",
                       "timeInForce":"GTT",
                       "status":"OPEN",
                       "postOnly":true,
                       "ticker":"string",
                       "updatedAt":"string",
                       "updatedAtHeight":"string"
                    },
                    {
                       "subaccountNumber":128,
                       "id":"string",
                       "subaccountId":"string",
                       "clientId":"string",
                       "clobPairId":"string",
                       "side":"BUY",
                       "size":"string",
                       "totalFilled":"string",
                       "price":"string",
                       "type":"LIMIT",
                       "reduceOnly":true,
                       "orderFlags":"string",
                       "goodTilBlock":"string",
                       "goodTilBlockTime":"string",
                       "createdAtHeight":"string",
                       "clientMetadata":"string",
                       "triggerPrice":"string",
                       "timeInForce":"GTT",
                       "status":"OPEN",
                       "postOnly":true,
                       "ticker":"string",
                       "updatedAt":"string",
                       "updatedAtHeight":"string"
                    }
                 ]
              }
           }
        }
    """.trimIndent()

    internal val channel_data = """
        {
           "type":"channel_data",
           "connection_id":"f7a21cc6-4a9f-42d7-8bbd-215f11614571",
           "message_id":120,
           "id":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4/0",
           "channel":"v4_parent_subaccounts",
           "subaccountNumber":128,
           "contents":{
              "subaccounts":{
                 "address":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4",
                 "subaccountNumber":128
              },
              "perpetualPositions":[
                 {
                    "address":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4",
                    "subaccountNumber":128,
                    "positionId":"1bb14a35-db8b-57c0-a39c-dc6b80b995e0",
                    "market":"RUNE-USD",
                    "side":"LONG",
                    "status":"OPEN",
                    "size":"300",
                    "maxSize":"300"
                 }
              ],
              "assetPositions":[
                 {
                    "address":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4",
                    "subaccountNumber":128,
                    "positionId":"24a26508-9d45-5b4c-a13b-31f6e9780ecc",
                    "assetId":"0",
                    "denom":"USDC",
                    "side":"LONG",
                    "size":"9000"
                 }
              ]
           }
        }
    """.trimIndent()
}
