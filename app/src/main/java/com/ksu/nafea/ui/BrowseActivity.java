package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.ksu.nafea.R;
import com.ksu.nafea.utilities.NafeaUtil;

import java.util.ArrayList;
import java.util.Random;

public class BrowseActivity extends AppCompatActivity
{
    private Spinner dropDown;
    private ArrayAdapter<String> dropDownList;
    private LinearLayout iconGallery;
    private Button next;
    private TextView selectedElement;
    private int step = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        browseActivInit();

        setStep(1);


        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if(id != 0)
                {
                    iconGallery.setVisibility(View.VISIBLE);
                }
                else
                {
                    iconGallery.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                executeNext();
            }
        });
    }

    private void browseActivInit()
    {
        dropDown = (Spinner) findViewById(R.id.dropDown_spi);

        dropDownList = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        dropDownList.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropDown.setAdapter(dropDownList);

        iconGallery = (LinearLayout) findViewById(R.id.iconGallery_layout);

        next = (Button) findViewById(R.id.b_brsNext);

        dropDown.setVisibility(View.VISIBLE);
        iconGallery.setVisibility(View.INVISIBLE);
        next.setVisibility(View.INVISIBLE);
    }

    private void executeNext()
    {

    }

    private void addDropDownItem(String item)
    {
        dropDownList.add(item);
    }

    private void addTextListner(final TextView text)
    {
        text.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                LinearLayout iconList = (LinearLayout) iconGallery.getChildAt(0);
                for(int i = 0; i < iconList.getChildCount(); i++)
                {
                    TextView t = (TextView) iconList.getChildAt(i);
                    float alpha = t.getAlpha();
                    if(alpha != 1)
                    {
                        t.setAlpha(1);
                    }
                }
                selectedElement = (TextView) v;
                v.setAlpha(0.3f);
                next.setVisibility(View.VISIBLE);
            }
        });
    }

    private TextView createIconText(String text, int textSize, int width, int height, int margin)
    {
        final float dp = getResources().getDisplayMetrics().density;
        //final float sp = getResources().getDisplayMetrics().scaledDensity;

        TextView icon = new TextView(this);

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

        addTextListner(icon);
        return icon;
    }

    private void addGalleryIcon(String text)
    {
        TextView icon = createIconText(text, 28, 200, 150, 5);

        iconGallery.addView(icon);
    }


    private void addDropdownList(ArrayList<String> list)
    {
        for(int i = 0; i < list.size(); i++)
            addDropDownItem(list.get(i));
    }

    private void addGalleryList(ArrayList<String> list)
    {
        for(int i = 0; i < list.size(); i++)
            addGalleryIcon(list.get(i));
    }

    private void setStep(int step)
    {
        switch (step)
        {
            case 1:
                //To-Do retrieve cities.
                ArrayList<String> city = new ArrayList<String>();
                city.add("اختر أين تقع الجامعة");
                city.add("الرياض");
                city.add("جدة");
                city.add("ظهران");
                //To-Do retrieve universities.
                ArrayList<String> university = new ArrayList<String>();
                university.add("جامعة الملك سعود");
                university.add("جامعة الإمام");
                university.add("جامعة الملك عبد العزيز");
                university.add("جامعة الملك فهد للبترول والمعادن");

                addDropdownList(city);
                addGalleryList(university);
                break;
        }
    }

}