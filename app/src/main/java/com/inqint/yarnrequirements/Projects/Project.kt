package com.inqint.yarnrequirements.Projects

import android.content.SharedPreferences
import com.inqint.yarnrequirements.ProjectFragment
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable
import kotlin.math.sqrt
import kotlin.reflect.KClass


/* The basic definition of a knitting project.  This class is extended for each project type. */
abstract class Project(var name: String, var nameID: Int, var thumbImageID: Int, var imageID: Int) : Serializable {
    var gauge: Double = 0.toDouble()
    var gaugeUnits: GaugeUnits
    lateinit var fragment: KClass<ProjectFragment>
        protected set
    var yarnNeeded: Int = 0
        protected set
    var yarnNeededUnits: LongLengthUnits
    var ballSize: Int = 0
    var ballSizeUnits: LongLengthUnits
    var ballsNeeded: Double = 0.toDouble()
        protected set

    var isPartialBalls: Boolean = false

    init {
        gauge = 20.0
        gaugeUnits = GaugeUnits.stsPer10cm
        yarnNeeded = 0
        yarnNeededUnits = LongLengthUnits.meters
        ballSize = 150
        ballSizeUnits = LongLengthUnits.meters
        ballsNeeded = 0.0
    }

    abstract fun calcYarnRequired()

    // Calculations derived by The Inquisitive Introvert
    fun calcYarnRequired(siLength: Double, siWidth: Double) {
        if (gauge <= 0) {
            yarnNeeded = 0
            ballsNeeded = 0.0
            return
        }
        var siGauge = gauge

        // First, put values into SI units
        if (gaugeUnits === GaugeUnits.stsPerInch) {
            siGauge *= 4.0
        }

        // Change to stitches per cm
        siGauge /= 10.0

        var siBallSize = ballSize.toDouble()
        if (ballSizeUnits === LongLengthUnits.yards) {
            siBallSize *= Project.yards2meters
        }
        val stitches = Math.ceil(siGauge * siWidth).toInt()
        val rowGauge = siGauge * 1.5
        val rows = Math.ceil(rowGauge * siLength).toInt()

        val totalStitches = stitches * (rows + 2) // 2 for cast on and bind off.

        // calculate meters and add 20%
        val meters = getStitchLength(siGauge) * totalStitches.toDouble() * 1.2

        // Now convert the yarn required into the desired units
        if (yarnNeededUnits !== LongLengthUnits.meters) {
            yarnNeeded = Math.ceil(meters / Project.yards2meters).toInt()
        } else {
            yarnNeeded = Math.ceil(meters).toInt()
        }

        // Now convert the yarn required into the desired units
        if (yarnNeededUnits !== LongLengthUnits.meters) {
            yarnNeeded = Math.ceil(meters / yards2meters).toInt()
        } else {
            yarnNeeded = Math.ceil(meters).toInt()
        }

        ballsNeeded = meters / siBallSize
        if (!isPartialBalls) {
            ballsNeeded = Math.ceil(ballsNeeded)
        }
    }

    // Compute the length of a stitch in m, treating the row of stitches as a helix
    private fun getStitchLength(cmGauge: Double): Double {
        val stitchWidth = 1.0 / cmGauge
        val stitchCir = Math.PI * stitchWidth
        // The stitch actually goes halfway into the neighboring stitch on each side
        val span = 2.0 * stitchWidth
        // use equation to calculate helical length, where the diameter is the stitchWidth and the
        // length is twice the stitchWidth, and convert to meters
        return sqrt(span * span + stitchCir * stitchCir) / 100.0
    }

    // Calculate the number of balls needed, taking into account the selected units
    fun calcBallsNeeded() {
        if (yarnNeededUnits === ballSizeUnits) {
            ballsNeeded = yarnNeeded.toDouble() / ballSize.toDouble()
        } else {
            val yarn = if (yarnNeededUnits === LongLengthUnits.meters)
                yarnNeeded.toDouble()
            else
                yarnNeeded.toDouble() * yards2meters
            val ballMeters = if (ballSizeUnits === LongLengthUnits.meters)
                ballSize.toDouble()
            else
                ballSize.toDouble() * yards2meters
            ballsNeeded = yarn / ballMeters
        }
        if (!isPartialBalls) {
            ballsNeeded = Math.ceil(ballsNeeded)
        }
    }

    /* Get the settings for the project from SharedPreferences if available,
     * otherwise, get them from the json file asset.
     */
    open fun getSettings(preferences: SharedPreferences, json: JSONObject) {
        var defGauge = 20f
        try {
            defGauge = json.getDouble("gauge").toFloat()
        } catch (e: JSONException) {
        }

        gauge = preferences.getFloat("gauge", defGauge).toDouble()

        var defGaugeUnits = 2
        try {
            defGaugeUnits = json.getInt("gaugeUnits")
        } catch (e: JSONException) {
        }

        gaugeUnits = GaugeUnits.fromInt(preferences.getInt("gaugeUnits", defGaugeUnits))

        var defYarnNeededUnits = 0
        try {
            defYarnNeededUnits = json.getInt("yarnNeededUnits")
        } catch (e: JSONException) {
        }

        yarnNeededUnits = LongLengthUnits.fromInt(preferences.getInt("yarnNeededUnits", defYarnNeededUnits))

        var defBallSize = 100
        try {
            defBallSize = json.getInt("ballSize")
        } catch (e: JSONException) {
        }

        ballSize = preferences.getInt("ballSize", defBallSize)

        var defBallSizeUnits = 0
        try {
            defBallSizeUnits = json.getInt("ballSizeUnits")
        } catch (e: JSONException) {
        }

        ballSizeUnits = LongLengthUnits.fromInt(preferences.getInt("ballSizeUnits", defBallSizeUnits))

        var defPartialBalls = false
        try {
            defPartialBalls = json.getBoolean("partialBalls")
        } catch (e: JSONException) {
        }

        isPartialBalls = preferences.getBoolean("isPartialBalls", defPartialBalls)
    }

    companion object {

        public var inches2cm = 2.54
        public var yards2meters = 0.9144
    }
}