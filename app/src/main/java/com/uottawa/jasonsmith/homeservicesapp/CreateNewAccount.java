package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateNewAccount extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);
    }
    public void signUp(View view) {
        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        EditText usernameInput = (EditText) findViewById(R.id.createUsername);
        String usernameContent = usernameInput.getText().toString();
        EditText passwordInput = (EditText) findViewById(R.id.createPassword);
        String passwordContent = usernameInput.getText().toString();
        User tempUser = new User(usernameContent, "tempEmail",passwordContent);
        if (!usernameContent.equals("") && !passwordContent.equals("")){
            if (RegistrationInfo.selection){
                if (Admin.notFoundInUser(tempUser)){
                }
            }
            startActivityForResult(intent, 0);
        }
    }
}