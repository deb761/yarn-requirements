package com.inqint.yarnrequirements;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.inqint.yarnrequirements.Projects.GaugeUnits;
import com.inqint.yarnrequirements.Projects.LongLengthUnits;
import com.inqint.yarnrequirements.Projects.Project;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProjectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private static final String ARG_PROJECT = "project";

    protected Project project;

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
        fragment.project = project;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            project = ProjectContent.PROJECT_MAP.get(savedInstanceState.getString(ARG_PROJECT));
        }
    }

    protected View view;  // parent view
    protected GridLayout gridLayout;
    protected Context context;
    protected SharedPreferences preferences;
    private TextView name;
    private ImageView image;
    private EditText gaugeText;
    private Spinner gaugeUnits;
    protected TextView yarnNeeded;
    protected Spinner yarnUnits;
    protected EditText ballSize;
    protected Spinner ballUnits;
    protected TextView ballsNeeded;
    protected Spinner partialBalls;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_project, container, false);
        context = view.getContext();
        preferences = context.getSharedPreferences(project.getName(), Context.MODE_PRIVATE);

        String filename = String.format(view.getResources().getString(R.string.project_settings),
                project.getName().toLowerCase());
        project.getSettings(preferences, readDefaults(filename));

        initValues();
        initGaugeUnitSpinner();
        initYarnUnitSpinner();
        initBallUnitSpinner();
        initPartialBallSpinner();

        initTextChangedEvents();

        name.setText(project.getName());
        image.setImageResource(project.getImageID());
        setInitialValues();

        return view;
    }
    // Read the default settings for the project
    protected JSONObject readDefaults(String file) {
        JSONObject object = null;
        try {
            InputStream is  = getActivity().getAssets().open(file);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            object = new JSONObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }
    // Identify the various views
    protected void initValues() {
        name = (TextView) view.findViewById(R.id.name);
        image = (ImageView) view.findViewById(R.id.image);
        gridLayout = (GridLayout) view.findViewById(R.id.gridLayout);
        gaugeText = (EditText) view.findViewById(R.id.editGauge);
        gaugeUnits = (Spinner) view.findViewById(R.id.gaugeUnitsSpinner);
        yarnNeeded = (TextView) view.findViewById(R.id.yarnNeededText);
        yarnUnits = (Spinner) view.findViewById(R.id.yarnUnitsSpinner);
        ballSize = (EditText) view.findViewById(R.id.editBallSize);
        ballUnits = (Spinner) view.findViewById(R.id.ballSizeSpinner);
        ballsNeeded = (TextView) view.findViewById(R.id.ballsNeededText);
        partialBalls = (Spinner) view.findViewById(R.id.ballFractSpinner);
    }
    // Set the values for all the views
    protected void setInitialValues() {
        gaugeText.setText(Double.toString(project.getGauge()));
        gaugeUnits.setSelection(project.getGaugeUnits().ordinal());
        yarnNeeded.setText(Integer.toString(project.getYarnNeeded()));
        yarnUnits.setSelection(project.getYarnNeededUnits().ordinal());
        ballSize.setText(Integer.toString(project.getBallSize()));
        ballUnits.setSelection(project.getBallSizeUnits().ordinal());
        ballsNeeded.setText(Double.toString(project.getBallsNeeded()));
        partialBalls.setSelection(project.isPartialBalls() ? 1 : 0);
    }

    // Set up the Gauge Unit spinner
    private void initGaugeUnitSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.gauge_units_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gaugeUnits.setAdapter(adapter);

        gaugeUnits.setOnItemSelectedListener(this);
    }

    private void initYarnUnitSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.long_length_units_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        yarnUnits.setAdapter(adapter);

        yarnUnits.setOnItemSelectedListener(this);
    }

    private void initBallUnitSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.long_length_units_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        ballUnits.setAdapter(adapter);

        ballUnits.setOnItemSelectedListener(this);
    }

    private void initPartialBallSpinner() {
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                R.array.partial_ball_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        partialBalls.setAdapter(adapter);

        partialBalls.setOnItemSelectedListener(this);
    }

    protected void initTextChangedEvents() {
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

    public void updateResults() {
        TextView yarnNeeded = (TextView) view.findViewById(R.id.yarnNeededText);
        TextView ballsNeeded = (TextView) view.findViewById(R.id.ballsNeededText);

        yarnNeeded.setText(String.format("%d", project.getYarnNeeded()));
        ballsNeeded.setText(String.format("%.1f", project.getBallsNeeded()));
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        switch (parent.getId()) {
            case R.id.gaugeUnitsSpinner:
                project.setGaugeUnits(GaugeUnits.fromInt(pos));
                project.calcYarnRequired();
                break;
            case R.id.yarnUnitsSpinner:
                project.setYarnNeededUnits(LongLengthUnits.fromInt(pos));
                break;
            case R.id.ballSizeSpinner:
                project.setBallSizeUnits(LongLengthUnits.fromInt(pos));
                project.calcBallsNeeded();
                break;
            case R.id.ballFractSpinner:
                project.setPartialBalls(pos != 0);
                break;
        }
        updateResults();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void setProject(Project project) {
        this.project = project;
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
