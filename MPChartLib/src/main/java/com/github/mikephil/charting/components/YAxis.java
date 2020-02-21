package com.github.mikephil.charting.components;

import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.utils.Utils;

/**
 * Class representing the y-axis labels settings and its entries. Only use the setter methods to
 * modify it. Do not
 * access public variables directly. Be aware that not all features the YLabels class provides
 * are suitable for the
 * RadarChart. Customizations that affect the value range of the axis need to be applied before
 * setting data for the
 * chart.
 *
 * @author Philipp Jahoda
 */
public class YAxis extends AxisBase {

    /**
     * indicates if the bottom y-label entry is drawn or not
     */
    private boolean mDrawBottomYLabelEntry = true;

    /**
     * indicates if the top y-label entry is drawn or not
     */
    private boolean mDrawTopYLabelEntry = true;

    /**
     * flag that indicates if the axis is inverted or not
     */
    protected boolean mInverted = false;

    /**
     * flag that indicates if the zero-line should be drawn regardless of other grid lines
     */
    protected boolean mDrawZeroLine = false;

    /**
     * flag indicating that auto scale min restriction should be used
     */
    private boolean mUseAutoScaleRestrictionMin = false;

    /**
     * flag indicating that auto scale max restriction should be used
     */
    private boolean mUseAutoScaleRestrictionMax = false;

    /**
     * Color of the zero line
     */
    protected int mZeroLineColor = Color.GRAY;

    /**
     * Width of the zero line in pixels
     */
    protected float mZeroLineWidth = 1f;

    /**
     * axis space from the largest value to the top in percent of the total axis range
     */
    protected float mSpacePercentTop = 10f;

    /**
     * axis space from the smallest value to the bottom in percent of the total axis range
     */
    protected float mSpacePercentBottom = 10f;

    /**
     * the position of the y-labels relative to the chart
     */
    private YAxisLabelPosition mPosition = YAxisLabelPosition.OUTSIDE_CHART;

    /**
     * the horizontal offset of the y-label
     */
    private float mXLabelOffset = 0.0f;

    /**
     * enum for the position of the y-labels relative to the chart
     */
    public enum YAxisLabelPosition {
        OUTSIDE_CHART, INSIDE_CHART
    }

    /**
     * the side this axis object represents
     */
    private AxisDependency mAxisDependency;

    /**
     * Enum that specifies the axis a DataSet should be plotted against, either LEFT or RIGHT.
     *
     * @author Philipp Jahoda
     */
    public enum AxisDependency {
        LEFT, RIGHT
    }

    public YAxis() {
        super();

        // default left
        this.mAxisDependency = AxisDependency.LEFT;
        this.mYOffset = 0f;
    }

    public YAxis(AxisDependency position) {
        super();
        this.mAxisDependency = position;
        this.mYOffset = 0f;
    }

    public AxisDependency getAxisDependency() {
        return mAxisDependency;
    }

    /**
     * returns the position of the y-labels
     */
    public YAxisLabelPosition getLabelPosition() {
        return mPosition;
    }

    /**
     * sets the position of the y-labels
     *
     * @param pos
     */
    public void setPosition(YAxisLabelPosition pos) {
        mPosition = pos;
    }

    /**
     * returns the horizontal offset of the y-label
     */
    public float getLabelXOffset() {
        return mXLabelOffset;
    }

    /**
     * sets the horizontal offset of the y-label
     *
     * @param xOffset
     */
    public void setLabelXOffset(float xOffset) {
        mXLabelOffset = xOffset;
    }

    /**
     * returns true if drawing the top y-axis label entry is enabled
     *
     * @return
     */
    public boolean isDrawTopYLabelEntryEnabled() {
        return mDrawTopYLabelEntry;
    }

    /**
     * returns true if drawing the bottom y-axis label entry is enabled
     *
     * @return
     */
    public boolean isDrawBottomYLabelEntryEnabled() {
        return mDrawBottomYLabelEntry;
    }

    /**
     * If this returns true, the y-axis is inverted.
     *
     * @return
     */
    public boolean isInverted() {
        return mInverted;
    }

    /**
     * This method is deprecated.
     * Use setAxisMinimum(...) / setAxisMaximum(...) instead.
     *
     * @param startAtZero
     */
    @Deprecated
    public void setStartAtZero(boolean startAtZero) {
        if (startAtZero)
            setAxisMinimum(0f);
        else
            resetAxisMinimum();
    }

    /**
     * Returns the top axis space in percent of the full range. Default 10f
     *
     * @return
     */
    public float getSpaceTop() {
        return mSpacePercentTop;
    }

    /**
     * Returns the bottom axis space in percent of the full range. Default 10f
     *
     * @return
     */
    public float getSpaceBottom() {
        return mSpacePercentBottom;
    }

    public boolean isDrawZeroLineEnabled() {
        return mDrawZeroLine;
    }

    public int getZeroLineColor() {
        return mZeroLineColor;
    }

    public float getZeroLineWidth() {
        return mZeroLineWidth;
    }

    /**
     * Returns true if autoscale restriction for axis min value is enabled
     */
    @Deprecated
    public boolean isUseAutoScaleMinRestriction( ) {
        return mUseAutoScaleRestrictionMin;
    }

    /**
     * Sets autoscale restriction for axis min value as enabled/disabled
     */
    @Deprecated
    public void setUseAutoScaleMinRestriction( boolean isEnabled ) {
        mUseAutoScaleRestrictionMin = isEnabled;
    }

    /**
     * Returns true if autoscale restriction for axis max value is enabled
     */
    @Deprecated
    public boolean isUseAutoScaleMaxRestriction() {
        return mUseAutoScaleRestrictionMax;
    }

    /**
     * Sets autoscale restriction for axis max value as enabled/disabled
     */
    @Deprecated
    public void setUseAutoScaleMaxRestriction( boolean isEnabled ) {
        mUseAutoScaleRestrictionMax = isEnabled;
    }


    @Override
    public void calculate(float dataMin, float dataMax) {

        float min = dataMin;
        float max = dataMax;

        // Make sure max is greater than min
        // Discussion: https://github.com/danielgindi/Charts/pull/3650#discussion_r221409991
        if (min > max)
        {
            if (mCustomAxisMax && mCustomAxisMin)
            {
                float t = min;
                min = max;
                max = t;
            }
            else if (mCustomAxisMax)
            {
                min = max < 0f ? max * 1.5f : max * 0.5f;
            }
            else if (mCustomAxisMin)
            {
                max = min < 0f ? min * 0.5f : min * 1.5f;
            }
        }

        float range = Math.abs(max - min);

        // in case all values are equal
        if (range == 0f) {
            max = max + 1f;
            min = min - 1f;
        }

        // recalculate
        range = Math.abs(max - min);

        // calc extra spacing
        this.mAxisMinimum = mCustomAxisMin ? this.mAxisMinimum : min - (range / 100f) * getSpaceBottom();
        this.mAxisMaximum = mCustomAxisMax ? this.mAxisMaximum : max + (range / 100f) * getSpaceTop();

        this.mAxisRange = Math.abs(this.mAxisMinimum - this.mAxisMaximum);
    }
}
