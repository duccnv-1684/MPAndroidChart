package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.data.RadarEntry;

/**
 * Class to format all values before they are drawn as labels.
 */
public abstract class ValueFormatter {

    /**
     * Called when drawing any label, used to change numbers into formatted strings.
     *
     * @param value float to be formatted
     * @return formatted string label
     */
    protected String getFormattedValue(float value) {
        return String.valueOf(value);
    }

    /**
     * Used to draw axis labels, calls {@link #getFormattedValue(float)} by default.
     *
     * @param value float to be formatted
     * @return formatted string label
     */
    public String getAxisLabel(float value) {
        return getFormattedValue(value);
    }

    /**
     * Used to draw radar value labels, calls {@link #getFormattedValue(float)} by default.
     *
     * @param radarEntry entry being labeled
     * @return formatted string label
     */
    public String getRadarLabel(RadarEntry radarEntry) {
        return getFormattedValue(radarEntry.getY());
    }

}
