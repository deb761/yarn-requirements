package com.inqint.yarnrequirements.Projects;

import com.inqint.yarnrequirements.ProjectFragment;

/**
 * Created by deb on 4/27/16.
 */
public class Mittens extends Project {

    private double size;

    private ShortLengthUnits sizeUnits;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public ShortLengthUnits getSizeUnits() {
        return sizeUnits;
    }

    public void setSizeUnits(ShortLengthUnits sizeUnits) {
        this.sizeUnits = sizeUnits;
    }

    public Mittens(String name, int thumbImageID, Class<ProjectFragment> fragmentClass) {
        super(name, thumbImageID, fragmentClass);
    }
    // Estimate the hand size and calculate yarn for two mittens
    @Override
    public void calcYarnRequired() {
        double width = size;
        if (sizeUnits != ShortLengthUnits.cm)
        {
            width *= Project.inches2cm;
        }
        double length = width * 1.33;
        width *= 2;
        calcYarnRequired(length, width);
    }
}
