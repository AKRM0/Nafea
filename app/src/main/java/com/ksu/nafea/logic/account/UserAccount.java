package com.ksu.nafea.logic.account;


import android.util.Patterns;

import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.data.sql.EntityObject;
import com.ksu.nafea.logic.Entity;
import com.ksu.nafea.utilities.InvalidFieldException;

import java.util.regex.Pattern;

public class UserAccount extends Entity<UserAccount>
{
    public static String TAG = "UserAccount";

    private String email;
    private String pass;
    private String fullname;
    private boolean hasAuthority = false;



    public UserAccount()
    {
        email = "";
        pass = "";
        fullname = "";
    }
    public UserAccount(String email, String pass)
    {
        this.email = email;
        this.pass = pass;
    }
    public UserAccount(String email, String pass, String firstName, String lastName)
    {
        this.email = email;
        this.pass = pass;
        this.setFullname(firstName, lastName);
    }



    @Override
    public EntityObject toEntity()
    {
        return null;
    }

    @Override
    public UserAccount toObject(EntityObject entityObject) throws ClassCastException
    {
        return null;
    }

    @Override
    public Class<UserAccount> getEntityClass()
    {
        return UserAccount.class;
    }



    public static void register(String email, String password, String firstName, String lastName, Integer majorID, final QueryRequestFlag resultFlag)
    {
        //UserPool.insertStudent(email, password, firstName, lastName, majorID, resultFlag);
    }


    public static void login(String email, final String password, final QueryRequestFlag resultFlag)
    {
        //UserPool.retrieveStudent(email, new QueryRequestFlag()
        //{
        //    @Override
        //    public void onQuerySuccess(Object queryResult)
        //    {
        //        if(queryResult != null)
        //        {
        //            ArrayList<UserAccount> students = (ArrayList<UserAccount>) queryResult;
        //            if(students != null)
        //            {
        //                UserAccount student = students.get(0);
        //                if(!student.getPass().equals(password))
        //                    student.setPass(null);
//
        //                resultFlag.onQuerySuccess(student);
        //                return;
        //            }
        //        }
//
        //        resultFlag.onQuerySuccess(new UserAccount(null, null));
        //    }
//
        //    @Override
        //    public void onQueryFailure(String failureMsg)
        //    {
        //        resultFlag.onQueryFailure(failureMsg + "/Retrieve Student");
        //    }
        //});

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

        //UserAccount.isValidEmail(fieldLabel, email);
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

        //UserAccount.isValidPassword(fieldLabel, password);
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



    @Override
    public String toString()
    {
        return "UserAccount{" +
                "email='" + email + '\'' +
                ", pass='" + pass + '\'' +
                ", fullname='" + fullname + '\'' +
                ", hasAuthority=" + hasAuthority +
                '}';
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

    public void setFullname(String firstName, String lastName)
    {
        fullname = "";

        if(firstName != null)
            fullname = firstName + " ";

        if(lastName != null)
            fullname += lastName;
    }


}

