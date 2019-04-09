package com.inqint.yarnrequirements.Projects

import com.inqint.yarnrequirements.SizeProjectFragment
import kotlin.reflect.KClass

/**
 * Created by deb on 4/27/16.
 */
class Gloves(name: String, thumbImageID: Int, imageID: Int, fragmentClass: KClass<SizeProjectFragment>) :
    Mittens(name, thumbImageID, imageID, fragmentClass) {

    override fun calcYarnRequired() {
        var width = size * 1.05 // add extra to go around fingers
        if (sizeUnits !== ShortLengthUnits.cm) {
            width *= Project.inches2cm
        }
        val length = width * 1.2 // gloves fit more snuggly than mittens
        width *= 2.0
        calcYarnRequired(length, width)
    }
}
