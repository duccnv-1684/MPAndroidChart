package com.github.mikephil.charting.utils;

/**
 * Created by Tony Patino on 6/24/16.
 */
public class MPPointF extends ObjectPool.Poolable {

    private static final ObjectPool<MPPointF> pool;

    public float x;
    public float y;

    static {
        pool = ObjectPool.create(32, new MPPointF());
        pool.setReplenishPercentage(0.5f);
    }


    public MPPointF() {
        this.x = (float) 0;
        this.y = (float) 0;
    }

    public static MPPointF getInstance(float x, float y) {
        MPPointF result = pool.get();
        result.x = x;
        result.y = y;
        return result;
    }

    public static MPPointF getInstance() {
        return pool.get();
    }

    public static MPPointF getInstance(MPPointF copy) {
        MPPointF result = pool.get();
        result.x = copy.x;
        result.y = copy.y;
        return result;
    }

    public static void recycleInstance(MPPointF instance){
        pool.recycle(instance);
    }


    @Override
    protected ObjectPool.Poolable instantiate() {
        return new MPPointF();
    }
}