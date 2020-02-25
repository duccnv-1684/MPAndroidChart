
package com.github.mikephil.charting.data;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Base dataset for line and radar DataSets.
 *
 * @author Philipp Jahoda
 */
public class RadarDataSet implements IRadarDataSet {

    private List<Integer> mColors;

    /**
     * List representing all colors that are used for drawing the actual values for this DataSet
     */
    private final List<Integer> mValueColors;

    /**
     * label that describes the DataSet or the data the DataSet represents
     */
    private String mLabel = "DataSet";

    /**
     * this specifies which axis this DataSet should be plotted against
     */
    private final YAxis.AxisDependency mAxisDependency = YAxis.AxisDependency.LEFT;

    /**
     * custom formatter that is used instead of the auto-formatter if set
     */
    private transient ValueFormatter mValueFormatter;

    /**
     * the typeface used for the value text
     */
    private Typeface mValueTypeface;

    private final Legend.LegendForm mForm = Legend.LegendForm.DEFAULT;

    /**
     * if true, y-values are drawn on the chart
     */
    private boolean mDrawValues = true;

    /**
     * the offset for drawing icons (in dp)
     */
    private final MPPointF mIconsOffset = new MPPointF();

    /**
     * the size of the value-text labels
     */
    private float mValueTextSize = 17f;


    /**
     * the entries that this DataSet represents / holds together
     */
    private List<RadarEntry> mEntries;

    /**
     * maximum y-value in the value array
     */
    private float mYMax = -Float.MAX_VALUE;

    /**
     * minimum y-value in the value array
     */
    private float mYMin = Float.MAX_VALUE;

    /**
     * maximum x-value in the value array
     */
    private float mXMax = -Float.MAX_VALUE;

    /**
     * minimum x-value in the value array
     */
    private float mXMin = Float.MAX_VALUE;

    // TODO: Move to using `Fill` class
    /**
     * the color that is used for filling the line surface
     */
    private int mFillColor = Color.rgb(140, 234, 255);

    /**
     * the drawable to be used for filling the line surface
     */
    private Drawable mFillDrawable;

    /**
     * transparency used for filling line surface
     */
    private int mFillAlpha = 85;

    /**
     * if true, the data will also be drawn filled
     */
    private boolean mDrawFilled = false;


    public RadarDataSet(List<RadarEntry> yVals, String label) {
        mColors = new ArrayList<>();
        mValueColors = new ArrayList<>();

        // default color
        mColors.add(Color.rgb(140, 234, 255));
        mValueColors.add(Color.BLACK);
        this.mLabel = label;
        this.mEntries = yVals;

        if (mEntries == null)
            mEntries = new ArrayList<>();

        calcMinMax();
    }

    @Override
    public void calcMinMax() {

        mYMax = -Float.MAX_VALUE;
        mYMin = Float.MAX_VALUE;
        mXMax = -Float.MAX_VALUE;
        mXMin = Float.MAX_VALUE;

        if (mEntries == null || mEntries.isEmpty())
            return;

        for (RadarEntry e : mEntries) {
            calcMinMax(e);
        }
    }

    /**
     * Updates the min and max x and y value of this DataSet based on the given Entry.
     *
     * @param e
     */
    private void calcMinMax(RadarEntry e) {

        if (e == null)
            return;

        calcMinMaxX(e);

        calcMinMaxY(e);
    }

    private void calcMinMaxX(RadarEntry e) {

        if (e.getX() < mXMin)
            mXMin = e.getX();

        if (e.getX() > mXMax)
            mXMax = e.getX();
    }

    private void calcMinMaxY(RadarEntry e) {

        if (e.getY() < mYMin)
            mYMin = e.getY();

        if (e.getY() > mYMax)
            mYMax = e.getY();
    }

