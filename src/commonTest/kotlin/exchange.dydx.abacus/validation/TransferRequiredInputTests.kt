package exchange.dydx.abacus.validation

import exchange.dydx.abacus.payload.v3.V3BaseTests
import exchange.dydx.abacus.state.modal.TradeInputField
import exchange.dydx.abacus.state.modal.TransferInputField
import exchange.dydx.abacus.state.modal.trade
import exchange.dydx.abacus.state.modal.transfer
import exchange.dydx.abacus.tests.extensions.log
import exchange.dydx.abacus.utils.ServerTime
import kotlinx.datetime.Clock
import kotlin.test.Test

class TransferRequiredInputTests : V3BaseTests() {
    @Test
    fun testDataFeed() {
        setup()


        print("--------First round----------\n")

        testTransferInputOnce()
    }

    private fun testTransferInputOnce() {
        var time = ServerTime.now()
        reset()
        testTransferInputDeposit()
        time = perp.log("Deposit", time)

        reset()
        testTransferInputWithdraw()
        time = perp.log("Withdrawal", time)

        reset()
        testTransferInputTransferOut()
        time = perp.log("Transfer", time)
    }

    override fun reset() {
        super.reset()
        test({
            perp.transfer(null, null)
        }, null)
    }

    private fun testTransferInputDeposit() {

        test(
            {
                perp.transfer("DEPOSIT", TransferInputField.type)
            },
            """
                {
                    "input": {
                        "current": "transfer",
                        "transfer": {
                            "type": "DEPOSIT"
                        },
                        "errors": [
                            {
                                "code": "REQUIRED_SIZE",
                                "type": "REQUIRED",
                                "fields": [
                                    "size.usdcSize"
                                ],
                                "resources": {
                                    "action": {
                                        "stringKey": "APP.TRADE.ENTER_AMOUNT"
                                    }
                                }
                            }
                        ]
                    }
                }
            """.trimIndent()
        )

        test(
            {
                perp.transfer("1.0", TransferInputField.usdcSize)
            },
            """
                {
                    "input": {
                        "current": "transfer",
                        "transfer": {
                            "type": "DEPOSIT"
                        },
                        "errors": null
                    }
                }
            """.trimIndent()
        )
    }


    private fun testTransferInputWithdraw() {
        test(
            {
                perp.transfer("WITHDRAWAL", TransferInputField.type)
            },
            """
                {
                    "input": {
                        "current": "transfer",
                        "transfer": {
                            "type": "WITHDRAWAL"
                        },
                        "errors": [
                            {
                                "code": "REQUIRED_SIZE",
                                "type": "REQUIRED",
                                "fields": [
                                    "size.usdcSize"
                                ],
                                "resources": {
                                    "action": {
                                        "stringKey": "APP.TRADE.ENTER_AMOUNT"
                                    }
                                }
                            }
                        ]
                    }
                }
            """.trimIndent()
        )

        test(
            {
                perp.transfer("1.0", TransferInputField.usdcSize)
            },
            """
                {
                    "input": {
                        "current": "transfer",
                        "transfer": {
                            "type": "WITHDRAWAL"
                        },
                        "errors": null
                    }
                }
            """.trimIndent()
        )
    }

    private fun testTransferInputTransferOut() {

        test(
            {
                perp.transfer("TRANSFER_OUT", TransferInputField.type)
            },
            """
                {
                    "input": {
                        "current": "transfer",
                        "transfer": {
                            "type": "TRANSFER_OUT"
                        },
                        "errors": [
                            {
                                "code": "REQUIRED_SIZE",
                                "type": "REQUIRED",
                                "fields": [
                                    "size.usdcSize"
                                ],
                                "resources": {
                                    "action": {
                                        "stringKey": "APP.TRADE.ENTER_AMOUNT"
                                    }
                                }
                            },
                            {
                                "code": "REQUIRED_ADDRESS",
                                "type": "REQUIRED",
                                "fields": [
                                    "address"
                                ],
                                "resources": {
                                    "action": {
                                        "stringKey": "APP.DIRECT_TRANSFER_MODAL.ENTER_ETH_ADDRESS"
                                    }
                                }
                            }
                        ]
                    }
                }
            """.trimIndent()
        )

        test(
            {
                perp.transfer("1.0", TransferInputField.usdcSize)
            },
            """
                {
                    "input": {
                        "current": "transfer",
                        "transfer": {
                            "type": "TRANSFER_OUT"
                        },
                        "errors": [
                            {
                                "code": "REQUIRED_ADDRESS",
                                "type": "REQUIRED",
                                "fields": [
                                    "address"
                                ],
                                "resources": {
                                    "action": {
                                        "stringKey": "APP.DIRECT_TRANSFER_MODAL.ENTER_ETH_ADDRESS"
                                    }
                                }
                            }
                        ]
                    }
                }
            """.trimIndent()
        )

        test(
            {
                perp.transfer("0x", TransferInputField.address)
            },
            """
                {
                    "input": {
                        "current": "transfer",
                        "transfer": {
                            "type": "TRANSFER_OUT"
                        },
                        "errors": null
                    }
                }
            """.trimIndent()
        )
    }

}