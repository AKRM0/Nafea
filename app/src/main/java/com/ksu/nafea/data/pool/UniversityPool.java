package com.ksu.nafea.data.pool;

import android.util.Log;

import com.ksu.nafea.data.DatabaseException;
import com.ksu.nafea.data.QueryRequestFlag;
import com.ksu.nafea.data.QueryResult;
import com.ksu.nafea.data.QueryResultFlag;
import com.ksu.nafea.data.QueryRow;
import com.ksu.nafea.logic.College;
import com.ksu.nafea.logic.University;

import java.util.ArrayList;

public class UniversityPool extends DatabasePool
{
    public static String TAG = "UniversityPool";




    private static void retUniv(ArrayList<String> attrs, String condition, final QueryResultFlag resultFlag)
    {
        final ArrayList<University> univs = new ArrayList<University>();
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
                        String univ_name = row.getString("univ_name");
                        String univ_city = row.getString("univ_city");


                        univs.add(new University(univ_id, univ_name, univ_city));

                    }
                }

                if(!univs.isEmpty()){
                    resultFlag.onQuerySuccess(univs);
                }
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



    public static void retrieveUniversity(Integer univID, final QueryResultFlag resultFlag)
    {
            retUniv(new ArrayList<String>(), ("univ_id =" + univID), resultFlag);
    }

    public static void retrieveOnCity(String city, final QueryResultFlag resultFlag)
    {
        retUniv(new ArrayList<String>() ,("univ_city = "+ convertString(city)), resultFlag);
    }

    public static void retrieveAllCities(final QueryResultFlag resultFlag)
    {
        ArrayList<String> attrs = new ArrayList<String>();
        attrs.add("DISTINCT univ_city");

        retUniv(attrs, "univ_city IS NOT NULL", resultFlag);
    }

    public static void retrieveAllUniversities(final QueryResultFlag resultFlag)
    {
        retUniv(new ArrayList<String>(),"",resultFlag);
    }
}