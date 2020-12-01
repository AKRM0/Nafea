package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.logic.College;
import com.ksu.nafea.logic.Major;
import com.ksu.nafea.logic.University;
import com.ksu.nafea.ui.nviews.NSpinner;
import com.ksu.nafea.ui.nviews.SliderGallery;
import com.ksu.nafea.ui.nviews.TextImage;
import com.ksu.nafea.data.DatabaseException;
import com.ksu.nafea.utilities.NafeaUtil;

import java.util.ArrayList;

public class BrowseActivity extends AppCompatActivity
{
    private ArrayList<LinearLayout> layouts;
    private ArrayList<NSpinner> dropDown;
    private ArrayList<SliderGallery> galleries;
    private ArrayList<TextView> results;
    private Button next;

    private University selectedUniversity = null;
    private College selectedCollege = null;
    private Major selectedMajor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        browseActivInit();


        // Testing
        for(int i = 0; i < 10; i++)
        {
            dropDown.get(0).addOption("city " + i);
            galleries.get(0).insert(new University("KSU " + i));

            dropDown.get(1).addOption("category " + i);
            galleries.get(1).insert(new University("Coll " + i));

            galleries.get(2).insert(new University("Major " + i));
        }

        onNextClicked();
        onSelectGalleryOption();
        onSelectDropdownOption();

    }



    private void browseActivInit()
    {
        layouts = new ArrayList<LinearLayout>();
        dropDown = new ArrayList<NSpinner>();
        galleries = new ArrayList<SliderGallery>();
        results = new ArrayList<TextView>();


        layouts.add((LinearLayout) findViewById(R.id.univ_layout));
        layouts.add((LinearLayout) findViewById(R.id.coll_layout));
        layouts.add((LinearLayout) findViewById(R.id.major_layout));

        dropDown.add((NSpinner) findViewById(R.id.dropDown_cities));
        dropDown.add((NSpinner) findViewById(R.id.dropDown_categories));

        galleries.add((SliderGallery) findViewById(R.id.gallery_univ));
        galleries.add((SliderGallery) findViewById(R.id.gallery_coll));
        galleries.add((SliderGallery) findViewById(R.id.gallery_major));

        results.add((TextView) findViewById(R.id.txt_univResult));
        results.add((TextView) findViewById(R.id.txt_collegeResult));
        results.add((TextView) findViewById(R.id.txt_majorResult));

        next = (Button) findViewById(R.id.b_brsNext);



        for(int i = 0; i < galleries.size(); i++)
        {
            galleries.get(i).setIconWidth(150);
            galleries.get(i).setIconHeight(100);
            galleries.get(i).setTextSize(14);
            galleries.get(i).setIconCornersRadius(20);
            galleries.get(i).setIconForm(TextImage.TEXT_FORM);
        }

        for(int i = 1; i < layouts.size(); i++)
            layouts.get(i).setVisibility(View.INVISIBLE);


        initCities();
        retrieveData(0);
    }

    private void initCities()
    {
        NSpinner cityDropDown = dropDown.get(0);
        ArrayList<String> cities = new  ArrayList<String>();
        try
        {
            cities = University.retrieveAllCities();
        }
        catch (DatabaseException e)
        {
            NafeaUtil.showToastMsg(this, e.getMessage(), Toast.LENGTH_LONG);
        }

        for(int i = 0; i < cities.size(); i++)
        {
            cityDropDown.addOption(cities.get(i));
        }
    }


    private void onNextClicked()
    {
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                executeNext();
            }
        });
    }

    private void executeNext()
    {
        // To-Do
    }


    private void onSelectGalleryOption()
    {
        for(int i = 0; i < galleries.size(); i++)
        {
            final SliderGallery gallery = galleries.get(i);
            final int currentStep = i;

            gallery.setElementOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    TextImage clickedElement = (TextImage) v;

                    gallery.selectElement(clickedElement);

                    if(gallery.isSelectedElement())
                    {
                        results.get(currentStep).setText(clickedElement.getText());
                        showNextStep(currentStep);
                    }
                }
            });
        }
    }


    private void onSelectDropdownOption()
    {
        for(int i = 0; i < dropDown.size(); i++)
        {
            final NSpinner dropDownList = dropDown.get(i);
            final int currentStep = i;


            dropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    if(dropDownList.getPreviousSelectedPosition()!= position)
                    {
                        clearSelection(currentStep);
                        dropDownList.setPreviousSelectedPosition(position);

                        retrieveData(currentStep);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
        }
    }


    private void showNextStep(int currentStepIndex)
    {
        int nextStepIndex = currentStepIndex + 1;

        if(nextStepIndex >= 0 && nextStepIndex < layouts.size())
            layouts.get(nextStepIndex).setVisibility(View.VISIBLE);
        else if(nextStepIndex == layouts.size())
            next.setVisibility(View.VISIBLE);

        retrieveData(nextStepIndex);
    }

    private void clearSelection(int galleryIndex)
    {
        next.setVisibility(View.INVISIBLE);

        for(int i = (galleries.size() - 1); i > galleryIndex; i--)
        {
            results.get(i).setText("");
            galleries.get(i).clearSelection();
            layouts.get(i).setVisibility(View.INVISIBLE);

            if(i < dropDown.size())
                dropDown.get(i).removeAllOptions();

            galleries.get(i).removeAll();
        }

        results.get(galleryIndex).setText("");
        galleries.get(galleryIndex).clearSelection();
    }


    private void retrieveData(int galleryIndex)
    {
        switch (galleryIndex)
        {
            case 0:
                // To-Do retrieve Universities.
                //galleries.get(galleryIndex).insert();


                break;
            case 1:
                // To-Do retrieve Colleges.
                break;
            case 2:
                // To-Do retrieve Majors.
                break;
        }
    }

}