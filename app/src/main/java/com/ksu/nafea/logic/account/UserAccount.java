package com.ksu.nafea.logic.account;


import android.util.Patterns;

import com.ksu.nafea.data.request.FailureResponse;
import com.ksu.nafea.data.request.QueryRequestFlag;
import com.ksu.nafea.logic.Entity;
import com.ksu.nafea.logic.QueryPostStatus;
import com.ksu.nafea.utilities.InvalidFieldException;

import java.util.ArrayList;
import java.util.regex.Pattern;

public abstract class UserAccount<AccountType> extends Entity<AccountType>
{
    public static final String TAG = "UserAccount";
    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;


    public UserAccount()
    {
        email = "";
        password = "";
        firstName = "";
        lastName = "";
    }
    public UserAccount(String email, String password, String firstName, String lastName)
    {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    @Override
    public String toString()
    {
        return "UserAccount{" + "email='" + email + '\'' + ", password='" + password + '\'' + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
    }


    //-------------------------------------------------------[Queries]-------------------------------------------------------
    public static <UserAccountType extends UserAccount<UserAccountType>> void register(UserAccount<UserAccountType> userAccount, final QueryRequestFlag<QueryPostStatus> requestFlag)
    {
        try
        {
            getPool().insert(userAccount, requestFlag);
        }
        catch (Exception e)
        {
            String msg = "Failed to register: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }
    }


    public static <UserAccountType extends UserAccount<UserAccountType>> void login(UserAccount<UserAccountType> userAccount, final QueryRequestFlag<UserAccountType> requestFlag)
    {
        String selectData = userAccount.getLoginSelectData();
        String joinData = userAccount.getLoginJoinData();
        String condition = userAccount.getLoginCondition();

        try
        {
            getPool().retrieve(userAccount.getEntityClass(), new QueryRequestFlag<ArrayList<UserAccountType>>()
            {
                @Override
                public void onQuerySuccess(ArrayList<UserAccountType> resultObject)
                {
                    if(resultObject != null)
                    {
                        UserAccountType account = resultObject.get(0);
                        requestFlag.onQuerySuccess(account);
                    }
                    else
                        requestFlag.onQuerySuccess(null);
                }

                @Override
                public void onQueryFailure(FailureResponse failure)
                {
                    failure.addNode(TAG);
                    requestFlag.onQueryFailure(failure);
                }
            }, selectData, joinData, condition);
        }
        catch (Exception e)
        {
            String msg = "Failed to login: " + e.getMessage();
            Entity.sendFailureResponse(requestFlag, TAG, msg);
        }

    }


    //-------------------------------------------------------[abstracted Methods]-------------------------------------------------------

    protected abstract String getLoginSelectData();
    protected abstract String getLoginJoinData();
    protected abstract String getLoginCondition();


    //-------------------------------------------------------[Checking Methods]-------------------------------------------------------

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



    //-------------------------------------------------------[Getters & Setters]-------------------------------------------------------

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getFullName()
    {
        return firstName + " " + lastName;
    }


}

