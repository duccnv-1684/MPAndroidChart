package com.github.mikephil.charting.components;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.util.Log;

import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Base-class of all axes (previously called labels).
 *
 * @author Philipp Jahoda
 */
public abstract class AxisBase extends ComponentBase {

    /**
     * custom formatter that is used instead of the auto-formatter if set
     */
    protected ValueFormatter mAxisValueFormatter;

    private int mGridColor = Color.GRAY;

    private float mGridLineWidth = 1f;

    private int mAxisLineColor = Color.GRAY;

    private float mAxisLineWidth = 1f;

    /**
     * the actual array of entries
     */
    public float[] mEntries = new float[]{};

    /**
     * axis label entries only used for centered labels
     */
    public float[] mCenteredEntries = new float[]{};

    /**
     * the number of entries the legend contains
     */
    public int mEntryCount;

    /**
     * the number of decimal digits to use
     */
    public int mDecimals;

    /**
     * the number of label entries the axis should have, default 6
     */
    private int mLabelCount = 6;

    /**
     * the minimum interval between axis values
     */
    protected float mGranularity = 1.0f;

    /**
     * When true, axis labels are controlled by the `granularity` property.
     * When false, axis values could possibly be repeated.
     * This could happen if two adjacent axis values are rounded to same value.
     * If using granularity this could be avoided by having fewer axis values visible.
     */
    protected boolean mGranularityEnabled = false;

    /**
     * if true, the set number of y-labels will be forced
     */
    protected boolean mForceLabels = false;

    /**
     * flag indicating if the grid lines for this axis should be drawn
     */
    protected boolean mDrawGridLines = true;

    /**
     * flag that indicates if the line alongside the axis is drawn or not
     */
    protected boolean mDrawAxisLine = true;

    /**
     * flag that indicates of the labels of this axis should be drawn or not
     */
    protected boolean mDrawLabels = true;

    protected boolean mCenterAxisLabels = false;

    /**
     * the path effect of the axis line that makes dashed lines possible
     */
    private DashPathEffect mAxisLineDashPathEffect = null;

    /**
     * the path effect of the grid lines that makes dashed lines possible
     */
    private DashPathEffect mGridDashPathEffect = null;

    /**
     * array of limit lines that can be set for the axis
     */
    protected List<LimitLine> mLimitLines;

    /**
     * flag indicating the limit lines layer depth
     */
    protected boolean mDrawLimitLineBehindData = false;

    /**
     * flag indicating the grid lines layer depth
     */
    protected boolean mDrawGridLinesBehindData = true;

    /**
     * Extra spacing for `axisMinimum` to be added to automatically calculated `axisMinimum`
     */
    protected float mSpaceMin = 0.f;

    /**
     * Extra spacing for `axisMaximum` to be added to automatically calculated `axisMaximum`
     */
    protected float mSpaceMax = 0.f;

    /**
     * flag indicating that the axis-min value has been customized
     */
    protected boolean mCustomAxisMin = false;

    /**
     * flag indicating that the axis-max value has been customized
     */
    protected boolean mCustomAxisMax = false;

    /**
     * don't touch this direclty, use setter
     */
    public float mAxisMaximum = 0f;

    /**
     * don't touch this directly, use setter
     */
    public float mAxisMinimum = 0f;

    /**
     * the total range of values this axis covers
     */
    public float mAxisRange = 0f;

    private int mAxisMinLabels = 2;
    private int mAxisMaxLabels = 25;

    /**
     * The minumum number of labels on the axis
     */
    public int getAxisMinLabels() {
        return mAxisMinLabels;
    }

    /**
     * The maximum number of labels on the axis
     */
    public int getAxisMaxLabels() {
        return mAxisMaxLabels;
    }

    /**
     * default constructor
     */
    public AxisBase() {
        this.mTextSize = Utils.convertDpToPixel(10f);
        this.mXOffset = Utils.convertDpToPixel(5f);
        this.mYOffset = Utils.convertDpToPixel(5f);
        this.mLimitLines = new ArrayList<LimitLine>();
    }

