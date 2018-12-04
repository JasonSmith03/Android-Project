package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class sp_info extends AppCompatActivity {

    int queryValue = 0;
    EditText compName, phoneNum, licensed, descrip;
    DatabaseHandler mDBHandler = new DatabaseHandler(this);
    ServiceProvider serviceProvider;
    Button done;
    String strLicensed = "no";
    boolean boolLicensed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(R.drawable.appbackground);
        setContentView(R.layout.activity_sp_info);

        Intent serviceProviderIntent = getIntent();
        queryValue = serviceProviderIntent.getIntExtra("Query value", 0);

        compName = (EditText) findViewById(R.id.compName);
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        licensed = (EditText) findViewById(R.id.licensed);
        descrip = (EditText) findViewById(R.id.descrip);
        done = (Button) findViewById(R.id.doneBtn1);
        serviceProvider = mDBHandler.findSPInfo(queryValue);

        if(serviceProvider.getLicensed() == true){
            boolLicensed = true;
            strLicensed = "yes";
        }
        compName.setText(serviceProvider.getCompanyName());
        phoneNum.setText(serviceProvider.getPhoneNumber());
        licensed.setText(strLicensed);
        descrip.setText(serviceProvider.getDescrition());


        doneClick();
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void doneClick(){
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(strLicensed.equals("yes")){
                    mDBHandler.updateSP(queryValue, compName.getText().toString(), phoneNum.getText().toString(), boolLicensed, descrip.getText().toString());
                }
                else{
                    mDBHandler.updateSP(queryValue, compName.getText().toString(), phoneNum.getText().toString(), boolLicensed, descrip.getText().toString());
                }
                toastMessage("Account information updated");
                Intent serviceProviderIntent = new Intent(sp_info.this, activity_service_provider_interface.class);
                serviceProviderIntent.putExtra("Query value", queryValue);
                startActivity(serviceProviderIntent);
            }
        });
    }
}
