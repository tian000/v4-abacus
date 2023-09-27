package exchange.dydx.abacus.processor.configs

import exchange.dydx.abacus.processor.base.BaseProcessor
import exchange.dydx.abacus.protocols.ParserProtocol
import exchange.dydx.abacus.utils.*
import exchange.dydx.abacus.utils.mutable

internal class ConfigsProcessor(parser: ParserProtocol) : BaseProcessor(parser) {
    private val equityTiersProcessor = EquityTiersProcessor(parser)
    private val feeTiersProcessor = FeeTiersProcessor(parser)
    private val feeDiscountsProcessor = FeeDiscountsProcessor(parser)
    private val networkConfigsProcessor = NetworkConfigsProcessor(parser)

    internal fun receivedOnChainEquityTiers(
        existing: Map<String, Any>?,
        payload: Map<String, Any>
    ): Map<String, Any>? {
        val modified = existing?.mutable() ?: mutableMapOf()
        val map = parser.asNativeMap(payload) as Map<String, List<Any>>?
        modified?.safeSet("equityTiers", map)

        return receivedObject(existing, "equityTiers", modified) { existing, payload ->
            val map = parser.asNativeMap(payload) as Map<String, Map<String, List<Any>>>?
            if (map != null) {
                equityTiersProcessor.received(map)
            } else {
                null
            }
        }
    }

    internal fun receivedFeeTiers(
        existing: Map<String, Any>?,
        payload: List<Any>
    ): Map<String, Any>? {
        return receivedObject(existing, "feeTiers", payload) { existing, payload ->
            val list = parser.asNativeList(payload)
            if (list != null) {
                feeTiersProcessor.received(list)
            } else {
                null
            }
        }
    }

    internal fun receivedOnChainFeeTiers(
        existing: Map<String, Any>?,
        payload: List<Any>
    ): Map<String, Any>? {
        return receivedObject(existing, "feeTiers", payload) { existing, payload ->
            val list = parser.asNativeList(payload)
            if (list != null) {
                feeTiersProcessor.received(list)
            } else {
                null
            }
        }
    }

    internal fun receivedFeeDiscounts(
        existing: Map<String, Any>?,
        payload: List<Any>
    ): Map<String, Any>? {
        return receivedObject(existing, "feeDiscounts", payload) { existing, payload ->
            val list = parser.asNativeList(payload)
            if (list != null) {
                feeDiscountsProcessor.received(list)
            } else {
                null
            }
        }
    }

    internal fun receivedNetworkConfigs(
        existing: Map<String, Any>?,
        payload: Map<String, Any>
    ): Map<String, Any>? {
        return receivedObject(existing, "network", payload) { existing, payload ->
            val map = parser.asNativeMap(payload)
            if (map != null) {
                networkConfigsProcessor.received(parser.asNativeMap(existing), map)
            } else {
                null
            }
        }
    }
}