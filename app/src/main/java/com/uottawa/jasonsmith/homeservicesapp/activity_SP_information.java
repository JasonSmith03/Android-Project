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
    EditText name, number, aLicense, description;
    boolean license = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__sp_information);

        done = (Button) findViewById(R.id.doneBtn);
        name = (EditText) findViewById(R.id.companyName);
        number = (EditText) findViewById(R.id.phoneNumber);
        aLicense = (EditText) findViewById(R.id.license);
        description = (EditText) findViewById(R.id.description);

        doneClick();
    }


    public void addServiceProvider(String companyName, String phoneNum, String license){

        DatabaseHandler mDBHandler = new DatabaseHandler(this);
        int fk = mDBHandler.findLastPersonsPK();

        boolean insertData = mDBHandler.addServiceProvider(fk, companyName, phoneNum, license);
        if(insertData) {
            toastMessage("Service Provider successfully added to Database");
        }else{  toastMessage("Something went wrong"); }

        mDBHandler.findAllServiceProviders();
    }



    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    private boolean isValidLicense(){
        if(aLicense.getText().toString().equalsIgnoreCase("yes") || aLicense.getText().toString().equalsIgnoreCase("no")){
            return true;
        }
        return false;
    }
    public void doneClick(){
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), activity_service_provider_interface.class);
                if(name.getText().toString().equals("") || number.getText().toString().equals("") || aLicense.getText().toString().equals("") || description.getText().toString().equals("")){
                    toastMessage("All fields must be full");
                }else{
                    if(isValidLicense() == false){
                        toastMessage("Invalid license");
                    }else{
                        //validate phone #
                        if(aLicense.getText().toString().equalsIgnoreCase("yes")){
                            license = true;
                        }
                        addServiceProvider(name.getText().toString(), number.getText().toString(), String.valueOf(license));

                        //go to the service provider interface
                        intent.putExtra("USERNAME", name.getText().toString()); //Display company name
                        startActivityForResult(intent, 0);
                    }

                }
            }
        });
    }
}
