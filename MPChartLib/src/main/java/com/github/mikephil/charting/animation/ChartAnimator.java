package com.github.mikephil.charting.animation;

import androidx.annotation.RequiresApi;

/**
 * Object responsible for all animations in the Chart. Animations require API level 11.
 *
 * @author Philipp Jahoda
 * @author Mick Ashton
 */
public class ChartAnimator {

    /** The phase of drawn values on the y-axis. 0 - 1 */
    @SuppressWarnings("WeakerAccess")
    protected final float mPhaseY = 1f;

    /** The phase of drawn values on the x-axis. 0 - 1 */
    @SuppressWarnings("WeakerAccess")
    protected final float mPhaseX = 1f;

    @RequiresApi(11)
    public ChartAnimator() {
    }

    /**
     * Gets the Y axis phase of the animation.
     *
     * @return float value of {@link #mPhaseY}
     */
    public float getPhaseY() {
        return mPhaseY;
    }

    /**
     * Gets the X axis phase of the animation.
     *
     * @return float value of {@link #mPhaseX}
     */
    public float getPhaseX() {
        return mPhaseX;
    }

}
