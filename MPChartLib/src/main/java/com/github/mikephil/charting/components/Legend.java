package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing the legend of the chart. The legend will contain one entry
 * per color and DataSet. Multiple colors in one DataSet are grouped together.
 * The legend object is NOT available before setting data to the chart.
 *
 * @author Philipp Jahoda
 */
public class Legend extends ComponentBase {

    public enum LegendForm {
        /**
         * Avoid drawing a form
         */
        NONE,

        /**
         * Do not draw the a form, but leave space for it
         */
        EMPTY,

        /**
         * Use default (default dataset's form to the legend's form)
         */
        DEFAULT,

        /**
         * Draw a square
         */
        SQUARE,

        /**
         * Draw a circle
         */
        CIRCLE,

        /**
         * Draw a horizontal line
         */
        LINE
    }

    public enum LegendHorizontalAlignment {
        LEFT, CENTER, RIGHT
    }

    public enum LegendVerticalAlignment {
        TOP, CENTER, BOTTOM
    }

    public enum LegendOrientation {
        HORIZONTAL, VERTICAL
    }

    public enum LegendDirection {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    /**
     * The legend entries array
     */
    private LegendEntry[] mEntries = new LegendEntry[]{};

    private LegendHorizontalAlignment mHorizontalAlignment = LegendHorizontalAlignment.LEFT;
    private LegendVerticalAlignment mVerticalAlignment = LegendVerticalAlignment.BOTTOM;
    private LegendOrientation mOrientation = LegendOrientation.HORIZONTAL;
    private boolean mDrawInside = false;

    /**
     * the text direction for the legend
     */
    private final LegendDirection mDirection = LegendDirection.LEFT_TO_RIGHT;

    /**
     * the shape/form the legend colors are drawn in
     */
    private final LegendForm mShape = LegendForm.SQUARE;

    /**
     * the size of the legend forms/shapes
     */
    private final float mFormSize = 8f;

    /**
     * the space between the legend entries on a horizontal axis, default 6f
     */
    private float mXEntrySpace = 6f;

    /**
     * the space between the legend entries on a vertical axis, default 5f
     */
    private float mYEntrySpace = 0f;

    /**
     * the space between the legend entries on a vertical axis, default 2f
     * private float mYEntrySpace = 2f; /** the space between the form and the
     * actual label/text
     */
    private final float mFormToTextSpace = 5f;

    /**
     * the space that should be left between stacked forms
     */
    private final float mStackSpace = 3f;

    /**
     * default constructor
     */
    public Legend() {

        this.mTextSize = Utils.convertDpToPixel(10f);
        this.mXOffset = Utils.convertDpToPixel(5f);
        this.mYOffset = Utils.convertDpToPixel(3f); // 2
    }

    /**
     * This method sets the automatically computed colors for the legend. Use setCustom(...) to set custom colors.
     *
     * @param entries
     */
    public void setEntries(List<LegendEntry> entries) {
        mEntries = entries.toArray(new LegendEntry[0]);
    }

    public LegendEntry[] getEntries() {
        return mEntries;
    }

    /**
     * returns the maximum height in pixels across all legend labels
     *
     * @param p the paint object used for rendering the text
     * @return
     */
    private float getMaximumEntryHeight(Paint p) {

        float max = 0f;

        for (LegendEntry entry : mEntries) {
            String label = entry.label;
            if (label == null) continue;

            float length = (float) Utils.calcTextHeight(p, label);

            if (length > max)
                max = length;
        }

        return max;
    }


    /**
     * @return true if a custom legend entries has been set default
     * false (automatic legend)
     */
    public boolean isLegendCustom() {
        return true;
    }

    /**
     * returns the horizontal alignment of the legend
     *
     * @return
     */
    public LegendHorizontalAlignment getHorizontalAlignment() {
        return mHorizontalAlignment;
    }

    /**
     * sets the horizontal alignment of the legend
     *
     * @param value
     */
    public void setHorizontalAlignment(LegendHorizontalAlignment value) {
        mHorizontalAlignment = value;
    }

    /**
     * returns the vertical alignment of the legend
     *
     * @return
     */
    public LegendVerticalAlignment getVerticalAlignment() {
        return mVerticalAlignment;
    }

    /**
     * sets the vertical alignment of the legend
     *
     * @param value
     */
    public void setVerticalAlignment(LegendVerticalAlignment value) {
        mVerticalAlignment = value;
    }

