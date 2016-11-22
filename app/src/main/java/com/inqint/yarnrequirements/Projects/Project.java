package com.inqint.yarnrequirements.Projects;

/**
 * Created by deb on 4/25/16.
 */
/* Enumeration for the gauge units: stitches per inch, 4 inches, or 10 cm */
public enum GaugeUnits { stsPerInch, stsPer4inch, stsPer10cm;
    private static GaugeUnits[] values = null;
    public static GaugeUnits fromInt(int i) {
        if (GaugeUnits.values == null) {
            GaugeUnits.values = GaugeUnits.values();
        }
        return GaugeUnits.values[i];
    }
}
/* Enumeration for shorter units: inches or cm */
public enum ShortLengthUnits { inches, cm;
    private static ShortLengthUnits[] values = null;
    public static ShortLengthUnits fromInt(int i) {
        if (ShortLengthUnits.values == null) {
            ShortLengthUnits.values = ShortLengthUnits.values();
        }
        return ShortLengthUnits.values[i];
    }
}
/* Enumeration for longer lengths: yards or meters */
public enum LongLengthUnits { yards, meters;
    private static LongLengthUnits[] values = null;
    public static LongLengthUnits fromInt(int i) {
        if (LongLengthUnits.values == null) {
            LongLengthUnits.values = LongLengthUnits.values();
        }
        return LongLengthUnits.values[i];
    }
}
/* The basic definition of a knitting project.  This class is extended for each project type. */
public abstract class Project {
    private String name;
    private double gauge;
    private GaugeUnits gaugeUnits;
    private int thumbImageID;
    private int imageID;
    private Class<?> aClass;
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

    public Class<?> getaClass() {
        return aClass;
    }

    public void setaClass(Class<?> aClass) {
        this.aClass = aClass;
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
    public Project(String name, int thumbImageID, Class<?> aClass)
    {
        this();
        this.name = name;
        this.thumbImageID = thumbImageID;
        this.aClass = aClass;
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
}
