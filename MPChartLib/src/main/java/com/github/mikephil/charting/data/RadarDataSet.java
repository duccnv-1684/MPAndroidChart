
package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;

import java.util.List;

public class RadarDataSet extends LineRadarDataSet<RadarEntry> implements IRadarDataSet {

    /// flag indicating whether highlight circle should be drawn or not

    public RadarDataSet(List<RadarEntry> yVals, String label) {
        super(yVals, label);
    }

    /// Sets whether highlight circle should be drawn or not

}
