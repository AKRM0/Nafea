package com.ksu.nafea.logic;

import com.ksu.nafea.ui.nviews.IconData;
import com.ksu.nafea.data.DatabaseException;

import java.util.ArrayList;

public class University implements IconData
{
    private String name;

    public University(String name)
    {
        this.name = name;
    }

    public String getText()
    {
        return name;
    }


    public static ArrayList<String> retrieveAllCities() throws DatabaseException
    {
        return null;
    }

}
