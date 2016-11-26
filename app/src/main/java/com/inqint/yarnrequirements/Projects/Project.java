package com.inqint.yarnrequirements.Projects;

import android.content.SharedPreferences;

import com.inqint.yarnrequirements.ProjectFragment;

import org.json.JSONException;
import org.json.JSONObject;

/* The basic definition of a knitting project.  This class is extended for each project type. */
public abstract class Project {
    private String name;
    private double gauge;
    private GaugeUnits gaugeUnits;
    private int thumbImageID;
    private int imageID;
    private Class<?> fragment;
    protected int yarnNeeded;
    private LongLengthUnits yarnNeededUnits;
    private int ballSize;
    private LongLengthUnits ballSizeUnits;
    protected double ballsNeeded;

    private boolean partialBalls;


    public String getName() {
        return name;
    }

    public double getGauge() {
        return gauge;
    }

    public void setGauge(double gauge) {
        this.gauge = gauge;
    }

    public GaugeUnits getGaugeUnits() {
        return gaugeUnits;
    }

    public void setGaugeUnits(GaugeUnits gaugeUnits) {
        this.gaugeUnits = gaugeUnits;
    }

    public int getThumbImageID() {
        return thumbImageID;
    }

    public int getImageID() { return imageID; }

    public Class<?> getFragment() {
        return fragment;
    }

    public void setFragment(Class<ProjectFragment> fragment) {
        this.fragment = fragment;
    }

    public int getYarnNeeded() {
        return yarnNeeded;
    }

    public LongLengthUnits getYarnNeededUnits() {
        return yarnNeededUnits;
    }

    public void setYarnNeededUnits(LongLengthUnits yarnNeededUnits) {
        this.yarnNeededUnits = yarnNeededUnits;
    }

    public int getBallSize() {
        return ballSize;
    }

    public void setBallSize(int ballSize) {
        this.ballSize = ballSize;
    }

    public LongLengthUnits getBallSizeUnits() {
        return ballSizeUnits;
    }

    public void setBallSizeUnits(LongLengthUnits ballSizeUnits) {
        this.ballSizeUnits = ballSizeUnits;
    }

    public double getBallsNeeded() {
        return ballsNeeded;
    }

    public boolean isPartialBalls() {
        return partialBalls;
    }

    public void setPartialBalls(boolean partialBalls) {
        this.partialBalls = partialBalls;
    }

    static protected double inches2cm = 2.54;
    static protected double yards2meters = 0.9144;

    public Project () {
        gauge = 20;
        gaugeUnits = GaugeUnits.stsPer10cm;
        yarnNeeded = 0;
        yarnNeededUnits = LongLengthUnits.meters;
        ballSize = 150;
        ballSizeUnits = LongLengthUnits.meters;
        ballsNeeded = 0;
    }
    public Project(String name, int thumbImageID, int imageID, Class<?> fragment)
    {
        this();
        this.name = name;
        this.thumbImageID = thumbImageID;
        this.imageID = imageID;
        this.fragment = fragment;
    }

    public abstract void calcYarnRequired();

