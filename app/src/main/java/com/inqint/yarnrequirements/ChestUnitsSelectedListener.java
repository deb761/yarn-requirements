package com.inqint.yarnrequirements;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.inqint.yarnrequirements.Projects.Project;
import com.inqint.yarnrequirements.Projects.ShortLengthUnits;
import com.inqint.yarnrequirements.Projects.Sweater;

/**
 * Created by deb on 4/28/16.
 */
public class ChestUnitsSelectedListener implements OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        Project proj = SelectActivity.getProject();
        Sweater project = (Sweater) SelectActivity.getProject();
        project.setChestUnits(ShortLengthUnits.fromInt(pos));
        project.calcYarnRequired();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
