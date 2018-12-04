package com.uottawa.jasonsmith.homeservicesapp;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class homeowner_interface extends AppCompatActivity {
    //DATABASE STUFF STILL NEEDS TO BE ADDED
    DatabaseHandler mDBHandler = new DatabaseHandler(this);
    ArrayList<Service>  arrayListDBServices;
    ArrayList<String> arrayListServiceView = new ArrayList<String>(), arrayListSPView = new ArrayList<String>();
    ArrayList<ServiceProvider> arrayListDBSP;
    ArrayAdapter<String> arrayAdapterServices;
    ArrayAdapter<ServiceProvider> arrayAdapterSP;
    Button editProfile, logOut, serviceSearch, timeSearch, ratingSearch;
    ArrayList<String> tmpList = new ArrayList<String>(), availabilitiesDB;
    ListView companies, lvservices;
    EditText searchBar;
    int queryValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.appbackground);
        setContentView(R.layout.activity_homeowner_interface);

        Intent homeownerIntent = getIntent();
        queryValue = homeownerIntent.getIntExtra("Query value", 0);

        arrayListDBServices = mDBHandler.findAllServices();
        lvservices = (ListView) findViewById(R.id.possibleServices);
        logOut = (Button) findViewById(R.id.logOutBtn);
        editProfile = (Button) findViewById(R.id.editProfileBtn);

        for(int i = 0; i < arrayListDBServices.size(); i++) {
            arrayListServiceView.add(arrayListDBServices.get(i).toString());
        }
        arrayAdapterServices = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayListServiceView);
        lvservices.setAdapter(arrayAdapterServices);

        searchBar = (EditText) findViewById(R.id.filter);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (homeowner_interface.this).arrayAdapterServices.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //availabilitiesDB = mDBHandler.findAllTimes();
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
                Intent intent = new Intent(getApplicationContext(), ho_interface.class);
                startActivityForResult(intent, 0);
                //TODO
                //need a way to display companies previous information in the text boxes
                //get the data from the database
            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}






















