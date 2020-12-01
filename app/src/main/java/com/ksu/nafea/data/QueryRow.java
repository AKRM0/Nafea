package com.ksu.nafea.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QueryRow
{
    private static String TAG = "QueryRow";
    private JSONObject queryRow = null;

    public QueryRow(JSONObject jsonObject)
    {
        queryRow = jsonObject;
    }


    public int length()
    {
        return queryRow.length();
    }

    public JSONObject getJsonObject()
    {
        return queryRow;
    }

    public Integer getInt(String attributeName)
    {
        if(queryRow.isNull(attributeName))
            return null;

        Integer attrValue = 0;
        try
        {
            attrValue = queryRow.getInt(attributeName);
        }
        catch(JSONException e)
        {
            Log.e(TAG, "couldn't getInt from attribute \""+ attributeName +"\": " + e.getMessage());
        }

        return attrValue;
    }

    public Double getDouble(String attributeName)
    {
        if(queryRow.isNull(attributeName))
            return null;

        Double attrValue = 0.0;
        try
        {
            attrValue = queryRow.getDouble(attributeName);
        }
        catch(JSONException e)
        {
            Log.e(TAG, "couldn't getDouble from attribute \""+ attributeName +"\": " + e.getMessage());
        }

        return attrValue;
    }

    public String getString(String attributeName)
    {
        if(queryRow.isNull(attributeName))
            return null;

        String attrValue = "";
        try
        {
            attrValue = queryRow.getString(attributeName);
        }
        catch(JSONException e)
        {
            Log.e(TAG, "couldn't getString from attribute \""+ attributeName +"\": " + e.getMessage());
        }

        return attrValue;
    }

    public Boolean getBoolean(String attributeName)
    {
        if(queryRow.isNull(attributeName))
            return null;

        Boolean attrValue = true;
        try
        {
            attrValue = queryRow.getBoolean(attributeName);
        }
        catch(JSONException e)
        {
            Log.e(TAG, "couldn't getBoolean from attribute \""+ attributeName +"\": " + e.getMessage());
        }

        return attrValue;
    }

    public ArrayList<String> getAttributesNames()
    {
        ArrayList<String> attrsNames = new ArrayList<String>();
        JSONArray names = queryRow.names();

        for(int i = 0; i < names.length(); i++)
        {
            try
            {
                attrsNames.add(names.getString(i));
            }
            catch (JSONException e)
            {
                Log.e(TAG, "couldn't getString of attribute name in index " + i + ": " + e.getMessage());
            }
        }

        return attrsNames;
    }

    public boolean has(String attributeName)
    {
        return queryRow.has(attributeName);
    }

}
