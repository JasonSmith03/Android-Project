package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.WrapperListAdapter;

public class LogInPage extends AppCompatActivity {

    User tempUser;
    ServiceProvider tempServiceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void register(View view) {
        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), RegistrationInfo.class);
        startActivityForResult(intent, 0);
    }

    public void logIn(View view) {

            //Application Context and Activity

        Intent intent = new Intent(this, WelcomeScreen.class);
        //Username field
        EditText usernameInput = (EditText) findViewById(R.id.usernameText);
        String usernameContent = usernameInput.getText().toString();
        //Password field
        EditText passwordInput = (EditText) findViewById(R.id.PasswordText);
        String passwordContent = passwordInput.getText().toString();

        //Creates the temp accounts that will be used for verifying username/password combination
        tempUser = new User(usernameContent, passwordContent);
        tempServiceProvider = new ServiceProvider(usernameContent, passwordContent);

        //Checks if fields either field is left blank
        if(!usernameContent.equals("") && !passwordContent.equals("")){
            //Checks if username/password match Admin account
            if (usernameContent.equals(Admin.getUsername()) && passwordContent.equals(Admin.getPassword())) {
                //Admin is brought to admin interface
                Intent adminIntent = new Intent(this, admin_interface.class);
                startActivityForResult (adminIntent,0);
                return;
            }
            //Checks if username/password match a User account
            else if((Admin.passwordMatchUser(tempUser))){
                //Welcome page is prepared to display role and username of account
                intent.putExtra("username", tempUser.getUsername());
                intent.putExtra("role", "Home owner");
                startActivity(intent);
                return;
            }
            //Checks if username/password match a Service Provider account
            else if(Admin.passwordMatchSP(tempServiceProvider)){
                //Welcome page is prepared to display role and username of account
                Intent serviceProviderIntent = new Intent(this, activity_service_provider_interface.class);
                startActivityForResult(serviceProviderIntent, 0);
                return;
            }

            toastMessage("Username and password do not match");
            return;

        }
        toastMessage("Fields can't be left blank");
    }
}