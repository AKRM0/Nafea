package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.Course;
import com.ksu.nafea.logic.GeneralPool;
import com.ksu.nafea.logic.QueryPostStatus;

import java.util.ArrayList;

public class TestActiv extends AppCompatActivity {

    public void showMsg(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        final Course c = new Course(0, "قواعد بيانات", "عال 227", null);

        Integer majorID = 1;
        String level = "one";

        Course.insert(majorID, level, c, new QueryRequestFlag<QueryPostStatus>() {
            @Override
            public void onQuerySuccess(QueryPostStatus resultObject)
            {
                if(resultObject != null)
                {
                    if(resultObject.getAffectedRows() > 0)
                    {
                        showMsg("Success");
                    }
                }
            }

            @Override
            public void onQueryFailure(FailureResponse failure) {

            }
        });

        //Course.insert(2, "5th", c, new QueryRequestFlag<QueryPostStatus>()
        //{
        //    @Override
        //    public void onQuerySuccess(QueryPostStatus resultObject)
        //    {
        //        if(resultObject != null)
        //        {
        //            showMsg("Success");
        //        }
        //    }
//
        //    @Override
        //    public void onQueryFailure(FailureResponse failure)
        //    {
        //        showMsg("Failed");
        //        Log.d("Test", failure.getMsg() + "\n" + failure.toString());
        //    }
        //});

    }
}