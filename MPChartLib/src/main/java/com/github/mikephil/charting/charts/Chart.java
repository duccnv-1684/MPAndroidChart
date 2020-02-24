package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;

/**
 * Baseclass of all Chart-Views.
 *
 * @author Philipp Jahoda
 */
public abstract class Chart<T extends ChartData<? extends IDataSet<? extends Entry>>> extends ViewGroup {

    static final String LOG_TAG = "MPAndroidChart";

    /**
     * flag that indicates if logging is enabled or not
     */
    final boolean mLogEnabled = false;

    /**
     * object that holds all data that was originally set for the chart, before
     * it was modified or any filtering algorithms had been applied
     */
    T mData = null;

    /**
     * default value-formatter, number of digits depends on provided chart-data
     */
    private final DefaultValueFormatter mDefaultValueFormatter = new DefaultValueFormatter(0);

    /**
     * paint object for drawing the information text when there are no values in
     * the chart
     */
    private Paint mInfoPaint;

    /**
     * the object representing the labels on the x-axis
     */
    XAxis mXAxis;
    /**
     * the legend object containing all data associated with the legend
     */
    Legend mLegend;


    LegendRenderer mLegendRenderer;

    /**
     * object responsible for rendering the data
     */
    DataRenderer mRenderer;

    /**
     * object that manages the bounds and drawing constraints of the chart
     */
    final ViewPortHandler mViewPortHandler = new ViewPortHandler();

    /**
     * object responsible for animations
     */
    ChartAnimator mAnimator;

    /**
     * default constructor for initialization in code
     */
    public Chart(Context context) {
        super(context);
        init();
    }

    /**
     * constructor for initialization in xml
     */
    public Chart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * even more awesome constructor
     */
    public Chart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * initialize all paints and stuff
     */
    void init() {

        setWillNotDraw(false);
        // setLayerType(View.LAYER_TYPE_HARDWARE, null);

        mAnimator = new ChartAnimator();

        // initialize the utils
        Utils.init(getContext());

        mLegend = new Legend();

        mLegendRenderer = new LegendRenderer(mViewPortHandler, mLegend);

        mXAxis = new XAxis();

        mInfoPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mInfoPaint.setColor(Color.rgb(247, 189, 51)); // orange
        mInfoPaint.setTextAlign(Align.CENTER);
        mInfoPaint.setTextSize(Utils.convertDpToPixel(12f));

        if (mLogEnabled)
            Log.i("", "Chart.init()");
    }

    // public void initWithDummyData() {
    // ColorTemplate template = new ColorTemplate();
    // template.addColorsForDataSets(ColorTemplate.COLORFUL_COLORS,
    // getContext());
    //
    // setColorTemplate(template);
    // setDrawYValues(false);
    //
    // ArrayList<String> xVals = new ArrayList<String>();
    // Calendar calendar = Calendar.getInstance();
    // for (int i = 0; i < 12; i++) {
    // xVals.add(calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT,
    // Locale.getDefault()));
    // }
    //
    // ArrayList<DataSet> dataSets = new ArrayList<DataSet>();
    // for (int i = 0; i < 3; i++) {
    //
    // ArrayList<Entry> yVals = new ArrayList<Entry>();
    //
    // for (int j = 0; j < 12; j++) {
    // float val = (float) (Math.random() * 100);
    // yVals.add(new Entry(val, j));
    // }
    //
    // DataSet set = new DataSet(yVals, "DataSet " + i);
    // dataSets.add(set); // add the datasets
    // }
    // // create a data object with the datasets
    // ChartData data = new ChartData(xVals, dataSets);
    // setData(data);
    // invalidate();
    // }

    /**
     * Sets a new data object for the chart. The data object contains all values
     * and information needed for displaying.
     *
     * @param data
     */
    public void setData(T data) {

        mData = data;
        mOffsetsCalculated = false;

        if (data == null) {
            return;
        }

        // calculate how many digits are needed
        setupDefaultFormatter(data.getYMin(), data.getYMax());

        for (IDataSet set : mData.getDataSets()) {
            if (set.needsFormatter() || set.getValueFormatter() == mDefaultValueFormatter)
                set.setValueFormatter(mDefaultValueFormatter);
        }

        // let the chart know there is new data
        notifyDataSetChanged();

        if (mLogEnabled)
            Log.i(LOG_TAG, "Data is set.");
    }

    /**
     * Lets the chart know its underlying data has changed and performs all
     * necessary recalculations. It is crucial that this method is called
     * everytime data is changed dynamically. Not calling this method can lead
     * to crashes or unexpected behaviour.
     */
    protected abstract void notifyDataSetChanged();

