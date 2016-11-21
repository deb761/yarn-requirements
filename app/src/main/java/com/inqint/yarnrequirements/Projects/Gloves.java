package com.inqint.yarnrequirements.Projects;

/**
 * Created by deb on 4/27/16.
 */
public class Gloves extends Mittens {
    public Gloves(String name, int thumbImageID, Class<?> aClass) {
        super(name, thumbImageID, aClass);
    }

    @Override
    public void calcYarnRequired() {
        double width = getSize() * 1.05; // add extra to go around fingers
        if (getSizeUnits() != ShortLengthUnits.cm)
        {
            width *= Project.inches2cm;
        }
        double length = width * 1.2; // gloves fit more snuggly than mittens
        width *= 2;
        calcYarnRequired(length, width);
    }
}
