
package com.github.mikephil.charting.data;

import android.graphics.Color;

import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class RadarDataSet extends LineRadarDataSet<RadarEntry> implements IRadarDataSet {

    /// flag indicating whether highlight circle should be drawn or not
    protected boolean mDrawHighlightCircleEnabled = false;

    protected int mHighlightCircleFillColor = Color.WHITE;

    /// The stroke color for highlight circle.
    /// If Utils.COLOR_NONE, the color of the dataset is taken.
    protected int mHighlightCircleStrokeColor = ColorTemplate.COLOR_NONE;

    protected int mHighlightCircleStrokeAlpha = (int) (0.3 * 255);
    protected float mHighlightCircleInnerRadius = 3.0f;
    protected float mHighlightCircleOuterRadius = 4.0f;
    protected float mHighlightCircleStrokeWidth = 2.0f;

    public RadarDataSet(List<RadarEntry> yVals, String label) {
        super(yVals, label);
    }

    /// Returns true if highlight circle should be drawn, false if not
    @Override
    public boolean isDrawHighlightCircleEnabled() {
        return mDrawHighlightCircleEnabled;
    }

    /// Sets whether highlight circle should be drawn or not
    @Override
    public void setDrawHighlightCircleEnabled(boolean enabled) {
        mDrawHighlightCircleEnabled = enabled;
    }

    @Override
    public int getHighlightCircleFillColor() {
        return mHighlightCircleFillColor;
    }

    /// Returns the stroke color for highlight circle.
    /// If Utils.COLOR_NONE, the color of the dataset is taken.
    @Override
    public int getHighlightCircleStrokeColor() {
        return mHighlightCircleStrokeColor;
    }

    @Override
    public int getHighlightCircleStrokeAlpha() {
        return mHighlightCircleStrokeAlpha;
    }

    @Override
    public float getHighlightCircleInnerRadius() {
        return mHighlightCircleInnerRadius;
    }

    @Override
    public float getHighlightCircleOuterRadius() {
        return mHighlightCircleOuterRadius;
    }

    @Override
    public float getHighlightCircleStrokeWidth() {
        return mHighlightCircleStrokeWidth;
    }

    @Override
    public DataSet<RadarEntry> copy() {
        List<RadarEntry> entries = new ArrayList<RadarEntry>();
        for (int i = 0; i < mEntries.size(); i++) {
            entries.add(mEntries.get(i).copy());
        }
        RadarDataSet copied = new RadarDataSet(entries, getLabel());
        copy(copied);
        return copied;
    }

    protected void copy(RadarDataSet radarDataSet) {
        super.copy(radarDataSet);
        radarDataSet.mDrawHighlightCircleEnabled = mDrawHighlightCircleEnabled;
        radarDataSet.mHighlightCircleFillColor = mHighlightCircleFillColor;
        radarDataSet.mHighlightCircleInnerRadius = mHighlightCircleInnerRadius;
        radarDataSet.mHighlightCircleStrokeAlpha = mHighlightCircleStrokeAlpha;
        radarDataSet.mHighlightCircleStrokeColor = mHighlightCircleStrokeColor;
        radarDataSet.mHighlightCircleStrokeWidth = mHighlightCircleStrokeWidth;
    }
}