    @Override
    public int getEntryCount() {
        return mEntries.size();
    }


    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(toSimpleString());
        for (int i = 0; i < mEntries.size(); i++) {
            buffer.append(mEntries.get(i).toString()).append(" ");
        }
        return buffer.toString();
    }

    /**
     * Returns a simple string representation of the DataSet with the type and
     * the number of Entries.
     *
     * @return
     */
    private String toSimpleString() {
        return "DataSet, label: " + (getLabel() == null ? "" : getLabel()) + ", entries: " + mEntries.size() + "\n";
    }

    @Override
    public float getYMin() {
        return mYMin;
    }

    @Override
    public float getYMax() {
        return mYMax;
    }

    @Override
    public float getXMin() {
        return mXMin;
    }

    @Override
    public float getXMax() {
        return mXMax;
    }

    @Override
    public RadarEntry getEntryForIndex(int index) {
        return mEntries.get(index);
    }

    /**
     * Use this method to tell the data set that the underlying data has changed.
     */
    public void notifyDataSetChanged() {
        calcMinMax();
    }


    /**
     * ###### ###### COLOR GETTING RELATED METHODS ##### ######
     */

    @Override
    public List<Integer> getColors() {
        return mColors;
    }

    @Override
    public int getColor(int index) {
        return mColors.get(index % mColors.size());
    }

    /**
     * Sets the one and ONLY color that should be used for this DataSet.
     * Internally, this recreates the colors array and adds the specified color.
     *
     * @param color
     */
    public void setColor(int color) {
        resetColors();
        mColors.add(color);
    }

    /**
     * Resets all colors of this DataSet and recreates the colors array.
     */
    private void resetColors() {
        if (mColors == null) {
            mColors = new ArrayList<>();
        }
        mColors.clear();
    }

    @Override
    public String getLabel() {
        return mLabel;
    }

    @Override
    public void setValueFormatter(ValueFormatter f) {
        if (f != null) {
            mValueFormatter = f;
        }
    }

    @Override
    public ValueFormatter getValueFormatter() {
        if (needsFormatter())
            return Utils.getDefaultValueFormatter();
        return mValueFormatter;
    }

    @Override
    public boolean needsFormatter() {
        return mValueFormatter == null;
    }

    @Override
    public void setValueTextColor(int color) {
        mValueColors.clear();
        mValueColors.add(color);
    }

    @Override
    public void setValueTypeface(Typeface tf) {
        mValueTypeface = tf;
    }

    @Override
    public void setValueTextSize(float size) {
        mValueTextSize = Utils.convertDpToPixel(size);
    }

    @Override
    public int getValueTextColor(int index) {
        return mValueColors.get(index % mValueColors.size());
    }

    @Override
    public Typeface getValueTypeface() {
        return mValueTypeface;
    }

    @Override
    public float getValueTextSize() {
        return mValueTextSize;
    }

    @Override
    public Legend.LegendForm getForm() {
        return mForm;
    }

    @Override
    public float getFormSize() {
        return Float.NaN;
    }

    @Override
    public float getFormLineWidth() {
        return Float.NaN;
    }

    @Override
    public DashPathEffect getFormLineDashEffect() {
        return null;
    }

    @Override
    public void setDrawValues(boolean enabled) {
        this.mDrawValues = enabled;
    }

    @Override
    public boolean isDrawValuesEnabled() {
        return mDrawValues;
    }

    @Override
    public boolean isDrawIconsEnabled() {
        return true;
    }

    @Override
    public MPPointF getIconsOffset() {
        return mIconsOffset;
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public YAxis.AxisDependency getAxisDependency() {
        return mAxisDependency;
    }

    @Override
    public int getFillColor() {
        return mFillColor;
    }

    /**
     * Sets the color that is used for filling the area below the line.
     * Resets an eventually set "fillDrawable".
     *
     * @param color
     */
    public void setFillColor(int color) {
        mFillColor = color;
        mFillDrawable = null;
    }

    @Override
    public Drawable getFillDrawable() {
        return mFillDrawable;
    }

    @Override
    public int getFillAlpha() {
        return mFillAlpha;
    }

    /**
     * sets the alpha value (transparency) that is used for filling the line
     * surface (0-255), default: 85
     *
     * @param alpha
     */
    public void setFillAlpha(int alpha) {
        mFillAlpha = alpha;
    }

    @Override
    public float getLineWidth() {
        return 2.5f;
    }

    public void setDrawFilled(boolean filled) {
        mDrawFilled = filled;
    }

    @Override
    public boolean isDrawFilledEnabled() {
        return mDrawFilled;
    }

}
