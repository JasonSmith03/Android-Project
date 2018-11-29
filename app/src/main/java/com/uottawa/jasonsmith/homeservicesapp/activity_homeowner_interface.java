package com.uottawa.jasonsmith.homeservicesapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class activity_homeowner_interface extends AppCompatActivity {
    //DATABASE STUFF STILL NEEDS TO BE ADDED

    Button editProfile, logOut, serviceSearch, timeSearch, ratingSearch;
    int queryValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeowner_interface);

        Intent homeownerIntent = getIntent();
        queryValue = homeownerIntent.getIntExtra("Query value", 0);

        logOut = (Button) findViewById(R.id.logOutBtn);
        editProfile = (Button) findViewById(R.id.editProfileBtn);

        logOutClick();
        editProfileClick();
    }

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
                Intent intent = new Intent(getApplicationContext(), activity_ho_information.class);
                startActivityForResult(intent, 0);
                //TODO
                //need a way to display companies previous information in the text boxes
                //get the data from the database
            }
        });
    }
}
