package com.uottawa.jasonsmith.homeservicesapp;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class admin_interface extends AppCompatActivity {

    //initiate database instance
    DatabaseHandler mDBHandler = new DatabaseHandler(this);

    EditText editText, initialRate;
    Button addBtn, removeBtn, editBtn, logOut;
    ListView lvServices, lvServiceProviders, lvUser;
    ArrayList<Service> arrayList;
    ArrayList<String> availabilities;
    ArrayList<ServiceProvider> arrayListServiceProvider;
    ArrayList<User> arrayListUser;
    ArrayAdapter<Service> arrayAdapter;
    ArrayAdapter<ServiceProvider> arrayAdapterServiceProvider;
    ArrayAdapter<User> arrayAdapterUser;
    double hourlyRate = 0.0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_interface);

        editText = (EditText) findViewById(R.id.service);
        initialRate = (EditText) findViewById(R.id.rate);
        addBtn = (Button) findViewById(R.id.addServiceBtn);
        removeBtn = (Button) findViewById(R.id.removeServiceBtn);
        editBtn = (Button) findViewById(R.id.editServiceBtn);
        logOut = (Button) findViewById(R.id.adminLogOut);
        lvServices = (ListView) findViewById(R.id.listViewServices);
        lvServiceProviders= (ListView) findViewById(R.id.serviceProviderList);
        lvUser = (ListView) findViewById(R.id.userClientList);
        availabilities = new ArrayList<String>();
        availabilities.add("Sunday: 10:00am - 3:00pm");
        availabilities.add("Monday: 9:00am - 5:00pm");
        availabilities.add("Tuesday: 9:00am - 5:00pm");
        availabilities.add("Wednesday: 9:00am - 5:00pm");
        availabilities.add("Thursday: 9:00am - 5:00pm");
        availabilities.add("Friday: 9:00am - 4:00pm");
        availabilities.add("Saturday: 10:00am - 3:00pm");

        arrayList = mDBHandler.findAllServices();
        arrayAdapter = new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_multiple_choice, arrayList);
        lvServices.setAdapter(arrayAdapter);

        arrayListServiceProvider = mDBHandler.findAllServiceProviders();
        arrayAdapterServiceProvider = new ArrayAdapter<ServiceProvider>(this, android.R.layout.simple_list_item_1, arrayListServiceProvider);
        lvServiceProviders.setAdapter(arrayAdapterServiceProvider);

//        arrayListUser = mDBHandler.finAllUsers();
//        arrayAdapterUser = new ArrayAdapter<User>(this, android.R.layout.simple_list_item_1, arrayListUser);
//        lvUser.setAdapter(arrayAdapterUser);

        //separates list view from scroll view (independent of each other)
        lvServices.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //separates list view from scroll view (independent of each other)
        lvServiceProviders.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        //separates list view from scroll view (independent of each other)
//        lvUser.setOnTouchListener(new View.OnTouchListener() {
//            // Setting on Touch Listener for handling the touch inside ScrollView
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // Disallow the touch request for parent scroll on touch of child view
//                v.getParent().requestDisallowInterceptTouchEvent(true);
//                return false;
//            }
//        });

        addServiceClick();
        removeServiceClick();
        editServiceClick();
        logOutClick();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    public void addServiceClick(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals("") || initialRate.getText().toString().equals("")) {
                    toastMessage("Fields cannot be blank");
                    return;
                }

                String result = editText.getText().toString();
                result = result.trim();

                try{
                    hourlyRate = Double.parseDouble(initialRate.getText().toString());
                }catch(NumberFormatException e){
                    toastMessage("Not a valid rate");
                    return;
                }

                Service service = new Service(result, hourlyRate);

                for (int i = 0; i < arrayList.size(); i++) {
                    if (arrayList.get(i).getService().equals(editText.getText().toString())) {
                        toastMessage("Service already exists");
                        return;
                    }
                }
                mDBHandler.addService(service.getService(), service.getHourlyRate());
                arrayList.add(service);
                arrayAdapter.notifyDataSetChanged();
                editText.setText("");
                initialRate.setText("");
            }
        });
    }

    public void removeServiceClick(){
        removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray positionChecker = lvServices.getCheckedItemPositions();
                int ctr = lvServices.getCount();
                for(int item = ctr - 1; item >= 0; item--){
                    if(positionChecker.get(item)){

                        mDBHandler.deleteService(arrayList.get(item).getService());
                        arrayAdapter.remove(arrayList.get(item));
                    }
                }

                positionChecker.clear();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    //Enters edit mode and allows the admin to edit the price of a service
    public void editServiceClick(){
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMessage("Entered edit mode");
                //change colour of button
                editBtn.setBackgroundResource(R.drawable.redroundbutton);
                lvServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    showInputBox(arrayList.get(position), position);
                    lvServices.clearChoices();
                    arrayAdapter.notifyDataSetChanged();
                    //returning to regular functionality
                    lvServices.setOnItemClickListener(null);
                    }
                });
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
    //shows dialog box where you can edit the price of a service
    public void showInputBox(final Service oldService, final int index){
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Edit Service");
        dialog.setContentView(R.layout.activity_dialog_box);
        TextView textView = (TextView) dialog.findViewById(R.id.textmessage);
        textView.setText("Update Service");
        textView.setTextColor(Color.parseColor("#ff2222"));
        final EditText editText1 = (EditText) dialog.findViewById(R.id.priceinput);
        editText1.setText(Double.toString(oldService.getHourlyRate()));
        Button bt = (Button) dialog.findViewById(R.id.btdone);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.set(index, new Service(oldService.getService(), Double.parseDouble(editText1.getText().toString())));
                //Updating the service into the database
                mDBHandler.editService(arrayList.get(index).getService(), arrayList.get(index).getHourlyRate());
                arrayAdapter.notifyDataSetChanged();
                dialog.dismiss();
                //change the colour of the button back to original
                editBtn.setBackgroundResource(R.drawable.roundbutton);
            }
        });
        dialog.show();
    }
}
