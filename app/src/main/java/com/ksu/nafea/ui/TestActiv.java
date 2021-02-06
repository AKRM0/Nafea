package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.Major;
import com.ksu.nafea.logic.QueryPostStatus;
import com.ksu.nafea.logic.course.Course;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.logic.account.Student;
import com.ksu.nafea.logic.course.CourseEvaluation;
import com.ksu.nafea.logic.material.ElectronicMaterial;
import com.ksu.nafea.logic.material.Material;

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


        //University university = new University(1, "", "الرياض");
        //College college = new College(1, "", "علمي");
        Major major = new Major(1, "");
        //Student student = new Student("test5@gmail.com", "123qwe", "Fahd", "Khalid", null);

        final Course c = new Course(3, "برمجة مرئية", "نال 224", null);
        User.userAccount = new Student("test@gmail.com", "123qwe");

        ElectronicMaterial material = new ElectronicMaterial(0, "Chapter 2 Slides", "Video", "www.google.com", null);




        //Material.retrieveAllMatsInCourse(c, new QueryRequestFlag<ArrayList<Material>>()
        //{
        //    @Override
        //    public void onQuerySuccess(ArrayList<Material> resultObject)
        //    {
        //        if(resultObject != null)
        //        {
        //            showMsg("Mats Retrieved.");
        //            Log.d(TAG, "Mats:\n");
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
        //        showMsg("Failed Mats");
        //        Log.d(TAG, failure.getMsg() + "\n" + failure.toString());
        //    }
        //});

        //Material.insert((Student) User.userAccount, c, material, new QueryRequestFlag<QueryPostStatus>()
        //{
        //    @Override
        //    public void onQuerySuccess(QueryPostStatus resultObject)
        //    {
        //        if(resultObject != null)
        //        {
        //            showMsg(resultObject.toString());
        //        }
        //    }
//
        //    @Override
        //    public void onQueryFailure(FailureResponse failure)
        //    {
        //        showMsg("Failed Insert");
        //        Log.d(TAG, failure.getMsg() + "\n" + failure.toString());
        //    }
        //});

    }
}