    /**
     * returns the orientation of the legend
     *
     * @return
     */
    public LegendOrientation getOrientation() {
        return mOrientation;
    }

    /**
     * sets the orientation of the legend
     *
     * @param value
     */
    public void setOrientation(LegendOrientation value) {
        mOrientation = value;
    }

    /**
     * returns whether the legend will draw inside the chart or outside
     *
     * @return
     */
    public boolean isDrawInsideEnabled() {
        return mDrawInside;
    }

    /**
     * sets whether the legend will draw inside the chart or outside
     *
     * @param value
     */
    public void setDrawInside(boolean value) {
        mDrawInside = value;
    }

    /**
     * returns the text direction of the legend
     *
     * @return
     */
    public LegendDirection getDirection() {
        return mDirection;
    }

    /**
     * returns the current form/shape that is set for the legend
     *
     * @return
     */
    public LegendForm getForm() {
        return mShape;
    }

    /**
     * returns the size in dp of the legend forms
     *
     * @return
     */
    public float getFormSize() {
        return mFormSize;
    }

    /**
     * returns the line width in dp for drawing forms that consist of lines
     *
     * @return
     */
    public float getFormLineWidth() {
        return 3f;
    }

    /**
     * @return The line dash path effect used for shapes that consist of lines.
     */
    public DashPathEffect getFormLineDashEffect() {
        return null;
    }

    /**
     * returns the space between the legend entries on a horizontal axis in
     * pixels
     *
     * @return
     */
    public float getXEntrySpace() {
        return mXEntrySpace;
    }

    /**
     * sets the space between the legend entries on a horizontal axis in pixels,
     * converts to dp internally
     *
     * @param space
     */
    public void setXEntrySpace(float space) {
        mXEntrySpace = space;
    }

    /**
     * returns the space between the legend entries on a vertical axis in pixels
     *
     * @return
     */
    public float getYEntrySpace() {
        return mYEntrySpace;
    }

    /**
     * sets the space between the legend entries on a vertical axis in pixels,
     * converts to dp internally
     *
     * @param space
     */
    public void setYEntrySpace(float space) {
        mYEntrySpace = space;
    }

    /**
     * returns the space between the form and the actual label/text
     *
     * @return
     */
    public float getFormToTextSpace() {
        return mFormToTextSpace;
    }

    /**
     * returns the space that is left out between stacked forms (with no label)
     *
     * @return
     */
    public float getStackSpace() {
        return mStackSpace;
    }

    /**
     * the total width of the legend (needed width space)
     */
    public float mNeededWidth = 0f;

    /**
     * the total height of the legend (needed height space)
     */
    public float mNeededHeight = 0f;

    public float mTextHeightMax = 0f;


    /**
     * The maximum relative size out of the whole chart view. / If the legend is
     * to the right/left of the chart, then this affects the width of the
     * legend. / If the legend is to the top/bottom of the chart, then this
     * affects the height of the legend. / If the legend is the center of the
     * piechart, then this defines the size of the rectangular bounds out of the
     * size of the "hole". / default: 0.95f (95%)
     *
     * @return
     */
    public float getMaxSizePercent() {
        return 0.95f;
    }

    private final List<FSize> mCalculatedLabelSizes = new ArrayList<>(16);
    private final List<Boolean> mCalculatedLabelBreakPoints = new ArrayList<>(16);
    private final List<FSize> mCalculatedLineSizes = new ArrayList<>(16);

    public List<FSize> getCalculatedLabelSizes() {
        return mCalculatedLabelSizes;
    }

    public List<Boolean> getCalculatedLabelBreakPoints() {
        return mCalculatedLabelBreakPoints;
    }

    public List<FSize> getCalculatedLineSizes() {
        return mCalculatedLineSizes;
    }

