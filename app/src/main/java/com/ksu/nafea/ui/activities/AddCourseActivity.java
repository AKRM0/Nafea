package com.ksu.nafea.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.Major;
import com.ksu.nafea.logic.QueryPostStatus;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.logic.course.Course;
import com.ksu.nafea.utilities.NafeaUtil;

import java.util.ArrayList;

public class AddCourseActivity extends AppCompatActivity
{
    public static final String TAG = "AddCourseActivity";
    private ProgressDialog progressDialog;
    private ArrayList<TextView> labels;
    private ArrayList<EditText> fields;
    private Button add, cancel;


    private void showToastMsg(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        AddCrsActivityInit();


        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onAddClicked();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onCancelClicked();
            }
        });

    }

    //@Override
    //public void onBackPressed()
    //{
    //    this.finish();
    //}

    private void AddCrsActivityInit()
    {
        labels = new ArrayList<TextView>();
        fields = new ArrayList<EditText>();

        labels.add((TextView) findViewById(R.id.addCrs_txt_crsName));
        labels.add((TextView) findViewById(R.id.addCrs_txt_crsSymbol));
        labels.add((TextView) findViewById(R.id.addCrs_txt_crsNum));
        labels.add((TextView) findViewById(R.id.addCrs_txt_crsLevel));
        labels.add((TextView) findViewById(R.id.addCrs_txt_crsDesc));

        fields.add((EditText) findViewById(R.id.addCrs_edt_crsName));
        fields.add((EditText) findViewById(R.id.addCrs_edt_crsSymbol));
        fields.add((EditText) findViewById(R.id.addCrs_edt_crsNum));
        fields.add((EditText) findViewById(R.id.addCrs_edt_crsLevel));
        fields.add((EditText) findViewById(R.id.addCrs_edt_crsDesc));

        add = (Button) findViewById(R.id.addCrs_b_add);
        cancel = (Button) findViewById(R.id.addCrs_b_cancel);
    }


    private void onAddClicked()
    {
        if(User.major != null)
        {
            try
            {
                String courseName = NafeaUtil.validateEmptyField(labels.get(0), fields.get(0));
                String crsSymbol = NafeaUtil.validateEmptyField(labels.get(1), fields.get(1));
                String crsNumber = NafeaUtil.validateEmptyField(labels.get(2), fields.get(2));
                String courseLevel = NafeaUtil.validateEmptyField(labels.get(3), fields.get(3));
                String courseDesc = NafeaUtil.validateEmptyField(labels.get(4), fields.get(4));

                String courseSymbol = crsSymbol + "-" + crsNumber;
                final Course course = new Course(0, courseName, courseSymbol, courseDesc);

                progressDialog.show();
                Course.insert(User.major, courseLevel, course, new QueryRequestFlag<QueryPostStatus>()
                {
                    @Override
                    public void onQuerySuccess(QueryPostStatus resultObject)
                    {
                        progressDialog.dismiss();

                        if(resultObject != null)
                        {
                            if(resultObject.getAffectedRows() > 0)
                            {
                                showToastMsg("تم إضافة المقرر \"" + course.getName() + "\" بنجاح.");
                            }
                        }
                    }

                    @Override
                    public void onQueryFailure(FailureResponse failure)
                    {
                        progressDialog.dismiss();
                        showToastMsg("فشل إضافة المقرر");
                        Log.e(TAG, failure.getMsg() + "\n" + failure.toString());
                    }
                });
            }
            catch(Exception e)
            {
                showToastMsg("خطأ في الإدخال!");
            }
        }
    }


    private void onCancelClicked()
    {
        this.finish();
    }

}