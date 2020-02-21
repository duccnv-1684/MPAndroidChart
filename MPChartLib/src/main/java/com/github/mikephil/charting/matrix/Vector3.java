
package com.github.mikephil.charting.matrix;

/**
 * Simple 3D vector class. Handles basic vector math for 3D vectors.
 */
public final class Vector3 {
    public float x;
    public float y;
    public float z;

    public Vector3(float xValue, float yValue, float zValue) {
        set(xValue, yValue, zValue);
    }

    public final void add(Vector3 other) {
        x += other.x;
        y += other.y;
        z += other.z;
    }

    public final void add(float otherX, float otherY, float otherZ) {
        x += otherX;
        y += otherY;
        z += otherZ;
    }

    public final void set(Vector3 other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public final void set(float xValue, float yValue, float zValue) {
        x = xValue;
        y = yValue;
        z = zValue;
    }

    public final float dot(Vector3 other) {
        return (x * other.x) + (y * other.y) + (z * other.z);
    }

    public final float length() {
        return (float) Math.sqrt(length2());
    }

    public final float length2() {
        return (x * x) + (y * y) + (z * z);
    }

}
