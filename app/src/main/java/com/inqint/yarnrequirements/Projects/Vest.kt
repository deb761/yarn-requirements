package com.inqint.yarnrequirements.Projects

/**
 * Created by deb on 4/27/16.
 */
class Vest(name: String, thumbImageID: Int, imageID: Int) :
    Sweater(name, thumbImageID, imageID) {

    /* Results of linear fit of Length to hips by Chest size:
     (Intercept)      Chest
     Baby     -6.50000 0.60000000
     Child   -23.85854 0.78042063
     Man      47.67266 0.17445286
     Woman    52.32509 0.06000826
     Youth    14.99031 0.38372093

     Looks like the baby line will cover up to 80cm,
     Then we will jump to the Man line
    */
    private val babyInt = -6.50000
    private val babySlope = 0.60000000

    private val sBreak = 80.0 //cm

    private val manInt = 47.67266
    private val manSlope = 0.17445286

    override fun calcYarnRequired() {
        var width = size
        if (sizeUnits !== ShortLengthUnits.cm) {
            width *= Project.inches2cm
        }
        val length: Double
        if (width < 80.0) {
            length = babyInt + babySlope * width
        } else {
            length = manInt + manSlope * width
        }
        calcYarnRequired(length, width)
    }
}
