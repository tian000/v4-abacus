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
                    },
                    {
                       "address":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4",
                       "subaccountNumber":256,
                       "equity":"500.000000",
                       "freeCollateral":"500.000000",
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
                 ]
              },
              "orders":[
                 {
                    "id":"b812bea8-29d3-5841-9549-caa072f6f8a8",
                    "market":"RUNE-USD",
                    "subaccountId":"660efb4c-5472-5119-8c17-65cf702ccaea",
                    "subaccountNumber":0,
                    "clientId":"194126268",
                    "clobPairId":"1",
                    "orderFlags": "0",
                    "side":"SELL",
                    "size":"1.653451",
                    "totalFilled":"0.682633",
                    "price":"1255.927",
                    "type":"LIMIT",
                    "status":"BEST_EFFORT_OPENED",
                    "timeInForce":"GTT",
                    "postOnly":false,
                    "reduceOnly":false,
                    "goodTilBlock":"5837"
                 },
                 {
                    "id":"b812bea8-29d3-5841-9549-caa072f6f8a9",
                    "market":"RUNE-USD",
                    "subaccountId":"660efb4c-5472-5119-8c17-65cf702ccaea",
                    "subaccountNumber":128,
                    "clientId":"194126269",
                    "clobPairId":"134",
                    "orderFlags": "0",
                    "side":"SELL",
                    "size":"1.653451",
                    "totalFilled":"0.682633",
                    "price":"1255.927",
                    "type":"LIMIT",
                    "status":"BEST_EFFORT_CANCELED",
                    "timeInForce":"GTT",
                    "postOnly":false,
                    "reduceOnly":false,
                    "goodTilBlock":"5837"
                 },
                 {
                    "id":"b812bea8-29d3-5841-9549-caa072f6f8b9",
                    "market":"RUNE-USD",
                    "subaccountId":"660efb4c-5472-5119-8c17-65cf702ccaea",
                    "subaccountNumber":256,
                    "clientId":"194126270",
                    "clobPairId":"134",
                    "orderFlags": "0",
                    "side":"SELL",
                    "size":"1.653451",
                    "totalFilled":"0.682633",
                    "price":"1255.927",
                    "type":"LIMIT",
                    "status":"BEST_EFFORT_CANCELED",
                    "timeInForce":"GTT",
                    "postOnly":false,
                    "reduceOnly":false,
                    "goodTilBlock":"5837"
                 }
              ]
           }
        }
    """.trimIndent()

    internal val channel_data = """
        {
           "type":"channel_data",
           "connection_id":"f7a21cc6-4a9f-42d7-8bbd-215f11614571",
           "message_id":120,
           "id":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4/1",
           "channel":"v4_parent_subaccounts",
           "subaccountNumber":129,
           "contents":{
              "subaccounts":{
                 "address":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4",
                 "subaccountNumber":129
              },
              "perpetualPositions":[
                 {
                    "address":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4",
                    "subaccountNumber":129,
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
                    "subaccountNumber":129,
                    "positionId":"24a26508-9d45-5b4c-a13b-31f6e9780ecc",
                    "assetId":"0",
                    "denom":"USDC",
                    "side":"LONG",
                    "size":"9000"
                 }
              ],
              "orders":[
                 {
                    "id":"b812bea8-29d3-5841-9549-caa072f6f8a8",
                    "market":"RUNE-USD",
                    "subaccountId":"660efb4c-5472-5119-8c17-65cf702ccaea",
                    "subaccountNumber":257,
                    "clientId":"94126268",
                    "clobPairId":"1",
                    "orderFlags": "0",
                    "side":"SELL",
                    "size":"1.653451",
                    "totalFilled":"0.682633",
                    "price":"1255.927",
                    "type":"LIMIT",
                    "status":"BEST_EFFORT_OPENED",
                    "timeInForce":"GTT",
                    "postOnly":false,
                    "reduceOnly":false,
                    "goodTilBlock":"5837"
                 },
                 {
                    "id":"b812bea8-29d3-5841-9549-caa072f6f8a9",
                    "market":"RUNE-USD",
                    "subaccountId":"660efb4c-5472-5119-8c17-65cf702ccaea",
                    "subaccountNumber":513,
                    "clientId":"94126269",
                    "clobPairId":"134",
                    "orderFlags": "0",
                    "side":"SELL",
                    "size":"1.653451",
                    "totalFilled":"0.682633",
                    "price":"1255.927",
                    "type":"LIMIT",
                    "status":"BEST_EFFORT_CANCELED",
                    "timeInForce":"GTT",
                    "postOnly":false,
                    "reduceOnly":false,
                    "goodTilBlock":"5837"
                 }
              ],
              "fills":[
                {
                   "id":"180c2462-eb3b-5985-a702-32c503462a37",
                   "subaccountNumber": 129,
                   "side":"BUY",
                   "size":"0.01",
                   "type":"LIMIT",
                   "price":"1878",
                   "eventId":"0001781f0000000200000011",
                   "orderId":"1118c548-1715-5a72-9c41-f4388518c6e2",
                   "createdAt":"2023-07-07T17:20:10.369Z",
                   "liquidity":"TAKER",
                   "clobPairId":"1",
                   "quoteAmount":"18.78",
                   "subaccountId":"8586bcf6-1f58-5ec9-a0bc-e53db273e7b0",
                   "createdAtHeight":"96287",
                   "transactionHash":"49AFD6A9FA9D4F973F2CF764FD0A297358F2C3B557252C51E9D4E8794543E1B2",
                   "ticker":"ETH-USD"
                }
             ],
             "transfers": {
                "id":"89586775-0646-582e-9b36-4f131715644d",
                "sender": {
                    "address":"dydx1sxdvx2kzgdykutxfv06ka9gt0klu8wctfwskhg",
                    "subaccountNumber":129
                },
                "recipient":{
                    "address":"dydx1vvjr376v4hfpy5r6m3dmu4u3mu6yl6sjds3gz8"
                 },
                "size":"419.98472",
                "createdAt":"2023-08-21T21:37:53.373Z",
                "createdAtHeight":"404014",
                "symbol":"USDC",
                "type":"WITHDRAWAL",
                "transactionHash": "MOCKHASH1"
            }
           }
        }
    """.trimIndent()

    internal val channel_data_with_fill_only = """
        {
           "type":"channel_data",
           "connection_id":"f7a21cc6-4a9f-42d7-8bbd-215f11614571",
           "message_id":120,
           "id":"dydx199tqg4wdlnu4qjlxchpd7seg454937hjrknju4/1",
           "channel":"v4_parent_subaccounts",
           "subaccountNumber":1,
           "contents":{
              "fills":[
                {
                   "id":"180c2462-eb3b-5985-a702-32c503462a37",
                   "subaccountNumber": 1,
                   "side":"BUY",
                   "size":"0.01",
                   "type":"LIMIT",
                   "price":"1878",
                   "eventId":"0001781f0000000200000011",
                   "orderId":"1118c548-1715-5a72-9c41-f4388518c6e2",
                   "createdAt":"2023-07-07T17:11:10.369Z",
                   "liquidity":"TAKER",
                   "clobPairId":"1",
                   "quoteAmount":"18.78",
                   "subaccountId":"8586bcf6-1f58-5ec9-a0bc-e53db273e7b0",
                   "createdAtHeight":"96287",
                   "transactionHash":"49AFD6A9FA9D4F973F2CF764FD0A297358F2C3B557252C51E9D4E8794543E1B2",
                   "ticker":"ETH-USD"
                }
             ],
             "transfers": {
                "id":"89586775-0646-582e-9b36-4f133445644d",
                "sender": {
                    "address":"dydx1sxdvx2kzgdykutxfv06ka9gt0klu8wctfwskhg",
                    "subaccountNumber":1
                },
                "recipient":{
                    "address":"dydx1vvjr376v4hfpy5r6m3dmu4u3mu6yl6sjds3gz8"
                 },
                "size":"419.98472",
                "createdAt":"2023-08-22T21:37:53.373Z",
                "createdAtHeight":"404014",
                "symbol":"USDC",
                "type":"WITHDRAWAL",
                "transactionHash": "MOCKHASH2"
            }
           }
        }
    """.trimIndent()
}
