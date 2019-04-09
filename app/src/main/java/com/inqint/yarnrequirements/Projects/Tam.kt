package com.inqint.yarnrequirements.Projects

import com.inqint.yarnrequirements.SizeProjectFragment
import kotlin.reflect.KClass

/**
 * Created by deb on 4/27/16.
 * Tams are also hats, so base them off of Toques.  Toques are this shape:
 *
 *   ----
 *  |    |
 *   -__-
 */
class Tam(name: String, thumbImageID: Int, imageID: Int, fragmentClass: KClass<SizeProjectFragment>) :
    SizeProject(name, thumbImageID, imageID, fragmentClass) {

    // Calculate the yarn required for a tam
    // The size is the actual head size, the band will be smaller to fit snug
    // The width is bigger than the head to form a disk
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
