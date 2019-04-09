package com.inqint.yarnrequirements.Projects

import android.content.SharedPreferences
import com.inqint.yarnrequirements.SockProjectFragment
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.reflect.KClass

/**
 * Created by deb on 4/27/16.
 * This class calculates the yarn needed for socks of US and Euro shoe sizes.
 */
class Socks(name: String, thumbImageID: Int, imageID: Int, fragmentClass: KClass<SockProjectFragment>) :
    Project(name, thumbImageID, imageID, fragmentClass) {
    // The shoe size
    var size: Double = 0.toDouble()
    // Type of shoe size
    lateinit var sizeUnits: ShoeSizeUnits

    private var usChild: HashMap<Int, Double>? = null
    private var usYouth: HashMap<Int, Double>? = null
    private var usMen: HashMap<Int, Double>? = null
    private var usWomen: HashMap<Int, Double>? = null
    private var eu: HashMap<Int, Double>? = null

    // Define an enumeration for all the shoe size units out there
    enum class ShoeSizeUnits {
        child, youth, women, men, euro;


        companion object {
            private var values: Array<ShoeSizeUnits>? = null
            fun fromInt(i: Int): ShoeSizeUnits {
                if (ShoeSizeUnits.values == null) {
                    ShoeSizeUnits.values = ShoeSizeUnits.values()
                }
                return ShoeSizeUnits.values!![i]
            }
        }
    }

    override fun calcYarnRequired() {
        var length = 0.0
        when (sizeUnits) {
            Socks.ShoeSizeUnits.child -> length = getLength(usChild!!, size)
            Socks.ShoeSizeUnits.youth -> length = getLength(usYouth!!, size)
            Socks.ShoeSizeUnits.women -> length = getLength(usWomen!!, size)
            Socks.ShoeSizeUnits.men -> length = getLength(usMen!!, size)
            Socks.ShoeSizeUnits.euro -> length = getLength(eu!!, size)
        }
        // I calculated a least-squares fit of foot length to circumference,
        // with a decent result for most sizes, although the really big feet
        // are underestimated a bit
        val intercept = 4.8250
        val slope = 0.6263
        val width = intercept + length * slope

        // We'll approximate the length of the sock as 1.75 times the length of the foot,
        // and add 0.5 * width for the the heel turn
        // and there are 2 feet, so multiply width by 2
        calcYarnRequired(length * 1.75 - 0.5 * width, width * 2)
    }

    /* Get the settings for the project from SharedPreferences if available,
     * otherwise, get them from the json file asset.
     */
    override fun getSettings(preferences: SharedPreferences, json: JSONObject) {
        super.getSettings(preferences, json)

        var defSize = 8f
        try {
            defSize = json.getDouble("size").toFloat()
        } catch (e: JSONException) {
        }

        size = preferences.getFloat("size", defSize).toDouble()

        var defSizeUnits = ShoeSizeUnits.euro.ordinal
        try {
            defSizeUnits = json.getInt("sizeUnits")
        } catch (e: JSONException) {
        }

        sizeUnits = ShoeSizeUnits.fromInt(preferences.getInt("sizeUnits", defSizeUnits))
    }

    // Get the foot size tables from the json file asset
    fun getFootSizes(json: JSONObject) {
        try {
            usChild = getSizes(json.getJSONArray("usChild"))
            usYouth = getSizes(json.getJSONArray("usYouth"))
            usMen = getSizes(json.getJSONArray("usMen"))
            usWomen = getSizes(json.getJSONArray("usWomen"))
            eu = getSizes(json.getJSONArray("eu"))
        } catch (e: JSONException) {
        }

    }

    private fun getSizes(jsonArray: JSONArray): HashMap<Int, Double> {
        val sizes = HashMap<Int, Double>()

        for (idx in 0 until jsonArray.length()) {
            try {
                val `object` = jsonArray.getJSONObject(idx)

                val size = `object`.getInt("size")
                val length = `object`.getDouble("length")

                sizes[size] = length

            } catch (e: JSONException) {
                continue
            }

        }
        return sizes
    }

    // Perform a linear interpolation between the input size and the next
    // highest to get foot length in cm
    private fun getLength(table: HashMap<Int, Double>, shoeSize: Double): Double {
        run {
            val lowSized = Math.floor(shoeSize)
            val lowSize = lowSized.toInt()

            val lowLength = table[lowSize]
            if (lowSized == shoeSize && lowLength != null) {
                return lowLength
            }
            var highSize = lowSize + 1
            // Check for the US youth size rollover from 13 to 1
            if (sizeUnits == ShoeSizeUnits.youth && lowSize == 13) {
                highSize = 1
            }
            val highLength = table[highSize]
            if (lowLength != null && highLength != null) {
                // This is a simple linear interpolation that is simplified a bit
                // because the difference in sizes is always 1
                return (shoeSize - lowSized) * (highLength - lowLength) + lowLength
            }
        }
        // We only get to this point if we couldn't find a size, so return default
        return 24.0 // about a US Women's size 8
    }


}
