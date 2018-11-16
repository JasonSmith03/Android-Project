package com.uottawa.jasonsmith.homeservicesapp;

import android.app.Dialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class admin_interface extends AppCompatActivity {

    //initiate database instance
    DatabaseHandler mDBHandler = new DatabaseHandler(this);

    EditText editText, initialRate;
    Button addBtn, removeBtn, editBtn;
    ListView lvServices, serviceProviders, homeOwners;
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
        editBtn = (Button) findViewById(R.id.editServiceBtn);
        lvServices = (ListView) findViewById(R.id.listViewServices);
        serviceProviders= (ListView) findViewById(R.id.serviceProviderList);
        homeOwners = (ListView) findViewById(R.id.userClientList);

        arrayList = mDBHandler.findAllServices();
        arrayAdapter = new ArrayAdapter<Service>(this, android.R.layout.simple_list_item_multiple_choice, arrayList);
        lvServices.setAdapter(arrayAdapter);

        addServiceClick();
        removeServiceClick();
        editServiceClick();
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
