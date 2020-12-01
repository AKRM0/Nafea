package com.ksu.nafea.logic;

import com.ksu.nafea.ui.nviews.IconData;
import com.ksu.nafea.data.DatabaseException;

import java.util.ArrayList;

public class University implements IconData
{
    private Integer id;
    private String name,city;
    public University(){

    }
    public University(Integer id,String name, String city) {
        this.id=id;
        this.name = name;
        this.city=city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

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
