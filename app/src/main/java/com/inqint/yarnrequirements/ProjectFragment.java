package com.inqint.yarnrequirements;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.inqint.yarnrequirements.Projects.Project;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectFragment extends Fragment {
    private static final String ARG_PROJECT = "project";

    private Project project;

    private OnFragmentInteractionListener mListener;

    public ProjectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param project Parameter 1.
     * @return A new instance of fragment ProjectFragment.
     */
    public static ProjectFragment newInstance(Project project) {
        ProjectFragment fragment = new ProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PROJECT, project.getName());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            project = ProjectContent.PROJECT_MAP.get(savedInstanceState.getString(ARG_PROJECT));
        }
    }

    private View view;  // parent view
    private Context context;
    private EditText gaugeText;
    private Spinner gaugeUnits;
    private TextView yarnNeeded;
    private Spinner yarnUnits;
    private EditText ballSize;
    private Spinner ballUnits;
    private TextView ballsNeeded;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_project, container, false);
        context = view.getContext();

        initGaugeUnitSpinner();
        initYarnUnitSpinner();
        initBallUnitSpinner();

        initTextChangedEvents();
        initValues();

        return view;
    }

    // Set up the Gauge Unit spinner
    private void initGaugeUnitSpinner() {
        Spinner spinner = (Spinner) view.findViewById(R.id.gaugeUnitsSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.gauge_units_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new GaugeUnitsSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

                super.onItemSelected(adapterView, view, pos, id);
                updateResults();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initYarnUnitSpinner() {
        Spinner spinner = (Spinner) view.findViewById(R.id.yarnUnitsSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.long_length_units_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new YarnUnitsSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

                super.onItemSelected(adapterView, view, pos, id);
                updateResults();
            }
        });
    }

    private void initBallUnitSpinner() {
        Spinner spinner = (Spinner) view.findViewById(R.id.ballSizeSpinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.long_length_units_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new BallUnitsSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

                super.onItemSelected(adapterView, view, pos, id);
                updateResults();
            }
        });
    }

    protected void initTextChangedEvents() {
        final EditText gaugeText = (EditText) view.findViewById(R.id.editGauge);
        gaugeText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String str = gaugeText.getText().toString();
                if (str.length() > 0) {
                    project.setGauge(Double.parseDouble(str));
                    project.calcYarnRequired();
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

        final EditText ballSizeText = (EditText) view.findViewById(R.id.editBallSize);
        ballSizeText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String str = ballSizeText.getText().toString();
                if (str.length() > 0) {
                    project.setBallSize(Integer.parseInt(str));
                    project.calcBallsNeeded();
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

    protected void initValues() {
        gaugeText = (EditText) view.findViewById(R.id.editGauge);
        gaugeUnits = (Spinner) view.findViewById(R.id.gaugeUnitsSpinner);
        yarnNeeded = (TextView) view.findViewById(R.id.yarnNeededText);
        yarnUnits = (Spinner) view.findViewById(R.id.yarnUnitsSpinner);
        ballSize = (EditText) view.findViewById(R.id.editBallSize);
        ballUnits = (Spinner) view.findViewById(R.id.ballSizeSpinner);
        ballsNeeded = (TextView) view.findViewById(R.id.ballsNeededText);

        gaugeText.setText(Double.toString(project.getGauge()));
        gaugeUnits.setSelection(project.getGaugeUnits().ordinal());
        yarnNeeded.setText(Integer.toString(project.getYarnNeeded()));
        yarnUnits.setSelection(project.getYarnNeededUnits().ordinal());
        ballSize.setText(Integer.toString(project.getBallSize()));
        ballUnits.setSelection(project.getBallSizeUnits().ordinal());
        ballsNeeded.setText(Double.toString(project.getBallsNeeded()));
    }

    public void updateResults() {
        TextView yarnNeeded = (TextView) view.findViewById(R.id.yarnNeededText);
        TextView ballsNeeded = (TextView) view.findViewById(R.id.ballsNeededText);

        yarnNeeded.setText(String.format("%1$?d", project.getYarnNeeded()));
        ballsNeeded.setText(String.format("%1$.1f", project.getBallsNeeded()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
