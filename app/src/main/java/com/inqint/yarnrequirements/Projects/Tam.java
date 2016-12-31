package com.inqint.yarnrequirements.Projects;

/**
 * Created by deb on 4/27/16.
 */
public class Tam extends SizeProject {
    public Tam(String name, int thumbImageID, int imageID, Class<?> fragmentClass) {
        super(name, thumbImageID, imageID, fragmentClass);
    }

    @Override
    public void calcYarnRequired() {
        final double tightness = 0.8;
        final double lengthCylF = 0.15;
        final double coneheightF = 0.5;
        double width = size;
        if (sizeUnits != ShortLengthUnits.cm)
        {
            width *= Project.inches2cm;
        }
        final double lengthCyl = width * lengthCylF;
        // apply tightness factor
        width *= tightness;
        final double rad = width / Math.PI / 2.0;
        final double coneHeight = coneheightF * width;
        // calculate cone area
        final double coneArea = Math.PI * rad * (rad + Math.sqrt(coneHeight * coneHeight + rad * rad));
        // calculate cylinder area
        final double cylinderArea = lengthCyl * width;
        // calculate relative length
        final double length = (coneArea + cylinderArea) / width;
        // use this relative length to calculate yarn
        calcYarnRequired(length, width);
    }
}
