package com.github.mikephil.charting.formatter;

/**
 * This formatter is used for passing an array of x-axis labels, on whole x steps.
 */
public class IndexAxisValueFormatter extends ValueFormatter
{
    private String[] mValues = new String[] {};
    private int mValueCount = 0;

    @Override
    public String getFormattedValue(float value) {
        int index = Math.round(value);

        if (index < 0 || index >= mValueCount || index != (int)value)
            return "";

        return mValues[index];
    }

    public String[] getValues()
    {
        return mValues;
    }

    public void setValues(String[] values)
    {
        if (values == null)
            values = new String[] {};

        this.mValues = values;
        this.mValueCount = values.length;
    }
}
