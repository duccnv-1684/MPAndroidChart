package com.xxmassdeveloper.mpchartexample;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.data.IRadarDataSet;

import java.util.ArrayList;

public class RadarChartActivity extends AppCompatActivity {

    private RadarChart chart;

    private Typeface tfLight;
    private Typeface tfRegular;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_radarchart);

        setTitle("RadarChartActivity");
        tfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");
        tfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        chart = findViewById(R.id.chart1);
        chart.setBackgroundColor(Color.rgb(255, 255, 255));

        chart.setWebLineWidth(1f);
        chart.setWebColor(0x80000000);
        chart.setWebLineWidthInner(0.75f);
        chart.setWebLineWidth(0.75f);
        chart.setWebColorInner(0x80000000);
        chart.setWebAlpha(100);

        setData();

//        chart.animateXY(1400, 1400, Easing.EaseInOutQuad);



        XAxis xAxis = chart.getXAxis();
        xAxis.setTypeface(tfRegular);
        xAxis.setTextSize(16f);
        xAxis.setYOffset(0f);
        xAxis.setTextColor(0x80000000);
        xAxis.setXOffset(0f);
        xAxis.setValueFormatter(new ValueFormatter() {

            private final String[] mActivities = new String[]{"Burger", "Steak", "Salad", "Pasta", "Pizza"};

            @Override
            public String getFormattedValue(float value) {
                return mActivities[(int) value % mActivities.length];
            }
        });
        xAxis.setTextColor(Color.RED);

        YAxis yAxis = chart.getYAxis();
        yAxis.setTypeface(tfLight);
        yAxis.setLabelCount(6, true);
        yAxis.setTextSize(9f);
        yAxis.setAxisMinimum(30f);
        yAxis.setAxisMaximum(80f);
        yAxis.setDrawLabels(true);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setTypeface(tfRegular);
        l.setXEntrySpace(7f);
        l.setTextSize(18f);
        l.setYEntrySpace(5f);
        l.setTextColor(Color.BLACK);
    }

    private void setData() {

        float mul = 40;
        float min = 30;
        int cnt = 8;

        ArrayList<RadarEntry> entries1 = new ArrayList<>();
        ArrayList<RadarEntry> entries2 = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < cnt; i++) {
            float val1 = 50+i*2;
            entries1.add(new RadarEntry(val1));

            float val2 = 70-i*2;
            entries2.add(new RadarEntry(val2));
        }

        RadarDataSet set1 = new RadarDataSet(entries1, "This is first label");
        set1.setColor(Color.RED);
        set1.setFillColor(Color.RED);
        set1.setDrawFilled(true);
        set1.setFillAlpha(120);

        RadarDataSet set2 = new RadarDataSet(entries2, "This is second label (an alternative)");
        set2.setColor(Color.BLUE);
        set2.setFillColor(Color.BLUE);
        set2.setDrawFilled(true);
        set2.setFillAlpha(120);

        ArrayList<IRadarDataSet> sets = new ArrayList<>();
        sets.add(set1);
        sets.add(set2);

        RadarData data = new RadarData(sets);
        data.setValueTypeface(tfLight);
        data.setValueTextSize(8f);
        data.setDrawValues(false);
        data.setValueTextColor(Color.WHITE);

        chart.setData(data);
        chart.invalidate();
    }
}
