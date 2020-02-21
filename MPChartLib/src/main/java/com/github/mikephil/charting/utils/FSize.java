
package com.github.mikephil.charting.utils;

/**
 * Class for describing width and height dimensions in some arbitrary
 * unit. Replacement for the android.Util.SizeF which is available only on API >= 21.
 */
@SuppressWarnings("unchecked")
public final class FSize extends ObjectPool.Poolable{

    // TODO : Encapsulate width & height

    public float width;
    public float height;

    private static final ObjectPool<FSize> pool;

    static {
        pool = ObjectPool.create(256, new FSize());
        pool.setReplenishPercentage(0.5f);
    }


    protected ObjectPool.Poolable instantiate(){
        return new FSize();
    }

    public static FSize getInstance(final float width, final float height){
        FSize result = pool.get();
        result.width = width;
        result.height = height;
        return result;
    }

    public static void recycleInstance(FSize instance){
        pool.recycle(instance);
    }

    private FSize() {
        this.width = (float) 0;
        this.height = (float) 0;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj instanceof FSize) {
            final FSize other = (FSize) obj;
            return width == other.width && height == other.height;
        }
        return false;
    }

    @Override
    public String toString() {
        return width + "x" + height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Float.floatToIntBits(width) ^ Float.floatToIntBits(height);
    }
}
