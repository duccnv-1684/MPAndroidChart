package com.github.mikephil.charting.data;

import android.graphics.Typeface;

import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds all relevant data that represents the chart. That involves
 * at least one (or more) DataSets, and an array of x-values.
 *
 * @author Philipp Jahoda
 */
public abstract class ChartData<T extends IDataSet<? extends Entry>> {

    /**
     * maximum y-value in the value array across all axes
     */
    protected float mYMax = -Float.MAX_VALUE;

    /**
     * the minimum y-value in the value array across all axes
     */
    protected float mYMin = Float.MAX_VALUE;

    /**
     * maximum x-value in the value array
     */
    protected float mXMax = -Float.MAX_VALUE;

    /**
     * minimum x-value in the value array
     */
    protected float mXMin = Float.MAX_VALUE;


    protected float mLeftAxisMax = -Float.MAX_VALUE;

    protected float mLeftAxisMin = Float.MAX_VALUE;

    protected float mRightAxisMax = -Float.MAX_VALUE;

    protected float mRightAxisMin = Float.MAX_VALUE;

    /**
     * array that holds all DataSets the ChartData object represents
     */
    protected List<T> mDataSets;

    /**
     * Default constructor.
     */
    public ChartData() {
        mDataSets = new ArrayList<T>();
    }

    /**
     * Constructor taking single or multiple DataSet objects.
     *
     * @param dataSets
     */
    public ChartData(T... dataSets) {
        mDataSets = arrayToList(dataSets);
        notifyDataChanged();
    }

    /**
     * Created because Arrays.asList(...) does not support modification.
     *
     * @param array
     * @return
     */
    private List<T> arrayToList(T[] array) {

        List<T> list = new ArrayList<>();

        for (T set : array) {
            list.add(set);
        }

        return list;
    }

    /**
     * constructor for chart data
     *
     * @param sets the dataset array
     */
    public ChartData(List<T> sets) {
        this.mDataSets = sets;
        notifyDataChanged();
    }

    /**
     * Call this method to let the ChartData know that the underlying data has
     * changed. Calling this performs all necessary recalculations needed when
     * the contained data has changed.
     */
    public void notifyDataChanged() {
        calcMinMax();
    }

    /**
     * Calc minimum and maximum values (both x and y) over all DataSets.
     */
    protected void calcMinMax() {

        if (mDataSets == null)
            return;

        mYMax = -Float.MAX_VALUE;
        mYMin = Float.MAX_VALUE;
        mXMax = -Float.MAX_VALUE;
        mXMin = Float.MAX_VALUE;

        for (T set : mDataSets) {
            calcMinMax(set);
        }

        mLeftAxisMax = -Float.MAX_VALUE;
        mLeftAxisMin = Float.MAX_VALUE;
        mRightAxisMax = -Float.MAX_VALUE;
        mRightAxisMin = Float.MAX_VALUE;

        // left axis
        T firstLeft = getFirstLeft(mDataSets);

        if (firstLeft != null) {

            mLeftAxisMax = firstLeft.getYMax();
            mLeftAxisMin = firstLeft.getYMin();

            for (T dataSet : mDataSets) {
                if (dataSet.getAxisDependency() == AxisDependency.LEFT) {
                    if (dataSet.getYMin() < mLeftAxisMin)
                        mLeftAxisMin = dataSet.getYMin();

                    if (dataSet.getYMax() > mLeftAxisMax)
                        mLeftAxisMax = dataSet.getYMax();
                }
            }
        }

        // right axis
        T firstRight = getFirstRight(mDataSets);

        if (firstRight != null) {

            mRightAxisMax = firstRight.getYMax();
            mRightAxisMin = firstRight.getYMin();

            for (T dataSet : mDataSets) {
                if (dataSet.getAxisDependency() == AxisDependency.RIGHT) {
                    if (dataSet.getYMin() < mRightAxisMin)
                        mRightAxisMin = dataSet.getYMin();

                    if (dataSet.getYMax() > mRightAxisMax)
                        mRightAxisMax = dataSet.getYMax();
                }
            }
        }
    }

    /** ONLY GETTERS AND SETTERS BELOW THIS */

    /**
     * returns the number of LineDataSets this object contains
     *
     * @return
     */
    public int getDataSetCount() {
        if (mDataSets == null)
            return 0;
        return mDataSets.size();
    }

    /**
     * Returns the smallest y-value the data object contains.
     *
     * @return
     */
    public float getYMin() {
        return mYMin;
    }

    /**
     * Returns the minimum y-value for the specified axis.
     *
     * @param axis
     * @return
     */
    public float getYMin(AxisDependency axis) {
        if (axis == AxisDependency.LEFT) {

            if (mLeftAxisMin == Float.MAX_VALUE) {
                return mRightAxisMin;
            } else
                return mLeftAxisMin;
        } else {
            if (mRightAxisMin == Float.MAX_VALUE) {
                return mLeftAxisMin;
            } else
                return mRightAxisMin;
        }
    }

