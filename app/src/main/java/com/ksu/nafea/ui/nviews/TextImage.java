package com.ksu.nafea.ui.nviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import com.ksu.nafea.R;
import com.ksu.nafea.utilities.NafeaUtil;


public class TextImage extends RelativeLayout
{

    /*
    textAreaPosition
    * */

    public static final int POSITION_CENTER = 0;
    public static final int POSITION_TOP = 1;
    public static final int POSITION_TOPRIGHT = 2;
    public static final int POSITION_RIGHT = 3;
    public static final int POSITION_BOTTOMRIGHT = 4;
    public static final int POSITION_BOTTOM = 5;
    public static final int POSITION_BOTTOMLEFT = 6;
    public static final int POSITION_LEFT = 7;
    public static final int POSITION_TOPLEFT = 8;


    public static final int DEFAULT_TEXT_COLOR = Color.BLACK;
    public static final int DEFAULT_TEXT_SIZE = 18;
    public static final int DEFAULT_TEXT_MARGIN = 0;
    public static final int DEFAULT_TEXTAREA_COLOR = Color.argb(150, 0,0,50);
    public static final int DEFAULT_TEXTAREA_COVERAGE = 50;


    public String getText() {
        if(textView != null)
            return textView.getText().toString();
        else
            return "";
    }

    public void setText(String text) {
        this.text = text;
        if(textView != null)
            textView.setText(text);
    }

    public int getTextColor() {
        if(textView != null)
            return  textView.getCurrentTextColor();
        else
            return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        if(textView != null)
            textView.setTextColor(textColor);
    }

    public int getTextSize() {
        if(textView != null)
            return  (int) textView.getTextSize();
        else
            return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        if(textView != null)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    public int getTextTopMargin() {
        if(textView != null)
            return  ((LayoutParams) textView.getLayoutParams()).topMargin;
        else
            return textTopMargin;
    }

    public void setTextTopMargin(int textTopMargin) {
        this.textTopMargin = textTopMargin;
        if(textView != null)
            ((LayoutParams) textView.getLayoutParams()).setMargins(textLeftMargin, textTopMargin, textRightMargin, textBottomMargin);
    }

    public int getTextRightMargin() {
        if(textView != null)
            return  ((LayoutParams) textView.getLayoutParams()).rightMargin;
        else
            return textRightMargin;
    }

    public void setTextRightMargin(int textRightMargin) {
        this.textRightMargin = textRightMargin;
        if(textView != null)
            ((LayoutParams) textView.getLayoutParams()).setMargins(textLeftMargin, textTopMargin, textRightMargin, textBottomMargin);
    }

    public int getTextBottomMargin() {
        if(textView != null)
            return  ((LayoutParams) textView.getLayoutParams()).bottomMargin;
        else
            return textBottomMargin;
    }

    public void setTextBottomMargin(int textBottomMargin) {
        this.textBottomMargin = textBottomMargin;
        if(textView != null)
            ((LayoutParams) textView.getLayoutParams()).setMargins(textLeftMargin, textTopMargin, textRightMargin, textBottomMargin);
    }

    public int getTextLeftMargin() {
        if(textView != null)
            return  ((LayoutParams) textView.getLayoutParams()).leftMargin;
        else
            return textLeftMargin;
    }

    public void setTextLeftMargin(int textLeftMargin) {
        this.textLeftMargin = textLeftMargin;
        if(textView != null)
            ((LayoutParams) textView.getLayoutParams()).setMargins(textLeftMargin, textTopMargin, textRightMargin, textBottomMargin);
    }

    public int getTextPosition() {
        return textPosition;
    }

    public void setTextPosition(int textPosition) {
        this.textPosition = textPosition;
        if(textView != null)
            setViewPosition(textView, textPosition);
    }

    public int getTextAreaPosition() {
        return textAreaPosition;
    }

    public void setTextAreaPosition(int textAreaPosition) {
        this.textAreaPosition = textAreaPosition;
        if(coverView != null)
            setViewPosition(coverView, textAreaPosition);
    }

    public int getTextAreaColor() {
        if(coverView != null)
            return  ((ColorDrawable) coverView.getBackground()).getColor();
        else
            return textAreaColor;
    }

    public void setTextAreaColor(int textAreaColor) {
        this.textAreaColor = textAreaColor;
        if(coverView != null)
            coverView.setBackgroundColor(textAreaColor);
    }

    public float getTextAreaCoverage() {
        return textAreaCoverage;
    }

    public void setTextAreaCoverage(float textAreaCoverage) {
        this.textAreaCoverage = textAreaCoverage;

        if(coverView != null)
        {
            final float dp = getResources().getDisplayMetrics().density;
            int fill = (int) (textAreaCoverage * dp);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) coverView.getLayoutParams();

            if( (textAreaPosition == POSITION_TOP) || (textAreaPosition == POSITION_BOTTOM) )
            {
                layoutParams.width = LayoutParams.MATCH_PARENT;
                layoutParams.height = fill;

            }
            else
            {
                layoutParams.width = fill;
                layoutParams.height = LayoutParams.MATCH_PARENT;
            }

            coverView.setLayoutParams(layoutParams);
        }
    }

    public Drawable getImgDrawable() {
        return imgDrawable;
    }

    public void setImgDrawable(Drawable imgDrawable) {
        this.imgDrawable = imgDrawable;
    }

