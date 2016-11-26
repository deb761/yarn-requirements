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

import com.inqint.yarnrequirements.Projects.Socks;

/**
 * Created by deb on 11/25/16.
 */

public class SockProjectFragment extends ProjectFragment {

    private EditText sizeText;
    private Spinner sizeUnits;
    private Socks socks;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        socks = (Socks) project;
        // socks needs the foot sizes before the view is created
        socks.getFootSizes(readDefaults("feet-sizes.json"));
        super.onCreateView(inflater, container, savedInstanceState);

        initSizeUnitSpinner();
        sizeText.setText(String.format("%.1f", socks.getSize()));
        sizeUnits.setSelection(socks.getSizeUnits().ordinal());

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
                    socks.setSize(Double.parseDouble(str));
                    socks.calcYarnRequired();
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
                R.array.shoe_size_units_array, android.R.layout.simple_spinner_item);
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
                socks.setSizeUnits(Socks.ShoeSizeUnits.fromInt(pos));
                project.calcYarnRequired();
                updateResults();
                break;
            default:
                super.onItemSelected(parent, view, pos, id);
                break;
        }
    }


}
