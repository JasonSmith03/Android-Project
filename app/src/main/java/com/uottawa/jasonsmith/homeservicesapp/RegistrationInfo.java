package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class RegistrationInfo extends AppCompatActivity {
    //indicates button selected
    public static boolean selection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.appbackground);
        setContentView(R.layout.activity_registration_info);
    }
    public void homeOwnerSelected(View view){
        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), CreateNewAccount.class);
        selection = true;
        startActivityForResult (intent,0);
    }
    public void serviceProviderSelected(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateNewAccount.class);
        selection = false;
        startActivityForResult (intent,0);
    }

}