    private String text;
    private int textColor, textSize;
    private int textTopMargin, textRightMargin, textBottomMargin, textLeftMargin;
    private int textPosition;
    private int textAreaPosition;
    private int textAreaColor;
    private float textAreaCoverage;
    private Drawable imgDrawable;

    private TextView textView = null;
    private ImageView coverView = null;
    private ImageView imageView = null;

    public TextImage(Context context)
    {
        super(context);
        init(null, 0);
    }

    public TextImage(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs, 0);
    }

    public TextImage(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle)
    {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextImage, defStyle, 0);


        text = a.getString(R.styleable.TextImage_text);
        textColor = a.getColor(R.styleable.TextImage_textColor, DEFAULT_TEXT_COLOR);

        int pxSize = a.getDimensionPixelSize(R.styleable.TextImage_textSize, DEFAULT_TEXT_SIZE);//a.getInteger(R.styleable.TextImage_textSize, DEFAULT_TEXT_SIZE);
        textSize = a.hasValue(R.styleable.TextImage_textSize) ? (int) (pxSize / getResources().getDisplayMetrics().scaledDensity) : DEFAULT_TEXT_SIZE;

        textTopMargin = a.getInteger(R.styleable.TextImage_textTopMargin, DEFAULT_TEXT_MARGIN);
        textRightMargin = a.getInteger(R.styleable.TextImage_textRightMargin, DEFAULT_TEXT_MARGIN);
        textBottomMargin = a.getInteger(R.styleable.TextImage_textBottomMargin, DEFAULT_TEXT_MARGIN);
        textLeftMargin = a.getInteger(R.styleable.TextImage_textLeftMargin, DEFAULT_TEXT_MARGIN);

        textPosition = a.getInteger(R.styleable.TextImage_textPosition, POSITION_CENTER);
        textAreaPosition = a.getInteger(R.styleable.TextImage_textAreaPosition, POSITION_BOTTOM);
        textAreaColor = a.getColor(R.styleable.TextImage_textAreaColor, DEFAULT_TEXTAREA_COLOR);
        textAreaCoverage = a.getInteger(R.styleable.TextImage_textAreaCoverage, DEFAULT_TEXTAREA_COVERAGE);

        imgDrawable = a.getDrawable(R.styleable.TextImage_android_drawable);

        a.recycle();
    }

    private void setViewPosition(View view, int position)
    {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();

        switch (position)
        {
            case POSITION_CENTER:
                layoutParams.addRule(CENTER_IN_PARENT);
                break;
            case POSITION_TOP:
                layoutParams.addRule(ALIGN_PARENT_TOP);
                layoutParams.addRule(CENTER_HORIZONTAL);
                break;
            case POSITION_TOPRIGHT:
                layoutParams.addRule(ALIGN_PARENT_TOP);
                layoutParams.addRule(ALIGN_PARENT_RIGHT);
                break;
            case POSITION_RIGHT:
                layoutParams.addRule(ALIGN_PARENT_RIGHT);
                layoutParams.addRule(CENTER_VERTICAL);
                break;
            case POSITION_BOTTOMRIGHT:
                layoutParams.addRule(ALIGN_PARENT_RIGHT);
                layoutParams.addRule(ALIGN_PARENT_BOTTOM);
                break;
            case POSITION_BOTTOM:
                layoutParams.addRule(CENTER_HORIZONTAL);
                layoutParams.addRule(ALIGN_PARENT_BOTTOM);
                break;
            case POSITION_BOTTOMLEFT:
                layoutParams.addRule(ALIGN_PARENT_LEFT);
                layoutParams.addRule(ALIGN_PARENT_BOTTOM);
                break;
            case POSITION_LEFT:
                layoutParams.addRule(ALIGN_PARENT_LEFT);
                layoutParams.addRule(CENTER_VERTICAL);
                break;
            case POSITION_TOPLEFT:
                layoutParams.addRule(ALIGN_PARENT_LEFT);
                layoutParams.addRule(ALIGN_PARENT_TOP);
                break;
        }

        view.setLayoutParams(layoutParams);
    }

    private TextView createTextView()
    {
        final float dp = getResources().getDisplayMetrics().density;

        textView = new TextView(getContext());

        //int width = (int) (200 * dp);
        //int height = (int) (150 * dp);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(textLeftMargin, textTopMargin, textRightMargin, textBottomMargin);
        textView.setLayoutParams(layoutParams);



        textView.setText(text);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        textView.setTextColor(textColor);
        setViewPosition(textView, textPosition);

        return textView;
    }

    private ImageView createCoverView()
    {
        final float dp = getResources().getDisplayMetrics().density;

        coverView = new ImageView(getContext());

        int fill = (int) (textAreaCoverage * dp);
        RelativeLayout.LayoutParams layoutParams;

        if( (textAreaPosition == POSITION_TOP) || (textAreaPosition == POSITION_BOTTOM) )
            layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, fill);
        else
            layoutParams = new RelativeLayout.LayoutParams(fill, LayoutParams.MATCH_PARENT);

        coverView.setLayoutParams(layoutParams);


        setViewPosition(coverView, textAreaPosition);
        coverView.setBackgroundColor(textAreaColor);

        return coverView;
    }

    private ImageView createImageView()
    {
        final float dp = getResources().getDisplayMetrics().density;

        imageView = new ImageView(getContext());


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(layoutParams);


        imageView.setImageDrawable(imgDrawable);

        return imageView;
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();

        addView(createImageView());
        addView(createCoverView());
        addView(createTextView());
    }
}
