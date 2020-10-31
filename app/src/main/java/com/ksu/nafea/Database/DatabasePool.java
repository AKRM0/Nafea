package com.ksu.nafea.Database;

import java.util.ArrayList;

public class DatabasePool

{
    public boolean update(String new_value,String tableName,String Condition,String pos){
        String command="UPDATE"+" "+tableName+" "+"SET"+" "+pos+" "+"="+" "+new_value+" "+"WHERE"+" "+Condition;

}

    public Object retrieve(String attribute,String tableName,String Condition) {
        ArrayList<Object> Retrieved = new ArrayList();
        String command = "SELECT " + " " + attribute + " " + "FROM" + " " + tableName + " " + "WHERE" + " " + Condition;

    }
    public boolean insert(String TableName,String att){
        String command="INSERT INTO"+" "+ TableName+" " +att;
        return true;
    }
}