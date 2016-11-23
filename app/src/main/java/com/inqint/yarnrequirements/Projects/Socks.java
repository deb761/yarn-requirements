package com.inqint.yarnrequirements.Projects;

import com.inqint.yarnrequirements.ProjectFragment;

import java.util.function.Function;

/**
 * Created by deb on 4/27/16.
 */
public class Socks extends Project {
    public Socks(String name, int thumbImageID, Function<Project, ProjectFragment> newFragment) {
        super(name, thumbImageID, newFragment);
    }

    @Override
    public void calcYarnRequired() {

    }
}
