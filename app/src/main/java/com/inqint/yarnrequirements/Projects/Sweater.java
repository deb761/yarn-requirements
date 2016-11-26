package com.inqint.yarnrequirements.Projects;

/**
 * Created by deb on 4/25/16.
 */
public class Sweater extends SizeProject {

    public Sweater(String name, int thumbImageID, int imageID, Class<?> fragmentClass) {
        super(name, thumbImageID, imageID, fragmentClass);
    }

    @Override
    public void calcYarnRequired()
    {
        double chest = size;
        if (sizeUnits == ShortLengthUnits.inches) {
            chest *= inches2cm;
        }
        double intercept = -5559.8; // cm
        double slope = 162.1; // cm^2
        double area = intercept + slope * chest;
        // let the sleeves be a trapezoid with max width 0.5 * width, and min width being 0.25 * width
        // let the length match the body length (which are really long sleeves), and add the average width
        // of the two sleeves to the body width
        double width = chest * 1.75;
        double length = area / width;

        super.calcYarnRequired(length, width);
    }
}
