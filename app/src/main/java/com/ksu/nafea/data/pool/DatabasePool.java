package com.ksu.nafea.data.pool;

import android.util.Log;

import com.ksu.nafea.api.NafeaApiRequest;
import com.ksu.nafea.data.DatabaseException;
import com.ksu.nafea.data.QueryRequestFlag;
import com.ksu.nafea.data.QueryResult;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabasePool

{
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://10.0.2.2:5002";
    protected static final String QUERY_FAILURE_MSG = "/Failed Request";

    protected static NafeaApiRequest getDatabaseAPI()
    {
        if(retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


        NafeaApiRequest api = retrofit.create(NafeaApiRequest.class);

        return api;
    }


    private static void sendPostRequest(Call<Object> request, String operation, final QueryRequestFlag requestFlag)
    {
        final String failedOperation = "Failed Operation:[" + operation + "]";

        request.enqueue(new Callback<Object>()
        {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response)
            {
                if(response.isSuccessful())
                {
                    QueryResult result = new QueryResult(response.body(), false);
                    requestFlag.onRequestSuccess(result);
                }
                else
                    requestFlag.onRequestFailure(new DatabaseException(response.message(), failedOperation));
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t)
            {
                requestFlag.onRequestFailure(new DatabaseException(t.getMessage(), failedOperation));
            }
        });
    }

    private static void sendGetRequest(Call<List<Object>> request, String operation, final QueryRequestFlag requestFlag)
    {
        final String failedOperation = "Failed Operation:[" + operation + "]";

        request.enqueue(new Callback<List<Object>>()
        {
            @Override
            public void onResponse(Call<List<Object>> call, Response<List<Object>> response)
            {
                if(response.isSuccessful())
                {
                    QueryResult result = new QueryResult(response.body(), true);
                    requestFlag.onRequestSuccess(result);
                }
                else
                    requestFlag.onRequestFailure(new DatabaseException(response.message(), failedOperation));
            }

            @Override
            public void onFailure(Call<List<Object>> call, Throwable t)
            {
                requestFlag.onRequestFailure(new DatabaseException(t.getMessage(), failedOperation));
            }
        });
    }

    protected static void insert(String table, ArrayList<Object> values, final QueryRequestFlag requestFlag)
    {
        String valuesSet = getValuesSet(values);

        Call<Object> insertRequest = getDatabaseAPI().insertData(table, valuesSet);
        String operation = "insert to " + table + " with values:{" + valuesSet + "}";

        sendPostRequest(insertRequest, operation, requestFlag);

    }

    protected static void Update(String table, String valuesSet, String condition, final QueryRequestFlag requestFlag)
    {
        Call<Object> updateRequest = getDatabaseAPI().updateData(table, valuesSet, condition);
        String operation = "update " + table + " where " + condition + " with new values:{" + valuesSet + "}";

        sendPostRequest(updateRequest, operation, requestFlag);
    }

    protected static void delete(String table, String condition, final QueryRequestFlag requestFlag)
    {
        Call<Object> deleteRequest = getDatabaseAPI().deleteData(table, condition);
        String operation = "delete from " + table + " where " + condition;

        sendPostRequest(deleteRequest, operation, requestFlag);
    }


    protected static void retrieve(String table, ArrayList<String> attrs, String condition, final QueryRequestFlag requestFlag)
    {
        String urlRequest = makeRetrieveUrlRequest(table, attrs, condition);
        Log.d("Nafea", "API Command: " + urlRequest);
        Call<List<Object>> retrieveRequest = getDatabaseAPI().retrieveData(urlRequest);
        final String operation = "retrieve " + table + " where " + condition;

        sendGetRequest(retrieveRequest, operation, requestFlag);

    }


    private static String getValuesSet(ArrayList<Object> values)
    {
        String valuesSet = "";
        for(int i = 0; i < values.size(); i++)
        {
            String currentValue = "";

            if(values.get(i) == null)
                currentValue = "null";
            else if(values.get(i) instanceof String)
                currentValue = "\"" + values.get(i) + "\"";
            else
                currentValue = values.get(i).toString();

            if(i == (values.size() - 1))
                valuesSet += currentValue;
            else
                valuesSet += currentValue + ",";
        }

        return valuesSet;
    }

    private static String getAttributesSet(ArrayList<String> attrs)
    {
        String attrsSet = "";
        for(int i = 0; i < attrs.size(); i++)
        {
            if(i == (attrs.size() - 1))
                attrsSet += attrs.get(i);
            else
                attrsSet += attrs.get(i) + ",";
        }

        return attrsSet;
    }


    protected static String makeRetrieveUrlRequest(String table, ArrayList<String> attrs, String condition)
    {
        String urlRequest = "/get/" + table + "?";

        if(!attrs.isEmpty())
        {
            String attrsSet = getAttributesSet(attrs);
            urlRequest += "attrs=[" + attrsSet + "]&";
        }
        if(!condition.isEmpty())
            urlRequest += "cond=[" + condition + "]";

        return urlRequest;
    }

    protected static String convertString(String s)
    {
        return "\""+s+"\"";
    }
}