package com.inqint.yarnrequirements.Projects

import android.content.SharedPreferences
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by deb on 11/25/16.
 *
 * Handle the basics for all the projects that take just a size to estimate yarn
 */

abstract class SizeProject(name: String, nameID: Int, thumbImageID: Int, imageID: Int) :
    Project(name, nameID, thumbImageID, imageID) {
    var size: Double = 0.toDouble()

    var sizeUnits: ShortLengthUnits = ShortLengthUnits.cm

    /* Get the settings for the project from SharedPreferences if available,
     * otherwise, get them from the json file asset.
     */
    override fun getSettings(preferences: SharedPreferences, json: JSONObject) {
        super.getSettings(preferences, json)

        var defSize = 0f
        try {
            defSize = json.getDouble("size").toFloat()
        } catch (e: JSONException) {
        }

        size = preferences.getFloat("size", defSize).toDouble()

        var defSizeUnits = 2
        try {
            defSizeUnits = json.getInt("sizeUnits")
        } catch (e: JSONException) {
        }

        sizeUnits = ShortLengthUnits.fromInt(preferences.getInt("sizeUnits", defSizeUnits))
    }
}

