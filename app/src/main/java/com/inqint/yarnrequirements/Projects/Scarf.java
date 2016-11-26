package com.inqint.yarnrequirements.Projects;

/**
 * Created by deb on 4/27/16.
 */
public class Scarf extends DimensionProject {


    public Scarf(String name, int thumbImageID, int imageID, Class<?> fragmentClass) {
        super(name, thumbImageID, imageID, fragmentClass);
    }

    /* Make sure units are in SI units, then calculate based on the length & width */
    @Override
    public void calcYarnRequired()
    {
        double siLength = length;
        if (getLengthUnits() != ShortLengthUnits.cm)
        {
            siLength = length * inches2cm;
        }
        double siWidth = width;
        if (getWidthUnits() != ShortLengthUnits.cm)
        {
            siWidth = width * inches2cm;
        }
        super.calcYarnRequired(siLength, siWidth);
    }

}
