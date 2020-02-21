
package com.github.mikephil.charting.components;

import android.graphics.Color;
import android.graphics.DashPathEffect;

/**
 * The limit line is an additional feature for all Line-, Bar- and
 * ScatterCharts. It allows the displaying of an additional line in the chart
 * that marks a certain maximum / limit on the specified axis (x- or y-axis).
 * 
 * @author Philipp Jahoda
 */
public class LimitLine extends ComponentBase {

    /** the color of the limit line */
    private final int mLineColor = Color.rgb(237, 91, 91);

    /** the path effect of this LimitLine that makes dashed lines possible */

    /**
     * Returns the limit that is set for this line.
     * 
     * @return
     */
    public float getLimit() {
        return 0f;
    }

    /**
     * returns the width of limit line
     * 
     * @return
     */
    public float getLineWidth() {
        return 2f;
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
     * returns the DashPathEffect that is set for this LimitLine
     * 
     * @return
     */
    public DashPathEffect getDashPathEffect() {
        return null;
    }

}
