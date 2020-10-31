package com.ksu.nafea.Database;

import java.util.ArrayList;

public class DatabasePool

{
    public boolean update(String tableName, String Condition){
        String command="UPDATE"+" "+tableName+" "+"SET"+" "+Condition;

        // To-Do execute SQL command.
        return false;
    }

    public Object retrieve(String attribute,String tableName,String Condition) {
        ArrayList<Object> Retrieved = new ArrayList();
        String command = "SELECT " + " " + attribute + " " + "FROM" + " " + tableName + " " + "WHERE" + " " + Condition;

        // To-Do execute SQL command.
        return null;
    }
    public boolean insert(String TableName,String att){
        String command="INSERT INTO"+" "+ TableName+" " +att;
        // To-Do execute SQL command.
        return true;
    }
}