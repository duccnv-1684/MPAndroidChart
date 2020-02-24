
package com.github.mikephil.charting.data;


import java.util.List;

/**
 * Baseclass of all DataSets for Bar-, Line-, Scatter- and CandleStickChart.
 *
 * @author Philipp Jahoda
 */
abstract class BarLineScatterCandleBubbleDataSet<T extends Entry> extends DataSet<T>{

    /**
     * default highlight color
     */

    BarLineScatterCandleBubbleDataSet(List<T> yVals, String label) {
        super(yVals, label);
    }
}
