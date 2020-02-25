package com.github.mikephil.charting.data;

import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

/**
 * Created by Philipp Jahoda on 21/10/15.
 */
public interface IRadarDataSet {

    /**
     * returns the minimum y-value this DataSet holds
     *
     * @return
     */
    float getYMin();

    /**
     * returns the maximum y-value this DataSet holds
     *
     * @return
     */
    float getYMax();

    /**
     * returns the minimum x-value this DataSet holds
     *
     * @return
     */
    float getXMin();

    /**
     * returns the maximum x-value this DataSet holds
     *
     * @return
     */
    float getXMax();

    /**
     * Returns the number of y-values this DataSet represents -> the size of the y-values array
     * -> yvals.size()
     *
     * @return
     */
    int getEntryCount();

    /**
     * Calculates the minimum and maximum x and y values (mXMin, mXMax, mYMin, mYMax).
     */
    void calcMinMax();


    /**
     * Returns the Entry object found at the given index (NOT xIndex) in the values array.
     *
     * @param index
     * @return
     */
    RadarEntry getEntryForIndex(int index);

    /**
     * Returns the label string that describes the DataSet.
     *
     * @return
     */
    String getLabel();

    /**
     * Returns the axis this DataSet should be plotted against.
     *
     * @return
     */
    YAxis.AxisDependency getAxisDependency();

    /**
     * returns all the colors that are set for this DataSet
     *
     * @return
     */
    List<Integer> getColors();

    /**
     * Returns the color at the given index of the DataSet's color array.
     * Performs a IndexOutOfBounds check by modulus.
     *
     * @param index
     * @return
     */
    int getColor(int index);

    /**
     * Sets the formatter to be used for drawing the values inside the chart. If
     * no formatter is set, the chart will automatically determine a reasonable
     * formatting (concerning decimals) for all the values that are drawn inside
     * the chart. Use chart.getDefaultValueFormatter() to use the formatter
     * calculated by the chart.
     *
     * @param f
     */
    void setValueFormatter(ValueFormatter f);

    /**
     * Returns the formatter used for drawing the values inside the chart.
     *
     * @return
     */
    ValueFormatter getValueFormatter();

    /**
     * Returns true if the valueFormatter object of this DataSet is null.
     *
     * @return
     */
    boolean needsFormatter();

    /**
     * Sets the color the value-labels of this DataSet should have.
     *
     * @param color
     */
    void setValueTextColor(int color);

    /**
     * Sets a Typeface for the value-labels of this DataSet.
     *
     * @param tf
     */
    void setValueTypeface(Typeface tf);

    /**
     * Sets the text-size of the value-labels of this DataSet in dp.
     *
     * @param size
     */
    void setValueTextSize(float size);

    /**
     * Returns the color at the specified index that is used for drawing the values inside the chart.
     * Uses modulus internally.
     *
     * @param index
     * @return
     */
    int getValueTextColor(int index);

    /**
     * Returns the typeface that is used for drawing the values inside the chart
     *
     * @return
     */
    Typeface getValueTypeface();

    /**
     * Returns the text size that is used for drawing the values inside the chart
     *
     * @return
     */
    float getValueTextSize();

    /**
     * The form to draw for this dataset in the legend.
     * <p/>
     * Return `DEFAULT` to use the default legend form.
     */
    Legend.LegendForm getForm();

    /**
     * The form size to draw for this dataset in the legend.
     * <p/>
     * Return `Float.NaN` to use the default legend form size.
     */
    float getFormSize();

    /**
     * The line width for drawing the form of this dataset in the legend
     * <p/>
     * Return `Float.NaN` to use the default legend form line width.
     */
    float getFormLineWidth();

    /**
     * The line dash path effect used for shapes that consist of lines.
     * <p/>
     * Return `null` to use the default legend form line dash effect.
     */
    DashPathEffect getFormLineDashEffect();

    /**
     * set this to true to draw y-values on the chart.
     *
     * NOTE (for bar and line charts): if `maxVisibleCount` is reached, no values will be drawn even
     * if this is enabled
     * @param enabled
     */
    void setDrawValues(boolean enabled);

    /**
     * Returns true if y-value drawing is enabled, false if not
     *
     * @return
     */
    boolean isDrawValuesEnabled();

    /**
     * Returns true if y-icon drawing is enabled, false if not
     *
     * @return
     */
    boolean isDrawIconsEnabled();

    /**
     * Get the offset for drawing icons.
     */
    MPPointF getIconsOffset();

    /**
     * Returns true if this DataSet is visible inside the chart, or false if it
     * is currently hidden.
     *
     * @return
     */
    boolean isVisible();

    /**
     * Returns the color that is used for filling the line surface area.
     *
     * @return
     */
    int getFillColor();

    /**
     * Returns the drawable used for filling the area below the line.
     *
     * @return
     */
    Drawable getFillDrawable();

    /**
     * Returns the alpha value that is used for filling the line surface,
     * default: 85
     *
     * @return
     */
    int getFillAlpha();

    /**
     * Returns the stroke-width of the drawn line
     *
     * @return
     */
    float getLineWidth();

    /**
     * Returns true if filled drawing is enabled, false if not
     *
     * @return
     */
    boolean isDrawFilledEnabled();

}
