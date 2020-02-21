package com.github.mikephil.charting.data;

import android.annotation.SuppressLint;

/**
 * Created by philipp on 13/06/16.
 */
@SuppressLint("ParcelCreator")
public class RadarEntry extends Entry {

    public RadarEntry(float value) {
        super(value);
    }

    @Deprecated
    @Override
    public void setX(float x) {
        super.setX(x);
    }

}
