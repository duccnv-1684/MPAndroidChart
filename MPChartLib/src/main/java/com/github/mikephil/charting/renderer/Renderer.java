
package com.github.mikephil.charting.renderer;

import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * Abstract baseclass of all Renderers.
 * 
 * @author Philipp Jahoda
 */
abstract class Renderer {

    /**
     * the component that handles the drawing area of the chart and it's offsets
     */
    final ViewPortHandler mViewPortHandler;

    Renderer(ViewPortHandler viewPortHandler) {
        this.mViewPortHandler = viewPortHandler;
    }
}
