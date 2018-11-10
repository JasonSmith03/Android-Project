package com.uottawa.jasonsmith.homeservicesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class admin_interface extends AppCompatActivity {

    //initiate database instance
    DatabaseHandler mDBHandler = new DatabaseHandler(this);

    EditText editText, initialRate;
    Button addBtn, removeBtn;
    ListView lv;
    ArrayList<Service> arrayList;
    ArrayAdapter<Service> arrayAdapter;
    double hourlyRate = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_interface);

        editText = (EditText) findViewById(R.id.service);
        initialRate = (EditText) findViewById(R.id.rate);
        addBtn = (Button) findViewById(R.id.addServiceBtn);
        removeBtn = (Button) findViewById(R.id.removeServiceBtn);
        lv = (ListView) findViewById(R.id.listViewServices);

        arrayList = mDBHandler.findAllServices();
        arrayAdapter = new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_multiple_choice, arrayList);
        lv.setAdapter(arrayAdapter);

        addServiceClick();
        removeServiceClick();
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
                //initiate database instance
                //DatabaseHandler mDBHandler = new DatabaseHandler(this);

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
                SparseBooleanArray positionChecker = lv.getCheckedItemPositions();
                int ctr = lv.getCount();
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

}
