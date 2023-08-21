package exchange.dydx.abacus.utils

import exchange.dydx.abacus.processor.base.ComparisonOrder
import exchange.dydx.abacus.protocols.ParserProtocol
import kollections.iMutableListOf
import kollections.iMutableMapOf
import kollections.toIList
import kollections.toIMap
import kotlinx.datetime.Instant

internal typealias ObjectKeyBlock = (Any) -> Any?
internal typealias ObjectComparisonBlock = (Any, Any) -> ComparisonOrder
internal typealias ObjectAndDataChangedBlock = (Any, IMap<*, *>) -> Boolean
internal typealias ObjectAndDataComparisonBlock = (Any, IMap<*, *>) -> ComparisonOrder?
internal typealias ObjectCreationBlock = (parser: ParserProtocol, IMap<*, *>) -> Any?
internal typealias IncludesObjectBlock = (Any) -> Boolean
internal typealias IncludesDataBlock = (IMap<*, *>) -> Boolean

@Suppress("UNCHECKED_CAST")
internal class ParsingHelper {
    companion object {
        private var parser = Parser()

        internal fun asset(market: String?): String? {
            return if (market != null) {
                val split = market.split("-")
                split.first()
            } else {
                null
            }
        }

        internal inline fun <reified T : Any> transform(
            parser: ParserProtocol,
            existing: IList<T>?,
            data: IMap<*, *>?,
            key: ObjectKeyBlock,
            changed: ObjectAndDataChangedBlock,
            crossinline comparison: ObjectComparisonBlock,
            createObject: ObjectCreationBlock,
        ): IList<T>? {
            if (data != null) {
                val existingMap = iMutableMapOf<String, T>()
                existing?.let {
                    for (obj in it) {
                        key(obj)?.let {
                            (it as? String)?.let {
                                existingMap[it] = obj
                            }
                        }
                    }
                }
                val merged = iMutableMapOf<String, T>()
                for ((key, itemData) in data) {
                    parser.asMap(itemData)?.let { itemData ->
                        (key as? String)?.let {
                            val obj = existingMap[key]
                            if (obj != null && !changed(obj, itemData)) {
                                merged[key] = obj
                            } else {
                                createObject(parser, itemData)?.let {
                                    merged[key] = it as T
                                }
                            }
                        }
                    }
                }
                return merged.values().sortedWith { obj1, obj2 ->
                    when (comparison(obj1, obj2)) {
                        ComparisonOrder.ascending -> -1
                        ComparisonOrder.descending -> 1
                        ComparisonOrder.same -> 0
                    }
                }.toIList()
            } else {
                return existing
            }
        }

        internal fun <T : Any> merge(
            parser: ParserProtocol,
            existing: IList<T>?,
            data: IList<*>?,
            comparison: ObjectAndDataComparisonBlock,
            createObject: ObjectCreationBlock,
            syncItems: Boolean = false,
            includesObjectBlock: IncludesObjectBlock? = null,
            includesDataBlock: IncludesDataBlock? = null,
        ): IList<T>? {
            if (data != null) {
                val result = iMutableListOf<T>()

                val size1 = existing?.size ?: 0
                val size2 = data.size
                var cursor1 = 0
                var cursor2 = 0

                if (existing != null) {
                    while (cursor1 < size1 && cursor2 < size2) {
                        val existingEntry = existing[cursor1]
                        val itemData = parser.asMap(data[cursor2])
                        if (itemData != null) {
                            when (comparison(existingEntry, itemData)) {
                                ComparisonOrder.same -> {
                                    result.add(existingEntry)
                                    cursor1 += 1
                                    cursor2 += 1
                                }

                                ComparisonOrder.ascending -> {
                                    if (!syncItems) {
                                        result.add(existingEntry)
                                    }
                                    cursor1 += 1
                                }

                                ComparisonOrder.descending -> {
                                    (createObject(parser, itemData) as? T)?.let {
                                        result.add(it)
                                    }
                                    cursor2 += 1
                                }

                                null -> {
                                    // Do not include this item
                                    cursor2 += 1
                                }
                            }
                        } else {
                            cursor2 += 1
                        }
                    }
                }

                if (cursor1 >= size1) {
                    // list1 finished
                    for (i in cursor2 until size2) {
                        data.get(i).let { itemData ->
                            parser.asMap(itemData)?.let { itemData ->
                                if (includesDataBlock == null || includesDataBlock.invoke(itemData)) {
                                    (createObject(parser, itemData) as? T)?.let {
                                        result.add(it)
                                    }
                                }
                            }
                        }
                    }
                }
                if (cursor2 >= size2 && !syncItems) {
                    for (i in cursor1 until size1) {
                        existing?.get(i)?.let {
                            if (includesObjectBlock == null || includesObjectBlock.invoke(it)) {
                                result.add(it)
                            }
                        }
                    }
                }

                return result
            } else {
                return null
            }
        }


        internal fun compare(value1: Double, value2: Double, ascending: Boolean): ComparisonOrder {
            return if (value1 == value2) {
                ComparisonOrder.same
            } else {
                if (ascending) {
                    if (value2 > value1) ComparisonOrder.ascending else ComparisonOrder.descending
                } else {
                    if (value2 > value1) ComparisonOrder.descending else ComparisonOrder.ascending
                }
            }
        }

        internal fun compare(value1: Int, value2: Int, ascending: Boolean): ComparisonOrder {
            return if (value1 == value2) {
                ComparisonOrder.same
            } else {
                if (ascending) {
                    if (value2 > value1) ComparisonOrder.ascending else ComparisonOrder.descending
                } else {
                    if (value2 > value1) ComparisonOrder.descending else ComparisonOrder.ascending
                }
            }
        }

        internal fun compare(
            value1: String?,
            value2: String?,
            ascending: Boolean,
        ): ComparisonOrder {
            return if (value1 == value2) {
                ComparisonOrder.same
            } else {
                if (ascending) {
                    if ((value2 ?: "") > (value1
                            ?: "")
                    ) ComparisonOrder.ascending else ComparisonOrder.descending
                } else {
                    if ((value2 ?: "") > (value1
                            ?: "")
                    ) ComparisonOrder.descending else ComparisonOrder.ascending
                }
            }
        }

        internal fun compare(
            value1: Instant?,
            value2: Instant?,
            ascending: Boolean,
        ): ComparisonOrder {
            if (value1 != null) {
                return if (value2 != null) {
                    if (value1 == value2) {
                        ComparisonOrder.same
                    } else {
                        if (ascending) {
                            if (value2 > value1) ComparisonOrder.ascending else ComparisonOrder.descending
                        } else {
                            if (value2 > value1) ComparisonOrder.descending else ComparisonOrder.ascending
                        }
                    }
                } else {
                    if (ascending) ComparisonOrder.descending else ComparisonOrder.ascending
                }
            } else {
                return if (value2 != null) {
                    if (ascending) ComparisonOrder.ascending else ComparisonOrder.descending
                } else {
                    ComparisonOrder.same
                }
            }
        }

        internal fun merge(
            existing: IMap<String, Any>?,
            incoming: IMap<String, Any>?,
        ): IMap<String, Any>? {
            return mergeMap(existing?.toMap(), incoming?.toMap())?.toIMap()
        }

        private fun mergeMap(
            existing: Map<String, Any>?,
            incoming: Map<String, Any>?,
        ): Map<String, Any>? {
            if (incoming != null) {
                val result = existing?.toMutableMap() ?: mutableMapOf()
                for ((key, value) in incoming) {
                    val newValue = parser.asNativeMap(value)
                    if (newValue != null) {
                        val existingValue = parser.asNativeMap(existing?.get(key))
                        if (existingValue != null) {
                            val mergedValue = mergeMap(
                                existingValue,
                                newValue
                            )
                            if (mergedValue != null) {
                                result[key] = mergedValue
                            }
                        } else {
                            result[key] = newValue
                        }
                    } else {
                        result[key] = value
                    }
                }
                return result
            } else {
                return existing
            }
        }
    }
}