    // Calculations borrowed from http://www.thedietdiary.com/knittingfiend/tools/EstimatingYardageRectangles.html
    // copyright Lucia Liljegren 2005.
    public void calcYarnRequired(double siLength, double siWidth) {
        if (gauge <= 0) {
            yarnNeeded = 0;
            ballsNeeded = 0;
            return;
        }
        double siGauge = gauge;

        // First, put values into SI units
        if (gaugeUnits == GaugeUnits.stsPerInch) {
            siGauge *= 4;
        }

        // Change to stitches per cm
        siGauge /= 10;

        double siBallSize = (double)ballSize;
        if (ballSizeUnits == LongLengthUnits.yards) {
            siBallSize *= Project.yards2meters;
        }
        int stitches = (int)(Math.ceil(siGauge * siWidth));
        double rowGauge = siGauge * 1.5;
        int rows = (int)(Math.ceil(rowGauge * siLength));

        int totalStitches = stitches * (rows + 2); // 2 for cast on and bind off.

        // calculate meters and add 20%
        double meters = getStitchLength(siGauge, siWidth, siLength) * (double)totalStitches * 1.2;

        // Now convert the yarn required into the desired units
        if (yarnNeededUnits != LongLengthUnits.meters) {
            yarnNeeded = (int) Math.ceil(meters / Project.yards2meters);
        }
        else {
            yarnNeeded = (int) Math.ceil(meters);
        }

        // Now convert the yarn required into the desired units
        if (getYarnNeededUnits() != LongLengthUnits.meters) {
            yarnNeeded = (int) Math.ceil(meters / yards2meters);
        }
        else {
            yarnNeeded = (int) Math.ceil(meters);
        }

        ballsNeeded = meters / (double)siBallSize;
        if (!partialBalls) {
            ballsNeeded = Math.ceil(ballsNeeded);
        }
    }
    // Compute the length of a stitch in m, treating the row of stitches as a helix
    private double getStitchLength(double cmGauge, double cmWidth, double cmLength) {
        double stitchWidth = 1.0 / cmGauge;
        double stitchCir = Math.PI * stitchWidth;
        // The stitch actually goes halfway into the neighboring stitch on each side
        double span = 2.0 * stitchWidth;
        // use equation to calculate helical length, where the diameter is the stitchWidth and the
        // length is twice the stitchWidth, and convert to meters
        return Math.sqrt(span * span + stitchCir * stitchCir) / 100.0;
    }
    // Calculate the number of balls needed, taking into account the selected units
    public void calcBallsNeeded()
    {
        if (yarnNeededUnits == ballSizeUnits) {
            ballsNeeded = (int) Math.ceil((double) yarnNeeded / (double) ballSize);
        }
        else {
            double yarn = (yarnNeededUnits == LongLengthUnits.meters) ? yarnNeeded :
                    yarnNeeded * yards2meters;
            double ballMeters = (ballSizeUnits == LongLengthUnits.meters) ? ballSize :
                    ballSize * yards2meters;
            ballsNeeded = (int) Math.ceil(yarn / ballMeters);
        }
    }
    /* Get the settings for the project from SharedPreferences if available,
     * otherwise, get them from the json file asset.
     */
    public void getSettings(SharedPreferences preferences, JSONObject json) {
        float defGauge = 20;
        try {
            defGauge = (float)json.getDouble("gauge");
        }
        catch (JSONException e) {}
        gauge = preferences.getFloat("gauge", defGauge);

        int defGaugeUnits = 2;
        try {
            defGaugeUnits = json.getInt("gaugeUnits");
        }
        catch (JSONException e) {}
        gaugeUnits = GaugeUnits.fromInt(preferences.getInt("gaugeUnits", defGaugeUnits));

        int defYarnNeededUnits = 0;
        try {
            defYarnNeededUnits = json.getInt("yarnNeededUnits");
        }
        catch (JSONException e) {}
        yarnNeededUnits = LongLengthUnits.fromInt(preferences.getInt("yarnNeededUnits", defYarnNeededUnits));

        int defBallSize = 100;
        try {
            defBallSize = json.getInt("ballSize");
        }
        catch (JSONException e) {}
        ballSize = preferences.getInt("ballSize", defBallSize);

        int defBallSizeUnits = 0;
        try {
            defBallSizeUnits = json.getInt("ballSizeUnits");
        }
        catch (JSONException e) {}
        ballSizeUnits = LongLengthUnits.fromInt(preferences.getInt("ballSizeUnits", defBallSizeUnits));

        boolean defPartialBalls = false;
        try {
            defPartialBalls = json.getBoolean("partialBalls");
        }
        catch (JSONException e) {}
        partialBalls = preferences.getBoolean("partialBalls", defPartialBalls);
    }
}
