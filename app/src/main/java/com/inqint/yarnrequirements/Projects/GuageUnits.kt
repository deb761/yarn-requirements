package com.inqint.yarnrequirements.Projects

/* Enumeration for the gauge units: stitches per inch, 4 inches, or 10 cm */
enum class GaugeUnits {
    stsPerInch, stsPer4inch, stsPer10cm;


    companion object {
        private var values: Array<GaugeUnits>? = null
        fun fromInt(i: Int): GaugeUnits {
            if (GaugeUnits.values == null) {
                GaugeUnits.values = GaugeUnits.values()
            }
            return GaugeUnits.values!![i]
        }
    }
}
