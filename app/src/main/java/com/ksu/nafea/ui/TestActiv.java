package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ksu.nafea.R;
import com.ksu.nafea.logic.University;
import com.ksu.nafea.ui.nviews.SliderGallery;
import com.ksu.nafea.ui.nviews.TextImage;

public class TestActiv extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        SliderGallery g = (SliderGallery) findViewById(R.id.testing);

        g.setIconForm(TextImage.TEXT_FORM);
        g.setIconCornersRadius(200);
        for(int i = 0; i < 10; i++)
        {
            g.insert(new University("KSU " + i));
        }
    }
}