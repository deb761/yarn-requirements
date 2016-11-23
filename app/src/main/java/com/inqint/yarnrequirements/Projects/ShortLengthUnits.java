package com.inqint.yarnrequirements.Projects;

/* Enumeration for shorter units: inches or cm */
public enum ShortLengthUnits { inches, cm;
    private static ShortLengthUnits[] values = null;
    public static ShortLengthUnits fromInt(int i) {
        if (ShortLengthUnits.values == null) {
            ShortLengthUnits.values = ShortLengthUnits.values();
        }
        return ShortLengthUnits.values[i];
    }
}
