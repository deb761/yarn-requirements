package com.inqint.yarnrequirements.Projects;

import com.inqint.yarnrequirements.ProjectFragment;

/**
 * Created by deb on 4/27/16.
 */
public class Gloves extends Mittens {
    public Gloves(String name, int thumbImageID, Class<ProjectFragment> fragmentClass) {
        super(name, thumbImageID, fragmentClass);
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
