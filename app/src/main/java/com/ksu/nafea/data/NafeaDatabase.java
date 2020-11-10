package com.ksu.nafea.data;

import android.database.sqlite.SQLiteDatabase;

import com.ksu.nafea.utilities.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NafeaDatabase
{
    private static String SQLDriver = "com.mysql.cj.jdbc.Driver";
    private static String databaseUrl = "jdbc:mysql://localhost:3306/nafea_schema";
    private static String dbUsername = "root";
    private static String dbPassword = "123098kfk";

    private static Connection connection = null;
    private static Statement statement = null;


    public static void startConnection() throws DatabaseException
    {
        try
        {
            if(connection == null || statement == null)
            {
                Class.forName(SQLDriver);
                connection = DriverManager.getConnection(databaseUrl, dbUsername, dbPassword);
                statement = connection.createStatement();
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Database fail to start connection:\nError: " + e.getMessage());
        }
        catch(ClassNotFoundException e)
        {
            throw new DatabaseException("SQL Driver not found.");
        }
    }


    public static int executeUpdate(String updateCommand) throws DatabaseException
    {
        if(connection == null || statement == null)
            throw new DatabaseException("there is no connection to database.");

        int effectedRows = 0;
        try
        {
            effectedRows = statement.executeUpdate(updateCommand);
        }
        catch(SQLException e)
        {
            throw new DatabaseException("Failed to execute update command:\nError: " + e.getMessage());
        }

        return effectedRows;
    }



    public static ResultSet executeQuery(String queryCommand) throws DatabaseException
    {
        if(connection == null || statement == null)
            throw new DatabaseException("there is no connection to database.");

        ResultSet rows = null;
        try
        {
            rows = statement.executeQuery(queryCommand);
        }
        catch(SQLException e)
        {
            throw new DatabaseException("Failed to execute query command:\nError: " + e.getMessage());
        }

        try
        {
            if(rows != null)
                rows.close();
        }
        catch(SQLException e)
        {
            throw new DatabaseException("Failed to close result sets:\nError: " + e.getMessage());
        }

        return rows;
    }



    public static void closeConnection() throws DatabaseException
    {
        try
        {
            if(connection != null)
                connection.close();

            if(statement != null)
                statement.close();
        }
        catch (SQLException e)
        {
            throw new DatabaseException("Database fail to close connection:\nError Messege: " + e.getMessage());
        }
    }
}
