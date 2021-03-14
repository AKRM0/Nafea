package com.ksu.nafea.ui.fragments.course;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ksu.nafea.R;

public class CoursePageActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    private AppBarConfiguration appBarConfiguration;
    public BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_page);


        toolbar = (Toolbar) findViewById(R.id.crs_toolbar);
        setSupportActionBar(toolbar);


        bottomNav = (BottomNavigationView)findViewById(R.id.crs_bottomNav);
        NavController navController = Navigation.findNavController(this, R.id.crs_navHost);
        appBarConfiguration = new AppBarConfiguration.Builder(R.id.crsNav_aboutCourse, R.id.crsNav_documents, R.id.crsNav_videos, R.id.crsNav_physMats, R.id.crsNav_comments).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        return true;
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        return super.onSupportNavigateUp();
    }
}