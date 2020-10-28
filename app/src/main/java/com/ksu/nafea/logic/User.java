package com.ksu.nafea.logic;

import android.util.Patterns;

import java.util.regex.Pattern;

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


    public static String isValidEmail(String email)
    {
        if(Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return "";
        else
            return "Email:\n- the email is incorrect!";
    }
    public static String isValidPassword(String password)
    {
        String errorMsg = "";
        int minLength = 4;

        if(password.isEmpty())
            errorMsg += "- password field is empty.\n";
        else
        {
            if(password.length() <= minLength)
                errorMsg += "- password must be more than " + minLength + " characters.\n";
        }


        if(errorMsg.isEmpty())
            return "";
        else
            return "Password:\n" + errorMsg;
    }
    public static String isValidRePassword(String password, String rePassword)
    {
        String errorMsg = "";
        if(rePassword.isEmpty())
            errorMsg += "- re-password field is empty.\n";
        else
        {
            if(!rePassword.equals(password))
                errorMsg += "- re-password doesn't match the password.\n";
        }

        if(errorMsg.isEmpty())
            return "";
        else
            return "Re-Password:\n" + errorMsg;
    }

    public static String isValidInput(String labelName, String text, boolean allowNumbers, boolean allowSymbols)
    {
        String errorMsg = "";

        if(text.isEmpty())
            errorMsg += "- field is empty.\n";
        else
        {
            String allowedChars = allowNumbers ? "[a-zA-Z0-9]*" : "[a-zA-Z]*";


            if(!allowNumbers && !Pattern.compile(allowedChars).matcher(text).matches())
                errorMsg += "- this field must haven't any number.\n";

            if(!allowSymbols && !Pattern.compile(allowedChars).matcher(text).matches())
                errorMsg += "- this field must haven't special characters (@, #, &, ...).\n";
        }


        if(errorMsg.isEmpty())
            return "";
        else
            return labelName + "\n" + errorMsg;
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
