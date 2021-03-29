package com.cenkkaraboa.kuponuygulamas.activitites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.cenkkaraboa.kuponuygulamas.R;
import com.google.android.material.card.MaterialCardView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean justDoFirst=preferences.getBoolean("justDoFirst",false);


        if(justDoFirst){
            boolean firstTime=preferences.getBoolean("firstTime",false);
            String ID=preferences.getString("ID","null");
            Intent intent;
            if(firstTime){
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }else {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
            }
            startActivity(intent);
        }else {
            Intent intent=new Intent(getApplicationContext(),IntroActivity.class);
            startActivity(intent);
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("justDoFirst", true);
            editor.apply();
        }
        finish();

    }
}