package com.inqint.yarnrequirements.Projects

/**
 * Created by deb on 4/27/16.
 *  For now, treat hats as a piece of fabric about like this:
     /\
    |  |
 */
open class Toque(name: String, thumbImageID: Int, imageID: Int, fragmentClass: Class<*>) :
    SizeProject(name, thumbImageID, imageID, fragmentClass) {

    // Calculate the yarn required for a toque, where the total length is
    // 0.5 * head circumference and a cone on the top for the cap that
    // is 0.1 * head circumference.  Hat circumference is 0.93 of head circumference
    //
    // A=πr(r+sqrt(h^2+r^2))
    override fun calcYarnRequired() {
        val tightness = 0.8
        val lengthCylF = 0.15
        val coneheightF = 0.5
        var width = size
        if (sizeUnits != ShortLengthUnits.cm)
        {
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
