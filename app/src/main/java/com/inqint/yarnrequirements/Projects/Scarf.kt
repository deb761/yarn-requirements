package com.inqint.yarnrequirements.Projects

/**
 * Created by deb on 4/27/16.
 */
open class Scarf(name: String, nameID: Int, thumbImageID: Int, imageID: Int) :
    DimensionProject(name, nameID, thumbImageID, imageID) {

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
