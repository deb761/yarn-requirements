package com.inqint.yarnrequirements.Projects

import com.inqint.yarnrequirements.DimensionProjectFragment
import kotlin.reflect.KClass

/**
 * Created by deb on 4/27/16.
 */
open class Scarf(name: String, thumbImageID: Int, imageID: Int) :
    DimensionProject(name, thumbImageID, imageID) {

    /* Make sure units are in SI units, then calculate based on the length & width */
    override fun calcYarnRequired() {
        var siLength = length
        if (lengthUnits !== ShortLengthUnits.cm) {
            siLength = length * inches2cm
        }
        var siWidth = width
        if (widthUnits !== ShortLengthUnits.cm) {
            siWidth = width * inches2cm
        }
        super.calcYarnRequired(siLength, siWidth)
    }

}
