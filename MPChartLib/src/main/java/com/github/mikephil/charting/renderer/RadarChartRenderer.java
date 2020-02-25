package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.IRadarDataSet;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class RadarChartRenderer extends Renderer {

    private final RadarChart mChart;

    /**
     * paint for drawing the web
     */
    private final Paint mWebPaint;

    /**
     * main paint object used for rendering
     */
    private final Paint mRenderPaint;

    /**
     * paint used for highlighting values
     */
    private Paint mHighlightPaint;

    /**
     * paint object for drawing values (text representing values of chart
     * entries)
     */
    private final Paint mValuePaint;

    public RadarChartRenderer(RadarChart chart,
                              ViewPortHandler viewPortHandler) {
        super(viewPortHandler);

        mRenderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRenderPaint.setStyle(Paint.Style.FILL);


        mValuePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mValuePaint.setColor(Color.rgb(63, 63, 63));
        mValuePaint.setTextAlign(Paint.Align.CENTER);
        mValuePaint.setTextSize(Utils.convertDpToPixel(9f));

        mHighlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHighlightPaint.setStyle(Paint.Style.STROKE);
        mHighlightPaint.setStrokeWidth(2f);
        mHighlightPaint.setColor(Color.rgb(255, 187, 115));
        mChart = chart;

        mHighlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mHighlightPaint.setStyle(Paint.Style.STROKE);
        mHighlightPaint.setStrokeWidth(2f);
        mHighlightPaint.setColor(Color.rgb(255, 187, 115));

        mWebPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mWebPaint.setStyle(Paint.Style.STROKE);

    }

    private void applyValueTextStyle(IRadarDataSet set) {

        mValuePaint.setTypeface(set.getValueTypeface());
        mValuePaint.setTextSize(set.getValueTextSize());
    }


    /**
     * Draws any kind of additional information (e.g. line-circles).
     */

    private boolean shouldDrawValues(IRadarDataSet set) {
        return set.isVisible() && (set.isDrawValuesEnabled() || set.isDrawIconsEnabled());
    }

    /**
     * Draws the provided path in filled mode with the provided drawable.
     *
     * @param c
     * @param filledPath
     * @param drawable
     */
    private void drawFilledPath(Canvas c, Path filledPath, Drawable drawable) {

        if (clipPathSupported()) {

            int save = c.save();
            c.clipPath(filledPath);

            drawable.setBounds((int) mViewPortHandler.contentLeft(),
                    (int) mViewPortHandler.contentTop(),
                    (int) mViewPortHandler.contentRight(),
                    (int) mViewPortHandler.contentBottom());
            drawable.draw(c);

            c.restoreToCount(save);
        } else {
            throw new RuntimeException("Fill-drawables not (yet) supported below API level 18, " +
                    "this code was run on API level " + Utils.getSDKInt() + ".");
        }
    }

    /**
     * Draws the provided path in filled mode with the provided color and alpha.
     * Special thanks to Angelo Suzuki (https://github.com/tinsukE) for this.
     *
     * @param c
     * @param filledPath
     * @param fillColor
     * @param fillAlpha
     */
    private void drawFilledPath(Canvas c, Path filledPath, int fillColor, int fillAlpha) {

        int color = (fillAlpha << 24) | (fillColor & 0xffffff);

        if (clipPathSupported()) {

            int save = c.save();

            c.clipPath(filledPath);

            c.drawColor(color);
            c.restoreToCount(save);
        } else {

            // save
            Paint.Style previous = mRenderPaint.getStyle();
            int previousColor = mRenderPaint.getColor();

            // set
            mRenderPaint.setStyle(Paint.Style.FILL);
            mRenderPaint.setColor(color);

            c.drawPath(filledPath, mRenderPaint);

            // restore
            mRenderPaint.setColor(previousColor);
            mRenderPaint.setStyle(previous);
        }
    }

    /**
     * Clip path with hardware acceleration only working properly on API level 18 and above.
     *
     * @return
     */
    private boolean clipPathSupported() {
        return Utils.getSDKInt() >= 18;
    }

    public void drawData(Canvas c) {

        RadarData radarData = mChart.getData();

        int mostEntries = radarData.getMaxEntryCountSet().getEntryCount();

        for (IRadarDataSet set : radarData.getDataSets()) {

            if (set.isVisible()) {
                drawDataSet(c, set, mostEntries);
            }
        }
    }

    private final Path mDrawDataSetSurfacePathBuffer = new Path();
    /**
     * Draws the RadarDataSet
     *
     * @param c
     * @param dataSet
     * @param mostEntries the entry count of the dataset with the most entries
     */
    private void drawDataSet(Canvas c, IRadarDataSet dataSet, int mostEntries) {


        float sliceangle = mChart.getSliceAngle();

        // calculate the factor that is needed for transforming the value to
        // pixels
        float factor = mChart.getFactor();

        MPPointF center = mChart.getCenterOffsets();
        MPPointF pOut = MPPointF.getInstance(0,0);
        Path surface = mDrawDataSetSurfacePathBuffer;
        surface.reset();

        boolean hasMovedToPoint = false;

        for (int j = 0; j < dataSet.getEntryCount(); j++) {

            mRenderPaint.setColor(dataSet.getColor(j));

            RadarEntry e = dataSet.getEntryForIndex(j);

            Utils.getPosition(
                    center,
                    (e.getY() - mChart.getYChartMin()) * factor,
                    sliceangle * j + mChart.getRotationAngle(), pOut);

            if (Float.isNaN(pOut.x))
                continue;

            if (!hasMovedToPoint) {
                surface.moveTo(pOut.x, pOut.y);
                hasMovedToPoint = true;
            } else
                surface.lineTo(pOut.x, pOut.y);
        }

        if (dataSet.getEntryCount() > mostEntries) {
            // if this is not the largest set, draw a line to the center before closing
            surface.lineTo(center.x, center.y);
        }

        surface.close();

        if (dataSet.isDrawFilledEnabled()) {

            final Drawable drawable = dataSet.getFillDrawable();
            if (drawable != null) {

                drawFilledPath(c, surface, drawable);
            } else {

                drawFilledPath(c, surface, dataSet.getFillColor(), dataSet.getFillAlpha());
            }
        }

        mRenderPaint.setStrokeWidth(dataSet.getLineWidth());
        mRenderPaint.setStyle(Paint.Style.STROKE);

        // draw the line (only if filled is disabled or alpha is below 255)
        if (!dataSet.isDrawFilledEnabled() || dataSet.getFillAlpha() < 255)
//            c.drawPath(surface, mRenderPaint);

        MPPointF.recycleInstance(center);
        MPPointF.recycleInstance(pOut);
    }

    public void drawValues(Canvas c) {


        float sliceangle = mChart.getSliceAngle();

        // calculate the factor that is needed for transforming the value to
        // pixels
        float factor = mChart.getFactor();

        MPPointF center = mChart.getCenterOffsets();
        MPPointF pOut = MPPointF.getInstance(0,0);
        MPPointF pIcon = MPPointF.getInstance(0,0);

        float yoffset = Utils.convertDpToPixel(5f);

        for (int i = 0; i < mChart.getData().getDataSetCount(); i++) {

            IRadarDataSet dataSet = mChart.getData().getDataSetByIndex(i);

            if (!shouldDrawValues(dataSet))
                continue;

            // apply the text-styling defined by the DataSet
            applyValueTextStyle(dataSet);

            ValueFormatter formatter = dataSet.getValueFormatter();

            MPPointF iconsOffset = MPPointF.getInstance(dataSet.getIconsOffset());
            iconsOffset.x = Utils.convertDpToPixel(iconsOffset.x);
            iconsOffset.y = Utils.convertDpToPixel(iconsOffset.y);

            for (int j = 0; j < dataSet.getEntryCount(); j++) {

                RadarEntry entry = dataSet.getEntryForIndex(j);

                 Utils.getPosition(
                         center,
                         (entry.getY() - mChart.getYChartMin()) * factor,
                         sliceangle * j + mChart.getRotationAngle(),
                         pOut);

                if (dataSet.isDrawValuesEnabled()) {
                    drawValue(c, formatter.getRadarLabel(entry), pOut.x, pOut.y - yoffset, dataSet.getValueTextColor(j));
                }

                if (entry.getIcon() != null && dataSet.isDrawIconsEnabled()) {

                    Drawable icon = entry.getIcon();

                    Utils.getPosition(
                            center,
                            (entry.getY()) * factor + iconsOffset.y,
                            sliceangle * j + mChart.getRotationAngle(),
                            pIcon);

                    //noinspection SuspiciousNameCombination
                    pIcon.y += iconsOffset.x;

                    Utils.drawImage(
                            c,
                            icon,
                            (int)pIcon.x,
                            (int)pIcon.y,
                            icon.getIntrinsicWidth(),
                            icon.getIntrinsicHeight());
                }
            }

            MPPointF.recycleInstance(iconsOffset);
        }

        MPPointF.recycleInstance(center);
        MPPointF.recycleInstance(pOut);
        MPPointF.recycleInstance(pIcon);
    }

    private void drawValue(Canvas c, String valueText, float x, float y, int color) {
        mValuePaint.setColor(color);
        c.drawText(valueText, x, y, mValuePaint);
    }

    public void drawExtras(Canvas c) {
        drawWeb(c);
    }

    private void drawWeb(Canvas c) {

        float sliceangle = mChart.getSliceAngle();

        // calculate the factor that is needed for transforming the value to
        // pixels
        float factor = mChart.getFactor();
        float rotationangle = mChart.getRotationAngle();

        MPPointF center = mChart.getCenterOffsets();

        // draw the web lines that come from the center
        mWebPaint.setStrokeWidth(mChart.getWebLineWidth());
        mWebPaint.setColor(mChart.getWebColor());
        mWebPaint.setAlpha(mChart.getWebAlpha());

        final int xIncrements = 1 + mChart.getSkipWebLineCount();
        int maxEntryCount = mChart.getData().getMaxEntryCountSet().getEntryCount();

        MPPointF p = MPPointF.getInstance(0,0);
        for (int i = 0; i < maxEntryCount; i += xIncrements) {

            Utils.getPosition(
                    center,
                    mChart.getYRange() * factor,
                    sliceangle * i + rotationangle,
                    p);

            c.drawLine(center.x, center.y, p.x, p.y, mWebPaint);
        }
        MPPointF.recycleInstance(p);

        // draw the inner-web
        mWebPaint.setStrokeWidth(mChart.getWebLineWidthInner());
        mWebPaint.setColor(mChart.getWebColorInner());
        mWebPaint.setAlpha(mChart.getWebAlpha());

        int labelCount = mChart.getYAxis().mEntryCount;

        MPPointF p1out = MPPointF.getInstance(0,0);
        MPPointF p2out = MPPointF.getInstance(0,0);
        for (int j = 0; j < labelCount; j++) {
                float r = (mChart.getYAxis().mEntries[j] - mChart.getYChartMin()) * factor;
            c.drawCircle(center.x, center.y, r, mWebPaint);
        }
        MPPointF.recycleInstance(p1out);
        MPPointF.recycleInstance(p2out);
    }

}
