package exchange.dydx.abacus.utils

import abs
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import kollections.JsExport
import kotlinx.serialization.Serializable
import numberOfDecimals
import kotlin.math.*


@JsExport
@Serializable
class Rounder {
    companion object {
        fun quickRound(number: Double, stepSize: Double, stepSizeDecimals: Int): Double {
            val negative = number < 0.0
            val multiplier = (number.abs() / stepSize).roundToInt()
            val absValue = if (stepSizeDecimals > 0) {
                val multiplierString = multiplier.toString()
                val length = multiplierString.length
                val stringBuilder = StringBuilder()
                if (multiplierString.length <= stepSizeDecimals) {
                    stringBuilder.append("0.")
                    for (i in 0 until stepSizeDecimals - multiplierString.length) {
                        stringBuilder.append("0")
                    }
                    stringBuilder.append(multiplierString)
                } else {
                    stringBuilder.append(multiplierString.substring(0, length - stepSizeDecimals))
                    stringBuilder.append(".")
                    stringBuilder.append(multiplierString.substring(length - stepSizeDecimals, length))
                }
                stringBuilder.toString().toDouble()
            } else {
                (multiplier * stepSize.toInt()).toDouble()
            }
            return if (absValue == 0.0) 0.0 else {
                absValue * (if (negative) -1 else 1)
            }
        }

        fun round(number: Double, stepSize: Double): Double {
            return roundDecimal(number.toBigDecimal(null, Numeric.decimal.mode),
                stepSize.toBigDecimal(null, Numeric.decimal.mode)).doubleValue(false)
        }

        fun roundDecimal(number: BigDecimal, stepSize: BigDecimal): BigDecimal {
            /*
            It should be (number / stepSize).toLong() * stepSize
            However, calculation with Double will cause problems
            We are using TextNumber to work around this
             */
            /*
            return if (stepSize > 0.0) {
                val textNumber = TextNumber(number)
                val textStepSize = TextNumber(stepSize)
                val long = textNumber.divide(textStepSize)?.toLong()
                if (long != null) {
                    val divided = TextNumber(long)
                    divided.multiply(textStepSize).toDouble()
                } else {
                    number
                }
            } else {
                number
            }
             */
            return if (stepSize > Numeric.double.ZERO) {
                val long = (number / stepSize).longValue(false)
                return stepSize * long.toBigDecimal(null, Numeric.decimal.mode)
            } else number
        }
    }
}