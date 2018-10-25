package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class CreateNewAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
    }
    public void signUp(View view) {
        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        startActivityForResult(intent, 0);
    }
}
