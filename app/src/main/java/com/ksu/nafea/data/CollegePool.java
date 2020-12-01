package com.ksu.nafea.data;

import android.util.Log;

import com.ksu.nafea.logic.College;


import java.util.ArrayList;

public class CollegePool extends DatabasePool
{
    public static String TAG = "CollegePool";

    public static void insertCollege(Integer college_id, String college_name, String college_category, Integer unvi_id, final QueryResultFlag resultFlag)
    {
        ArrayList<Object> values = new ArrayList<Object>();

        values.add(college_id);
        values.add(college_name);
        values.add(college_category);
        values.add(unvi_id);

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
    private static void retCollege(ArrayList<String> attrs,String condition, final QueryResultFlag resultFlag)
    {
        final ArrayList<College> college = new ArrayList<College>();
        retrieve("College", attrs, condition, new QueryRequestFlag()
        {
            @Override
            public void onRequestSuccess(QueryResult result)
            {
                for(int i = 0; i < result.length(); i++)
                {
                    QueryRow row = result.getRow(i);
                    if(row != null)
                    {
                        Integer college_id = row.getInt("college_id");
                        String college_name = row.getString("college_name");
                        String college_category = row.getString("unvi_city");
                        Integer univ_id = row.getInt("univ_id");
                        college.add(new College(college_id,college_name,college_category,univ_id));

                    }
                }
                if(!college.isEmpty()){
                    resultFlag.onQuerySuccess(college);
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

    private static void retrieveCollege(Integer id, final QueryResultFlag resultFlag)
    {
        retCollege(new ArrayList<String>(),"college_id =" + id,resultFlag);
    }
    public static void retrieveOnUniversity(Integer univ_id,final QueryResultFlag resultFlag) {
        retCollege(new ArrayList<String>() ,"univ_id = "+ univ_id , resultFlag);
    }
    public static void retrieveOnCategory(String category,Integer id,final QueryResultFlag resultFlag) {
        retCollege(new ArrayList<String>(),"univ_id = "+ id +" AND coll_category = "+converString(category),resultFlag);

    }
}