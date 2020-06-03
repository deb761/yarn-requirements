package com.inqint.yarnrequirements.Projects

/**
 * Created by deb on 4/25/16.
 */
open class Sweater(name: String, nameID: Int, thumbImageID: Int, imageID: Int) :
    SizeProject(name, nameID, thumbImageID, imageID) {

    override fun calcYarnRequired() {
        var chest = size
        if (sizeUnits === ShortLengthUnits.inches) {
            chest *= Project.inches2cm
        }
        val intercept = -5559.8 // cm
        val slope = 162.1 // cm^2
        val area = intercept + slope * chest
        // let the sleeves be a trapezoid with max width 0.5 * width, and min width being 0.25 * width
        // let the length match the body length (which are really long sleeves), and add the average width
        // of the two sleeves to the body width
        val width = chest * 1.75
        val length = area / width

        super.calcYarnRequired(length, width)
    }
}
