package com.ksu.nafea.ui.nviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;

import com.ksu.nafea.R;


public class NSpinner extends androidx.appcompat.widget.AppCompatSpinner
{
    private ArrayAdapter<String> spinnerAdapter;
    private int previousSelectedPosition = 0;


    public NSpinner(Context context) {
        super(context);
        init(null, 0);
    }

    public NSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public NSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NSpinner, defStyle, 0);
        spinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        setAdapter(spinnerAdapter);

        a.recycle();
    }

    public void addOption(String option)
    {
        spinnerAdapter.add(option);
    }
    public void removeOption(String option)
    {
        spinnerAdapter.remove(option);
    }

    public void removeAllOptions()
    {
        spinnerAdapter.clear();
    }

    public String getSelectedOption()
    {
        return (String) getSelectedItem();
    }

    public int getPreviousSelectedPosition() {
        return previousSelectedPosition;
    }

    public void setPreviousSelectedPosition(int newSelectedPosition) {
        this.previousSelectedPosition = newSelectedPosition;
    }

}
