package com.ksu.nafea.logic;

import com.ksu.nafea.ui.nviews.IconData;

public class College implements IconData
{
    private String name;

    public College(String name)
    {
        this.name = name;
    }


    public String getText()
    {
        return name;
    }
}