    /**
     * Calculates the dimensions of the Legend. This includes the maximum width
     * and height of a single entry, as well as the total width and height of
     * the Legend.
     *
     * @param labelpaint
     */
    public void calculateDimensions(Paint labelpaint) {

        float defaultFormSize = Utils.convertDpToPixel(mFormSize);
        float stackSpace = Utils.convertDpToPixel(mStackSpace);
        float formToTextSpace = Utils.convertDpToPixel(mFormToTextSpace);
        float xEntrySpace = Utils.convertDpToPixel(mXEntrySpace);
        float yEntrySpace = Utils.convertDpToPixel(mYEntrySpace);
        LegendEntry[] entries = mEntries;
        int entryCount = entries.length;

        mTextHeightMax = getMaximumEntryHeight(labelpaint);

        switch (mOrientation) {
            case VERTICAL: {

                float maxWidth = 0f, maxHeight = 0f, width = 0f;
                float labelLineHeight = Utils.getLineHeight(labelpaint);
                boolean wasStacked = false;

                for (int i = 0; i < entryCount; i++) {

                    LegendEntry e = entries[i];
                    boolean drawingForm = e.form != LegendForm.NONE;
                    float formSize = Float.isNaN(e.formSize)
                            ? defaultFormSize
                            : Utils.convertDpToPixel(e.formSize);
                    String label = e.label;

                    if (!wasStacked)
                        width = 0.f;

                    if (drawingForm) {
                        if (wasStacked)
                            width += stackSpace;
                        width += formSize;
                    }

                    // grouped forms have null labels
                    if (label != null) {

                        // make a step to the left
                        if (drawingForm && !wasStacked)
                            width += formToTextSpace;
                        else if (wasStacked) {
                            maxWidth = Math.max(maxWidth, width);
                            maxHeight += labelLineHeight + yEntrySpace;
                            width = 0.f;
                            wasStacked = false;
                        }

                        width += Utils.calcTextWidth(labelpaint, label);

                        maxHeight += labelLineHeight + yEntrySpace;
                    } else {
                        wasStacked = true;
                        width += formSize;
                        if (i < entryCount - 1)
                            width += stackSpace;
                    }

                    maxWidth = Math.max(maxWidth, width);
                }

                mNeededWidth = maxWidth;
                mNeededHeight = maxHeight;

                break;
            }
            case HORIZONTAL: {

                float labelLineHeight = Utils.getLineHeight(labelpaint);
                float labelLineSpacing = Utils.getLineSpacing(labelpaint) + yEntrySpace;

                // Start calculating layout
                float maxLineWidth = 0.f;
                float currentLineWidth = 0.f;
                float requiredWidth = 0.f;
                int stackedStartIndex = -1;

                mCalculatedLabelBreakPoints.clear();
                mCalculatedLabelSizes.clear();
                mCalculatedLineSizes.clear();

                for (int i = 0; i < entryCount; i++) {

                    LegendEntry e = entries[i];
                    boolean drawingForm = e.form != LegendForm.NONE;
                    float formSize = Float.isNaN(e.formSize)
                            ? defaultFormSize
                            : Utils.convertDpToPixel(e.formSize);
                    String label = e.label;

                    mCalculatedLabelBreakPoints.add(false);

                    if (stackedStartIndex == -1) {
                        // we are not stacking, so required width is for this label
                        // only
                        requiredWidth = 0.f;
                    } else {
                        // add the spacing appropriate for stacked labels/forms
                        requiredWidth += stackSpace;
                    }

                    // grouped forms have null labels
                    if (label != null) {

                        mCalculatedLabelSizes.add(Utils.calcTextSize(labelpaint, label));
                        requiredWidth += drawingForm ? formToTextSpace + formSize : 0.f;
                        requiredWidth += mCalculatedLabelSizes.get(i).width;
                    } else {

                        mCalculatedLabelSizes.add(FSize.getInstance(0.f, 0.f));
                        requiredWidth += drawingForm ? formSize : 0.f;

                        if (stackedStartIndex == -1) {
                            // mark this index as we might want to break here later
                            stackedStartIndex = i;
                        }
                    }

                    if (label != null || i == entryCount - 1) {

                        float requiredSpacing = currentLineWidth == 0.f ? 0.f : xEntrySpace;

                        // No word wrapping, it must fit.
                        // The line is empty, it must fit
                        // It simply fits
                        // Expand current line
                        currentLineWidth += requiredSpacing + requiredWidth;

                        if (i == entryCount - 1) {
                            // Add last line size to array
                            mCalculatedLineSizes.add(FSize.getInstance(currentLineWidth, labelLineHeight));
                            maxLineWidth = Math.max(maxLineWidth, currentLineWidth);
                        }
                    }

                    stackedStartIndex = label != null ? -1 : stackedStartIndex;
                }

                mNeededWidth = maxLineWidth;
                mNeededHeight = labelLineHeight
                        * (float) (mCalculatedLineSizes.size())
                        + labelLineSpacing *
                        (float) (mCalculatedLineSizes.size() == 0
                                ? 0
                                : (mCalculatedLineSizes.size() - 1));

                break;
            }
        }

        mNeededHeight += mYOffset;
        mNeededWidth += mXOffset;
    }
}
