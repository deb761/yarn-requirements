package com.inqint.yarnrequirements.Projects
import java.io.Serializable


/* Enumeration for shorter units: inches or cm */
enum class ShortLengthUnits : Serializable {
    inches, cm;


    companion object {
        private var values: Array<ShortLengthUnits>? = null
        fun fromInt(i: Int): ShortLengthUnits {
            if (ShortLengthUnits.values == null) {
                ShortLengthUnits.values = ShortLengthUnits.values()
            }
            return ShortLengthUnits.values!![i]
        }
    }
}
