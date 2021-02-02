package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.College;
import com.ksu.nafea.logic.Course;
import com.ksu.nafea.logic.GeneralPool;
import com.ksu.nafea.logic.Major;
import com.ksu.nafea.logic.QueryPostStatus;
import com.ksu.nafea.logic.University;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.logic.account.Student;
import com.ksu.nafea.logic.account.UserAccount;

import java.util.ArrayList;

public class TestActiv extends AppCompatActivity
{
    public static final String TAG = "Test";

    public void showMsg(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        University university = new University(1, "", "الرياض");
        College college = new College(1, "", "علمي");
        Major major = new Major(1, "");
        final Course c = new Course(1, "تراكيب بيانات", "عال 212", null);
        Student student = new Student("test5@gmail.com", "123qwe", "Fahd", "Khalid", null);
        Student student2 = new Student("test@gmail.com", "123qwe", "Saad", "Ahmed", 3);




        //Major.retrieveMajorsInCollege(college, new QueryRequestFlag<ArrayList<Major>>()
        //{
        //    @Override
        //    public void onQuerySuccess(ArrayList<Major> resultObject)
        //    {
        //        if(resultObject != null)
        //        {
        //            showMsg("Majors Retrieved.");
        //            Log.d(TAG, "Majors:\n");
        //            for(int i = 0; i < resultObject.size(); i++)
        //            {
        //                Log.d(TAG, resultObject.get(i).toString());
        //            }
        //        }
        //    }
//
        //    @Override
        //    public void onQueryFailure(FailureResponse failure)
        //    {
        //        showMsg("Failed Majors");
        //        Log.d(TAG, failure.getMsg() + "\n" + failure.toString());
        //    }
        //});

    }
}