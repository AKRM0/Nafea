package com.ksu.nafea.Database;

import com.ksu.nafea.Database.DatabasePool;
import java.util.ArrayList;

public class UserPool extends DatabasePool {
    public boolean insert(String s_email, String password, String firstName, String lastName) {
        String ins = "(" + s_email + "," + password + "," + firstName + "," + lastName + ")";
        return insert("Student", ins);
    }

    public boolean update(String new_value, String pos, String s_email) {
        String condition = "SET" + " " + pos + " " + "=" + " " + new_value + " " + "WHERE s_email = " + s_email;
        return update("Student", condition);

    }
}