package com.ksu.nafea.utilities;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

public class NafeaUtil
{


    public static void clearFields(ArrayList<EditText> field)
    {
        for(int i = 0; i < field.size(); i++)
        {
            field.get(i).setText("");
            field.get(i).setError(null);
            field.get(i).setTextColor(Color.BLACK);
        }
    }

    public static void updateField(EditText field, String errorMsg)
    {
        // if errorMsg is empty that's mean there is no error.
        if(errorMsg.isEmpty())
        {
            field.setTextColor(Color.BLACK);
        }
        else
        {
            field.setError(errorMsg);
            field.setTextColor(Color.RED);
        }
    }

    public static int getRandomColor(int aplha)
    {
        Random randGen = new Random();
        int red = randGen.nextInt(256);
        int green = randGen.nextInt(256);
        int blue = randGen.nextInt(256);

        return Color.argb(aplha,red, green, blue);
    }

}
