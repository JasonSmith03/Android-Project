package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.WrapperListAdapter;

public class LogInPage extends AppCompatActivity {

    Admin admin = new Admin();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_page);

    }

    public void register(View view) {
        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), RegistrationInfo.class);
        startActivityForResult(intent, 0);
    }

    public void logIn(View view) {
        //Application Context and Activity
        EditText usernameInput = (EditText) findViewById(R.id.usernameText);
        String usernameContent = usernameInput.getText().toString();
        EditText passwordInput = (EditText) findViewById(R.id.PasswordText);
        String passwordContent = passwordInput.getText().toString();

        if (usernameContent.equals(admin.getUsername()) && passwordContent.equals(admin.getPassword())) {
            //Application Context and Activity
            Intent intent = new Intent(this, WelcomeScreen.class);
            intent.putExtra("username", admin.getUsername());
            intent.putExtra("role", "Admin");
            startActivity(intent);
        }

    }
}
