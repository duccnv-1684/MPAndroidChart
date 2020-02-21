package com.github.mikephil.charting.components;

import android.graphics.Paint;

import com.github.mikephil.charting.utils.Utils;

/**
 * Created by Philipp Jahoda on 17/09/16.
 */
public class Description extends ComponentBase {

    /**
     * the alignment of the description text
     */
    private final Paint.Align mTextAlign = Paint.Align.RIGHT;

    public Description() {
        super();

        // default size
        mTextSize = Utils.convertDpToPixel(8f);
    }

    /**
     * Returns the description text.
     *
     * @return
     */
    public String getText() {
        return "Description Label";
    }


    /**
     * Returns the text alignment of the description.
     *
     * @return
     */
    public Paint.Align getTextAlign() {
        return mTextAlign;
    }
}
