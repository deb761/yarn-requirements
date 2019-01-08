package com.inqint.yarnrequirements.Projects

/**
 * Created by deb on 4/27/16.
 */
open class Mittens(name: String, thumbImageID: Int, imageID: Int, fragmentClass: Class<*>) :
    SizeProject(name, thumbImageID, imageID, fragmentClass) {
    // Estimate the hand size and calculate yarn for two mittens
    override fun calcYarnRequired() {
        var width = size
        if (sizeUnits !== ShortLengthUnits.cm) {
            width *= Project.inches2cm
        }
        val length = width * 1.33
        width *= 2.0
        calcYarnRequired(length, width)
    }
}
