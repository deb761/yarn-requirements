package com.inqint.yarnrequirements.weights;

/**
 * Created by deb on 11/20/16.
 */

public class KnittingNeedle {
    private final double size;

    public KnittingNeedle(double size) {
        this.size = size;
    }

    public double getmm() {
        return size;
    }

    public String getus() {
        if (size <= 1.5) {
            return "000";
        }
        if (size <= 1.75) {
            return "00";
        }
        if (size <= 2) {
            return "0";
        }
        if (size <= 2.25) {
            return "1";
        }
        if (size <= 2.5) {
            return "1.5";
        }
        if (size <= 2.75) {
            return "2";
        }
        if (size <= 3) {
            return "2.5";
        }
        if (size <= 3.25) {
            return "3";
        }
        if (size <= 3.5) {
            return "4";
        }
        if (size <= 3.75) {
            return "5";
        }
        if (size <= 4) {
            return "6";
        }
        if (size <= 4.25) {
            return "6.5";
        }
        if (size <= 4.5) {
            return "7";
        }
        if (size <= 5) {
            return "8";
        }
        if (size <= 5.5) {
            return "9";
        }
        if (size <= 6) {
            return "10";
        }
        if (size <= 6.5) {
            return "10.5";
        }
        if (size <= 7) {
            return "10.75";
        }
        if (size <= 8) {
            return "11";
        }
        if (size <= 9) {
            return "13";
        }
        if (size <= 10) {
            return "15";
        }
        if (size <= 12) {
            return "17";
        }
        if (size <= 15) {
            return "19";
        }
        if (size <= 19) {
            return "35";
        }
        return "N/A";
    }
}
