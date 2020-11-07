package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ksu.nafea.R;
import com.ksu.nafea.logic.User;
import com.ksu.nafea.utilities.InvalidFieldException;
import com.ksu.nafea.utilities.NafeaUtil;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private ArrayList<TextView> label;
    private ArrayList<EditText> field;
    private ArrayList<Button> button;
    private int step = 1;
    private User user = null; // To-Do

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // shows back button on up-left corner.

        regActivInit();



        for(int i = 0; i < field.size(); i++)
            addFieldListener(i);


        // 0 for Next Button
        button.get(0).setOnClickListener(new View.OnClickListener()// Next Button
        {
            @Override
            public void onClick(View v)
            {
                executeNext();
            }
        });
        // 1 for Cancel Button
        button.get(1).setOnClickListener(new View.OnClickListener() // Cancel Button
        {
            @Override
            public void onClick(View v)
            {
                executeCancel();
            }
        });

    }

    private void regActivInit()
    {
        label = new ArrayList<TextView>();
        field = new ArrayList<EditText>();
        button = new ArrayList<Button>();

        label.add((TextView) findViewById(R.id.txt_regEmail));
        label.add((TextView) findViewById(R.id.txt_regConfirmEmail));
        label.add((TextView) findViewById(R.id.txt_regPass));
        label.add((TextView) findViewById(R.id.txt_rePass));

        field.add((EditText) findViewById(R.id.et_regEmail));
        field.add((EditText) findViewById(R.id.et_regConfirmEmail));
        field.add((EditText) findViewById(R.id.et_regPass));
        field.add((EditText) findViewById(R.id.et_rePass));

        button.add((Button) findViewById(R.id.b_regNext));
        button.add((Button) findViewById(R.id.b_regCancel));
    }

    private void addFieldListener(final int fieldIndex)
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
                validateFieldSyntax(inputField, fieldIndex);
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private void executeNext()
    {
        boolean isValidFields = validateFields();

        if(step == 1 && isValidFields)
        {
            String email = field.get(0).getText().toString();
            String password = field.get(2).getText().toString();
            user = new User(email, password);

            setStep(2);
        }
        else if(step == 2 && isValidFields)
        {
            String firstName = field.get(0).getText().toString();
            String lastName = field.get(1).getText().toString();
            user.setFullname(firstName + " " + lastName);

            setStep(3);
        }
        else if(step == 3)
        {
            //To-Do add university, college, major.

            if(user.register())
            {
                //To-Do end activity.
            }
        }
    }

    private void executeCancel()
    {
        this.finish();
    }

    private boolean validateFields()
    {
        int syntaxErrors = 0;
        for(int i = 0; i < field.size(); i++)
            if(!validateFieldSyntax(field.get(i), i))
                syntaxErrors++;

        if(syntaxErrors == 0)
        {
            int dataErrors = 0;
            for(int i = 0; i < field.size(); i++)
                if(!validateFieldData(field.get(i), i))
                    dataErrors++;

            if(dataErrors == 0)
                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed()
    {
        if(step == 1)
            super.onBackPressed(); // leave register
        else
            setStep(step - 1); // go backward one step
    }

    private boolean validateFieldSyntax(EditText inputField, int fieldIndex)
    {
        String fieldText = inputField.getText().toString();
        String fieldLabel = label.get(fieldIndex).getText().toString();

        try
        {
            if(step == 1)
            {
                switch (fieldIndex)
                {
                    case 0: // 0 for email
                        User.isValidEmail(fieldLabel, fieldText);
                        break;
                    case 2: //2 for password
                        User.isValidPassword(fieldLabel, fieldText);
                        break;
                    case 1: // 1 for confirm email\
                    case 3: // 3 for re-password
                        String originalLabel = label.get(fieldIndex - 1).getText().toString();
                        String originalfield = field.get(fieldIndex - 1).getText().toString();

                        User.isValidConfirmField(originalfield, fieldText, originalLabel, fieldLabel);
                        break;
                }
            }
            else if(step == 2)
            {
                User.isValidInput(fieldLabel, fieldText, false, false);
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

    private boolean validateFieldData(EditText inputField, int fieldIndex)
    {
        String fieldText = inputField.getText().toString();

        try
        {
            if(step == 1)
            {
                String emailLabel = label.get(fieldIndex).getText().toString();
                User.isEmailExist(fieldText, emailLabel);
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

    private void setStep(int step)
    {
        switch (step)
        {
            case 1 :
                label.get(0).setText("Email:");
                label.get(1).setText("Confirm Email:");
                label.get(2).setText("Password:");
                label.get(3).setText("Re-Password:");

                field.get(0).setInputType(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                field.get(1).setInputType(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                for(int i = 0; i < field.size(); i++)
                {
                    label.get(i).setVisibility(View.VISIBLE);
                    field.get(i).setVisibility(View.VISIBLE);
                }

                break;
            case 2 :
                label.get(0).setText("First Name:");
                label.get(1).setText("Last Name:");

                field.get(0).setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);
                field.get(1).setInputType(EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_FLAG_CAP_WORDS);


                for(int i = 2; i < field.size(); i++)
                {
                    label.get(i).setVisibility(View.INVISIBLE);
                    field.get(i).setVisibility(View.INVISIBLE);
                }

                break;
            case 3:
                Intent intent = new Intent(this, BrowseActivity.class);
                startActivity(intent);
                break;
        }


        NafeaUtil.clearFields(field);
        this.step = step;
    }

}