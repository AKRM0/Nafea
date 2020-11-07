package com.ksu.nafea.logic;


import android.util.Patterns;

import com.ksu.nafea.utilities.InvalidFieldException;

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



    // register user with current data.
    public boolean register()
    {
        return true;
    }

    // login user with current data.
    public boolean login()
    {
        return true;
    }


    /*
    checks if the email is already in the database,
    returns true if the email in the database,
    otherwise throws an InvalidFieldException.
    */
    public static boolean isEmailExist(String email, String fieldLabel) throws InvalidFieldException
    {
        String errorMsg = "Not Exist";
        boolean isEmailExist = true;

        //To-Do Checking the existence of the email in the database(for Student & Admin).

        //User.isValidEmail(fieldLabel, email);
        return true;
        //if(!isEmailExist)
            //throw new InvalidFieldException(fieldLabel, errorMsg);

        //return true;
    }

    /*
    checks if the password is correct,
    returns true if the password is correct,
    otherwise throws an InvalidFieldException.
    */
    public static boolean isPassMatch(String email, String password, String fieldLabel) throws InvalidFieldException
    {
        String errorMsg = "Pass Not Match";
        boolean isPassMatch = true;

        //To-Do Checking is the password correct of the given email(for Student & Admin).

        //User.isValidPassword(fieldLabel, password);
        return true;
        //if(!isPassMatch)
            //throw new InvalidFieldException(fieldLabel, errorMsg);

        //return true;
    }


    /*
    checks if the email has valid syntax,
    returns true if the email is valid,
    otherwise throws an InvalidFieldException.
    */
    public static boolean isValidEmail(String emailLabel, String email) throws InvalidFieldException
    {
        String errorMsg = "";
        boolean isValidEmail = true;


        if(email.isEmpty())
        {
            errorMsg += "- field is empty.\n";
            isValidEmail = false;
        }
        else
        {
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                errorMsg += "- the Email is incorrect.\n";
                isValidEmail = false;
            }
        }

        if(!isValidEmail)
            throw new InvalidFieldException(emailLabel, errorMsg);

        return true;
    }

    /*
    checks if the password has valid syntax,
    returns true if the password is valid,
    otherwise throws an InvalidFieldException.
    */
    public static boolean isValidPassword(String passwaordLabel, String password) throws InvalidFieldException
    {
        String errorMsg = "";
        int minLength = 4;
        boolean isValidPass = true;

        if(password.isEmpty())
        {
            errorMsg += "- field is empty.\n";
            isValidPass = false;
        }
        else
        {
            if(password.length() <= minLength)
            {
                errorMsg += "- Password must be more than " + minLength + " characters.\n";
                isValidPass = false;
            }
        }

        if(!isValidPass)
            throw new InvalidFieldException(passwaordLabel, errorMsg);

        return true;
    }

    /*
    checks if the confirmed field is matching the original field,
    returns true if the fields ia matches,
    otherwise throws an InvalidFieldException.
    */
    public static boolean isValidConfirmField(String originalField, String confirmedField, String originalLabel, String confirmedLabel) throws InvalidFieldException
    {
        String fieldLabel = confirmedLabel;
        confirmedLabel = confirmedLabel.replace(":", "");
        originalLabel = originalLabel.replace(":", "");

        String errorMsg = "";
        boolean isValidConfirmField = true;

        if(confirmedField.isEmpty())
        {
            errorMsg += "- field is empty.\n";
            isValidConfirmField = false;
        }
        else
        {
            if(!confirmedField.equals(originalField))
            {
                errorMsg += "- the " + confirmedLabel + " doesn't match the " + originalLabel + ".\n";
                isValidConfirmField = false;
            }
        }

        if(!isValidConfirmField)
            throw new InvalidFieldException(fieldLabel, errorMsg);

        return true;
    }

    /*
    checks if the input text is not empty and is allowed numbers and symbols or not,
    returns true if the input text is ok(based on allowNumbers and allowSymbols),
    otherwise throws an InvalidFieldException.
    */
    public static boolean isValidInput(String fieldLabel, String inputText, boolean allowNumbers, boolean allowSymbols) throws InvalidFieldException
    {
        String errorMsg = "";
        boolean isValidInput = true;

        if(inputText.isEmpty())
        {
            errorMsg += "- field is empty.\n";
            isValidInput = false;
        }
        else
        {
            String numbersRegex = "[a-zA-Z0-9]*";
            String symbolsRegex = "[a-zA-Z\\\\!\\\\\\\"\\\\#\\\\$\\\\%\\\\&\\\\'" +
                                    "\\\\(\\\\)\\\\*\\\\+\\\\,\\\\-\\\\.\\\\/\\\\:" +
                                    "\\\\;\\\\<\\\\>\\\\=\\\\?\\\\@\\\\[\\\\]\\\\{" +
                                    "\\\\}\\\\\\\\\\\\^\\\\_\\\\`\\\\~]*";

            boolean isNumberDetected = !Pattern.compile(symbolsRegex).matcher(inputText).matches();
            boolean isSymbolDetected = !Pattern.compile(numbersRegex).matcher(inputText).matches();

            if(isNumberDetected && !allowNumbers)
            {
                errorMsg += "- this field must haven't any number.\n";
                isValidInput = false;
            }
            if(isSymbolDetected && !allowSymbols)
            {
                errorMsg += "- this field must haven't special characters (@, _, #, ...).\n";
                isValidInput = false;
            }
        }


        if(!isValidInput)
            throw new InvalidFieldException(fieldLabel, errorMsg);

        return true;
    }


    //-----------------------[Getters & Setters]-----------------------

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

