
package com.github.mikephil.charting.components;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.github.mikephil.charting.utils.Utils;

/**
 * The limit line is an additional feature for all Line-, Bar- and
 * ScatterCharts. It allows the displaying of an additional line in the chart
 * that marks a certain maximum / limit on the specified axis (x- or y-axis).
 * 
 * @author Philipp Jahoda
 */
public class LimitLine extends ComponentBase {

    /** limit / maximum (the y-value or xIndex) */
    private float mLimit = 0f;

    /** the width of the limit line */
    private float mLineWidth = 2f;

    /** the color of the limit line */
    private int mLineColor = Color.rgb(237, 91, 91);

    /** the style of the label text */
    private Paint.Style mTextStyle = Paint.Style.FILL_AND_STROKE;

    /** label string that is drawn next to the limit line */
    private String mLabel = "";

    /** the path effect of this LimitLine that makes dashed lines possible */
    private DashPathEffect mDashPathEffect = null;

    /** indicates the position of the LimitLine label */
    private LimitLabelPosition mLabelPosition = LimitLabelPosition.RIGHT_TOP;

    /** enum that indicates the position of the LimitLine label */
    public enum LimitLabelPosition {
        LEFT_TOP, LEFT_BOTTOM, RIGHT_TOP, RIGHT_BOTTOM
    }

    /**
     * Constructor with limit.
     * 
     * @param limit - the position (the value) on the y-axis (y-value) or x-axis
     *            (xIndex) where this line should appear
     */
    public LimitLine(float limit) {
        mLimit = limit;
    }

    /**
     * Returns the limit that is set for this line.
     * 
     * @return
     */
    public float getLimit() {
        return mLimit;
    }

    /**
     * returns the width of limit line
     * 
     * @return
     */
    public float getLineWidth() {
        return mLineWidth;
    }

    /**
     * Returns the color that is used for this LimitLine
     * 
     * @return
     */
    public int getLineColor() {
        return mLineColor;
    }

    /**
     * Disables the line to be drawn in dashed mode.
     */
    public void disableDashedLine() {
        mDashPathEffect = null;
    }

    /**
     * returns the DashPathEffect that is set for this LimitLine
     * 
     * @return
     */
    public DashPathEffect getDashPathEffect() {
        return mDashPathEffect;
    }

    /**
     * Returns the color of the value-text that is drawn next to the LimitLine.
     *
     * @return
     */
    public Paint.Style getTextStyle() {
        return mTextStyle;
    }

    /**
     * Returns the position of the LimitLine label (value).
     * 
     * @return
     */
    public LimitLabelPosition getLabelPosition() {
        return mLabelPosition;
    }

    /**
     * Sets the label that is drawn next to the limit line. Provide "" if no
     * label is required.
     * 
     * @param label
     */
    public void setLabel(String label) {
        mLabel = label;
    }

    /**
     * Returns the label that is drawn next to the limit line.
     * 
     * @return
     */
    public String getLabel() {
        return mLabel;
    }
}
