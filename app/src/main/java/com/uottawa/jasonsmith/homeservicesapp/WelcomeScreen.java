package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeowner_interface);
    /*
        Intent usernameIntent = getIntent();

        String message1 = usernameIntent.getStringExtra("username");
        String message2 = usernameIntent.getStringExtra("role");

        TextView tview1 = (TextView)findViewById(R.id.displayUsername);
        TextView tview2 = (TextView)findViewById(R.id.displayRole);

        tview1.setText(message1);
        tview2.setText(message2);

     */

        logOut = (Button) findViewById(R.id.logOutBtn);
        editProfile = (Button) findViewById(R.id.editProfileBtn);

        logOutClick();
        editProfileClick();
    }
    Button editProfile, logOut, serviceSearch, timeSearch, ratingSearch;

    public void logOutClick(){
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInPage.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    public void editProfileClick(){
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ho_interface.class);
                startActivityForResult(intent, 0);
                //TODO
                //need a way to display companies previous information in the text boxes
                //get the data from the database
            }
        });
    }

}
