package com.inqint.yarnrequirements.Projects;

import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by deb on 4/27/16.
 * This class calculates the yarn needed for socks of US and Euro shoe sizes.
 */
public class Socks extends Project {
    // Define an enumeration for all the shoe size units out there
    public enum ShoeSizeUnits {
         child, youth, women, men, euro;
        private static ShoeSizeUnits[] values = null;
        public static ShoeSizeUnits fromInt(int i) {
            if (ShoeSizeUnits.values == null) {
                ShoeSizeUnits.values = ShoeSizeUnits.values();
            }
            return ShoeSizeUnits.values[i];
        }
    }
    // The shoe size
    protected double size;
    // Type of shoe size
    protected ShoeSizeUnits sizeUnits;

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public ShoeSizeUnits getSizeUnits() {
        return sizeUnits;
    }

    public void setSizeUnits(ShoeSizeUnits sizeUnits) {
        this.sizeUnits = sizeUnits;
    }

    public Socks(String name, int thumbImageID, int imageID, Class<?> fragmentClass) {
        super(name, thumbImageID, imageID, fragmentClass);
    }

    @Override
    public void calcYarnRequired() {
        double length = 0.0;
        switch (sizeUnits) {
            case child:
                length = getLength(usChild, size);
                break;
            case youth:
                length = getLength(usYouth, size);
                break;
            case women:
                length = getLength(usWomen, size);
                break;
            case men:
                length = getLength(usMen, size);
                break;
            case euro:
                length = getLength(eu, size);
                break;
        }
        // I calculated a least-squares fit of foot length to circumference,
        // with a decent result for most sizes, although the really big feet
        // are underestimated a bit
        final double intercept = 4.8250;
        final double slope = 0.6263;
        double width = intercept + length * slope;

        // We'll approximate the length of the sock as 1.75 times the length of the foot,
        // and add 0.5 * width for the the heel turn
        // and there are 2 feet, so multiply width by 2
        calcYarnRequired(length * 1.75 - 0.5 * width, width * 2);
    }

    /* Get the settings for the project from SharedPreferences if available,
     * otherwise, get them from the json file asset.
     */
    public void getSettings(SharedPreferences preferences, JSONObject json) {
        super.getSettings(preferences, json);

        float defSize = 8;
        try {
            defSize = (float)json.getDouble("size");
        }
        catch (JSONException e) {}
        size = preferences.getFloat("size", defSize);

        int defSizeUnits = ShoeSizeUnits.euro.ordinal();
        try {
            defSizeUnits = json.getInt("sizeUnits");
        }
        catch (JSONException e) {}
        sizeUnits = ShoeSizeUnits.fromInt(preferences.getInt("sizeUnits", defSizeUnits));
    }

    private HashMap<Integer, Double> usChild;
    private HashMap<Integer, Double> usYouth;
    private HashMap<Integer, Double> usMen;
    private HashMap<Integer, Double> usWomen;
    private HashMap<Integer, Double> eu;

    // Get the foot size tables from the json file asset
    public void getFootSizes(JSONObject json) {
        try {
            usChild = getSizes(json.getJSONArray("usChild"));
            usYouth = getSizes(json.getJSONArray("usYouth"));
            usMen = getSizes(json.getJSONArray("usMen"));
            usWomen = getSizes(json.getJSONArray("usWomen"));
            eu = getSizes(json.getJSONArray("eu"));
        }
        catch (JSONException e) {}
    }

    private HashMap<Integer, Double> getSizes(JSONArray jsonArray) {
        HashMap<Integer, Double> sizes = new HashMap<>();

        for (int idx = 0; idx < jsonArray.length(); idx++) {
            try {
                JSONObject object = jsonArray.getJSONObject(idx);

                int size = object.getInt("size");
                double length = object.getDouble("length");

                sizes.put(new Integer(size), new Double(length));

            } catch (JSONException e) {
                continue;
            }
        }
        return sizes;
    }

    // Perform a linear interpolation between the input size and the next
    // highest to get foot length in cm
    private double getLength(HashMap<Integer, Double> table, double shoeSize) {
        {
            double lowSized = Math.floor(shoeSize);
            int lowSize = (int) lowSized;

            Double lowLength = table.get(new Integer(lowSize));
            if ((lowSized == shoeSize) && lowLength != null) {
                return lowLength;
            }
            int highSize = lowSize + 1;
            // Check for the US youth size rollover from 13 to 1
            if ((sizeUnits == ShoeSizeUnits.youth) && (lowSize == 13)) {
                highSize = 1;
            }
            Double highLength = table.get(new Integer(highSize));
            if (lowLength != null && highLength != null) {
                // This is a simple linear interpolation that is simplified a bit
                // because the difference in sizes is always 1
                double length = (shoeSize - lowSized) * (highLength - lowLength) + lowLength;
                return length;
            }
        }
        // We only get to this point if we couldn't find a size, so return default
        return 24; // about a US Women's size 8
    }


}
