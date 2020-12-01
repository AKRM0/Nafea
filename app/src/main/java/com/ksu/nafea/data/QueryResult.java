package com.ksu.nafea.data;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QueryResult
{
    private static String TAG = "QueryResult";
    private JSONArray queryResult = null;

    private <T> JSONArray convertBodyToJSON(T body, boolean isList)
    {
        String data = new Gson().toJson(body);
        JSONArray json = null;
        try
        {
            if(isList)
                json = new JSONArray(data);
            else
            {
                JSONObject jsonObject = new JSONObject(data);
                json = new JSONArray();
                json.put(jsonObject);
            }
        }
        catch(JSONException e)
        {
            Log.e(TAG, "couldn't convert body to json array: " + e.getMessage());
        }

        return json;
    }


    public <T> QueryResult(T body, boolean isList)
    {
        queryResult = convertBodyToJSON(body, isList);
    }


    public int length()
    {
        return queryResult.length();
    }

    public JSONArray getJsonArray()
    {
        return queryResult;
    }

    public QueryRow getRow(int index)
    {
        if(queryResult == null)
            return null;

        QueryRow row = null;
        try
        {
            row = new QueryRow(queryResult.getJSONObject(index));
        }
        catch(JSONException e)
        {
            Log.e(TAG, "couldn't get json object from json array: " + e.getMessage());
        }

        return row;
    }

    public QueryRow getQueryStatus()
    {
        return getRow(0);
    }

    public ArrayList<String> getAttributesNames()
    {
        ArrayList<String> attrsNames = new ArrayList<String>();
        QueryRow row = getRow(0);

        if(row != null)
            attrsNames = row.getAttributesNames();

        return attrsNames;
    }


}
