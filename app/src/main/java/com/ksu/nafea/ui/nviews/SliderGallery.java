package com.ksu.nafea.ui.nviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ksu.nafea.R;
import com.ksu.nafea.utilities.NafeaUtil;


public class SliderGallery extends LinearLayout
{
    public int iconsCount = 3;

    public SliderGallery(Context context)
    {
        super(context);
    }

    public SliderGallery(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SliderGallery(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }


    public void addTextV(String text, int textSize, int width, int height, int margin)
    {
        final float dp = getResources().getDisplayMetrics().density;

        TextView icon = new TextView(getContext());

        width = (int) (width * dp);
        height = (int) (height * dp);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);

        margin = (int) (margin * dp);
        layoutParams.setMargins(margin, margin, margin, margin);

        icon.setLayoutParams(layoutParams);


        icon.setText(text);
        icon.setTextSize(textSize);
        icon.setGravity(Gravity.CENTER);

        icon.setBackgroundColor(NafeaUtil.getRandomColor(150));

        addView(icon);
    }

    private void updateIcons()
    {
        for(int i = 0; i < iconsCount; i++)
            addTextV("Test " + i, 32, 100, 50, 3);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //updateIcons();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateIcons();
    }
}
