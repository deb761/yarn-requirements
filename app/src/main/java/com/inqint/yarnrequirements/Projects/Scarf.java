package com.inqint.yarnrequirements.Projects;

import com.inqint.yarnrequirements.ProjectFragment;

/**
 * Created by deb on 4/27/16.
 */
public class Scarf extends DimensionProject {


    public Scarf(String name, int thumbImageID, Class<ProjectFragment> fragmentClass) {
        super(name, thumbImageID, fragmentClass);
    }

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
