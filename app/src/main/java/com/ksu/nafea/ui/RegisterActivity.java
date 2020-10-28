package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.MultiTapKeyListener;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ksu.nafea.R;
import com.ksu.nafea.logic.User;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private ArrayList<TextView> label;
    private ArrayList<EditText> field;
    private ArrayList<Button> button;
    private int step = 1;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        label = new ArrayList<TextView>();
        field = new ArrayList<EditText>();
        button = new ArrayList<Button>();

        label.add((TextView) findViewById(R.id.txt_email));
        label.add((TextView) findViewById(R.id.txt_pass));
        label.add((TextView) findViewById(R.id.txt_rePass));

        field.add((EditText) findViewById(R.id.et_email));
        field.add((EditText) findViewById(R.id.et_pass));
        field.add((EditText) findViewById(R.id.et_rePass));

        button.add((Button) findViewById(R.id.b_next));
        button.add((Button) findViewById(R.id.b_cancel));


        for(int i = 0; i < field.size(); i++)
            addFieldListener(i);

        button.get(0).setOnClickListener(new View.OnClickListener()// Next Button
        {
            @Override
            public void onClick(View v)
            {
                int errors = 0;
                for(int i = 0; i < field.size(); i++)
                    if(!validateField(field.get(i), i))
                        errors++;

                if(errors == 0)
                    setStep(2);
            }
        });
        button.get(1).setOnClickListener(new View.OnClickListener() // Cancel Button
        {
            @Override
            public void onClick(View v)
            {
                setStep(1);
            }
        });

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
                validateField(inputField, fieldIndex);
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    private boolean validateField(EditText inputField, int fieldIndex)
    {
        String fieldText = inputField.getText().toString();

        String errorMsg = "";
        if(step == 1)
        {
            switch (fieldIndex)
            {
                case 0:
                    errorMsg = User.isValidEmail(fieldText);
                    break;
                case 1:
                    errorMsg = User.isValidPassword(fieldText);
                    break;
                case 2:
                    String pass = field.get(1).getText().toString();
                    errorMsg = User.isValidRePassword(pass, fieldText);
                    break;
            }
        }
        else if(step == 2)
            errorMsg = User.isValidInput(label.get(fieldIndex).getText().toString(), fieldText, false, false);


        return updateField(inputField, errorMsg);
    }

    private boolean updateField(EditText textField, String ErrorMsg)
    {
        if(ErrorMsg.isEmpty())
        {
            textField.setTextColor(Color.BLACK);

            return true;
        }
        else
        {
            textField.setError(ErrorMsg);
            textField.setTextColor(Color.RED);

            return false;
        }
    }

    private void setStep(int step)
    {
        //clearFields();

        switch (step)
        {
            case 1 :
                label.get(0).setText("Email:");
                label.get(1).setText("Password:");
                label.get(2).setText("Re-Password:");

                field.get(0).setInputType(EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                field.get(1).setTransformationMethod(PasswordTransformationMethod.getInstance());

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
                //field.get(1).setTransformationMethod();

                label.get(2).setVisibility(View.INVISIBLE);
                field.get(2).setVisibility(View.INVISIBLE);

                break;
        }

        clearFields();
        this.step = step;
    }

    private void clearFields()
    {
        for(int i = 0; i < field.size(); i++)
        {
            field.get(i).setText("");
            field.get(i).setError(null);
            field.get(i).setTextColor(Color.BLACK);
        }
    }

}