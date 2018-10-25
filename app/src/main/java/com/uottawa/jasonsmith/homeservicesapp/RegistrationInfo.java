package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegistrationInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_info);
    }

    public void roleSelection(View view) {
    //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), CreateNewAccount.class);
        startActivityForResult (intent,0);
    }
}
