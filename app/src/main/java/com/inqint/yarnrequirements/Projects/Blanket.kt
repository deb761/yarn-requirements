package com.inqint.yarnrequirements.Projects

import com.inqint.yarnrequirements.DimensionProjectFragment
import kotlin.reflect.KClass

/**
 * Created by deb on 4/27/16.
 */
class Blanket(name: String, thumbImageID: Int, imageID: Int, fragmentClass: KClass<DimensionProjectFragment>) :
    Scarf(name, thumbImageID, imageID, fragmentClass) {

    override fun calcYarnRequired() {
        super.calcYarnRequired()

        yarnNeeded = (1.2 * yarnNeeded.toDouble()).toInt() // Add 20% to estimate
        calcBallsNeeded()
    }
}
