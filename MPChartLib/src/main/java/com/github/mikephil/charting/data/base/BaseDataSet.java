package com.github.mikephil.charting.data.base;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Philipp Jahoda on 21/10/15.
 * This is the base dataset of all DataSets. It's purpose is to implement critical methods
 * provided by the IDataSet interface.
 */
public abstract class BaseDataSet<T extends Entry> implements IDataSet<T> {

    /**
     * List representing all colors that are used for this DataSet
     */
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
     * Default constructor.
     */
    protected BaseDataSet() {
        mColors = new ArrayList<>();
        mValueColors = new ArrayList<>();

        // default color
        mColors.add(Color.rgb(140, 234, 255));
        mValueColors.add(Color.BLACK);
    }

    /**
     * Constructor with label.
     *
     * @param label
     */
    protected BaseDataSet(String label) {
        this();
        this.mLabel = label;
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

}
