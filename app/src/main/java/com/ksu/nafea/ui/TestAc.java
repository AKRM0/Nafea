package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ksu.nafea.R;
import com.ksu.nafea.ui.nviews.SliderGallery;

public class TestAc extends AppCompatActivity
{

    private Button button;
    private SliderGallery gall;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        button = (Button) findViewById(R.id.button);
        gall = (SliderGallery) findViewById(R.id.Gall);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //gall.addTextV("Testing", 32, 100, 50, 3);
                gall.iconsCount++;
            }
        });
    }
}