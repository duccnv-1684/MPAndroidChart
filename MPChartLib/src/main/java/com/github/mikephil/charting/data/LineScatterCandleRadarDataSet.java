package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;

import java.util.List;

/**
 * Created by Philipp Jahoda on 11/07/15.
 */
abstract class LineScatterCandleRadarDataSet<T extends Entry> extends BarLineScatterCandleBubbleDataSet<T> implements ILineScatterCandleRadarDataSet<T> {





    LineScatterCandleRadarDataSet(List<T> yVals, String label) {
        super(yVals, label);
    }

}
