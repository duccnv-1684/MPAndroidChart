package com.github.mikephil.charting.renderer;

import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Created by Philipp Jahoda on 09/06/16.
 */
abstract class BarLineScatterCandleBubbleRenderer extends DataRenderer {

    BarLineScatterCandleBubbleRenderer( ViewPortHandler viewPortHandler) {
        super(viewPortHandler);
    }

    /**
     * Returns true if the DataSet values should be drawn, false if not.
     *
     * @param set
     * @return
     */
    boolean shouldDrawValues(IDataSet set) {
        return set.isVisible() && (set.isDrawValuesEnabled() || set.isDrawIconsEnabled());
    }


}
