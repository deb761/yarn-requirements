package com.inqint.yarnrequirements.Projects

/**
 * Created by deb on 4/27/16.
 */
class Blanket(name: String, nameID: Int, thumbImageID: Int, imageID: Int) :
    Scarf(name, nameID, thumbImageID, imageID) {

    override fun calcYarnRequired() {
        super.calcYarnRequired()

        yarnNeeded = (1.2 * yarnNeeded.toDouble()).toInt() // Add 20% to estimate
        calcBallsNeeded()
    }
}
