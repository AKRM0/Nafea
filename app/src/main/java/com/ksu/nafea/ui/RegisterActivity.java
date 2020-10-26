package com.ksu.nafea.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ksu.nafea.R;
import com.ksu.nafea.logic.User;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    private ArrayList<TextView> label;
    private ArrayList<EditText> field;
    private ArrayList<Button> button;
    private int step = 1;
    User user = null;

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

        button.get(0).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch (step)
                {
                    case 1:
                        useStepOne();
                        break;
                    case 2:
                        useStepTwo();
                        break;
                    default:
                        break;
                }
            }
        });

    }


    private void useStepOne()
    {
        String email = field.get(0).getText().toString();
        String pass = field.get(1).getText().toString();
        String rePass = field.get(2).getText().toString();

        if(User.isValidInfo(email, pass, rePass))
        {
            user = new User(email, pass);
            label.get(0).setText("First Name:");
            field.get(0).setText("");

            label.get(1).setText("Last Name:");
            field.get(1).setText("");

            label.get(2).setVisibility(View.INVISIBLE);
            field.get(2).setVisibility(View.INVISIBLE);

            field.get(0).setTextColor(Color.BLACK);
            field.get(1).setTextColor(Color.BLACK);
            field.get(2).setTextColor(Color.BLACK);

            step++;
        }
        else
        {
            field.get(0).setTextColor(Color.RED);
            field.get(1).setTextColor(Color.RED);
            field.get(2).setTextColor(Color.RED);
        }
    }
    private void useStepTwo()
    {
        String firstName = field.get(0).getText().toString();
        String lastName = field.get(1).getText().toString();

        if(User.isValidName(firstName, lastName) && user != null)
        {
            user.setFullname(firstName + " " + lastName);

            step++;
        }
    }
    private void useStepThree()
    {

    }

}