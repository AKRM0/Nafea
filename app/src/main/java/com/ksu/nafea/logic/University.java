package com.ksu.nafea.logic;

import com.ksu.nafea.data.QueryResultFlag;
import com.ksu.nafea.data.pool.UniversityPool;
import com.ksu.nafea.ui.nviews.IconData;
import com.ksu.nafea.data.DatabaseException;

import java.util.ArrayList;

public class University implements IconData
{
    private Integer id;
    private String name,city;
    private ArrayList<College> colleges;

    public University()
    {
        this.id = 0;
        this.name = "";
        this.city = "";
    }

    public University(Integer id,String name, String city) {
        this.id=id;
        this.name = name;
        this.city=city;
    }


    public static void retrieveAllCities(final QueryResultFlag resultFlag)
    {
        UniversityPool.retrieveAllCities(new QueryResultFlag()
        {
            @Override
            public void onQuerySuccess(Object queryResult)
            {
                if(queryResult != null)
                {
                    ArrayList<University> univs = (ArrayList<University>) queryResult;
                    if(univs != null)
                    {
                        ArrayList<String> cities = new ArrayList<String>();
                        for(int i = 0; i < univs.size(); i++)
                        {
                            University university = univs.get(i);
                            cities.add(university.getCity());
                        }

                        resultFlag.onQuerySuccess(cities);
                        return;
                    }
                }

                resultFlag.onQuerySuccess(null);
            }

            @Override
            public void onQueryFailure(String failureMsg)
            {
                resultFlag.onQueryFailure(failureMsg + "/Retrieve All Cities");
            }
        });
    }

    public static void retrieveUniversitiesOnCity(String city, final  QueryResultFlag resultFlag)
    {
        UniversityPool.retrieveOnCity(city, new QueryResultFlag()
        {
            @Override
            public void onQuerySuccess(Object queryResult)
            {
                if(queryResult != null)
                {
                    ArrayList<University> univs = (ArrayList<University>) queryResult;
                    if(univs != null)
                    {
                        resultFlag.onQuerySuccess(univs);
                        return;
                    }
                }

                resultFlag.onQuerySuccess(null);
            }

            @Override
            public void onQueryFailure(String failureMsg)
            {
                resultFlag.onQueryFailure(failureMsg + "/Retrieve Universities On City");
            }
        });
    }


    //--------------------------------------[Setters & Getters]--------------------------------------
    public Integer getId() {
        return id;
    }

    public Integer getIconID()
    {
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

    public ArrayList<College> getColleges()
    {
        return colleges;
    }

    public University(String name)
    {
        this.name = name;
    }

    public String getText()
    {
        return name;
    }

    @Override
    public String toString() {
        return "University{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
