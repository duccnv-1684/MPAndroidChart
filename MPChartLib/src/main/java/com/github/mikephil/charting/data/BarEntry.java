package com.github.mikephil.charting.data;

import android.annotation.SuppressLint;

import com.github.mikephil.charting.highlight.Range;

/**
 * Entry class for the BarChart. (especially stacked bars)
 *
 * @author Philipp Jahoda
 */
@SuppressLint("ParcelCreator")
public class BarEntry extends Entry {

    /**
     * the values the stacked barchart holds
     */
    private float[] mYVals;

    /**
     * the ranges for the individual stack values - automatically calculated
     */
    private Range[] mRanges;

    /**
     * the sum of all negative values this entry (if stacked) contains
     */
    private float mNegativeSum;

    /**
     * Constructor for normal bars (not stacked).
     *
     * @param x
     * @param y
     * @param data - Spot for additional data this Entry represents.
     */
    public BarEntry(float x, float y, Object data) {
        super(x, y, data);
    }

    /**
     * Returns an exact copy of the BarEntry.
     */
    public BarEntry copy() {

        BarEntry copied = new BarEntry(getX(), getY(), getData());
        copied.setVals(mYVals);
        return copied;
    }

    /**
     * Returns the stacked values this BarEntry represents, or null, if only a single value is represented (then, use
     * getY()).
     *
     * @return
     */
    public float[] getYVals() {
        return mYVals;
    }

    /**
     * Set the array of values this BarEntry should represent.
     *
     * @param vals
     */
    public void setVals(float[] vals) {
        setY(calcSum(vals));
        mYVals = vals;
        calcPosNegSum();
        calcRanges();
    }

    /**
     * Returns the value of this BarEntry. If the entry is stacked, it returns the positive sum of all values.
     *
     * @return
     */
    @Override
    public float getY() {
        return super.getY();
    }

    /**
     * Use `getSumBelow(stackIndex)` instead.
     */
    @Deprecated
    public float getBelowSum(int stackIndex) {
        return getSumBelow(stackIndex);
    }

    public float getSumBelow(int stackIndex) {

        if (mYVals == null)
            return 0;

        float remainder = 0f;
        int index = mYVals.length - 1;

        while (index > stackIndex && index >= 0) {
            remainder += mYVals[index];
            index--;
        }

        return remainder;
    }

    /**
     * Returns the sum of all negative values this entry (if stacked) contains. (this is a positive number)
     *
     * @return
     */
    public float getNegativeSum() {
        return mNegativeSum;
    }

    private void calcPosNegSum() {

        if (mYVals == null) {
            mNegativeSum = 0;
            return;
        }

        float sumNeg = 0f;
        float sumPos = 0f;

        for (float f : mYVals) {
            if (f <= 0f)
                sumNeg += Math.abs(f);
            else
                sumPos += f;
        }

        mNegativeSum = sumNeg;
    }

    /**
     * Calculates the sum across all values of the given stack.
     *
     * @param vals
     * @return
     */
    private static float calcSum(float[] vals) {

        if (vals == null)
            return 0f;

        float sum = 0f;

        for (float f : vals)
            sum += f;

        return sum;
    }

    protected void calcRanges() {

        float[] values = getYVals();

        if (values == null || values.length == 0)
            return;

        mRanges = new Range[values.length];

        float negRemain = -getNegativeSum();
        float posRemain = 0f;

        for (int i = 0; i < mRanges.length; i++) {

            float value = values[i];

            if (value < 0) {
                mRanges[i] = new Range(negRemain, negRemain - value);
                negRemain -= value;
            } else {
                mRanges[i] = new Range(posRemain, posRemain + value);
                posRemain += value;
            }
        }
    }
}


