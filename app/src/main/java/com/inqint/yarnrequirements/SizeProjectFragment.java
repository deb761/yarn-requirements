package com.inqint.yarnrequirements;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.inqint.yarnrequirements.Projects.ShortLengthUnits;
import com.inqint.yarnrequirements.Projects.SizeProject;

/**
 * Created by deb on 11/25/16.
 */

public class SizeProjectFragment extends ProjectFragment {

    private EditText sizeText;
    private Spinner sizeUnits;
    private SizeProject sizeProject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sizeProject = (SizeProject) project;
        super.onCreateView(inflater, container, savedInstanceState);

        initSizeUnitSpinner();

        sizeText.setText(String.format("%.1f", sizeProject.getSize()));
        sizeUnits.setSelection(sizeProject.getSizeUnits().ordinal());

        // Add size view to listview
        return view;
    }

    @Override
    protected void initValues() {
        super.initValues();
        sizeText = (EditText) view.findViewById(R.id.editSize);
        sizeUnits = (Spinner) view.findViewById(R.id.sizeUnitsSpinner);
    }

    // Add the text changed event for the size value
    @Override
    protected void initTextChangedEvents() {
        super.initTextChangedEvents();

        sizeText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String str = sizeText.getText().toString();
                if (str.length() > 0) {
                    sizeProject.setSize(Double.parseDouble(str));
                    sizeProject.calcYarnRequired();
                    updateResults();
                }
            }

            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                //  Auto-generated method stub

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                //  Auto-generated method stub
            }
        });
    }

    private void initSizeUnitSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.short_length_units_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sizeUnits.setAdapter(adapter);

        sizeUnits.setOnItemSelectedListener(this);
    }

    // Check to see if the spinner selected is the size spinner, and if so, update the size units and
    // yarn required
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch (parent.getId()) {
            case R.id.sizeUnitsSpinner:
                sizeProject.setSizeUnits(ShortLengthUnits.fromInt(pos));
                project.calcYarnRequired();
                updateResults();
                break;
            default:
                super.onItemSelected(parent, view, pos, id);
                break;
        }
    }


}
