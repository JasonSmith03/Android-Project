package com.uottawa.jasonsmith.homeservicesapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class activity_service_provider_interface extends AppCompatActivity {
    //initiate database instance
    DatabaseHandler mDBHandler = new DatabaseHandler(this);
    Button editProfile, editService, availability, logOut, removeBtn, removeAvail;
    ArrayList<Service> arrayListViewServices = new ArrayList<Service>(), arrayListEditServices;
    ArrayList<String> tmpList = new ArrayList<String>(), availabilitiesEdit, availabilitiesView = new ArrayList<String>(), times;
    ArrayList<Integer> pkList;
    ArrayAdapter<Service> arrayAdapterView;
    ArrayAdapter<String> arrayAdapterAvailability;
    ListView lvViewServices, lvAvailability;
    int queryValue = 0, serviceID = 0;
    Service service = new Service();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_interface);

        Intent serviceProviderIntent = getIntent();
        queryValue = serviceProviderIntent.getIntExtra("Query value", 0);


        pkList = mDBHandler.findServicesFromPk(queryValue); //list of the ID of a service
        editProfile = (Button) findViewById(R.id.editProfileBtn);
        editService = (Button) findViewById(R.id.spEditServiceBtn);
        removeBtn = (Button) findViewById(R.id.spRemoveServie);
        logOut = (Button) findViewById(R.id.logOutBtn);
        removeAvail = (Button) findViewById(R.id.spRemoveAvail);
        availability = (Button) findViewById(R.id.spEditAvailBtn);
        lvViewServices = (ListView) findViewById(R.id.yourServices);
        lvAvailability = (ListView) findViewById(R.id.avail);


        //TESTING
        mDBHandler.findAllServices();
        mDBHandler.getIntermediateTable();


        //finds a service based off of the id from the pkList
        for(int i = 0; i < pkList.size(); i++){
            service = mDBHandler.findSpecificService(pkList.get(i));
            //adds the existing service provided
            arrayListViewServices.add(service);
        }

        times = mDBHandler.findAvailabilitiesFromPk(queryValue);
        for(int i = 0; i < times.size(); i++){
            availabilitiesView.add(times.get(i));
        }

        arrayListEditServices = mDBHandler.findAllServices();
        arrayAdapterView = new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_multiple_choice, arrayListViewServices);
        lvViewServices.setAdapter(arrayAdapterView);

        availabilitiesEdit = mDBHandler.findAllTimes();
        arrayAdapterAvailability = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, availabilitiesView);
        lvAvailability.setAdapter(arrayAdapterAvailability);

        lvViewServices.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        lvAvailability.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        editProfileClick();
        editServiceClick();
        removeServiceClick();
        availabilityClick();
        removeAvailabilityClick();
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

    public void editServiceClick(){
        editService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_service_provider_interface.this);
                builder.setTitle("Choose some services");
                // add a checkbox list
                final String[] stringArrayServices = new String[arrayListEditServices.size()];
                //tmpList = new ArrayList<String>();
                //arrayListViewServices = new ArrayList<Service>();

                boolean[] booleans = new boolean[arrayListEditServices.size()];
                for (int i = 0; i < arrayListEditServices.size(); i++){
                    stringArrayServices[i] = arrayListEditServices.get(i).toString();
                    booleans[i] = false;
                }
                Log.d("TEMPLIST", "String array services: " + stringArrayServices);
                builder.setMultiChoiceItems(stringArrayServices, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // user checked or unchecked a box
                        if (isChecked == true){
                            tmpList.add(stringArrayServices[which]);
                            Log.d("TEMPLIST", "temp list add: " + tmpList.toString());
                        }else{
                            tmpList.remove(stringArrayServices[which]);
                            Log.d("TEMPLIST", "temp list remove: " + tmpList.toString());
                        }
                    }
                });
                // add OK and Cancel buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user clicked OK
                        for(int i = 0; i < tmpList.size(); i++){
                            for(int j = 0; j < arrayListEditServices.size(); j++){

                                //get name of service selected from tmpList
                                String res = tmpList.get(i).replace("\n", " ");
                                res = res.replaceAll(" .+$", "");

                                for(int k = 0; k < tmpList.size(); k++) {
                                    Log.d("EXISTSVALUE", "If condition: " + String.valueOf(mDBHandler.alreadyExists(queryValue, mDBHandler.findID(res, "Services"))));
                                    Log.d("EXISTSVALUE", "Query value: " + String.valueOf(queryValue));
                                    if (mDBHandler.alreadyExists(queryValue, mDBHandler.findID(res, "Services"))) {
                                        if(tmpList.size() == 1){
                                            toastMessage("You are already subscribed to service " + res);
                                        }
                                        else{
                                            toastMessage("You have already subscribed to one or more of these services");
                                        }
                                        tmpList = new ArrayList<>();
                                        return;
                                    }
                                }

                                if (tmpList.get(i).equals(arrayListEditServices.get(j).toString())) {
                                    arrayListViewServices.add(arrayListEditServices.get(j));
                                    //
                                    serviceID = mDBHandler.findID(arrayListEditServices.get(j).getService(), "Services");
                                    //subscribe user to service
                                    mDBHandler.subscribeToService(queryValue, serviceID);
                                    arrayAdapterView.notifyDataSetChanged();
                                    Log.d("TEMPLIST", "If array list view is < 1 after added: " + arrayListViewServices.toString());
                                    break;
                                }
                            }
                        }
                        tmpList = new ArrayList<>();
                        Log.d("TEMPLIST", "Resetting temp list: " + tmpList.toString());
                    }
                });
                builder.setNegativeButton("Cancel", null);
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });
    }

    public void removeServiceClick(){
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray positionChecker = lvViewServices.getCheckedItemPositions();
                int ctr = lvViewServices.getCount();
                Log.d("TESTING", "ctr: " + ctr);

                for(int item = ctr - 1; item >= 0; item--){
                    if(positionChecker.get(item)){
                        Log.d("TESTING", "arrayListViewServices.get(item).getService(): " + arrayListViewServices.get(item).getService());
                        serviceID = mDBHandler.findID(arrayListViewServices.get(item).getService(), "Services");
                        mDBHandler.unsubscribeFromService(queryValue, serviceID);
                        arrayAdapterView.remove(arrayListViewServices.get(item));
                    }
                }
                positionChecker.clear();
                arrayAdapterView.notifyDataSetChanged();
            }
        });
    }

    public void availabilityClick(){
        availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(activity_service_provider_interface.this);
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

                                if (tmpList.get(i).equals(availabilitiesEdit.get(j))) {
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
                                }
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

    public void removeAvailabilityClick(){
        removeAvail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray positionChecker = lvAvailability.getCheckedItemPositions();
                int ctr = lvAvailability.getCount();
                for(int item = ctr - 1; item >= 0; item--){
                    if(positionChecker.get(item)){
                        Log.d("TESTING", "availabilitiesView.get(item): " + availabilitiesView.get(item));
                        mDBHandler.deleteAvailability(queryValue, availabilitiesView.get(item));
                        arrayAdapterAvailability.remove(availabilitiesView.get(item));
                    }
                }
                positionChecker.clear();
                arrayAdapterAvailability.notifyDataSetChanged();
            }
        });
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

}
