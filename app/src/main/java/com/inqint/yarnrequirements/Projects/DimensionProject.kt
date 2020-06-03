package com.inqint.yarnrequirements.Projects

import android.content.SharedPreferences
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by deb on 11/25/16.
 */

abstract class DimensionProject(name: String, nameID: Int, thumbImageID: Int, imageID: Int) :
    Project(name, nameID, thumbImageID, imageID) {

    var length: Double = 0.toDouble()
    var width: Double = 0.toDouble()
    public lateinit var lengthUnits: ShortLengthUnits

    public lateinit var widthUnits: ShortLengthUnits


    /* Get the settings for the project from SharedPreferences if available,
     * otherwise, get them from the json file asset.
     */
    override fun getSettings(preferences: SharedPreferences, json: JSONObject) {
        super.getSettings(preferences, json)

        var defLength = 0f
        try {
            defLength = json.getDouble("length").toFloat()
        } catch (e: JSONException) {
        }

        length = preferences.getFloat("length", defLength).toDouble()

        var defLengthUnits = 0
        try {
            defLengthUnits = json.getInt("lengthUnits")
        } catch (e: JSONException) {
        }

        lengthUnits = ShortLengthUnits.fromInt(preferences.getInt("lengthUnits", defLengthUnits))

        var defWidth = 0f
        try {
            defWidth = json.getDouble("width").toFloat()
        } catch (e: JSONException) {
        }

        width = preferences.getFloat("width", defWidth).toDouble()

        var defWidthUnits = 0
        try {
            defWidthUnits = json.getInt("widthUnits")
        } catch (e: JSONException) {
        }

        widthUnits = ShortLengthUnits.fromInt(preferences.getInt("widthUnits", defWidthUnits))
    }
}