    /**
     * Calculates the offsets of the chart to the border depending on the
     * position of an eventual legend or depending on the length of the y-axis
     * and x-axis labels and their position
     */
    protected abstract void calculateOffsets();

    /**
     * Calculates the required number of digits for the values that might be
     * drawn in the chart (if enabled), and creates the default-value-formatter
     */
    private void setupDefaultFormatter(float min, float max) {

        float reference;

        if (mData == null || mData.getEntryCount() < 2) {

            reference = Math.max(Math.abs(min), Math.abs(max));
        } else {
            reference = Math.abs(max - min);
        }

        int digits = Utils.getDecimals(reference);

        // setup the formatter with a new number of digits
        mDefaultValueFormatter.setup(digits);
    }

    /**
     * flag that indicates if offsets calculation has already been done or not
     */
    private boolean mOffsetsCalculated = false;

    @Override
    protected void onDraw(Canvas canvas) {
        // super.onDraw(canvas);

        if (mData == null) {

            String mNoDataText = "No chart data available.";
            boolean hasText = !TextUtils.isEmpty(mNoDataText);

            if (hasText) {
                MPPointF pt = getCenter();

                switch (mInfoPaint.getTextAlign()) {
                    case LEFT:
                        pt.x = 0;
                        canvas.drawText(mNoDataText, pt.x, pt.y, mInfoPaint);
                        break;

                    case RIGHT:
                        pt.x *= 2.0;
                        canvas.drawText(mNoDataText, pt.x, pt.y, mInfoPaint);
                        break;

                    default:
                        canvas.drawText(mNoDataText, pt.x, pt.y, mInfoPaint);
                        break;
                }
            }

            return;
        }

        if (!mOffsetsCalculated) {

            calculateOffsets();
            mOffsetsCalculated = true;
        }
    }


    /**
     * Returns the object representing all x-labels, this method can be used to
     * acquire the XAxis object and modify it (e.g. change the position of the
     * labels, styling, etc.)
     *
     * @return
     */
    public XAxis getXAxis() {
        return mXAxis;
    }

    /**
     * Returns a recyclable MPPointF instance.
     * Returns the center point of the chart (the whole View) in pixels.
     *
     * @return
     */
    MPPointF getCenter() {
        return MPPointF.getInstance(getWidth() / 2f, getHeight() / 2f);
    }

    /**
     * Returns a recyclable MPPointF instance.
     * Returns the center of the chart taking offsets under consideration.
     * (returns the center of the content rectangle)
     *
     * @return
     */
    public MPPointF getCenterOffsets() {
        return mViewPortHandler.getContentCenter();
    }

    /**
     * @return the extra offset to be appended to the viewport's top
     */
    float getExtraTopOffset() {
        return 0.f;
    }

    /**
     * @return the extra offset to be appended to the viewport's right
     */
    float getExtraRightOffset() {
        return 0.f;
    }

    /**
     * @return the extra offset to be appended to the viewport's bottom
     */
    float getExtraBottomOffset() {
        return 0.f;
    }

    /**
     * @return the extra offset to be appended to the viewport's left
     */
    float getExtraLeftOffset() {
        return 0.f;
    }


    /**
     * Returns the Legend object of the chart. This method can be used to get an
     * instance of the legend in order to customize the automatically generated
     * Legend.
     *
     * @return
     */
    public Legend getLegend() {
        return mLegend;
    }

    /**
     * Returns the ChartData object that has been set for the chart.
     *
     * @return
     */
    public T getData() {
        return mData;
    }

    /**
     * tasks to be done after the view is setup
     */
    private final ArrayList<Runnable> mJobs = new ArrayList<>();

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).layout(left, top, right, bottom);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = (int) Utils.convertDpToPixel(50f);
        setMeasuredDimension(
                Math.max(getSuggestedMinimumWidth(),
                        resolveSize(size,
                                widthMeasureSpec)),
                Math.max(getSuggestedMinimumHeight(),
                        resolveSize(size,
                                heightMeasureSpec)));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mLogEnabled)
            Log.i(LOG_TAG, "OnSizeChanged()");

        if (w > 0 && h > 0 && w < 10000 && h < 10000) {
            if (mLogEnabled)
                Log.i(LOG_TAG, "Setting chart dimens, width: " + w + ", height: " + h);
            mViewPortHandler.setChartDimens(w, h);
        } else {
            if (mLogEnabled)
                Log.w(LOG_TAG, "*Avoiding* setting chart dimens! width: " + w + ", height: " + h);
        }

        // This may cause the chart view to mutate properties affecting the view port --
        //   lets do this before we try to run any pending jobs on the view port itself
        notifyDataSetChanged();

        for (Runnable r : mJobs) {
            post(r);
        }

        mJobs.clear();

        super.onSizeChanged(w, h, oldw, oldh);
    }

}
