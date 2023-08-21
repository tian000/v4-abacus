package exchange.dydx.abacus.payload

import exchange.dydx.abacus.payload.v3.V3BaseTests
import exchange.dydx.abacus.state.modal.TransferInputField
import exchange.dydx.abacus.state.modal.transfer
import exchange.dydx.abacus.tests.extensions.log
import exchange.dydx.abacus.utils.ServerTime
import kotlin.test.Test

class TransferInputTests : V3BaseTests() {
    @Test
    fun testDataFeed() {
        setup()

        print("--------First round----------\n")

        testTransferInputOnce()
    }

    private fun testTransferInputOnce() {
        var time = ServerTime.now()
        testDepositTransferInput()
        time = perp.log("Deposit", time)

        testSlowWithdrawalTransferInput()
        time = perp.log("Slow Withdrawal", time)

        testFastWithdrawalTransferInput()
        perp.log("Fast Withdrawl", time)

        testTransferOutTransferInput()
        perp.log("Transfer Out", time)


    }

    private fun testDepositTransferInput() {
        /*
        Designed workflow
        transfer("DEPOSIT", TransferInputField.type)
        transfer("1000", TransferInputField.usdcSize)
        transfer("0", TransferInputField.usdcFee)   // if fee is charged outside usdcSize
        transfer("1.3", TransferInputField.usdcFee) // if fee is deducted from usdcSize
         */

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
                        }
                    }
                }
            """.trimIndent()
        )

        test(
            {
                perp.transfer("1", TransferInputField.usdcSize)
            },
            """
                {
                    "input": {
                        "current": "transfer",
                        "transfer": {
                            "type": "DEPOSIT",
                            "size": {
                                "usdcSize": 1.0
                            }
                        }
                    },
                    "wallet": {
                        "account": {
                            "subaccounts": {
                                "0": {
                                    "equity": {
                                        "current": 206724.545896,
                                        "postOrder": 206725.545896
                                    },
                                    "freeCollateral": {
                                        "current": 187967.3363698,
                                        "postOrder": 187968.3363698
                                    },
                                    "quoteBalance": {
                                        "current": -62697.279528,
                                        "postOrder": -62696.279528
                                    },
                                    "leverage": {
                                        "current": 1.3041504890262603,
                                        "postOrder": 1.3041441804179876
                                    },
                                    "marginUsage": {
                                        "current": 0.09073527986191088,
                                        "postOrder": 0.09073484094528117
                                    },
                                    "buyingPower": {
                                        "current": 3759346.727396,
                                        "postOrder": 3759366.727396
                                    }
                                }
                            }
                        }
                    }
                }
            """.trimIndent()
        )


        test(
            {
                perp.transfer("5000.0", TransferInputField.usdcSize)
            },
            """
                {
                    "input": {
                        "transfer": {
                            "type": "DEPOSIT",
                            "size": {
                                "usdcSize": 5000.0
                            },
                            "summary": {
                                "usdcSize": 5000.0
                            },
                            "options": {
                                "needsSize": true,
                                "needsGasless": true
                            }
                        }
                    },
                    "wallet": {
                        "account": {
                            "subaccounts": {
                                "0": {
                                    "equity": {
                                        "postOrder": 211724.545896
                                    },
                                    "freeCollateral": {
                                        "postOrder": 192967.3363698
                                    },
                                    "quoteBalance": {
                                        "postOrder": -57697.279528
                                    },
                                    "leverage": {
                                        "postOrder": 1.2733522062030003
                                    },
                                    "marginUsage": {
                                        "postOrder": 0.0885925127236481
                                    },
                                    "buyingPower": {
                                        "postOrder": 3859346.727396
                                    }
                                }
                            }
                        }
                    }
                }
            """.trimIndent()
        )


        /*
        size = 1000.0
         */

        test(
            {
                perp.transfer("1000.0", TransferInputField.usdcSize)
            },
            """
                {
                    "input": {
                        "transfer": {
                            "type": "DEPOSIT",
                            "size": {
                                "usdcSize": 1000.0
                            },
                            "summary": {
                                "usdcSize": 1000.0
                            },
                            "options": {
                                "needsSize": true,
                                "needsGasless": true
                            }
                        }
                    },
                    "wallet": {
                        "account": {
                            "subaccounts": {
                                "0": {
                                    "equity": {
                                        "postOrder": 207724.545896
                                    },
                                    "freeCollateral": {
                                        "postOrder": 188967.3363698
                                    },
                                    "quoteBalance": {
                                        "postOrder": -61697.279528
                                    },
                                    "leverage": {
                                        "postOrder": 1.2978722204499544
                                    },
                                    "marginUsage": {
                                        "postOrder": 0.09029847409362513
                                    },
                                    "buyingPower": {
                                        "postOrder": 3779346.727396
                                    }
                                }
                            }
                        }
                    }
                }
            """.trimIndent()
        )

        test(
            {
                perp.transfer("10.0", TransferInputField.usdcFee)
            },
            """
                {
                    "input": {
                        "transfer": {
                            "type": "DEPOSIT",
                            "size": {
                                "usdcSize": 1000.0
                            },
                            "summary": {
                                "usdcSize": 1000.0,
                                "filled": true,
                                "fee": 10.0
                            },
                            "options": {
                                "needsSize": true,
                                "needsFastSpeed": false
                            },
                            "fee": 10.0
                        }
                    },
                    "wallet": {
                        "account": {
                            "subaccounts": {
                                "0": {
                                    "equity": {
                                        "current": 206724.545896,
                                        "postOrder": 207714.545896
                                    },
                                    "freeCollateral": {
                                        "current": 187967.3363698,
                                        "postOrder": 188957.3363698
                                    },
                                    "quoteBalance": {
                                        "current": -62697.279528,
                                        "postOrder": -61707.279528
                                    },
                                    "leverage": {
                                        "current": 1.3041504890262603,
                                        "postOrder": 1.2979347039036218
                                    },
                                    "marginUsage": {
                                        "current": 0.09073527986191088,
                                        "postOrder": 0.09030282133246215
                                    },
                                    "buyingPower": {
                                        "current": 3759346.727396,
                                        "postOrder": 3779146.727396
                                    }
                                }
                            }
                        }
                    }
                }
            """.trimIndent()
        )
    }

    private fun testSlowWithdrawalTransferInput() {
        test({
            perp.transfer("WITHDRAWAL", TransferInputField.type)
        }, null)

        test({
            perp.transfer("false", TransferInputField.fastSpeed)
        }, null)

        test({
            perp.transfer("0", TransferInputField.usdcFee)
        }, null)

        /*
        size = 1000.0
         */
        test({
            perp.transfer("5000.0", TransferInputField.usdcSize)
        }, null)

        test(
            {
                perp.transfer("1000.0", TransferInputField.usdcSize)
            },
            """
                {
                    "input": {
                        "transfer": {
                            "type": "WITHDRAWAL",
                            "size": {
                                "usdcSize": 1000.0
                            },
                            "summary": {
                                "usdcSize": 1000.0,
                                "filled": true
                            },
                            "options": {
                                "needsSize": true,
                                "needsFastSpeed": true
                            }
                        }
                    },
                    "wallet": {
                        "account": {
                            "subaccounts": {
                                "0": {
                                    "equity": {
                                        "postOrder": 205724.545896
                                    },
                                    "freeCollateral": {
                                        "postOrder": 186967.3363698
                                    },
                                    "quoteBalance": {
                                        "postOrder": -63697.279528
                                    },
                                    "leverage": {
                                        "postOrder": 1.3104897932806274
                                    },
                                    "marginUsage": {
                                        "postOrder": 0.09117633214114529
                                    },
                                    "buyingPower": {
                                        "postOrder": 3739346.727396
                                    }
                                }
                            }
                        }
                    }
                }
            """.trimIndent()
        )


        test(
            {
                perp.transfer("10.0", TransferInputField.usdcFee)
            },
            """
                {
                    "input": {
                        "transfer": {
                            "type": "WITHDRAWAL",
                            "size": {
                                "usdcSize": 1000.0
                            },
                            "summary": {
                                "usdcSize": 1000.0,
                                "filled": true
                            },
                            "options": {
                                "needsSize": true,
                                "needsFastSpeed": true
                            }
                        }
                    },
                    "wallet": {
                        "account": {
                            "subaccounts": {
                                "0": {
                                    "equity": {
                                        "postOrder": 205714.545896
                                    },
                                    "freeCollateral": {
                                        "postOrder": 186957.3363698
                                    },
                                    "quoteBalance": {
                                        "postOrder": -63707.279528
                                    },
                                    "leverage": {
                                        "postOrder": 1.3105534975650073
                                    },
                                    "marginUsage": {
                                        "postOrder": 0.09118076431835209
                                    },
                                    "buyingPower": {
                                        "postOrder": 3739146.727396
                                    }
                                }
                            }
                        }
                    }
                }
            """.trimIndent()
        )
    }


    private fun testFastWithdrawalTransferInput() {
        test({
            perp.transfer("WITHDRAWAL", TransferInputField.type)
        }, null)

        test({
            perp.transfer("true", TransferInputField.fastSpeed)
        }, null)


        test({
            perp.transfer("0.0", TransferInputField.usdcFee)
        }, null)


        /*
        size = 1000.0
         */
        test({
            perp.transfer("5000.0", TransferInputField.usdcSize)
        }, null)

        test({
            perp.transfer("10.0", TransferInputField.usdcFee)
        }, null)

        test(
            {
                perp.transfer("1000.0", TransferInputField.usdcSize)
            },
            """
                {
                    "input": {
                        "transfer": {
                            "type": "WITHDRAWAL",
                            "size": {
                                "usdcSize": 1000.0
                            },
                            "summary": {
                                "usdcSize": 1000.0,
                                "fastSpeed": true,
                                "filled": true,
                                "fee": 10.0
                            },
                            "options": {
                                "needsSize": true,
                                "needsFastSpeed": true
                            },
                            "fastSpeed": true,
                            "fee": 10.0
                        }
                    },
                    "wallet": {
                        "account": {
                            "subaccounts": {
                                "0": {
                                    "equity": {
                                        "current": 206724.545896,
                                        "postOrder": 205714.545896
                                    },
                                    "freeCollateral": {
                                        "current": 187967.3363698,
                                        "postOrder": 186957.3363698
                                    },
                                    "quoteBalance": {
                                        "current": -62697.279528,
                                        "postOrder": -63707.279528
                                    },
                                    "leverage": {
                                        "current": 1.3041504890262603,
                                        "postOrder": 1.3105534975650073
                                    },
                                    "marginUsage": {
                                        "current": 0.09073527986191088,
                                        "postOrder": 0.09118076431835209
                                    },
                                    "buyingPower": {
                                        "current": 3759346.727396,
                                        "postOrder": 3739146.727396
                                    }
                                }
                            }
                        }
                    }
                }
            """.trimIndent()
        )
    }

    private fun testTransferOutTransferInput() {
        test({
            perp.transfer("TRANSFER_OUT", TransferInputField.type)
        }, null)


        test({
            perp.transfer("0.0", TransferInputField.usdcFee)
        }, null)

        /*
        size = 1000.0
         */
        test({
            perp.transfer("5000.0", TransferInputField.usdcSize)
        }, null)

        test(
            {
                perp.transfer("1000.0", TransferInputField.usdcSize)
            },
            """
                {
                    "input": {
                        "transfer": {
                            "type": "TRANSFER_OUT",
                            "size": {
                                "usdcSize": 1000.0
                            },
                            "summary": {
                                "usdcSize": 1000.0,
                                "filled": true
                            },
                            "options": {
                                "needsSize": true
                            }
                        }
                    },
                    "wallet": {
                        "account": {
                            "subaccounts": {
                                "0": {
                                    "equity": {
                                        "postOrder": 205724.545896
                                    },
                                    "freeCollateral": {
                                        "postOrder": 186967.3363698
                                    },
                                    "quoteBalance": {
                                        "postOrder": -63697.279528
                                    },
                                    "leverage": {
                                        "postOrder": 1.3104897932806274
                                    },
                                    "marginUsage": {
                                        "postOrder": 0.09117633214114529
                                    },
                                    "buyingPower": {
                                        "postOrder": 3739346.727396
                                    }
                                }
                            }
                        }
                    }
                }
            """.trimIndent()
        )
    }
}