package com.inqint.yarnrequirements.Projects;

import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deb on 11/25/16.
 */

public abstract class DimensionProject extends Project {

    protected double length;
    protected double width;
    protected ShortLengthUnits lengthUnits;

    protected ShortLengthUnits widthUnits;

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

    public DimensionProject(String name, int thumbImageID, int imageID, Class<?> fragmentClass) {
        super(name, thumbImageID, imageID, fragmentClass);
    }


    /* Get the settings for the project from SharedPreferences if available,
     * otherwise, get them from the json file asset.
     */
    public void getSettings(SharedPreferences preferences, JSONObject json) {
        super.getSettings(preferences, json);

        float defLength = 0;
        try {
            defLength = (float)json.getDouble("length");
        }
        catch (JSONException e) {}
        length = preferences.getFloat("length", defLength);

        int defLengthUnits = 0;
        try {
            defLengthUnits = json.getInt("lengthUnits");
        }
        catch (JSONException e) {}
        lengthUnits = ShortLengthUnits.fromInt(preferences.getInt("lengthUnits", defLengthUnits));

        float defWidth = 0;
        try {
            defWidth = (float)json.getDouble("width");
        }
        catch (JSONException e) {}
        width = preferences.getFloat("width", defWidth);

        int defWidthUnits = 0;
        try {
            defWidthUnits = json.getInt("widthUnits");
        }
        catch (JSONException e) {}
        widthUnits = ShortLengthUnits.fromInt(preferences.getInt("widthUnits", defWidthUnits));
    }
}
