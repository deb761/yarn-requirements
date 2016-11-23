package com.inqint.yarnrequirements.Projects;

/* Enumeration for longer lengths: yards or meters */
public enum LongLengthUnits { yards, meters;
    private static LongLengthUnits[] values = null;
    public static LongLengthUnits fromInt(int i) {
        if (LongLengthUnits.values == null) {
            LongLengthUnits.values = LongLengthUnits.values();
        }
        return LongLengthUnits.values[i];
    }
}
