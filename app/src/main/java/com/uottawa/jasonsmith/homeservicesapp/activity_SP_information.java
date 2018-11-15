package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class activity_SP_information extends AppCompatActivity {

    Button done;
    EditText name, number, address, aLicense, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__sp_information);

        done = (Button) findViewById(R.id.doneBtn);
        name = (EditText) findViewById(R.id.companyName);
        number = (EditText) findViewById(R.id.phoneNumber);
        address = (EditText) findViewById(R.id.address);
        aLicense = (EditText) findViewById(R.id.license);
        description = (EditText) findViewById(R.id.description);

        doneClick();
    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void doneClick(){
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doneText = done.getText().toString();
                String nameText = name.getText().toString();
                String phoneNum = number.getText().toString();
                String addressText = address.getText().toString();
                String licensed = aLicense.getText().toString();
                String descriptionText = description.getText().toString();
                //add all these values to the database

                //make sure all fields are full in the information page
                if(doneText.equals("") || nameText.equals("") || phoneNum.equals("") || addressText.equals("") || licensed.equals("") || descriptionText.equals("")){
                    toastMessage("All fields must be full");
                }else{
                    //go to the service provider interface
                    Intent intent = new Intent(getApplicationContext(), activity_service_provider_interface.class);
                    startActivityForResult(intent, 0);
                }
            }
        });
    }
}
