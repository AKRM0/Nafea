package com.ksu.nafea.data;

import java.util.ArrayList;

public class DatabasePool

{
    public boolean Update(String tableName, String Condition){
        String command="UPDATE"+" "+tableName+" "+Condition;

        return false;
    }
    public Object retrieve(String attribute,String tableName,String command) {
        ArrayList<Object> Retrieved = new ArrayList();
        String Command = "SELECT " + " " + attribute + " " + "FROM" + " " + tableName + command;
        // execute(Command);
        return null;
    }
    public boolean insert(String TableName,String att){
        String command="INSERT INTO"+" "+ TableName+" " +att;
       // return execute(command);
        return true;
    }
    public boolean delete(String tableName,String condition){
        String  command = "DELETE FROM"+""+tableName+""+condition;
      // return execute(command);
        return true;
    }
}