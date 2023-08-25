package exchange.dydx.abacus.processor.configs

import exchange.dydx.abacus.processor.base.BaseProcessor
import exchange.dydx.abacus.protocols.ParserProtocol
import exchange.dydx.abacus.utils.IList
import exchange.dydx.abacus.utils.IMap
import exchange.dydx.abacus.utils.IMutableList
import exchange.dydx.abacus.utils.iMutableMapOf
import kollections.iMutableListOf

@Suppress("UNCHECKED_CAST")
internal class EquityTiersProcessor(parser: ParserProtocol): BaseProcessor(parser) {
    private val itemProcessor = EquityTierProcessor(parser = parser)

    internal fun received(
        payload: IMap<String, IMap<String, IList<Any>>>?
    ): IMap<String, Any>? {
        if (payload == null) return null
        val equityTiers = parser.asMap(payload["equityTiers"])
        val modified = iMutableMapOf<String, IMutableList<Any>>(
            "shortTermOrderEquityTiers" to iMutableListOf(),
            "statefulOrderEquityTiers" to iMutableListOf()
        )

        parser.asList(equityTiers?.get("shortTermOrderEquityTiers"))?.let { shortTermOrderEquityTiers ->
            for (item in shortTermOrderEquityTiers) {
                parser.asMap(item)?.let { it ->
                    itemProcessor.received(null, it)?.let { received ->
                        modified["shortTermOrderEquityTiers"]?.add(received)
                    }
                }
            }
        }

        parser.asList(equityTiers?.get("statefulOrderEquityTiers"))?.let { statefulOrderEquityTiers ->
            for (item in statefulOrderEquityTiers) {
                parser.asMap(item)?.let { it ->
                    itemProcessor.received(null, it)?.let { received ->
                        modified["statefulOrderEquityTiers"]?.add(received)
                    }
                }
            }
        }

        return modified
    }
}