package com.inqint.yarnrequirements.Projects;

import com.inqint.yarnrequirements.ProjectFragment;

import java.util.function.Function;

/**
 * Created by deb on 4/27/16.
 */
public class Blanket extends Scarf {

    public Blanket(String name, int thumbImageID, Function<Project, ProjectFragment> newFragment) {
        super(name, thumbImageID, newFragment);

        setLength(60.0);
        setWidth(90.0);
        setLengthUnits(ShortLengthUnits.cm);
        setWidthUnits(ShortLengthUnits.cm);
    }

    // Calculations borrowed from http://www.thedietdiary.com/knittingfiend/tools/EstimatingYardageRectangles.html
    // copyright Lucia Liljegren 2005.
    @Override
    public void calcYarnRequired() {
        super.calcYarnRequired();

        yarnNeeded = (int)(1.2 * (double)yarnNeeded); // Add 20% to estimate
        calcBallsNeeded();
    }
}
