package com.ksu.nafea.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.QueryPostStatus;
import com.ksu.nafea.ui.nafea_views.dialogs.PopupDetailsDialog;
import com.ksu.nafea.ui.nafea_views.recycler_view.ListAdapter;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.logic.course.Course;
import com.ksu.nafea.ui.nafea_views.recycler_view.GeneralRecyclerAdapter;

import java.util.ArrayList;

public class DeleteCourseActivity extends AppCompatActivity
{
    public static final String TAG = "DeleteCourseActivity";
    private final Context context = this;
    private ProgressDialog progressDialog;

    private RecyclerView coursesListView;
    private Button deleteButton, cancelButton;

    private ArrayList<Course> courses;
    private ArrayList<Course> targetCourses;


    private void showToastMsg(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_course);

        progressDialog = new ProgressDialog(context);

        coursesListView = (RecyclerView) findViewById(R.id.delCrs_coursesList);
        deleteButton = (Button) findViewById(R.id.delCrs_b_delete);
        cancelButton = (Button) findViewById(R.id.delCrs_b_cancel);


        deleteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                executeDelete();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                executeCancel();
            }
        });



        if(User.major != null)
        {
            progressDialog.show();


            Course.retrieveAllCoursesInMajor(User.major, new QueryRequestFlag<ArrayList<Course>>()
            {
                @Override
                public void onQuerySuccess(final ArrayList<Course> resultObject)
                {
                    progressDialog.dismiss();

                    if(resultObject != null)
                    {
                        courses = resultObject;
                        targetCourses = new ArrayList<Course>();

                        refreshCoursesRecycleView();
                    }
                }

                @Override
                public void onQueryFailure(FailureResponse failure)
                {
                    progressDialog.dismiss();

                    showToastMsg("فشل عرض المقررات");
                    Log.d(TAG, failure.getMsg() + "\n" + failure.toString());
                }
            });
        }

    }

    private void executeCancel()
    {
        finish();
    }

    private void executeDelete()
    {
        if(targetCourses.isEmpty())
            return;


        progressDialog.show();
        Course.deleteAllCourses(targetCourses, new QueryRequestFlag<QueryPostStatus>()
        {
            @Override
            public void onQuerySuccess(QueryPostStatus resultObject)
            {
                progressDialog.dismiss();

                if(resultObject != null)
                {
                    if(resultObject.getAffectedRows() > 0)
                    {
                        showToastMsg("تم حذف المقررات");

                        for(int i = 0; i < targetCourses.size(); i++)
                            courses.remove(targetCourses.get(i));

                        targetCourses.clear();
                        refreshCoursesRecycleView();
                    }
                }
            }

            @Override
            public void onQueryFailure(FailureResponse failure)
            {
                progressDialog.dismiss();
                showToastMsg("فشل حذف المقررات");
                Log.d(TAG, failure.getMsg() + "\n" + failure.toString());
            }
        });

    }


    public void refreshCoursesRecycleView()
    {
        ListAdapter listAdapter = new ListAdapter()
        {
            @Override
            public int getResourceLayout()
            {
                return R.layout.item_view_delete_course;
            }

            @Override
            public int getItemCount()
            {
                return courses.size();
            }

            @Override
            public void onBind(View itemView, final int position)
            {
                CheckBox course = (CheckBox) itemView.findViewById(R.id.delRow_chBox_course);
                TextView courseDetails = (TextView) itemView.findViewById(R.id.delRow_txtb_course);

                course.setText(courses.get(position).getSymbol());

                course.setOnCheckedChangeListener(getOnCourseCheckedChangeListener(position));
                courseDetails.setOnClickListener(getOnCourseDetailsClicked(position));
            }
        };


        GeneralRecyclerAdapter adapter = new GeneralRecyclerAdapter(context, listAdapter);
        coursesListView.setItemViewCacheSize(courses.size());
        coursesListView.setAdapter(adapter);
        coursesListView.setLayoutManager(new LinearLayoutManager(context));

    }


    private CompoundButton.OnCheckedChangeListener getOnCourseCheckedChangeListener(final int position)
    {
        return new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked)
                    targetCourses.add(courses.get(position));
                else
                    targetCourses.remove(courses.get(position));
            }
        };
    }

    private View.OnClickListener getOnCourseDetailsClicked(final int position)
    {
        return new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Course course = courses.get(position);

                PopupDetailsDialog detailsDialog = new PopupDetailsDialog(course.getName(), course.getDescription(), "حسناً");
                detailsDialog.show(getSupportFragmentManager(), course.getSymbol());
            }
        };
    }


}