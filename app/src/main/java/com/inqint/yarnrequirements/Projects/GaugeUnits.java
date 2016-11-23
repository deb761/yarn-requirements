package com.inqint.yarnrequirements.Projects;

/**
 * Created by deb on 4/25/16.
 */
/* Enumeration for the gauge units: stitches per inch, 4 inches, or 10 cm */
public enum GaugeUnits { stsPerInch, stsPer4inch, stsPer10cm;
    private static GaugeUnits[] values = null;
    public static GaugeUnits fromInt(int i) {
        if (GaugeUnits.values == null) {
            GaugeUnits.values = GaugeUnits.values();
        }
        return GaugeUnits.values[i];
    }
}
