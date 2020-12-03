package com.ksu.nafea.data.pool;

import android.util.Log;

import com.ksu.nafea.data.DatabaseException;
import com.ksu.nafea.data.QueryRequestFlag;
import com.ksu.nafea.data.QueryResult;
import com.ksu.nafea.data.QueryResultFlag;
import com.ksu.nafea.data.QueryRow;
import com.ksu.nafea.logic.Major;


import java.util.ArrayList;

public class MajorPool extends DatabasePool
{
    public static String TAG = "MajorPool";


    private static void retMajor(ArrayList<String> attrs,String condition, final QueryResultFlag resultFlag)
    {
        final ArrayList<Major> majors = new ArrayList<Major>();
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
                        Integer coll_id = row.getInt("coll_id");
                        majors.add(new Major(major_id, major_name, major_plan, coll_id));

                    }
                }

                if(!majors.isEmpty())
                    resultFlag.onQuerySuccess(majors);
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



    public static void retrieveMajor(Integer major_id, final QueryResultFlag resultFlag)
    {
        retMajor(new ArrayList<String>(), ("major_id =" + major_id), resultFlag);
    }

    public static void retrieveMajorsOnCollege(Integer coll_id, final QueryResultFlag resultFlag)
    {
        retMajor(new ArrayList<String>(), ("coll_id = "+ coll_id), resultFlag);
    }

    public static void retrieveAllMajors(final QueryResultFlag resultFlag)
    {
        retMajor(new ArrayList<String>(),"",resultFlag);
    }

}