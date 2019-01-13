package com.inqint.yarnrequirements

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.inqint.yarnrequirements.Projects.DimensionProject
import com.inqint.yarnrequirements.Projects.ShortLengthUnits

/**
 * Created by deb on 11/25/16.  Converted to Kotlin 1/9/19
 */

class DimensionProjectFragment : ProjectFragment() {

    // these two need are reuses of the size fields
    private lateinit var lengthText: EditText
    private lateinit var lengthUnits: Spinner
    private lateinit var widthText: EditText
    private lateinit var widthUnits: Spinner
    private var widthUnitsId: Int = 0
    private lateinit var dimensionProject: DimensionProject

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dimensionProject = project as DimensionProject
        super.onCreateView(inflater, container, savedInstanceState)

        initSizeUnitSpinner()

        lengthText.setText(String.format("%.1f", dimensionProject!!.length))
        lengthUnits.setSelection(dimensionProject!!.lengthUnits.ordinal)

        widthText.setText(String.format("%.1f", dimensionProject!!.width))
        widthUnits.setSelection(dimensionProject!!.widthUnits.ordinal)

        // Add dimension view to listview
        return mview
    }

    override fun initValues() {
        super.initValues()

        val lengthLabel = mview.findViewById(R.id.sizeLabel) as TextView
        lengthLabel.text = getString(R.string.length_label)
        lengthText = mview.findViewById(R.id.editSize) as EditText
        lengthUnits = mview.findViewById(R.id.sizeUnitsSpinner) as Spinner

        val widthLabel = TextView(context)
        widthLabel.text = getString(R.string.width_label)
        widthLabel.setTextColor(lengthLabel.textColors)
        widthLabel.setTextSize(TypedValue.COMPLEX_UNIT_PX, lengthLabel.textSize)
        widthText = EditText(context)
        widthText.setTextColor(lengthText!!.textColors)
        widthText.setTextSize(TypedValue.COMPLEX_UNIT_PX, lengthText.textSize)
        //widthText.setEms(lengthText.getMaxEms());
        widthUnits = Spinner(context)
        widthUnitsId = View.generateViewId()
        widthUnits.id = widthUnitsId
        var row = 3

        // get the grid view so we can insert the width stuff
        gridLayout = mview.findViewById(R.id.gridLayout) as GridLayout
        val widthRow = GridLayout.spec(row++)
        val col1 = GridLayout.spec(0)
        val col2 = GridLayout.spec(1)
        val col3 = GridLayout.spec(2)

        gridLayout.addView(widthLabel, GridLayout.LayoutParams(widthRow, col1))
        gridLayout.addView(widthText, GridLayout.LayoutParams(widthRow, col2))
        gridLayout.addView(widthUnits, GridLayout.LayoutParams(widthRow, col3))

        val margin = mview.getResources().getDimensionPixelSize(R.dimen.weight_padding)
        (widthLabel.layoutParams as ViewGroup.MarginLayoutParams).setMargins(margin, margin, margin, margin)
        (widthText.layoutParams as ViewGroup.MarginLayoutParams).setMargins(margin, margin, margin, margin)
        (widthUnits.layoutParams as ViewGroup.MarginLayoutParams).setMargins(margin, margin, margin, margin)

        // update other rows
        val yarnRow = GridLayout.spec(row++)
        val yarnLabel = mview.findViewById(R.id.yarnNeededLabel) as TextView
        (yarnLabel.layoutParams as GridLayout.LayoutParams).rowSpec = widthRow
        (yarnNeeded.layoutParams as GridLayout.LayoutParams).rowSpec = yarnRow
        (yarnUnits.layoutParams as GridLayout.LayoutParams).rowSpec = yarnRow

        val ballRow = GridLayout.spec(row++)
        val ballLabel = mview.findViewById(R.id.ballSizeLabel) as TextView
        (ballLabel.layoutParams as GridLayout.LayoutParams).rowSpec = yarnRow
        (ballSize.layoutParams as GridLayout.LayoutParams).rowSpec = ballRow
        (ballUnits.layoutParams as GridLayout.LayoutParams).rowSpec = ballRow

        val numRow = GridLayout.spec(row++)
        val numLabel = mview.findViewById(R.id.ballsNeededLabel) as TextView
        (numLabel.layoutParams as GridLayout.LayoutParams).rowSpec = ballRow
        (ballsNeeded.layoutParams as GridLayout.LayoutParams).rowSpec = numRow
        (partialBalls.layoutParams as GridLayout.LayoutParams).rowSpec = numRow
    }

    // Add the text changed event for the size value
    override fun initTextChangedEvents() {
        super.initTextChangedEvents()

        lengthText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val str = lengthText.text.toString()
                if (str.isNotEmpty()) {
                    dimensionProject.length = java.lang.Double.parseDouble(str)
                    dimensionProject.calcYarnRequired()
                    updateResults()
                }
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int,
                arg2: Int, arg3: Int
            ) {
                //  Auto-generated method stub

            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                //  Auto-generated method stub
            }
        })

        widthText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val str = widthText.text.toString()
                if (str.isNotEmpty()) {
                    dimensionProject.width = java.lang.Double.parseDouble(str)
                    dimensionProject.calcYarnRequired()
                    updateResults()
                }
            }

            override fun beforeTextChanged(
                arg0: CharSequence, arg1: Int,
                arg2: Int, arg3: Int
            ) {
                //  Auto-generated method stub

            }

            override fun onTextChanged(
                s: CharSequence, start: Int, before: Int,
                count: Int
            ) {
                //  Auto-generated method stub
            }
        })
    }

    private fun initSizeUnitSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.short_length_units_array, R.layout.spinner
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner)
        // Apply the adapter to the spinner
        lengthUnits.adapter = adapter

        lengthUnits.onItemSelectedListener = this

        widthUnits.adapter = adapter

        widthUnits.onItemSelectedListener = this
    }

    // Check to see if the spinner selected is the length or width unit spinner, and if so, update the size units and
    // yarn required
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        val parentID = parent.id
        if (parentID == R.id.sizeUnitsSpinner) {
            dimensionProject.lengthUnits = ShortLengthUnits.fromInt(pos)
            project.calcYarnRequired()
            updateResults()
        } else if (parentID == widthUnitsId) {
            dimensionProject.lengthUnits = ShortLengthUnits.fromInt(pos)
            project.calcYarnRequired()
            updateResults()
        } else {
            super.onItemSelected(parent, view, pos, id)
        }
    }


}
