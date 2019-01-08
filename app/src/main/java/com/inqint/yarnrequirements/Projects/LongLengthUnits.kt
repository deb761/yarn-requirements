package com.inqint.yarnrequirements.Projects

/* Enumeration for longer lengths: yards or meters */
enum class LongLengthUnits {
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
