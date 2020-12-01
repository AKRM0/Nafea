package com.ksu.nafea.data;

import android.util.Log;

import com.ksu.nafea.logic.Course;
import com.ksu.nafea.logic.Major;


import java.util.ArrayList;

public class CoursePool extends DatabasePool
{
    public static String TAG = "CoursePool";

    public static void insertMajor(Integer course_id, String course_name, String course_symbol, String course_description, final QueryResultFlag resultFlag)
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
                resultFlag.onQueryFailure();
            }
        });
    }
    private static void retCourse(ArrayList<String> attrs,String condition, final QueryResultFlag resultFlag)
    {
        final ArrayList<Course> course = new ArrayList<Course>();
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
                        Integer course_id = row.getInt("course_id");
                        String course_name = row.getString("course_name");
                        String course_symbol = row.getString("course_symbol");
                        String course_description = row.getString("course_description");
                        course.add(new Course(course_id,course_name,course_symbol,course_description));

                    }
                }
                if(!course.isEmpty()){
                    resultFlag.onQuerySuccess(course);
                }
                else
                    resultFlag.onQuerySuccess(null);
            }

            @Override
            public void onRequestFailure(DatabaseException error)
            {
                Log.e(TAG, error.getQueryErrorFormat());
                resultFlag.onQueryFailure();
            }
        });
    }

    private static void retrieveCourse(Integer id, final QueryResultFlag resultFlag)
    {
        retCourse(new ArrayList<String>(),"crs_id =" + id,resultFlag);
    }

    public static void retrieveAllCourses(final QueryResultFlag resultFlag) {
        retCourse(new ArrayList<String>(),"",resultFlag);
    }

    public static void retrieveAllCoursesInMajor(Integer major_id,final QueryResultFlag resultFlag) {
        String condition = "crs_id IN ( SELECT crs_id From Contain Where major_id = "+major_id+" )";
        retCourse(new ArrayList<String>(),condition,resultFlag);
    }
}