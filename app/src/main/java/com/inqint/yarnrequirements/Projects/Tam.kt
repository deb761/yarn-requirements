package com.inqint.yarnrequirements.Projects

/**
 * Created by deb on 4/27/16.
 */
class Tam(name: String, thumbImageID: Int, imageID: Int, fragmentClass: Class<*>) :
    SizeProject(name, thumbImageID, imageID, fragmentClass) {

    override fun calcYarnRequired() {
        val tightness = 0.8
        val lengthCylF = 0.15
        val coneheightF = 0.5
        var width = size
        if (sizeUnits !== ShortLengthUnits.cm) {
            width *= Project.inches2cm
        }
        val lengthCyl = width * lengthCylF
        // apply tightness factor
        width *= tightness
        val rad = width / Math.PI / 2.0
        val coneHeight = coneheightF * width
        // calculate cone area
        val coneArea = Math.PI * rad * (rad + Math.sqrt(coneHeight * coneHeight + rad * rad))
        // calculate cylinder area
        val cylinderArea = lengthCyl * width
        // calculate relative length
        val length = (coneArea + cylinderArea) / width
        // use this relative length to calculate yarn
        calcYarnRequired(length, width)
    }
}
