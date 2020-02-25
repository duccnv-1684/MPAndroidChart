package com.github.mikephil.charting.data;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.ParcelFormatException;
import android.os.Parcelable;

/**
 * Created by philipp on 13/06/16.
 */
@SuppressLint("ParcelCreator")
public class RadarEntry implements Parcelable {

    /**
     * the y value
     */
    private float y = 0f;

    /**
     * optional spot for additional data this RadarEntry represents
     */
    private Object mData = null;

    /**
     * the x value
     */
    private float x = 0f;

    public RadarEntry() {

    }

    public RadarEntry(float value) {
        this.y = value;
        this.x = (float) 0.0;
    }

    public float getX() {
        return x;
    }

    /**
     * Sets the x-value of this RadarEntry object.
     *
     * @param x
     */
    void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    /**
     * Returns the icon of this RadarEntry.
     *
     * @return
     */
    public Drawable getIcon() {
        return null;
    }

    /**
     * Sets the y-value for the RadarEntry.
     *
     * @param y
     */
    private void setY(float y) {
        this.y = y;
    }

    /**
     * Returns the data, additional information that this RadarEntry represents, or
     * null, if no data has been specified.
     *
     * @return
     */
    private Object getData() {
        return mData;
    }

    /**
     * Sets additional data this RadarEntry should represent.
     *
     * @param data
     */
    private void setData(Object data) {
        this.mData = data;
    }

    /**
     * returns a string representation of the entry containing x-index and value
     */
    @Override
    public String toString() {
        return "RadarEntry, x: " + x + " y: " + getY();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.x);
        dest.writeFloat(this.getY());
        if (getData() != null) {
            if (getData() instanceof Parcelable) {
                dest.writeInt(1);
                dest.writeParcelable((Parcelable) this.getData(), flags);
            } else {
                throw new ParcelFormatException("Cannot parcel an RadarEntry with non-parcelable data");
            }
        } else {
            dest.writeInt(0);
        }
    }

    private RadarEntry(Parcel in) {
        this.x = in.readFloat();
        this.setY(in.readFloat());
        if (in.readInt() == 1) {
            this.setData(in.readParcelable(Object.class.getClassLoader()));
        }
    }

    public static final Parcelable.Creator<RadarEntry> CREATOR = new Parcelable.Creator<RadarEntry>() {
        public RadarEntry createFromParcel(Parcel source) {
            return new RadarEntry(source);
        }

        public RadarEntry[] newArray(int size) {
            return new RadarEntry[size];
        }
    };

}
