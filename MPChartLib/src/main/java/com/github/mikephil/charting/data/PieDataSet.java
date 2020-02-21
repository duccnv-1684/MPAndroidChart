
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.Utils;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PieDataSet extends DataSet<PieEntry> implements IPieDataSet {

    /**
     * the space in pixels between the chart-slices, default 0f
     */
    private float mSliceSpace = 0f;
    private boolean mAutomaticallyDisableSliceSpacing;

    /**
     * indicates the selection distance of a pie slice
     */
    private float mShift = 18f;

    private ValuePosition mXValuePosition = ValuePosition.INSIDE_SLICE;
    private ValuePosition mYValuePosition = ValuePosition.INSIDE_SLICE;
    private int mValueLineColor = 0xff000000;
    private boolean mUseValueColorForLine = false;
    private float mValueLineWidth = 1.0f;
    private float mValueLinePart1OffsetPercentage = 75.f;
    private float mValueLinePart1Length = 0.3f;
    private float mValueLinePart2Length = 0.4f;
    private boolean mValueLineVariableLength = true;
    private Integer mHighlightColor = null;

    public PieDataSet(List<PieEntry> yVals, String label) {
        super(yVals, label);
//        mShift = Utils.convertDpToPixel(12f);
    }

    @Override
    public DataSet<PieEntry> copy() {
        List<PieEntry> entries = new ArrayList<>();
        for (int i = 0; i < mEntries.size(); i++) {
            entries.add(mEntries.get(i).copy());
        }
        PieDataSet copied = new PieDataSet(entries, getLabel());
        copy(copied);
        return copied;
    }

    protected void copy(PieDataSet pieDataSet) {
        super.copy(pieDataSet);
    }

    @Override
    protected void calcMinMax(PieEntry e) {

        if (e == null)
            return;

        calcMinMaxY(e);
    }

    @Override
    public float getSliceSpace() {
        return mSliceSpace;
    }

    /**
     * When enabled, slice spacing will be 0.0 when the smallest value is going to be
     * smaller than the slice spacing itself.
     *
     * @return
     */
    @Override
    public boolean isAutomaticallyDisableSliceSpacingEnabled() {
        return mAutomaticallyDisableSliceSpacing;
    }

    @Override
    public float getSelectionShift() {
        return mShift;
    }

    @Override
    public ValuePosition getXValuePosition() {
        return mXValuePosition;
    }

    @Override
    public ValuePosition getYValuePosition() {
        return mYValuePosition;
    }

    /**
     * This method is deprecated.
     * Use isUseValueColorForLineEnabled() instead.
     */
    @Deprecated
    public boolean isUsingSliceColorAsValueLineColor() {
        return isUseValueColorForLineEnabled();
    }

    /**
     * This method is deprecated.
     * Use setUseValueColorForLine(...) instead.
     *
     * @param enabled
     */
    @Deprecated
    public void setUsingSliceColorAsValueLineColor(boolean enabled) {
        setUseValueColorForLine(enabled);
    }

    /**
     * When valuePosition is OutsideSlice, indicates line color
     */
    @Override
    public int getValueLineColor() {
        return mValueLineColor;
    }

    @Override
    public boolean isUseValueColorForLineEnabled()
    {
        return mUseValueColorForLine;
    }

    public void setUseValueColorForLine(boolean enabled)
    {
        mUseValueColorForLine = enabled;
    }

    /**
     * When valuePosition is OutsideSlice, indicates line width
     */
    @Override
    public float getValueLineWidth() {
        return mValueLineWidth;
    }

    /**
     * When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size
     */
    @Override
    public float getValueLinePart1OffsetPercentage() {
        return mValueLinePart1OffsetPercentage;
    }

    /**
     * When valuePosition is OutsideSlice, indicates length of first half of the line
     */
    @Override
    public float getValueLinePart1Length() {
        return mValueLinePart1Length;
    }

    /**
     * When valuePosition is OutsideSlice, indicates length of second half of the line
     */
    @Override
    public float getValueLinePart2Length() {
        return mValueLinePart2Length;
    }

    /**
     * When valuePosition is OutsideSlice, this allows variable line length
     */
    @Override
    public boolean isValueLineVariableLength() {
        return mValueLineVariableLength;
    }

    /** Gets the color for the highlighted sector */
    @Override
    @Nullable
    public Integer getHighlightColor()
    {
        return mHighlightColor;
    }


    public enum ValuePosition {
        INSIDE_SLICE,
        OUTSIDE_SLICE
    }
}
