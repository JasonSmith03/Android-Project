package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity {

    Admin admin = new Admin();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Intent usernameIntent = getIntent();

        String message1 = usernameIntent.getStringExtra("username");
        String message2 = usernameIntent.getStringExtra("role");

        TextView tview1 = (TextView)findViewById(R.id.displayUsername);
        TextView tview2 = (TextView)findViewById(R.id.displayRole);

        tview1.setText(message1);
        tview2.setText(message2);
    }

}
