package com.ksu.nafea.data;

import android.util.Log;

import com.ksu.nafea.logic.Major;


import java.util.ArrayList;

public class MajorPool extends DatabasePool
{
    public static String TAG = "MajorPool";

    public static void insertMajor(Integer major_id, String major_name, String major_plan, Integer college_id, final QueryResultFlag resultFlag)
    {
        ArrayList<Object> values = new ArrayList<Object>();

        values.add(major_id);
        values.add(major_name);
        values.add(major_plan);
        values.add(college_id);

        insert("College", values, new QueryRequestFlag()
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
    private static void retMajor(ArrayList<String> attrs,String condition, final QueryResultFlag resultFlag)
    {
        final ArrayList<Major> major = new ArrayList<Major>();
        retrieve("Major", attrs, condition, new QueryRequestFlag()
        {
            @Override
            public void onRequestSuccess(QueryResult result)
            {
                for(int i = 0; i < result.length(); i++)
                {
                    QueryRow row = result.getRow(i);
                    if(row != null)
                    {
                        Integer major_id = row.getInt("major_id");
                        String major_name = row.getString("major_name");
                        String major_plan = row.getString("major_plan");
                        Integer college_id = row.getInt("college_id");
                        major.add(new Major(major_id,major_name,major_plan,college_id));

                    }
                }
                if(!major.isEmpty()){
                    resultFlag.onQuerySuccess(major);
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

    private static void retrieveMajor(Integer id, final QueryResultFlag resultFlag)
    {
        retMajor(new ArrayList<String>(),"major_id =" + id,resultFlag);
    }
    public static void retrieveMajorsOnCollege(Integer univ_id,final QueryResultFlag resultFlag) {
        retMajor(new ArrayList<String>() ,"college_id = "+ univ_id , resultFlag);
    }
    public static void retrieveAllMajors(final QueryResultFlag resultFlag) {
        retMajor(new ArrayList<String>(),"",resultFlag);

    }
}