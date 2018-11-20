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
    Button editProfile, editService, availability, logOut, removeBtn;
    ArrayList<Service> arrayListViewServices = new ArrayList<Service>(), arrayListEditServices;
    ArrayList<String> tmpList = new ArrayList<String>();
    ArrayAdapter<Service> arrayAdapterView, arrayAdapterEditServices;
    ListView lvViewServices, lvEditServices;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider_interface);

//        Intent usernameIntent = getIntent();
//
//        String message1 = usernameIntent.getStringExtra("USERNAME");
//
//        TextView tview1 = (TextView)findViewById(R.id.companyName);
//
//        tview1.setText(message1);

        editProfile = (Button) findViewById(R.id.editProfileBtn);
        editService = (Button) findViewById(R.id.spEditServiceBtn);
        removeBtn = (Button) findViewById(R.id.spRemoveServie);
        logOut = (Button) findViewById(R.id.logOutBtn);
        lvViewServices = (ListView) findViewById(R.id.yourServices);

        arrayListEditServices = mDBHandler.findAllServices();
        arrayAdapterView = new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_multiple_choice, arrayListViewServices);
        lvViewServices.setAdapter(arrayAdapterView);


        lvViewServices.setOnTouchListener(new View.OnTouchListener() {
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
                    stringArrayServices[i] = arrayListEditServices.get(i).getService();
                    booleans[i] = false;
                }
                builder.setMultiChoiceItems(stringArrayServices, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        // user checked or unchecked a box
                        if (isChecked == true){
                            tmpList.add(stringArrayServices[which]);
                        }else{
                            tmpList.remove(stringArrayServices[which]);
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
                                if(arrayListViewServices.size() < 1){
                                    if(tmpList.get(i).equals(arrayListEditServices.get(j).getService())){
                                        arrayListViewServices.add(arrayListEditServices.get(j));
                                        arrayAdapterView.notifyDataSetChanged();
                                    }
                                }else if(tmpList.get(i).equals(arrayListEditServices.get(j).getService()) && !(arrayListViewServices.contains(arrayListEditServices.get(j)))){
                                    arrayListViewServices.add(arrayListEditServices.get(j));
                                    arrayAdapterView.notifyDataSetChanged();
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

    public void removeServiceClick(){
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray positionChecker = lvViewServices.getCheckedItemPositions();
                int ctr = lvViewServices.getCount();
                for(int item = ctr - 1; item >= 0; item--){
                    if(positionChecker.get(item)){
                        arrayAdapterView.remove(arrayListViewServices.get(item));
                    }
                }
                positionChecker.clear();
                arrayAdapterView.notifyDataSetChanged();
            }
        });
    }
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
