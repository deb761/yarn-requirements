package com.inqint.yarnrequirements.Projects;

/**
 * Created by deb on 4/25/16.
 */
public class Sweater extends Project {

    // Finished size around the chest
    private double size = 40.0;
    // Units for chest size
    private ShortLengthUnits sizeUnits = ShortLengthUnits.inches;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public ShortLengthUnits getSizeUnits() {
        return sizeUnits;
    }

    public void setChestUnits(ShortLengthUnits sizeUnits) {
        this.sizeUnits = sizeUnits;
    }

    public Sweater(String name, int thumbImageID, Class<?> aClass) {
        super(name, thumbImageID, aClass);
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
