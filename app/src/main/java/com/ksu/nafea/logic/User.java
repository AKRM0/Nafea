package com.ksu.nafea.logic;

public class User
{
    private String email;
    private String pass;
    private String fullname;
    private boolean hasAuthority = false;

    public User(String email, String pass)
    {
        this.email = email;
        this.pass = pass;
    }
    public User(String email, String pass, String fullname)
    {
        this.email = email;
        this.pass = pass;
        this.fullname = fullname;
    }


    public static boolean isValidInfo(String email, String pass, String rePass)
    {
        if(!email.equals(null) && !pass.equals(null) && !rePass.equals(null))
        {
            if(pass.equals(rePass))
                return true;
        }
        return false;
    }
    public static boolean isValidName(String firstName, String lastName)
    {
        if(!firstName.equals(null) && !lastName.equals(null))
            return true;
        return false;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

}
