
package com.github.mikephil.charting.utils;

/**
 * Point encapsulating two double values.
 *
 * @author Philipp Jahoda
 */
public class MPPointD extends ObjectPool.Poolable {

    private static final ObjectPool<MPPointD> pool;

    static {
        pool = ObjectPool.create(64, new MPPointD());
        pool.setReplenishPercentage(0.5f);
    }

    public static MPPointD getInstance(double x, double y){
        MPPointD result = pool.get();
        result.x = x;
        result.y = y;
        return result;
    }

    public static void recycleInstance(MPPointD instance){
        pool.recycle(instance);
    }

    public double x;
    public double y;

    protected ObjectPool.Poolable instantiate(){
        return new MPPointD();
    }

    private MPPointD() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * returns a string representation of the object
     */
    public String toString() {
        return "MPPointD, x: " + x + ", y: " + y;
    }
}