package com.ksu.nafea.data.pool;

import android.util.Log;

import com.ksu.nafea.data.DatabaseException;
import com.ksu.nafea.data.QueryRequestFlag;
import com.ksu.nafea.data.QueryResult;
import com.ksu.nafea.data.QueryResultFlag;
import com.ksu.nafea.data.QueryRow;
import com.ksu.nafea.logic.User;

import java.util.ArrayList;

public class UserPool extends DatabasePool
{
    public static String TAG = "UserPool";




    private static void retStudents(ArrayList<String> attrs, String condition, final QueryResultFlag resultFlag)
    {
        final ArrayList<User> students = new ArrayList<User>();

        retrieve("student", attrs, condition, new QueryRequestFlag()
        {
            @Override
            public void onRequestSuccess(QueryResult result)
            {
                for(int i = 0; i < result.length(); i++)
                {
                    QueryRow row = result.getRow(i);
                    if(row != null)
                    {
                        String email = row.getString("s_email");
                        String pass = row.getString("password");
                        String firstName = row.getString("first_name");
                        String lastName = row.getString("last_name");

                        students.add(new User(email, pass, firstName, lastName));
                    }
                }

                if(!students.isEmpty())
                    resultFlag.onQuerySuccess(students);
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



    public static void insertStudent(String email, String password, String firstName, String lastName, Integer majorID, final QueryResultFlag resultFlag)
    {
        ArrayList<Object> values = new ArrayList<Object>();

        values.add(email);
        values.add(password);
        values.add(firstName);
        values.add(lastName);
        values.add(majorID);

        insert("student", values, new QueryRequestFlag()
        {
            @Override
            public void onRequestSuccess(QueryResult result)
            {
                if(result != null)
                {
                    QueryRow status = result.getQueryStatus();
                    if(status != null)
                    {
                        Integer affectedRows = status.getInt("affectedRows");
                        resultFlag.onQuerySuccess(affectedRows);
                        return;
                    }
                }

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


    public static void retrieveStudent(String email, final QueryResultFlag resultFlag)
    {
        String condition = "s_email = " + convertString(email);
        retStudents(new ArrayList<String>(), condition, resultFlag);
    }

    public static void retrieveAllStudents(final QueryResultFlag resultFlag)
    {
        retStudents(new ArrayList<String>(), "", resultFlag);
    }

}