package com.inqint.yarnrequirements.Projects;

import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by deb on 11/25/16.
 *
 * Handle the basics for all the projects that take just a size to estimate yarn
 */

public abstract class SizeProject extends Project {
    protected double size;

    protected ShortLengthUnits sizeUnits;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public ShortLengthUnits getSizeUnits() {
        return sizeUnits;
    }

    public void setSizeUnits(ShortLengthUnits sizeUnits) {
        this.sizeUnits = sizeUnits;
    }

    public SizeProject(String name, int thumbImageID, int imageID, Class<?> fragment) {
        super(name, thumbImageID, imageID, fragment);
    }

    /* Get the settings for the project from SharedPreferences if available,
     * otherwise, get them from the json file asset.
     */
    public void getSettings(SharedPreferences preferences, JSONObject json) {
        super.getSettings(preferences, json);

        float defSize = 0;
        try {
            defSize = (float)json.getDouble("size");
        }
        catch (JSONException e) {}
        size = preferences.getFloat("size", defSize);

        int defSizeUnits = 2;
        try {
            defSizeUnits = json.getInt("sizeUnits");
        }
        catch (JSONException e) {}
        sizeUnits = ShortLengthUnits.fromInt(preferences.getInt("sizeUnits", defSizeUnits));
    }
}

