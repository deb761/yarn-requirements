package com.inqint.yarnrequirements

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.inqint.yarnrequirements.Projects.Socks

/**
 * Created by deb on 11/25/16. Converted to Kotlin 1/9/19
 */

class SockProjectFragment : ProjectFragment() {

    private lateinit var sizeText: EditText
    private lateinit var sizeUnits: Spinner
    private lateinit var socks: Socks

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        socks = project as Socks
        // socks needs the foot sizes before the view is created
        socks.getFootSizes(readDefaults("feet-sizes.json")!!)
        super.onCreateView(inflater, container, savedInstanceState)

        initSizeUnitSpinner()

        userUpdate = false // let TextWatcher know user is not changing text
        sizeText.setText(String.format("%.1f", socks.size))
        sizeUnits.setSelection(socks.sizeUnits.ordinal)
        userUpdate = true

        return mview
    }

    override fun initValues() {
        super.initValues()
        sizeText = mview.findViewById(R.id.editSize) as EditText
        sizeUnits = mview.findViewById(R.id.sizeUnitsSpinner) as Spinner
    }

    // Add the text changed event for the size value
    override fun initTextChangedEvents() {
        super.initTextChangedEvents()

        sizeText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val str = sizeText.text.toString()
                if (userUpdate && str.isNotEmpty()) {
                    socks.size = java.lang.Double.parseDouble(str)
                    with (preferences.edit()) {
                        putFloat("size", socks.size.toFloat())
                        commit()
                    }
                    socks.calcYarnRequired()
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
            R.array.shoe_size_units_array, R.layout.spinner
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner)
        // Apply the adapter to the spinner
        sizeUnits.adapter = adapter

        sizeUnits.onItemSelectedListener = this
    }

    // Check to see if the spinner selected is the size spinner, and if so, update the size units and
    // yarn required
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        when (parent.id) {
            R.id.sizeUnitsSpinner -> {
                socks.sizeUnits = Socks.ShoeSizeUnits.fromInt(pos)
                with (preferences.edit()) {
                    putInt("sizeUnits", pos)
                    commit()
                }
                project.calcYarnRequired()
                updateResults()
            }
            else -> super.onItemSelected(parent, view, pos, id)
        }
    }


}
