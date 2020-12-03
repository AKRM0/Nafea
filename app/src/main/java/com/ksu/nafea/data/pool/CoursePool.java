package com.ksu.nafea.data.pool;

import android.util.Log;

import com.ksu.nafea.data.DatabaseException;
import com.ksu.nafea.data.QueryRequestFlag;
import com.ksu.nafea.data.QueryResult;
import com.ksu.nafea.data.QueryResultFlag;
import com.ksu.nafea.data.QueryRow;
import com.ksu.nafea.logic.Course;


import java.util.ArrayList;

public class CoursePool extends DatabasePool
{
    public static String TAG = "CoursePool";


    private static void retCourse(ArrayList<String> attrs,String condition, final QueryResultFlag resultFlag)
    {
        final ArrayList<Course> courses = new ArrayList<Course>();
        retrieve("Course", attrs, condition, new QueryRequestFlag()
        {
            @Override
            public void onRequestSuccess(QueryResult result)
            {
                for(int i = 0; i < result.length(); i++)
                {
                    QueryRow row = result.getRow(i);
                    if(row != null)
                    {
                        Integer course_id = row.getInt("crs_id");
                        String course_name = row.getString("crs_name");
                        String course_symbol = row.getString("crs_symbol");
                        String course_description = row.getString("crs_desc");

                        courses.add(new Course(course_id, course_name, course_symbol, course_description));
                    }
                }

                if(!courses.isEmpty())
                    resultFlag.onQuerySuccess(courses);
                else
                    resultFlag.onQuerySuccess(null);
            }

            @Override
            public void onRequestFailure(DatabaseException error)
            {
                Log.e(TAG, error.getQueryErrorFormat());
                resultFlag.onQueryFailure(QUERY_FAILURE_MSG);
            }
        });
    }




    public static void insertCourse(Integer course_id, String course_name, String course_symbol, String course_description, final QueryResultFlag resultFlag)
    {
        ArrayList<Object> values = new ArrayList<Object>();

        values.add(course_id);
        values.add(course_name);
        values.add(course_symbol);
        values.add(course_description);

        insert("Course", values, new QueryRequestFlag()
        {
            @Override
            public void onRequestSuccess(QueryResult result)
            {
                result.getQueryStatus().getInt("affectedRows");
                resultFlag.onQuerySuccess(null);

            }

            @Override
            public void onRequestFailure(DatabaseException error)
            {
                Log.e(TAG, error.getQueryErrorFormat());
                resultFlag.onQueryFailure(QUERY_FAILURE_MSG);
            }
        });
    }


    public static void retrieveCourse(Integer course_id, final QueryResultFlag resultFlag)
    {
        retCourse(new ArrayList<String>(), ("crs_id = " + course_id), resultFlag);
    }

    public static void retrieveAllCourses(final QueryResultFlag resultFlag)
    {
        retCourse(new ArrayList<String>(),"",resultFlag);
    }

    public static void retrieveAllCoursesInMajor(Integer major_id, final QueryResultFlag resultFlag)
    {
        String condition = "crs_id IN ( SELECT crs_id From Contain Where major_id = "+major_id+" )";
        retCourse(new ArrayList<String>(),condition,resultFlag);
    }

}