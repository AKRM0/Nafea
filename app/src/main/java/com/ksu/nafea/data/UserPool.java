package com.ksu.nafea.data;

import android.util.Log;

import com.ksu.nafea.logic.User;

import java.util.ArrayList;

public class UserPool extends DatabasePool
{
    public static String TAG = "UserPool";

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



    public static void retrieveStudent(String email, final QueryResultFlag resultFlag)
    {
        final User student = new User();

        ArrayList<String> attrs = new ArrayList<String>();
        attrs.add("s_email");
        attrs.add("password");
        String condition = "s_email =  \"" + email + "\"";

        retrieve("student", attrs, condition, new QueryRequestFlag()
        {
            @Override
            public void onRequestSuccess(QueryResult result)
            {
                QueryRow row = result.getRow(0);
                if(row != null)
                {
                    String email = row.getString("s_email");
                    String pass = row.getString("password");
                    String firstName = row.getString("first_name");
                    String lastName = row.getString("last_name");

                    student.setEmail(email);
                    student.setPass(pass);
                    student.setFullname(firstName, lastName);

                    resultFlag.onQuerySuccess(student);
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

    public static void retrieveAllStudents(final QueryResultFlag resultFlag)
    {
        final ArrayList<User> students = new ArrayList<User>();

        retrieve("student", new ArrayList<String>(), "", new QueryRequestFlag()
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
                resultFlag.onQueryFailure();
            }
        });
    }

}