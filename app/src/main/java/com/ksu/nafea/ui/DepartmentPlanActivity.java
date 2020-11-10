package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ksu.nafea.R;

public class DepartmentPlanActivity extends AppCompatActivity
{
    private ImageView planImage;
    private TextView download;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_plan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        planImage = (ImageView) findViewById(R.id.img_plan);
        download = (TextView) findViewById(R.id.txtb_download);


        download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                downlaodPlan();
            }
        });
    }


    private void downlaodPlan()
    {
        Toast.makeText(this, "Downloading", Toast.LENGTH_LONG).show();
    }


}