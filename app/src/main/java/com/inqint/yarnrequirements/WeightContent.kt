package com.inqint.yarnrequirements

import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


/**
 * Helper class for providing sample yarn weights for user interfaces created by
 * Android template wizards.
 *
 *
 */
class WeightContent(json: String) {

    init {
        try {
            val jsonarray = JSONArray(json)

            for (idx in 0 until jsonarray.length()) {
                val item = WeightItem(jsonarray.getJSONObject(idx))
                ITEMS.add(idx, item)
                ITEM_MAP[item.name] = item
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    /**
     * An item containing information about a weight of yarn.
     */
    class WeightItem @Throws(JSONException::class)
    constructor(jsonObject: JSONObject) {
        val name: String    // yarn thickness description
        val needles: Array<KnittingNeedle?> // suggested needle sizes in US and international units
        val gauge: DoubleArray // Stitches per 4" or 10cm
        val wpi: DoubleArray // Windings per inch
        val length: Int // Density per 50 gm for wool
        val weight: Int // ball weight in grams

        init {
            this.name = jsonObject.getString("name")
            var array = jsonObject.getJSONArray("needles")
            this.needles = arrayOfNulls<KnittingNeedle>(size = array.length())
            for (idx in needles.indices) {
                needles[idx] = KnittingNeedle(array.getDouble(idx))
            }
            array = jsonObject.getJSONArray("gauge")
            this.gauge = DoubleArray(array.length())
            for (idx in gauge.indices) {
                gauge[idx] = array.getDouble(idx)
            }
            array = jsonObject.getJSONArray("windings")
            this.wpi = DoubleArray(array.length())
            for (idx in wpi.indices) {
                wpi[idx] = array.getDouble(idx)
            }

            this.length = jsonObject.getInt("length")
            this.weight = jsonObject.getInt("weight")
        }

        override fun toString(): String {
            return name
        }
    }

    companion object {

        /**
         * An array of sample (weight) items.
         */
        val ITEMS: MutableList<WeightItem> = ArrayList()

        /**
         * A map of sample (dummy) items, by ID.
         */
        val ITEM_MAP: MutableMap<String, WeightItem> = HashMap()

        private fun makeDetails(position: Int): String {
            val builder = StringBuilder()
            builder.append("Details about Item: ").append(position)
            for (i in 0 until position) {
                builder.append("\nMore gauge information here.")
            }
            return builder.toString()
        }
    }
}
