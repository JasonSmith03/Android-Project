package com.uottawa.jasonsmith.homeservicesapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class activity_service_provider_interface extends AppCompatActivity {
    //initiate database instance
    DatabaseHandler mDBHandler = new DatabaseHandler(this);
    Button editProfile, editService, availability, logOut;
    ArrayList<Service> arrayList;
    ArrayAdapter<Service> arrayAdapter;
    ListView serviceListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_interface);

        editProfile = (Button) findViewById(R.id.editProfileBtn);
        logOut = (Button) findViewById(R.id.logOutBtn);

//        arrayList = mDBHandler.findAllServices();
//        arrayAdapter = new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_multiple_choice, arrayList);
//        serviceListView.setAdapter(arrayAdapter);

        Intent usernameIntent = getIntent();

        String message1 = usernameIntent.getStringExtra("USERNAME");

        TextView tview1 = (TextView)findViewById(R.id.user);

        tview1.setText(message1);

        editProfileClick();
        //editServiceClick();
        //availabilityClick();
        logOutClick();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void editProfileClick(){
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_SP_information.class);
                startActivityForResult(intent, 0);
                //TODO
                //need a way to display companies previous information in the text boxes
                //get the data from the database
            }
        });
    }

//    public void editServiceClick(){
//        editService.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }
//
//    public void availabilityClick(){
//        availability.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//    }

    public void logOutClick(){
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInPage.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
