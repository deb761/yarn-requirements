package com.inqint.yarnrequirements.Projects;

/**
 * Created by deb on 4/27/16.
 */
public class Blanket extends Scarf {

    public Blanket(String name, int thumbImageID, int imageID, Class<?> fragmentClass) {
        super(name, thumbImageID, imageID, fragmentClass);
    }

    @Override
    public void calcYarnRequired() {
        super.calcYarnRequired();

        yarnNeeded = (int)(1.2 * (double)yarnNeeded); // Add 20% to estimate
        calcBallsNeeded();
    }
}
