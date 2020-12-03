package com.ksu.nafea.data.pool;

import android.util.Log;

import com.ksu.nafea.data.DatabaseException;
import com.ksu.nafea.data.QueryRequestFlag;
import com.ksu.nafea.data.QueryResult;
import com.ksu.nafea.data.QueryResultFlag;
import com.ksu.nafea.data.QueryRow;
import com.ksu.nafea.logic.College;


import java.util.ArrayList;

public class CollegePool extends DatabasePool
{
    public static String TAG = "CollegePool";


    private static void retCollege(ArrayList<String> attrs,String condition, final QueryResultFlag resultFlag)
    {
        final ArrayList<College> colleges = new ArrayList<College>();
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
                        Integer coll_id = row.getInt("coll_id");
                        String coll_name = row.getString("coll_name");
                        String coll_category = row.getString("coll_category");
                        Integer univ_id = row.getInt("univ_id");

                        colleges.add(new College(coll_id, coll_name, coll_category, univ_id));

                    }
                }
                if(!colleges.isEmpty()){
                    resultFlag.onQuerySuccess(colleges);
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



    public static void retrieveCollege(Integer coll_id, final QueryResultFlag resultFlag)
    {
        retCollege(new ArrayList<String>(), ("coll_id = " + coll_id), resultFlag);
    }

    public static void retrieveOnUniversity(Integer univ_id,final QueryResultFlag resultFlag)
    {
        retCollege(new ArrayList<String>(), ("univ_id = "+ univ_id), resultFlag);
    }

    public static void retrieveOnCategory(Integer univ_id, String coll_category, final QueryResultFlag resultFlag)
    {
        retCollege(new ArrayList<String>(), ("univ_id = "+ univ_id +" AND coll_category = "+ convertString(coll_category)), resultFlag);
    }

    public static void retrieveAllCategories(Integer univ_id, final  QueryResultFlag resultFlag)
    {
        ArrayList<String> attrs = new ArrayList<String>();
        attrs.add("DISTINCT coll_category");

        String condition = "univ_id = " + univ_id;
        retCollege(attrs, condition, resultFlag);
    }

}