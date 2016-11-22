package com.inqint.yarnrequirements;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.inqint.yarnrequirements.Projects.Project;
import com.inqint.yarnrequirements.Projects.LongLengthUnits;

/**
 * Created by deb on 4/28/16.
 */
public class BallUnitsSelectedListener implements OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Project project = SelectActivity.getProject();
        project.setBallSizeUnits(LongLengthUnits.fromInt(pos));
        project.calcBallsNeeded();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
