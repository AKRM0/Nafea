package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ksu.nafea.R;

public class MajorActivity extends AppCompatActivity
{
    private TextView showPlanButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_major);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showPlanButton = (TextView) findViewById(R.id.txtb_showPlan);


        showPlanButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showPlan();
            }
        });
    }


    private void showPlan()
    {
        Intent intent = new Intent(this, DepartmentPlanActivity.class);
        startActivity(intent);
    }

}