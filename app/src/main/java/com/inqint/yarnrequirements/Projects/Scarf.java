package com.inqint.yarnrequirements.Projects;

/**
 * Created by deb on 4/27/16.
 */
public class Scarf extends Project {

    private double length;
    private double width;
    private ShortLengthUnits lengthUnits;

    private ShortLengthUnits widthUnits;

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public ShortLengthUnits getLengthUnits() {
        return lengthUnits;
    }

    public void setLengthUnits(ShortLengthUnits units) {
        this.lengthUnits = units;
    }

    public ShortLengthUnits getWidthUnits() {
        return widthUnits;
    }

    public void setWidthUnits(ShortLengthUnits widthUnits) {
        this.widthUnits = widthUnits;
    }

    public Scarf(String name, int thumbImageID, Class<?> aClass) {
        super(name, thumbImageID, aClass);
    }

    @Override
    public void calcYarnRequired()
    {
        double siLength = length;
        if (getLengthUnits() != ShortLengthUnits.cm)
        {
            siLength = length * inches2cm;
        }
        double siWidth = width;
        if (getWidthUnits() != ShortLengthUnits.cm)
        {
            siWidth = width * inches2cm;
        }
        super.calcYarnRequired(siLength, siWidth);
    }

}
