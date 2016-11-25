package com.inqint.yarnrequirements.Projects;

import com.inqint.yarnrequirements.ProjectFragment;

/**
 * Created by deb on 11/25/16.
 */

public abstract class SizeProject extends Project {
    protected double size;

    protected ShortLengthUnits sizeUnits;

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

    public SizeProject(String name, int thumbImageID, Class<ProjectFragment> fragment)
    {
        super(name, thumbImageID, fragment);
    }
}