    /**
     * Returns true if drawing grid lines is enabled for this axis.
     *
     * @return
     */
    public boolean isDrawGridLinesEnabled() {
        return mDrawGridLines;
    }

    /**
     * Returns true if the line alongside the axis should be drawn.
     *
     * @return
     */
    public boolean isDrawAxisLineEnabled() {
        return mDrawAxisLine;
    }

    public boolean isCenterAxisLabelsEnabled() {
        return mCenterAxisLabels && mEntryCount > 0;
    }

    /**
     * Returns the color of the grid lines for this axis (the horizontal lines
     * coming from each label).
     *
     * @return
     */
    public int getGridColor() {
        return mGridColor;
    }

    /**
     * Returns the width of the axis line (line alongside the axis).
     *
     * @return
     */
    public float getAxisLineWidth() {
        return mAxisLineWidth;
    }

    /**
     * Returns the width of the grid lines that are drawn away from each axis
     * label.
     *
     * @return
     */
    public float getGridLineWidth() {
        return mGridLineWidth;
    }

    /**
     * Returns the color of the axis line (line alongside the axis).
     *
     * @return
     */
    public int getAxisLineColor() {
        return mAxisLineColor;
    }

    /**
     * Set this to true to enable drawing the labels of this axis (this will not
     * affect drawing the grid lines or axis lines).
     *
     * @param enabled
     */
    public void setDrawLabels(boolean enabled) {
        mDrawLabels = enabled;
    }

    /**
     * Returns true if drawing the labels is enabled for this axis.
     *
     * @return
     */
    public boolean isDrawLabelsEnabled() {
        return mDrawLabels;
    }

    /**
     * Sets the number of label entries for the y-axis max = 25, min = 2, default: 6, be aware
     * that this number is not fixed.
     *
     * @param count the number of y-axis labels that should be displayed
     */
    public void setLabelCount(int count) {

        if (count > getAxisMaxLabels())
            count = getAxisMaxLabels();
        if (count < getAxisMinLabels())
            count = getAxisMinLabels();

        mLabelCount = count;
        mForceLabels = false;
    }

    /**
     * sets the number of label entries for the y-axis max = 25, min = 2, default: 6, be aware
     * that this number is not
     * fixed (if force == false) and can only be approximated.
     *
     * @param count the number of y-axis labels that should be displayed
     * @param force if enabled, the set label count will be forced, meaning that the exact
     *              specified count of labels will
     *              be drawn and evenly distributed alongside the axis - this might cause labels
     *              to have uneven values
     */
    public void setLabelCount(int count, boolean force) {

        setLabelCount(count);
        mForceLabels = force;
    }

    /**
     * Returns true if focing the y-label count is enabled. Default: false
     *
     * @return
     */
    public boolean isForceLabelsEnabled() {
        return mForceLabels;
    }

    /**
     * Returns the number of label entries the y-axis should have
     *
     * @return
     */
    public int getLabelCount() {
        return mLabelCount;
    }

    /**
     * @return true if granularity is enabled
     */
    public boolean isGranularityEnabled() {
        return mGranularityEnabled;
    }

    /**
     * @return the minimum interval between axis values
     */
    public float getGranularity() {
        return mGranularity;
    }

    /**
     * Returns the LimitLines of this axis.
     *
     * @return
     */
    public List<LimitLine> getLimitLines() {
        return mLimitLines;
    }

    public boolean isDrawLimitLinesBehindDataEnabled() {
        return mDrawLimitLineBehindData;
    }

    /**
     * Returns the longest formatted label (in terms of characters), this axis
     * contains.
     *
     * @return
     */
    public String getLongestLabel() {

        String longest = "";

        for (int i = 0; i < mEntries.length; i++) {
            String text = getFormattedLabel(i);

            if (text != null && longest.length() < text.length())
                longest = text;
        }

        return longest;
    }

