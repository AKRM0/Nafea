package com.ksu.nafea.data;

import com.ksu.nafea.logic.User;

public class UserPool extends DatabasePool {
    public boolean insertStudent(String s_email, String password, String firstName, String lastName) {
        String ins = "(" + s_email + "," + password + "," + firstName + "," + lastName + ")";
        return insert("Student", ins);
    }
    public boolean insertadmin(String a_email, String password, String firstName, String lastName) {
        String ins = "(" + a_email + "," + password + "," + firstName + "," + lastName + ")";
        return insert("Admin", ins);
    }

    public boolean updateStudent(String new_value, String position, String s_email) {
        String condition = "SET" + " " + position + " " + "=" + " " + new_value + " " + "WHERE s_email = " + s_email;
        return Update( "Student", condition);
    }
    public boolean updateAdmin(String new_value, String position, String a_email) {
        String condition = "SET" + " " + position + " " + "=" + " " + new_value + " " + "WHERE e_email = " + a_email;
        return Update( "Admin", condition);
    }

    public boolean deleteStudent(String condition){
        String cond = "WHERE"+ ""+condition;
        return delete("Student",cond);
    }
    public boolean deleteAdmin(String condition){
        String cond = "WHERE"+ ""+condition;
        return delete("Admin",cond);
    }

    public User retrieveStudent(String condition, String attribute){
        String command =  " " + "WHERE" + " " + condition;
        //To-Do retrieve result Student as object,
        //retrieve(attribute,"Admin",command);
        User st = new User("","");
        //assign attributes to st object
       return st;
    }
    public User retrieveAdmin(String condition,String attribute){
        String command =  " " + "WHERE" + " " + condition;
        //To-Do retrieve result admin as object,
        // retrieve(attribute,"Admin",command);
        User ad = new User("","");
        //assign attributes to ad object
        return ad;
    }
}