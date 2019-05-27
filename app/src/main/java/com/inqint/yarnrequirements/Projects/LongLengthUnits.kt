package com.inqint.yarnrequirements.Projects
import java.io.Serializable

/* Enumeration for longer lengths: yards or meters */
enum class LongLengthUnits: Serializable {
    yards, meters;


    companion object {
        private var values: Array<LongLengthUnits>? = null
        fun fromInt(i: Int): LongLengthUnits {
            if (LongLengthUnits.values == null) {
                LongLengthUnits.values = LongLengthUnits.values()
            }
            return LongLengthUnits.values!![i]
        }
    }
}
