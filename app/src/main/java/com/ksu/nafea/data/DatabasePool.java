package com.ksu.nafea.data;

import com.ksu.nafea.utilities.DatabaseException;

import java.sql.ResultSet;
import java.util.ArrayList;

public class DatabasePool

{

    private boolean executeUpdateCommand(String command) throws DatabaseException
    {
        NafeaDatabase.startConnection();

        int effectedRows = NafeaDatabase.executeUpdate(command);

        NafeaDatabase.closeConnection();


        if(effectedRows > 0)
            return true;
        else
            return false;
    }

    public boolean Update(String tableName, String Condition) throws DatabaseException
    {
        String command="UPDATE"+" "+tableName+" "+Condition;
        return executeUpdateCommand(command);
    }

    public boolean insert(String TableName,String att) throws DatabaseException
    {
        String command="INSERT INTO"+" "+ TableName+" " +att;
        return executeUpdateCommand(command);
    }

    public boolean delete(String tableName,String condition) throws DatabaseException
    {
        String  command = "DELETE FROM"+""+tableName+""+condition;
        return executeUpdateCommand(command);
    }


    public ResultSet retrieve(String attribute, String tableName, String condition) throws DatabaseException
    {
        NafeaDatabase.startConnection();

        String command = "SELECT " + attribute;
        command += "FROM " + tableName;
        command += "WHERE " + condition;

        ResultSet rows = NafeaDatabase.executeQuery(command);

        NafeaDatabase.closeConnection();
        return rows;
    }

    public ResultSet retrieve(ArrayList<String> attributes, String tableName, String condition) throws DatabaseException
    {
        NafeaDatabase.startConnection();

        String command = "SELECT ";
        for(int i = 0; i < attributes.size(); i++)
        {
            if(i == (attributes.size() -1) )
                command += attributes.get(i);
            else
                command += attributes.get(i) + ",";
        }
        command += "FROM " + tableName;
        command += "WHERE " + condition;

        ResultSet rows = NafeaDatabase.executeQuery(command);

        NafeaDatabase.closeConnection();
        return rows;
    }

    public ResultSet retrieve(String command) throws DatabaseException
    {
        NafeaDatabase.startConnection();

        ResultSet rows = NafeaDatabase.executeQuery(command);

        NafeaDatabase.closeConnection();
        return rows;
    }
}