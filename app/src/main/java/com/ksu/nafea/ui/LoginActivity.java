package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.data.QueryResultFlag;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.utilities.InvalidFieldException;
import com.ksu.nafea.utilities.NafeaUtil;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity
{
    public static final String TAG = "LoginActivity";

    private ArrayList<TextView> label;
    private ArrayList<EditText> field;
    private ArrayList<Button> button;
    private CheckBox rememberMe;
    private TextView createAccount;
    private User user = null;

    /* To-Do:
    * Add forget password feature.
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Intent intent = new Intent(this, BrowseActivity.class);
        //startActivity(intent);

        loginActivInit();

        for(int i = 0; i < field.size(); i++)
            addFieldListener(i);

        // when create an account is pressed.
        createAccount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startRegisterActicity();
            }
        });

        // when login Button is pressed.
        button.get(0).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                executeLogin();
            }
        });
    }

    private void loginActivInit()
    {
        label = new ArrayList<TextView>();
        field = new ArrayList<EditText>();
        button = new ArrayList<Button>();

        label.add((TextView) findViewById(R.id.txt_logEmail));
        label.add((TextView) findViewById(R.id.txt_logPass));

        field.add((EditText) findViewById(R.id.et_logEmail));
        field.add((EditText) findViewById(R.id.et_logPass));

        button.add((Button) findViewById(R.id.b_login));

        rememberMe = (CheckBox) findViewById(R.id.chb_remember);

        createAccount = (TextView) findViewById(R.id.txtb_register);
    }

    private void addFieldListener(int fieldIndex)
    {
        final EditText inputField = field.get(fieldIndex);
        inputField.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                NafeaUtil.updateField(inputField, "");
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private void startRegisterActicity()
    {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void executeLogin()
    {
        NafeaUtil.updateField(field.get(0), "");
        NafeaUtil.updateField(field.get(1), "");

        if(validateFields())
        {
            String email = field.get(0).getText().toString();
            String pass = field.get(1).getText().toString();
            user = new User(email, pass);

            user.login(email, pass, new QueryResultFlag()
            {
                @Override
                public void onQuerySuccess(Object queryResult)
                {
                    user = (User) queryResult;
                    if(user.getEmail() != null)
                    {
                        if(user.getPass() != null)
                            Toast.makeText(LoginActivity.this, "Login Successfully.", Toast.LENGTH_LONG).show();
                        else
                        {
                            NafeaUtil.updateField(field.get(1), "Wrong Password");
                        }
                    }
                    else
                    {
                        NafeaUtil.updateField(field.get(0), "Wrong Email");
                    }


                    if(rememberMe.isChecked())
                    {

                    }
                    else
                    {
                        //Toast.makeText(this, "Logging in successfully.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onQueryFailure(String failureMsg)
                {
                    Log.e(TAG, failureMsg + "/Login");
                    Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
                }
            });

        }
    }

    private boolean validateFields()
    {
        String email = field.get(0).getText().toString();
        String pass = field.get(1).getText().toString();

        String emailLabel = label.get(0).getText().toString();
        String passLabel = label.get(1).getText().toString();

        EditText inputField = field.get(0);

        try
        {
            if(User.isEmailExist(email, emailLabel))
            {
                inputField = field.get(1);
                User.isPassMatch(email, pass, passLabel);
            }
        }
        catch (InvalidFieldException e)
        {
            NafeaUtil.updateField(inputField, e.getMessage());
            return false;
        }


        NafeaUtil.updateField(inputField, "");
        return true;
    }

}