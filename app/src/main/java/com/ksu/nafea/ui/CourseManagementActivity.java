package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ksu.nafea.R;
import com.ksu.nafea.ui.nviews.NSpinner;

import java.util.ArrayList;

public class CourseManagementActivity extends AppCompatActivity
{
    private ArrayList<NSpinner> dropDown;
    private LinearLayout addCourseArea;
    private ArrayList<EditText> fields;
    private Button confirm;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_management);


        courseManageInit();

        //Testing
        for(int i = 0; i < dropDown.size(); i++)
        {
            for(int j = 0 ; j < 10; j++)
            {
                dropDown.get(i).addOption("Test " + j);
            }
        }

        onSelectDropdownOption();

    }

    private void courseManageInit()
    {
        dropDown = new ArrayList<NSpinner>();
        fields = new ArrayList<EditText>();


        dropDown.add((NSpinner) findViewById(R.id.dropDown_operation));
        dropDown.add((NSpinner) findViewById(R.id.dropDown_university));
        dropDown.add((NSpinner) findViewById(R.id.dropDown_college));
        dropDown.add((NSpinner) findViewById(R.id.dropDown_major));
        dropDown.add((NSpinner) findViewById(R.id.dropDown_course));

        addCourseArea = (LinearLayout) findViewById(R.id.verBox_addCourse);

        fields.add((EditText) findViewById(R.id.et_courseName));
        fields.add((EditText) findViewById(R.id.et_courseSymbol));
        fields.add((EditText) findViewById(R.id.et_courseLevel));
        fields.add((EditText) findViewById(R.id.et_courseDesc));

        confirm = (Button) findViewById(R.id.b_crsConfirm);


        confirm.setVisibility(View.INVISIBLE);
        addCourseArea.setVisibility(View.INVISIBLE);
        for(int i = 2; i < dropDown.size(); i++)
            dropDown.get(i).setVisibility(View.INVISIBLE);

    }


    private void onSelectDropdownOption()
    {
        for(int i = 0; i < dropDown.size(); i++)
        {
            final NSpinner dropdownList = dropDown.get(i);
            final int currentIndex = i;

            dropdownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
            {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                {
                    if(dropdownList.getPreviousSelectedPosition()!= position)
                    {
                        clearSelection(currentIndex);
                        dropdownList.setPreviousSelectedPosition(position);;
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent)
                {

                }
            });
        }
    }



    private void clearSelection(int dropdownIndex)
    {
        for(int i = (dropDown.size() - 1); i > dropdownIndex; i--)
        {
            dropDown.get(i).removeAllOptions();
            dropDown.get(i).setVisibility(View.INVISIBLE);
        }

        if(dropdownIndex < 3)
        {
            addCourseArea.setVisibility(View.INVISIBLE);
        }

        // To-Do
    }

    private void showNextStep()
    {

    }


}