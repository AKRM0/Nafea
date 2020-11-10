package com.ksu.nafea.logic;

import com.ksu.nafea.ui.nviews.IconData;

public class Major implements IconData
{
    private String name;

    public Major(String name)
    {
        this.name = name;
    }


    public String getText()
    {
        return name;
    }
}
