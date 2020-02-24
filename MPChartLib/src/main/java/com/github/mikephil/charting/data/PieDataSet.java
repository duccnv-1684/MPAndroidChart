
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

import java.util.List;

public class PieDataSet extends DataSet<PieEntry> implements IPieDataSet {

    private final ValuePosition mXValuePosition = ValuePosition.INSIDE_SLICE;
    private final ValuePosition mYValuePosition = ValuePosition.INSIDE_SLICE;

    public PieDataSet(List<PieEntry> yVals, String label) {
        super(yVals, label);
    }

    @Override
    protected void calcMinMax(PieEntry e) {

        if (e == null)
            return;

        calcMinMaxY(e);
    }

    @Override
    public float getSliceSpace() {
        return 0f;
    }


    @Override
    public float getSelectionShift() {
        return 18f;
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
     * When valuePosition is OutsideSlice, indicates line color
     */
    @Override
    public int getValueLineColor() {
        return 0xff000000;
    }

    @Override
    public boolean isUseValueColorForLineEnabled()
    {
        return false;
    }


    /**
     * When valuePosition is OutsideSlice, indicates line width
     */
    @Override
    public float getValueLineWidth() {
        return 1.0f;
    }

    /**
     * When valuePosition is OutsideSlice, indicates offset as percentage out of the slice size
     */
    @Override
    public float getValueLinePart1OffsetPercentage() {
        return 75.f;
    }

    /**
     * When valuePosition is OutsideSlice, indicates length of first half of the line
     */
    @Override
    public float getValueLinePart1Length() {
        return 0.3f;
    }

    /**
     * When valuePosition is OutsideSlice, indicates length of second half of the line
     */
    @Override
    public float getValueLinePart2Length() {
        return 0.4f;
    }

    /**
     * When valuePosition is OutsideSlice, this allows variable line length
     */
    @Override
    public boolean isValueLineVariableLength() {
        return true;
    }


    public enum ValuePosition {
        INSIDE_SLICE,
        OUTSIDE_SLICE
    }
}
