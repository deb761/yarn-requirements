package com.inqint.yarnrequirements.Projects;

import com.inqint.yarnrequirements.ProjectFragment;

/**
 * Created by deb on 4/27/16.
 */
public class Vest extends Sweater {
    public Vest(String name, int thumbImageID, Class<ProjectFragment> fragmentClass) {
        super(name, thumbImageID, fragmentClass);
    }

    /* Results of linear fit of Length to hips by Chest size:
     (Intercept)      Chest
     Baby     -6.50000 0.60000000
     Child   -23.85854 0.78042063
     Man      47.67266 0.17445286
     Woman    52.32509 0.06000826
     Youth    14.99031 0.38372093
     
     Looks like the baby line will cover up to 80cm,
     Then we will jump to the Man line
    */
    private final double babyInt = -6.50000;
    private final double babySlope = 0.60000000;

    private final double sBreak = 80.0; //cm

    private final double manInt = 47.67266;
    private final double manSlope = 0.17445286;

    @Override
    public void calcYarnRequired() {
        double width = getSize();
        if (getSizeUnits() != ShortLengthUnits.cm)
        {
            width *= inches2cm;
        }
        double length;
        if (width < 80.0)
        {
            length = babyInt + babySlope * width;
        } else {
            length = manInt + manSlope * width;
        }
        calcYarnRequired(length, width);
    }
}
