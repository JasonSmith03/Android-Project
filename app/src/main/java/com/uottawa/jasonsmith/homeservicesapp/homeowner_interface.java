package com.uottawa.jasonsmith.homeservicesapp;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class homeowner_interface extends AppCompatActivity {
    //DATABASE STUFF STILL NEEDS TO BE ADDED
    DatabaseHandler mDBHandler = new DatabaseHandler(this);
    ArrayList<Service>  arrayListEditServices;
    Button editProfile, logOut, serviceSearch, timeSearch, ratingSearch;
    ArrayList<String> tmpList = new ArrayList<String>(), availabilitiesEdit;
    int queryValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeowner_interface);

        Intent homeownerIntent = getIntent();
        queryValue = homeownerIntent.getIntExtra("Query value", 0);

        arrayListEditServices = mDBHandler.findAllServices();

        availabilitiesEdit = mDBHandler.findAllTimes();

        logOut = (Button) findViewById(R.id.logOutBtn);
        editProfile = (Button) findViewById(R.id.editProfileBtn);
        serviceSearch = (Button) findViewById(R.id.typeOfServiceBtn);
        timeSearch = (Button) findViewById(R.id.timeBtn);
        ratingSearch= (Button) findViewById(R.id.ratingBtn);

        logOutClick();
        editProfileClick();
        typeOfServiceClick();
        timeClick();
        //ratingClick();
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

    public void typeOfServiceClick(){
        serviceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(homeowner_interface.this);
                builder.setTitle("What do you need fixed?");
                // add a checkbox list
                final String[] stringArrayTypeService = new String[arrayListEditServices.size()];
                //tmpList = new ArrayList<String>();
                //arrayListViewServices = new ArrayList<Service>();

                boolean[] booleans = new boolean[arrayListEditServices.size()];
                for (int i = 0; i < arrayListEditServices.size(); i++){
                    stringArrayTypeService[i] = arrayListEditServices.get(i).toString();
                    booleans[i] = false;
                }
                Log.d("TEMPLIST", "String array services: " + stringArrayTypeService);
                builder.setMultiChoiceItems(stringArrayTypeService, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // user checked or unchecked a box
                        if (isChecked == true){
                            tmpList.add(stringArrayTypeService[which]);
                            Log.d("TESTING", "temp list add: " + tmpList.toString());
                        }else{
                            tmpList.remove(stringArrayTypeService[which]);
                            Log.d("TESTING", "temp list remove: " + tmpList.toString());
                        }
                    }
                });
                // add OK and Cancel buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user clicked OK
                        for(int i = 0; i < tmpList.size(); i++) {
                            for (int j = 0; j < arrayListEditServices.size(); j++) {
                                String res = tmpList.get(i);
                                for (int k = 0; k < tmpList.size(); k++) {
                                    if (mDBHandler.availAlreadyExists(queryValue, res)) {
                                        if (tmpList.size() == 1) {
                                            toastMessage("You are already provide a service on " + tmpList.get(k) + ".");
                                        } else {
                                            toastMessage("You have already provided one or more of these times.");
                                        }
                                        tmpList = new ArrayList<>();
                                        return;
                                    }
                                }

                                /*if (tmpList.get(i).equals(arrayListEditServices.get(j))) {
                                    availabilitiesView.add(arrayListEditServices.get(j));
                                    Log.d("TESTING1", "availabilitiesView: " + availabilitiesView);
                                    boolean add = mDBHandler.addAvailability(queryValue, arrayListEditServices.get(j));
                                    if(add){
                                        Log.d("TESTING1", "added to database");
                                    }
                                    Log.d("TESTING1", "availabilities.get(j)" + arrayListEditServices.get(j));
                                    Log.d("TESTING", "Table: " + mDBHandler.getAvailabilitiesTable());
                                    arrayAdapterAvailability.notifyDataSetChanged();
                                    break;
                                }*/
                            }
                        }
                        tmpList = new ArrayList<>();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    public void timeClick(){
        timeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(homeowner_interface.this);
                builder.setTitle("Choose time to provide service");
                // add a checkbox list
                final String[] stringArrayAvailability = new String[availabilitiesEdit.size()];
                //tmpList = new ArrayList<String>();
                //arrayListViewServices = new ArrayList<Service>();

                boolean[] booleans = new boolean[availabilitiesEdit.size()];
                for (int i = 0; i < availabilitiesEdit.size(); i++){
                    stringArrayAvailability[i] = availabilitiesEdit.get(i);
                    booleans[i] = false;
                }
                Log.d("TEMPLIST", "String array services: " + stringArrayAvailability);
                builder.setMultiChoiceItems(stringArrayAvailability, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // user checked or unchecked a box
                        if (isChecked == true){
                            tmpList.add(stringArrayAvailability[which]);
                            Log.d("TESTING", "temp list add: " + tmpList.toString());
                        }else{
                            tmpList.remove(stringArrayAvailability[which]);
                            Log.d("TESTING", "temp list remove: " + tmpList.toString());
                        }
                    }
                });
                // add OK and Cancel buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user clicked OK
                        for(int i = 0; i < tmpList.size(); i++) {
                            for (int j = 0; j < availabilitiesEdit.size(); j++) {
                                String res = tmpList.get(i);
                                for (int k = 0; k < tmpList.size(); k++) {
                                    if (mDBHandler.availAlreadyExists(queryValue, res)) {
                                        if (tmpList.size() == 1) {
                                            toastMessage("You are already provide a service on " + tmpList.get(k) + ".");
                                        } else {
                                            toastMessage("You have already provided one or more of these times.");
                                        }
                                        tmpList = new ArrayList<>();
                                        return;
                                    }
                                }

                                /*if (tmpList.get(i).equals(availabilitiesEdit.get(j))) {
                                    availabilitiesView.add(availabilitiesEdit.get(j));
                                    Log.d("TESTING1", "availabilitiesView: " + availabilitiesView);
                                    boolean add = mDBHandler.addAvailability(queryValue, availabilitiesEdit.get(j));
                                    if(add){
                                        Log.d("TESTING1", "added to database");
                                    }
                                    Log.d("TESTING1", "availabilities.get(j)" + availabilitiesEdit.get(j));
                                    Log.d("TESTING", "Table: " + mDBHandler.getAvailabilitiesTable());
                                    arrayAdapterAvailability.notifyDataSetChanged();
                                    break;
                                }*/
                            }
                        }
                        tmpList = new ArrayList<>();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}






















