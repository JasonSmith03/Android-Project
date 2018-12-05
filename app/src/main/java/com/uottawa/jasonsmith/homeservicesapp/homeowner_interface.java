package com.uottawa.jasonsmith.homeservicesapp;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class homeowner_interface extends AppCompatActivity {
    //DATABASE STUFF STILL NEEDS TO BE ADDED
    DatabaseHandler mDBHandler = new DatabaseHandler(this);
    ArrayList<Service>  arrayListDBServices;
    ArrayList<ServiceProvider> arrayListDBSP;
    ArrayList<Service> arrayListServiceView = new ArrayList<Service>();
    ArrayList<ServiceProvider> arrayListSPView = new ArrayList<ServiceProvider>();
    ArrayList<ServiceProvider> arrayListSP = new ArrayList<ServiceProvider>(), arrayListTheSP = new ArrayList<ServiceProvider>();
    ArrayList<Integer> spID;
    ArrayAdapter<Service> arrayAdapterServices;
    ArrayAdapter<ServiceProvider> arrayAdapterSP;
    Button editProfile, logOut, serviceSearch, timeSearch, ratingSearch;
    ArrayList<String> tmpList = new ArrayList<String>(), availabilitiesDB;
    ListView companies, lvservices;
    EditText searchBar, spSearch;
    int queryValue = 0, numRating = 0;
    double rating = 0.0, currRating = 0.0;
    ServiceProvider serviceProvider = new ServiceProvider();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.appbackground);
        setContentView(R.layout.activity_homeowner_interface);

        Intent homeownerIntent = getIntent();
        queryValue = homeownerIntent.getIntExtra("Query value", 0);

        arrayListDBServices = mDBHandler.findAllServices();
        arrayListDBSP = mDBHandler.findAllServiceProviders();
        searchBar = (EditText) findViewById(R.id.filter);
        spSearch = (EditText) findViewById(R.id.spSearch);
        lvservices = (ListView) findViewById(R.id.possibleServices);
        companies = (ListView) findViewById(R.id.spListView);
        logOut = (Button) findViewById(R.id.logOutBtn);
        editProfile = (Button) findViewById(R.id.editProfileBtn);


        for(int i = 0; i < arrayListDBServices.size(); i++) {
            arrayListServiceView.add(arrayListDBServices.get(i));
        }
        arrayAdapterServices = new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_activated_1, arrayListServiceView);
        lvservices.setAdapter(arrayAdapterServices);

        for(int i = 0; i < arrayListDBSP.size(); i++){
            arrayListSPView.add(arrayListDBSP.get(i));
            Log.d("GETSPID", "FOR LOOP TESTING (70): " + arrayListSPView.get(i).getServiceProviderID());
        }
        Log.d("GETSPID", "arrayListSPView (71): " + arrayListSPView);
        arrayAdapterSP = new ArrayAdapter<ServiceProvider>(this, android.R.layout.simple_list_item_activated_1, arrayListSPView);
        companies.setAdapter(arrayAdapterSP);

        lvservices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(homeowner_interface.this);
                builder.setTitle("Related service provider(s)");
                // add a checkbox list
                //Get list of IDs of service provider associated with specific service
                spID = mDBHandler.findServiceProvidersFromSID(arrayListServiceView.get(position).getSid());
                for (int j = 0; j < spID.size(); j++){
                    serviceProvider = mDBHandler.findSpecificSP(spID.get(j));
                    arrayListSP.add(serviceProvider);
                }
                final String[] stringArraySP = new String[arrayListSP.size()];

                for (int i = 0; i < arrayListSP.size(); i++){
                    stringArraySP[i] = arrayListSP.get(i).toString();
                    //booleans[i] = false;
                }
                Log.d("TEMPLIST", "String array services: " + stringArraySP);
                builder.setItems(stringArraySP, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Display service providers
                        arrayListSP = new ArrayList<ServiceProvider>();
                        lvservices.clearChoices();
                        arrayAdapterServices.notifyDataSetChanged();
                    }
                });
                // add OK and Cancel buttons
                builder.setPositiveButton("GO BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // user clicked GO BACK
                        arrayListSP = new ArrayList<ServiceProvider>();
                        lvservices.clearChoices();
                        arrayAdapterServices.notifyDataSetChanged();
                    }
                });
                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
        });

        companies.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showInputBox(arrayListSPView.get(position).getID(), arrayListSPView.get(position).getRating());
                companies.clearChoices();
                arrayAdapterSP.notifyDataSetChanged();
            }
        });

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

        spSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (homeowner_interface.this).arrayAdapterSP.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //availabilitiesDB = mDBHandler.findAllTimes();
        logOutClick();
        editProfileClick();

        //removeSPClick();


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

    //shows dialog box where you can edit the price of a service
    public void showInputBox(final int id, final double theRating){
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Rate service provider");
        dialog.setContentView(R.layout.rating_dialog_box);
        TextView textView = (TextView) dialog.findViewById(R.id.atextmessage);
        textView.setText("Rate service provider");
        textView.setTextColor(Color.parseColor("#ff2222"));
        final EditText editText1 = (EditText) dialog.findViewById(R.id.arating);
        Button bt = (Button) dialog.findViewById(R.id.rate);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Log.d("GETSPID", "ID OF SP (213): " + id);
                   // currRating = mDBHandler.getRating(id);
                    currRating = theRating;
                    rating = Double.parseDouble(editText1.getText().toString());
                    numRating = mDBHandler.getNumOfRatings(id);
                    if(numRating == 0){
                        currRating = rating;
                        numRating = 1;
                        mDBHandler.updateNumRating(id, numRating);
                        mDBHandler.updateRating(id, currRating);
                    }
                    else{
                        double origRating = currRating * numRating;
                        currRating = ( (origRating + rating) / (numRating + 1) );
                        numRating ++;
                        mDBHandler.updateNumRating(id, numRating);
                        mDBHandler.updateRating(id, currRating);
                    }
                    Log.d("RATINGS", "numRating, rating, currRating: " + numRating + ", " + rating + ", " + currRating);
                    dialog.dismiss();
                }catch (NumberFormatException e){
                    toastMessage("Choose a valid rating (1 to 5)");
                    return;
                }
            }
        });
        dialog.show();
    }
}






















