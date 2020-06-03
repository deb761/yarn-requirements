package com.inqint.yarnrequirements.Projects

/**
 * Created by deb on 4/27/16.
 */
class Gloves(name: String, nameID: Int, thumbImageID: Int, imageID: Int) :
    Mittens(name, nameID, thumbImageID, imageID) {

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
