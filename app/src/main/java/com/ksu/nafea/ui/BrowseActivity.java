package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ksu.nafea.R;
import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.College;
import com.ksu.nafea.logic.Course;
import com.ksu.nafea.logic.Major;
import com.ksu.nafea.logic.University;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.ui.nviews.NSpinner;
import com.ksu.nafea.ui.nviews.SliderGallery;
import com.ksu.nafea.ui.nviews.TextImage;

import java.util.ArrayList;
import java.util.Arrays;

public class BrowseActivity extends AppCompatActivity
{
    public static final String TAG = "BrowseActivity";

    private ArrayList<LinearLayout> layouts;
    private ArrayList<NSpinner> dropDown;
    private ArrayList<SliderGallery> galleries;
    private ArrayList<TextView> results;
    private Button next;

    private ArrayList<University> universities = null;
    private ArrayList<College> colleges = null;
    private ArrayList<Major> majors = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        browseActivInit();

        onNextClicked();
        onSelectGalleryOption();
        onSelectDropdownOption();

    }



    private void browseActivInit()
    {
        universities = new ArrayList<University>();
        colleges = new ArrayList<College>();
        majors = new ArrayList<Major>();

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
    }

    private void initCities()
    {
        final NSpinner cityDropDown = dropDown.get(0);

        //University.retrieveAllCities(new QueryRequestFlag()
        //{
        //    @Override
        //    public void onQuerySuccess(Object queryResult)
        //    {
        //        if(queryResult != null)
        //        {
        //            ArrayList<String> cities = (ArrayList<String>) queryResult;
        //            for(int i = 0; i < cities.size(); i++)
        //            {
        //                cityDropDown.addOption(cities.get(i));
        //            }
//
//
        //            retrieveGalleryData(0);
        //        }
        //    }
//
        //    @Override
        //    public void onQueryFailure(String failureMsg)
        //    {
        //        Log.e(TAG, failureMsg + "/Init Cities");
        //        Toast.makeText(BrowseActivity.this, "Couldn't retrieve cities.", Toast.LENGTH_LONG).show();
        //    }
        //});
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
        String emailKey = "email";
        String passKey = "pass";
        String fullnameKey = "fullname";

        final String email = getIntent().getStringExtra(emailKey);
        String pass = getIntent().getStringExtra(passKey);
        ArrayList<String> fullname = new ArrayList<String>(Arrays.asList(getIntent().getStringExtra(fullnameKey).split(" ")));

        String firstName = fullname.get(0);
        String lastName = fullname.get(1);

        Integer majorID = galleries.get(2).getSelectedElement().getIconID();
        //UserAccount.register(email, pass, firstName, lastName, majorID, new QueryRequestFlag()
        //{
        //    @Override
        //    public void onQuerySuccess(Object queryResult)
        //    {
        //        if(queryResult != null)
        //        {
        //            Integer affectedRows = (Integer)queryResult;
        //            if(affectedRows != null)
        //            {
        //                if(affectedRows >= 1)
        //                {
        //                    Toast.makeText(BrowseActivity.this, "UserAccount with email "  + email + " register successfully.", Toast.LENGTH_LONG).show();
//
        //                    finish();
        //                }
        //            }
        //        }
        //    }
//
        //    @Override
        //    public void onQueryFailure(String failureMsg)
        //    {
        //        Toast.makeText(BrowseActivity.this, "Failed to register.", Toast.LENGTH_LONG).show();
        //    }
        //});
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
                        int nextStep = currentStep + 1;
                        if(nextStep <= 2)
                            galleries.get(currentStep+1).removeAll();

                        results.get(currentStep).setText(clickedElement.getText());
                        retrieveDropdownData(nextStep);
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

                        retrieveGalleryData(currentStep);
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
        }
    }

    private void onRetrieveData()
    {
        onSelectGalleryOption();
        onSelectDropdownOption();
    }


    private void showNextStep(int currentStepIndex)
    {
        int nextStepIndex = currentStepIndex + 1;

        if(nextStepIndex >= 0 && nextStepIndex < layouts.size())
            layouts.get(nextStepIndex).setVisibility(View.VISIBLE);
        else if(nextStepIndex == layouts.size())
            next.setVisibility(View.VISIBLE);
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


    private void retrieveDropdownData(int dropdownIndex)
    {
        switch (dropdownIndex)
        {
            case 0:
                break;
            case 1:
                getCategoriesFromDB();
                break;
            case 2:
                retrieveGalleryData(2);
                break;
        }
    }

    private void retrieveGalleryData(int galleryIndex)
    {
        switch (galleryIndex)
        {
            case 0:
                getUniversitiesFromDB();
                break;
            case 1:
                getCollegesFromDB();
                break;
            case 2:
                getMajorsFromDB();
                break;
        }
    }

    private void getUniversitiesFromDB()
    {
        //University.retrieveUniversitiesOnCity(dropDown.get(0).getSelectedOption(), new QueryRequestFlag()
        //{
        //    @Override
        //    public void onQuerySuccess(Object queryResult)
        //    {
        //        if(queryResult != null)
        //        {
        //            universities = (ArrayList<University>) queryResult;
        //            SliderGallery gallery = galleries.get(0);
        //            gallery.removeAll();
        //            for(int i = 0; i < universities.size(); i++)
        //            {
        //                gallery.insert(universities.get(i));
        //            }
//
        //            onRetrieveData();
        //        }
        //    }
//
        //    @Override
        //    public void onQueryFailure(String failureMsg)
        //    {
        //        Toast.makeText(BrowseActivity.this, "Failed to retrieve Universities.", Toast.LENGTH_LONG).show();
        //    }
        //});
    }

    private void getCategoriesFromDB()
    {
        Integer univID = galleries.get(0).getSelectedElement().getIconID();
        final NSpinner categoriesDropdown = dropDown.get(1);

        //College.retrieveAllCategories(univID, new QueryRequestFlag()
        //{
        //    @Override
        //    public void onQuerySuccess(Object queryResult)
        //    {
        //        if(queryResult != null)
        //        {
        //            ArrayList<String> categories = (ArrayList<String>) queryResult;
        //            categoriesDropdown.removeAllOptions();
        //            for(int i = 0; i < categories.size(); i++)
        //            {
        //                categoriesDropdown.addOption(categories.get(i));
        //            }
//
        //            retrieveGalleryData(1);
        //            onRetrieveData();
        //        }
        //    }
//
        //    @Override
        //    public void onQueryFailure(String failureMsg)
        //    {
        //        Toast.makeText(BrowseActivity.this, "Failed to retrieve College Categories.", Toast.LENGTH_LONG).show();
        //    }
        //});
    }

    private void getCollegesFromDB()
    {
        Integer univID = galleries.get(0).getSelectedElement().getIconID();
        String collCategory = dropDown.get(1).getSelectedOption();
        if(univID == null || collCategory == null)
            return;

        //College.retrieveCollegeOnCategory(univID, collCategory, new QueryRequestFlag()
        //{
        //    @Override
        //    public void onQuerySuccess(Object queryResult)
        //    {
        //        if(queryResult != null)
        //        {
        //            colleges = (ArrayList<College>) queryResult;
        //            SliderGallery gallery = galleries.get(1);
        //            gallery.removeAll();
        //            for(int i = 0; i < colleges.size(); i++)
        //            {
        //                gallery.insert(colleges.get(i));
        //            }
//
        //            onRetrieveData();
        //        }
        //    }
//
        //    @Override
        //    public void onQueryFailure(String failureMsg)
        //    {
        //        Toast.makeText(BrowseActivity.this, "Failed to retrieve Colleges.", Toast.LENGTH_LONG).show();
        //    }
        //});
    }

    private void getMajorsFromDB()
    {
        Integer collID = galleries.get(1).getSelectedElement().getIconID();
        //Major.retrieveMajorsOnCollege(collID, new QueryRequestFlag()
        //{
        //    @Override
        //    public void onQuerySuccess(Object queryResult)
        //    {
        //        if(queryResult != null)
        //        {
        //            majors = (ArrayList<Major>) queryResult;
        //            SliderGallery gallery = galleries.get(2);
        //            gallery.removeAll();
        //            for(int i = 0; i < majors.size(); i++)
        //            {
        //                gallery.insert(majors.get(i));
        //            }
//
        //            onRetrieveData();
        //        }
        //    }
//
        //    @Override
        //    public void onQueryFailure(String failureMsg)
        //    {
        //        Toast.makeText(BrowseActivity.this, "Failed to retrieve Majors.", Toast.LENGTH_LONG).show();
        //    }
        //});
    }

}