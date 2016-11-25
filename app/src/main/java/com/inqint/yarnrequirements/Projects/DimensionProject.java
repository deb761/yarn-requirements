package com.inqint.yarnrequirements.Projects;

import com.inqint.yarnrequirements.ProjectFragment;

/**
 * Created by deb on 11/25/16.
 */

public abstract class DimensionProject extends Project {

    protected double length;
    protected double width;
    protected ShortLengthUnits lengthUnits;

    protected ShortLengthUnits widthUnits;

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public ShortLengthUnits getLengthUnits() {
        return lengthUnits;
    }

    public void setLengthUnits(ShortLengthUnits units) {
        this.lengthUnits = units;
    }

    public ShortLengthUnits getWidthUnits() {
        return widthUnits;
    }

    public void setWidthUnits(ShortLengthUnits widthUnits) {
        this.widthUnits = widthUnits;
    }

    public DimensionProject(String name, int thumbImageID, Class<ProjectFragment> fragmentClass) {
        super(name, thumbImageID, fragmentClass);
    }
}
