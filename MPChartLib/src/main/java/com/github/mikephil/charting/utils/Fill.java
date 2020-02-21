package com.github.mikephil.charting.utils;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Fill
{
    public enum Type
    {
        EMPTY, COLOR, LINEAR_GRADIENT, DRAWABLE
    }

    public enum Direction
    {
        DOWN, UP, RIGHT, LEFT
    }

    /**
     * the type of fill
     */
    private Type mType = Type.EMPTY;

    /**
     * the color that is used for filling
     */
    @Nullable
    private Integer mColor = null;

    private Integer mFinalColor = null;

    /**
     * the drawable to be used for filling
     */
    @Nullable
    protected Drawable mDrawable;

    @Nullable
    private int[] mGradientColors;

    @Nullable
    private float[] mGradientPositions;

    /**
     * transparency used for filling
     */
    private int mAlpha = 255;

    public Fill()
    {
    }

    public Fill(@NonNull Drawable drawable)
    {
        this.mType = Type.DRAWABLE;
        this.mDrawable = drawable;
    }

    @Nullable
    public Integer getColor()
    {
        return mColor;
    }

    public void setColor(int color)
    {
        this.mColor = color;
        calculateFinalColor();
    }

    public int[] getGradientColors()
    {
        return mGradientColors;
    }

    public void setGradientColors(int[] colors)
    {
        this.mGradientColors = colors;
    }

    public int getAlpha()
    {
        return mAlpha;
    }

    public void setAlpha(int alpha)
    {
        this.mAlpha = alpha;
        calculateFinalColor();
    }

    private void calculateFinalColor()
    {
        if (mColor == null)
        {
            mFinalColor = null;
        } else
        {
            int alpha = (int) Math.floor(((mColor >> 24) / 255.0) * (mAlpha / 255.0) * 255.0);
            mFinalColor = (alpha << 24) | (mColor & 0xffffff);
        }
    }

    private boolean isClipPathSupported()
    {
        return Utils.getSDKInt() >= 18;
    }

    private void ensureClipPathSupported()
    {
        if (Utils.getSDKInt() < 18)
        {
            throw new RuntimeException("Fill-drawables not (yet) supported below API level 18, " +
                    "this code was run on API level " + Utils.getSDKInt() + ".");
        }
    }
}
