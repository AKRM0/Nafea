package com.ksu.nafea.utilities;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class NafeaUtil
{

    public static void showToastMsg(Context context, String msg, int msgLength)
    {
        Toast.makeText(context, msg, msgLength).show();
    }

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

    public static int getRandomColor(int alpha)
    {
        Random randGen = new Random();
        int red = randGen.nextInt(256);
        int green = randGen.nextInt(256);
        int blue = randGen.nextInt(256);

        return Color.argb(alpha,red, green, blue);
    }

    public static int getRangedRandomColor(int min, int max, int alpha)
    {
        Random randGen = new Random();
        int red = randGen.nextInt((max - min) + 1) + min;
        int green = randGen.nextInt((max - min) + 1) + min;
        int blue = randGen.nextInt((max - min) + 1) + min;

        return Color.argb(alpha,red, green, blue);
    }

    public static int changeColorAlpha(int color, int alpha)
    {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        return Color.argb(alpha,red, green, blue);
    }



}
