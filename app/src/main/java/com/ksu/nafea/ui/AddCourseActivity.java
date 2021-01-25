package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ksu.nafea.R;

import java.util.ArrayList;

public class AddCourseActivity extends AppCompatActivity
{
    private ArrayList<TextView> Labels;
    private ArrayList<EditText> fields;
    private Button add, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        AddCrsActivityInit();

    }

    private void AddCrsActivityInit()
    {
        Labels = new ArrayList<TextView>();
        fields = new ArrayList<EditText>();

        Labels.add((TextView) findViewById(R.id.addCrs_txt_crsName));
        Labels.add((TextView) findViewById(R.id.addCrs_txt_crsSymbol));
        Labels.add((TextView) findViewById(R.id.addCrs_txt_crsLevel));
        Labels.add((TextView) findViewById(R.id.addCrs_txt_crsDesc));

        fields.add((EditText) findViewById(R.id.addCrs_edt_crsName));
        fields.add((EditText) findViewById(R.id.addCrs_edt_crsSymbol));
        fields.add((EditText) findViewById(R.id.addCrs_edt_crsLevel));
        fields.add((EditText) findViewById(R.id.addCrs_edt_crsDesc));

        add = (Button) findViewById(R.id.addCrs_b_add);
        cancel = (Button) findViewById(R.id.addCrs_b_cancel);
    }

}