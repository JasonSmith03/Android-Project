package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ho_interface extends AppCompatActivity {
    Button done;
    EditText address;
    String STD, virus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.appbackground);
        setContentView(R.layout.activity_ho_interface);

        done = (Button) findViewById(R.id.doneBtn);

        address = (EditText) findViewById(R.id.address);

        doneClick();
    }

    public void doneClick(){
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), homeowner_interface.class);
                if(address.getText().toString().equals("")){
                    //this keeps homeowner's address the same
                    startActivityForResult(intent, 0);
                }else{
                    //validating address
                    if(!address.getText().toString().matches("\\d+\\s+([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)")){
                        toastMessage("Invalid address");
                    }else{
                        addHomeowner(address.getText().toString());

                        //go to the homeowner interface
                        startActivityForResult(intent, 0);

                    }

                }
            }
        });
    }

    //THIS NEEDS TO BE DONE BY JASON AND NIC
    public void addHomeowner(String address){
    /*
        DatabaseHandler mDBHandler = new DatabaseHandler(this);
        int fk = mDBHandler.findLastPersonsPK();

        boolean insertData = mDBHandler.addServiceProvider(fk, companyName, phoneNum, license);
        if(insertData) {
            toastMessage("Service Provider successfully added to Database");
        }else{  toastMessage("Something went wrong"); }
     */
    }


    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