    /**
     * Returns the greatest y-value the data object contains.
     *
     * @return
     */
    public float getYMax() {
        return mYMax;
    }

    /**
     * Returns the maximum y-value for the specified axis.
     *
     * @param axis
     * @return
     */
    public float getYMax(AxisDependency axis) {
        if (axis == AxisDependency.LEFT) {

            if (mLeftAxisMax == -Float.MAX_VALUE) {
                return mRightAxisMax;
            } else
                return mLeftAxisMax;
        } else {
            if (mRightAxisMax == -Float.MAX_VALUE) {
                return mLeftAxisMax;
            } else
                return mRightAxisMax;
        }
    }

    /**
     * Returns all DataSet objects this ChartData object holds.
     *
     * @return
     */
    public List<T> getDataSets() {
        return mDataSets;
    }

    /**
     * Get the Entry for a corresponding highlight object
     *
     * @param highlight
     * @return the entry that is highlighted
     */
    public Entry getEntryForHighlight(Highlight highlight) {
        if (highlight.getDataSetIndex() >= mDataSets.size())
            return null;
        else {
            return mDataSets.get(highlight.getDataSetIndex()).getEntryForXValue(highlight.getX(), highlight.getY());
        }
    }

    public T getDataSetByIndex(int index) {

        if (mDataSets == null || index < 0 || index >= mDataSets.size())
            return null;

        return mDataSets.get(index);
    }

    /**
     * Adjusts the minimum and maximum values based on the given DataSet.
     *
     * @param d
     */
    protected void calcMinMax(T d) {

        if (mYMax < d.getYMax())
            mYMax = d.getYMax();
        if (mYMin > d.getYMin())
            mYMin = d.getYMin();

        if (mXMax < d.getXMax())
            mXMax = d.getXMax();
        if (mXMin > d.getXMin())
            mXMin = d.getXMin();

        if (d.getAxisDependency() == AxisDependency.LEFT) {

            if (mLeftAxisMax < d.getYMax())
                mLeftAxisMax = d.getYMax();
            if (mLeftAxisMin > d.getYMin())
                mLeftAxisMin = d.getYMin();
        } else {
            if (mRightAxisMax < d.getYMax())
                mRightAxisMax = d.getYMax();
            if (mRightAxisMin > d.getYMin())
                mRightAxisMin = d.getYMin();
        }
    }

    /**
     * Returns the first DataSet from the datasets-array that has it's dependency on the left axis.
     * Returns null if no DataSet with left dependency could be found.
     *
     * @return
     */
    protected T getFirstLeft(List<T> sets) {
        for (T dataSet : sets) {
            if (dataSet.getAxisDependency() == AxisDependency.LEFT)
                return dataSet;
        }
        return null;
    }

    /**
     * Returns the first DataSet from the datasets-array that has it's dependency on the right axis.
     * Returns null if no DataSet with right dependency could be found.
     *
     * @return
     */
    public T getFirstRight(List<T> sets) {
        for (T dataSet : sets) {
            if (dataSet.getAxisDependency() == AxisDependency.RIGHT)
                return dataSet;
        }
        return null;
    }

    /**
     * Sets the color of the value-text (color in which the value-labels are
     * drawn) for all DataSets this data object contains.
     *
     * @param color
     */
    public void setValueTextColor(int color) {
        for (IDataSet set : mDataSets) {
            set.setValueTextColor(color);
        }
    }

    /**
     * Sets the Typeface for all value-labels for all DataSets this data object
     * contains.
     *
     * @param tf
     */
    public void setValueTypeface(Typeface tf) {
        for (IDataSet set : mDataSets) {
            set.setValueTypeface(tf);
        }
    }

    /**
     * Sets the size (in dp) of the value-text for all DataSets this data object
     * contains.
     *
     * @param size
     */
    public void setValueTextSize(float size) {
        for (IDataSet set : mDataSets) {
            set.setValueTextSize(size);
        }
    }

    /**
     * Enables / disables drawing values (value-text) for all DataSets this data
     * object contains.
     *
     * @param enabled
     */
    public void setDrawValues(boolean enabled) {
        for (IDataSet set : mDataSets) {
            set.setDrawValues(enabled);
        }
    }

    /**
     * Clears this data object from all DataSets and removes all Entries. Don't
     * forget to invalidate the chart after this.
     */
    public void clearValues() {
        if (mDataSets != null) {
            mDataSets.clear();
        }
        notifyDataChanged();
    }

    /**
     * Returns the total entry count across all DataSet objects this data object contains.
     *
     * @return
     */
    public int getEntryCount() {

        int count = 0;

        for (T set : mDataSets) {
            count += set.getEntryCount();
        }

        return count;
    }

    /**
     * Returns the DataSet object with the maximum number of entries or null if there are no DataSets.
     *
     * @return
     */
    public T getMaxEntryCountSet() {

        if (mDataSets == null || mDataSets.isEmpty())
            return null;

        T max = mDataSets.get(0);

        for (T set : mDataSets) {

            if (set.getEntryCount() > max.getEntryCount())
                max = set;
        }

        return max;
    }
}
