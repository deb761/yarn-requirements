package com.inqint.yarnrequirements;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.inqint.yarnrequirements.Projects.DimensionProject;
import com.inqint.yarnrequirements.Projects.ShortLengthUnits;

/**
 * Created by deb on 11/25/16.
 */

public class DimensionProjectFragment extends ProjectFragment {

    // these two need are reuses of the size fields
    private EditText lengthText;
    private Spinner lengthUnits;
    private EditText widthText;
    private Spinner widthUnits;
    private int widthUnitsId;
    private DimensionProject dimensionProject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dimensionProject = (DimensionProject) project;
        super.onCreateView(inflater, container, savedInstanceState);

        initSizeUnitSpinner();

        lengthText.setText(String.format("%.1f", dimensionProject.getLength()));
        lengthUnits.setSelection(dimensionProject.getLengthUnits().ordinal());

        widthText.setText(String.format("%.1f", dimensionProject.getLength()));
        widthUnits.setSelection(dimensionProject.getLengthUnits().ordinal());

        // Add size view to listview
        return view;
    }

    @Override
    protected void initValues() {
        super.initValues();

        TextView lengthLabel = (TextView) view.findViewById(R.id.sizeLabel);
        lengthLabel.setText(getString(R.string.length_label));
        lengthText = (EditText) view.findViewById(R.id.editSize);
        lengthUnits = (Spinner) view.findViewById(R.id.sizeUnitsSpinner);

        TextView widthLabel = new TextView(context);
        widthLabel.setText(getString(R.string.width_label));
        widthLabel.setTextColor(lengthLabel.getTextColors());
        widthLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, lengthLabel.getTextSize());
        widthText = new EditText(context);
        widthText.setTextColor(lengthText.getTextColors());
        widthText.setTextSize(TypedValue.COMPLEX_UNIT_PX, lengthText.getTextSize());
        //widthText.setEms(lengthText.getMaxEms());
        widthUnits = new Spinner(context);
        widthUnitsId = View.generateViewId();
        widthUnits.setId(widthUnitsId);
        int row = 3;

        // get the grid view so we can insert the width stuff
        gridLayout = (GridLayout) view.findViewById(R.id.gridLayout);
        GridLayout.Spec widthRow = GridLayout.spec(row++);
        GridLayout.Spec col1 = GridLayout.spec(0);
        GridLayout.Spec col2 = GridLayout.spec(1);
        GridLayout.Spec col3 = GridLayout.spec(2);

        gridLayout.addView(widthLabel, new GridLayout.LayoutParams(widthRow, col1));
        gridLayout.addView(widthText, new GridLayout.LayoutParams(widthRow, col2));
        gridLayout.addView(widthUnits, new GridLayout.LayoutParams(widthRow, col3));

        int margin = view.getResources().getDimensionPixelSize(R.dimen.weight_padding);
        ((ViewGroup.MarginLayoutParams) widthLabel.getLayoutParams()).setMargins(margin, margin, margin, margin);
        ((ViewGroup.MarginLayoutParams) widthText.getLayoutParams()).setMargins(margin, margin, margin, margin);
        ((ViewGroup.MarginLayoutParams) widthUnits.getLayoutParams()).setMargins(margin, margin, margin, margin);

        // update other rows
        GridLayout.Spec yarnRow = GridLayout.spec(row++);
        TextView yarnLabel = (TextView) view.findViewById(R.id.yarnNeededLabel);
        ((GridLayout.LayoutParams) yarnLabel.getLayoutParams()).rowSpec = widthRow;
        ((GridLayout.LayoutParams) yarnNeeded.getLayoutParams()).rowSpec = yarnRow;
        ((GridLayout.LayoutParams) yarnUnits.getLayoutParams()).rowSpec = yarnRow;

        GridLayout.Spec ballRow = GridLayout.spec(row++);
        TextView ballLabel = (TextView) view.findViewById(R.id.ballSizeLabel);
        ((GridLayout.LayoutParams) ballLabel.getLayoutParams()).rowSpec = yarnRow;
        ((GridLayout.LayoutParams) ballSize.getLayoutParams()).rowSpec = ballRow;
        ((GridLayout.LayoutParams) ballUnits.getLayoutParams()).rowSpec = ballRow;

        GridLayout.Spec numRow = GridLayout.spec(row++);
        TextView numLabel = (TextView) view.findViewById(R.id.ballsNeededLabel);
        ((GridLayout.LayoutParams) numLabel.getLayoutParams()).rowSpec = ballRow;
        ((GridLayout.LayoutParams) ballsNeeded.getLayoutParams()).rowSpec = numRow;
        ((GridLayout.LayoutParams) partialBalls.getLayoutParams()).rowSpec = numRow;
    }

    // Add the text changed event for the size value
    @Override
    protected void initTextChangedEvents() {
        super.initTextChangedEvents();

        lengthText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String str = lengthText.getText().toString();
                if (str.length() > 0) {
                    dimensionProject.setLength(Double.parseDouble(str));
                    dimensionProject.calcYarnRequired();
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

        widthText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String str = widthText.getText().toString();
                if (str.length() > 0) {
                    dimensionProject.setWidth(Double.parseDouble(str));
                    dimensionProject.calcYarnRequired();
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
        lengthUnits.setAdapter(adapter);

        lengthUnits.setOnItemSelectedListener(this);

        widthUnits.setAdapter(adapter);

        widthUnits.setOnItemSelectedListener(this);
    }

    // Check to see if the spinner selected is the length or width unit spinner, and if so, update the size units and
    // yarn required
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        int parentID = parent.getId();
        if (parentID == R.id.sizeUnitsSpinner) {
            dimensionProject.setLengthUnits(ShortLengthUnits.fromInt(pos));
            project.calcYarnRequired();
            updateResults();
        } else if (parentID == widthUnitsId) {
            dimensionProject.setLengthUnits(ShortLengthUnits.fromInt(pos));
            project.calcYarnRequired();
            updateResults();
        } else {
            super.onItemSelected(parent, view, pos, id);
        }
    }


}
