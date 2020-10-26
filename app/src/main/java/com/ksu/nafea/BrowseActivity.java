package com.ksu.nafea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ksu.nafea.ui.RegisterActivity;
import com.ksu.nafea.ui.ui.login.LoginActivity;

public class BrowseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}