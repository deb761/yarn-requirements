package com.inqint.yarnrequirements.weights;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample yarn weights for user interfaces created by
 * Android template wizards.
 * <p>
 */
public class WeightContent {

    /**
     * An array of sample (weight) items.
     */
    public static final List<WeightItem> ITEMS = new ArrayList<WeightItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, WeightItem> ITEM_MAP = new HashMap<String, WeightItem>();

    public WeightContent(String json) {
        try
        {
            JSONArray jsonarray = new JSONArray(json);

            for (int idx = 0; idx < jsonarray.length(); idx++) {
                WeightItem item = new WeightItem(jsonarray.getJSONObject(idx));
                ITEMS.add(idx, item);
                ITEM_MAP.put(item.name, item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore gauge information here.");
        }
        return builder.toString();
    }

    /**
     * An item containing information about a weight of yarn.
     */
    public static class WeightItem {
        public final String name;    // yarn thickness description
        public final KnittingNeedle[] needles; // suggested needle sizes in US and international units
        public final double[] gauge; // Stitches per 4" or 10cm
        public final double[] wpi; // Windings per inch
        public final int length; // Density per 50 gm for wool
        public final int weight; // ball weight in grams

        public WeightItem(JSONObject jsonObject) throws JSONException {
            this.name = jsonObject.getString("name");
            JSONArray array = jsonObject.getJSONArray("needles");
            this.needles = new KnittingNeedle[array.length()];
            for (int idx = 0; idx < needles.length; idx++) {
                needles[idx] = new KnittingNeedle(array.getDouble(idx));
            }
            array = jsonObject.getJSONArray("gauge");
            this.gauge = new double[array.length()];
            for (int idx = 0; idx < gauge.length; idx++) {
                gauge[idx] = array.getDouble(idx);
            }
            array = jsonObject.getJSONArray("windings");
            this.wpi = new double[array.length()];
            for (int idx = 0; idx < wpi.length; idx++) {
                wpi[idx] = array.getDouble(idx);
            }

            this.length = jsonObject.getInt("length");
            this.weight = jsonObject.getInt("weight");
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
