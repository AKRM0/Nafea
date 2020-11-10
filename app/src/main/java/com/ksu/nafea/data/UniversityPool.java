package com.ksu.nafea.data;

import com.ksu.nafea.logic.University;
import com.ksu.nafea.utilities.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UniversityPool extends DatabasePool
{

    public static ArrayList<String> retrieveAllCities() throws DatabaseException
    {
        String cityLabel = "univ_city";

        ResultSet rs = retrieve(cityLabel, "University");
        ArrayList<String> cities = new ArrayList<String>();

        try
        {
            while(rs.next())
            {
                String city = rs.getString(cityLabel);

                if(!cities.contains(city))
                    cities.add(city);
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Failed to retrieve all cities.");
        }


        return cities;
    }

}
