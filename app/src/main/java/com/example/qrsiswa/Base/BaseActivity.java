package com.example.qrsiswa.Base;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "myprefs";

    public SharedPreferences.Editor setSecPref(){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedpreferences.edit();

        return editor;
    }

    public SharedPreferences getSecPref(){
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        return sharedpreferences;
    }
}