    public String getFormattedLabel(int index) {

        if (index < 0 || index >= mEntries.length)
            return "";
        else
            return getValueFormatter().getAxisLabel(mEntries[index], this);
    }

    /**
     * Sets the formatter to be used for formatting the axis labels. If no formatter is set, the
     * chart will
     * automatically determine a reasonable formatting (concerning decimals) for all the values
     * that are drawn inside
     * the chart. Use chart.getDefaultValueFormatter() to use the formatter calculated by the chart.
     *
     * @param f
     */
    public void setValueFormatter(ValueFormatter f) {

        if (f == null)
            mAxisValueFormatter = new DefaultAxisValueFormatter(mDecimals);
        else
            mAxisValueFormatter = f;
    }

    /**
     * Returns the formatter used for formatting the axis labels.
     *
     * @return
     */
    public ValueFormatter getValueFormatter() {

        if (mAxisValueFormatter == null ||
                (mAxisValueFormatter instanceof DefaultAxisValueFormatter &&
                        ((DefaultAxisValueFormatter)mAxisValueFormatter).getDecimalDigits() != mDecimals))
            mAxisValueFormatter = new DefaultAxisValueFormatter(mDecimals);

        return mAxisValueFormatter;
    }

    /**
     * returns the DashPathEffect that is set for grid line
     *
     * @return
     */
    public DashPathEffect getGridDashPathEffect() {
        return mGridDashPathEffect;
    }


    /**
     * returns the DashPathEffect that is set for axis line
     *
     * @return
     */
    public DashPathEffect getAxisLineDashPathEffect() {
        return mAxisLineDashPathEffect;
    }

    /**
     * By calling this method, any custom minimum value that has been previously set is reseted,
     * and the calculation is
     * done automatically.
     */
    public void resetAxisMinimum() {
        mCustomAxisMin = false;
    }

    /**
     * Set a custom minimum value for this axis. If set, this value will not be calculated
     * automatically depending on
     * the provided data. Use resetAxisMinValue() to undo this. Do not forget to call
     * setStartAtZero(false) if you use
     * this method. Otherwise, the axis-minimum value will still be forced to 0.
     *
     * @param min
     */
    public void setAxisMinimum(float min) {
        mCustomAxisMin = true;
        mAxisMinimum = min;
        this.mAxisRange = Math.abs(mAxisMaximum - min);
    }

    /**
     * Use setAxisMinimum(...) instead.
     *
     * @param min
     */
    @Deprecated
    public void setAxisMinValue(float min) {
        setAxisMinimum(min);
    }

    /**
     * Set a custom maximum value for this axis. If set, this value will not be calculated
     * automatically depending on
     * the provided data. Use resetAxisMaxValue() to undo this.
     *
     * @param max
     */
    public void setAxisMaximum(float max) {
        mCustomAxisMax = true;
        mAxisMaximum = max;
        this.mAxisRange = Math.abs(max - mAxisMinimum);
    }

    /**
     * Use setAxisMaximum(...) instead.
     *
     * @param max
     */
    @Deprecated
    public void setAxisMaxValue(float max) {
        setAxisMaximum(max);
    }

    /**
     * Calculates the minimum / maximum  and range values of the axis with the given
     * minimum and maximum values from the chart data.
     *
     * @param dataMin the min value according to chart data
     * @param dataMax the max value according to chart data
     */
    public void calculate(float dataMin, float dataMax) {

        // if custom, use value as is, else use data value
        float min = mCustomAxisMin ? mAxisMinimum : (dataMin - mSpaceMin);
        float max = mCustomAxisMax ? mAxisMaximum : (dataMax + mSpaceMax);

        // temporary range (before calculations)
        float range = Math.abs(max - min);

        // in case all values are equal
        if (range == 0f) {
            max = max + 1f;
            min = min - 1f;
        }

        this.mAxisMinimum = min;
        this.mAxisMaximum = max;

        // actual range
        this.mAxisRange = Math.abs(max - min);
    }

}
