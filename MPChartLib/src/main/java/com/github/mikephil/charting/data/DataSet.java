
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.data.base.BaseDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * The DataSet class represents one group or type of entries (Entry) in the
 * Chart that belong together. It is designed to logically separate different
 * groups of values inside the Chart (e.g. the values for a specific line in the
 * LineChart, or the values of a specific group of bars in the BarChart).
 *
 * @author Philipp Jahoda
 */
public abstract class DataSet<T extends Entry> extends BaseDataSet<T> {

    /**
     * the entries that this DataSet represents / holds together
     */
    private List<T> mEntries;

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


    /**
     * Creates a new DataSet object with the given values (entries) it represents. Also, a
     * label that describes the DataSet can be specified. The label can also be
     * used to retrieve the DataSet from a ChartData object.
     *
     * @param entries
     * @param label
     */
    DataSet(List<T> entries, String label) {
        super(label);
        this.mEntries = entries;

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

        for (T e : mEntries) {
            calcMinMax(e);
        }
    }

    /**
     * Updates the min and max x and y value of this DataSet based on the given Entry.
     *
     * @param e
     */
    void calcMinMax(T e) {

        if (e == null)
            return;

        calcMinMaxX(e);

        calcMinMaxY(e);
    }

    private void calcMinMaxX(T e) {

        if (e.getX() < mXMin)
            mXMin = e.getX();

        if (e.getX() > mXMax)
            mXMax = e.getX();
    }

    void calcMinMaxY(T e) {

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
    public T getEntryForIndex(int index) {
        return mEntries.get(index);
    }
}