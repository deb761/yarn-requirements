package com.inqint.yarnrequirements

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.inqint.yarnrequirements.Projects.GaugeUnits
import com.inqint.yarnrequirements.Projects.LongLengthUnits
import com.inqint.yarnrequirements.Projects.Project
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProjectFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProjectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
open class ProjectFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var mListener: OnFragmentInteractionListener? = null
    public lateinit var project: Project
    protected lateinit var gridLayout: GridLayout
    protected lateinit var preferences: SharedPreferences
    private lateinit var name: TextView
    private lateinit var image: ImageView
    private lateinit var gaugeText: EditText
    private lateinit var gaugeUnits: Spinner
    protected lateinit var yarnNeeded: TextView
    protected lateinit var yarnUnits: Spinner
    protected lateinit var ballSize: EditText
    protected lateinit var ballUnits: Spinner
    protected lateinit var ballsNeeded: TextView
    protected lateinit var partialBalls: Spinner
    protected lateinit var mview: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            project = ProjectContent.PROJECT_MAP.get(key = savedInstanceState!!.getString(ARG_PROJECT)!!)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mview = inflater.inflate(R.layout.fragment_project, container, false)
        val context = mview.context
        preferences = context.getSharedPreferences(project.name, Context.MODE_PRIVATE)

        val filename = String.format(
            mview.resources.getString(R.string.project_settings),
            project.name.toLowerCase()
        )
        project.getSettings(preferences, readDefaults(filename)!!)

        initValues()
        initGaugeUnitSpinner()
        initYarnUnitSpinner()
        initBallUnitSpinner()
        initPartialBallSpinner()

        initTextChangedEvents()

        name.text = project.name
        image.setImageResource(project.imageID)
        setInitialValues()

        return mview
    }

    // Read the default settings for the project
    protected fun readDefaults(file: String): JSONObject? {
        var `object`: JSONObject? = null
        try {
            val `is` = activity!!.assets.open(file)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            val json = String(buffer, Charset.forName("UTF-8"))
            `object` = JSONObject(json)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return `object`
    }

    // Identify the various views
    open protected fun initValues() {
        name = mview.findViewById<View>(R.id.name) as TextView
        image = mview.findViewById<View>(R.id.image) as ImageView
        gridLayout = mview.findViewById(R.id.gridLayout) as GridLayout
        gaugeText = mview.findViewById(R.id.editGauge) as EditText
        gaugeUnits = mview.findViewById(R.id.gaugeUnitsSpinner) as Spinner
        yarnNeeded = mview.findViewById(R.id.yarnNeededText) as TextView
        yarnUnits = mview.findViewById(R.id.yarnUnitsSpinner) as Spinner
        ballSize = mview.findViewById(R.id.editBallSize) as EditText
        ballUnits = mview.findViewById(R.id.ballSizeSpinner) as Spinner
        ballsNeeded = mview.findViewById(R.id.ballsNeededText) as TextView
        partialBalls = mview.findViewById(R.id.ballFractSpinner) as Spinner
    }

    // Set the values for all the views
    protected fun setInitialValues() {
        gaugeText.setText(String.format("%.1f", project.gauge))
        gaugeUnits.setSelection(project.gaugeUnits.ordinal)
        yarnNeeded.text = String.format("%d", project.yarnNeeded)
        yarnUnits.setSelection(project.yarnNeededUnits.ordinal)
        ballSize.setText(String.format("%d", project.ballSize))
        ballUnits.setSelection(project.ballSizeUnits.ordinal)
        ballsNeeded.text = String.format("%.1f", project.ballsNeeded)
        partialBalls.setSelection(if (project.isPartialBalls) 1 else 0)
    }

    // Set up the Gauge Unit spinner
    private fun initGaugeUnitSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(
            mview.context,
            R.array.gauge_units_array, R.layout.spinner
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner)
        // Apply the adapter to the spinner
        gaugeUnits.adapter = adapter

        gaugeUnits.onItemSelectedListener = this
    }

    private fun initYarnUnitSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.long_length_units_array, R.layout.spinner
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner)
        // Apply the adapter to the spinner
        yarnUnits.adapter = adapter

        yarnUnits.onItemSelectedListener = this
    }

    private fun initBallUnitSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.long_length_units_array, R.layout.spinner
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner)
        // Apply the adapter to the spinner
        ballUnits.adapter = adapter

        ballUnits.onItemSelectedListener = this
    }

    private fun initPartialBallSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        val adapter = ArrayAdapter.createFromResource(
            context,
            R.array.partial_ball_array, R.layout.spinner
        )
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner)
        // Apply the adapter to the spinner
        partialBalls.adapter = adapter

        partialBalls.onItemSelectedListener = this
    }

    open protected fun initTextChangedEvents() {
        gaugeText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val str = gaugeText.text.toString()
                if (str.isNotEmpty()) {
                    project.gauge = java.lang.Double.parseDouble(str)
                    project.calcYarnRequired()
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

        val ballSizeText = mview.findViewById(R.id.editBallSize) as EditText
        ballSizeText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                val str = ballSizeText.text.toString()
                if (str.isNotEmpty()) {
                    project.ballSize = Integer.parseInt(str)
                    project.calcBallsNeeded()
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

    fun updateResults() {
        val yarnNeeded = mview.findViewById(R.id.yarnNeededText) as TextView
        val ballsNeeded = mview.findViewById(R.id.ballsNeededText) as TextView

        yarnNeeded.text = String.format("%d", project.yarnNeeded)
        ballsNeeded.text = String.format("%.1f", project.ballsNeeded)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        when (parent.id) {
            R.id.gaugeUnitsSpinner -> {
                project.gaugeUnits = GaugeUnits.fromInt(pos)
                project.calcYarnRequired()
            }
            R.id.yarnUnitsSpinner -> project.yarnNeededUnits = LongLengthUnits.fromInt(pos)
            R.id.ballSizeSpinner -> {
                project.ballSizeUnits = LongLengthUnits.fromInt(pos)
                project.calcBallsNeeded()
            }
            R.id.ballFractSpinner -> project.isPartialBalls = pos != 0
        }
        updateResults()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private val ARG_PROJECT = "project"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param project Parameter 1.
         * @return A new instance of fragment ProjectFragment.
         */
        fun newInstance(project: Project): ProjectFragment {
            val fragment = ProjectFragment()
            val args = Bundle()
            args.putString(ARG_PROJECT, project.name)
            fragment.arguments = args
            fragment.project = project
            return fragment
        }
    }
}// Required empty public constructor
