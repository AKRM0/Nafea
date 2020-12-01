package com.ksu.nafea.data;

import android.util.Log;

import com.ksu.nafea.logic.University;

import java.util.ArrayList;

public class UniversityPool extends DatabasePool
{
    public static String TAG = "UniversityPool";

    public static void insertUniversity(int univ_id, String password, String unvi_name, String unvi_city, final QueryResultFlag resultFlag)
    {
        ArrayList<Object> values = new ArrayList<Object>();

        values.add(univ_id);
        values.add(unvi_name);
        values.add(unvi_city);

        insert("university", values, new QueryRequestFlag()
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
    private static void retUniv(ArrayList<String> attrs,String condition, final QueryResultFlag resultFlag)
    {
        final ArrayList<University> univ = new ArrayList<University>();
        retrieve("University", attrs, condition, new QueryRequestFlag()
        {
            @Override
            public void onRequestSuccess(QueryResult result)
            {
                for(int i = 0; i < result.length(); i++)
                {
                    QueryRow row = result.getRow(i);
                    if(row != null)
                    {
                        Integer univ_id = row.getInt("univ_id");
                        String unvi_name = row.getString("unvi_name");
                        String unvi_city = row.getString("unvi_city");

                        univ.add(new University(univ_id,unvi_name,unvi_city));

                    }
                }
                if(!univ.isEmpty()){
                    resultFlag.onQuerySuccess(univ);
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

    private static void retrieveUniversity(Integer id, final QueryResultFlag resultFlag)
        {
            retUniv(new ArrayList<String>(),"univ_id =" + id,resultFlag);
    }

    public static void retrieveOnCity(String city,final QueryResultFlag resultFlag) {

        retUniv(new ArrayList<String>() ,"univ_city = "+ converString(city), resultFlag);
    }

    public static void retrieveAllUniversities(final QueryResultFlag resultFlag) {
        retUniv(new ArrayList<String>(),"",resultFlag);

    }
}