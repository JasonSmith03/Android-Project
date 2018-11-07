package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class CreateNewAccount extends AppCompatActivity {

    DatabaseHandler mDBHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

    }

    //add user to database
    public void add_User(String newUserEntry){
        Log.d("myTag", "add_User (" + newUserEntry + ") to database - call addUser from DatabaseHandler");
        boolean insertData = mDBHandler.addUser(newUserEntry);
        if(insertData){
            toastMessage("Username successfully inserted");
        }
        else{
            toastMessage("Something went wrong when trying to add Username to Database");
        }
    }


    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



    public void signUp(View view) {
        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), WelcomeScreen.class);
        //Following lines takes currently typed text in appropriate fields and assigns them to variables.

        //Username field
        EditText usernameInput = (EditText) findViewById(R.id.createUsername);
        String usernameContent = usernameInput.getText().toString();


        if(usernameInput.length() != 0){
            add_User(usernameContent);
            usernameInput.setText("");
        }
        else{
            toastMessage("Username must contain at least one character");
        }



        //Password field
        EditText passwordInput = (EditText) findViewById(R.id.createPassword);
        String passwordContent = passwordInput.getText().toString();
        //Email field
        EditText emailInput = (EditText) findViewById(R.id.email);
        String emailContent = emailInput.getText().toString();

        //Creates an instance of User that will be added to Admin's list if it satisfies the if statements.
        User tempUser = new User(usernameContent, emailContent, passwordContent);
        //Creates an instance of ServiceProvider that will be added to Admin's list if it satisfies the if statements.
        ServiceProvider tempServiceProvider = new ServiceProvider(usernameContent, emailContent, passwordContent);
        //If the username and/or password fields are blank, do nothing.
        if (!usernameContent.equals("") && !passwordContent.equals("")){
            //selection is true if the homeOwnerBtn was pressed, otherwise false.
            if (RegistrationInfo.selection){
                //Checks if the username of tempUser is found in Admin's list.
                if (Admin.notFoundInUser(tempUser)){
                    //Creates temporary String array where element 0 is the left of the @ symbol, and 1 is right.
                    //If no symbol exists in emailContent String, there will only be one element.
                    String[] email = emailContent.split("@", 2);
                    // Will do nothing if there is no @ symbol (array is length 1).
                    System.out.println(email[0]);
                    System.out.println(email[1]);
                    if (email.length==2){
                        //Creates temporary String array where element 0 is left of . symbol, and 1 is right.
                        //If no symbol exists in emailContent String, there will only be one element.
                        String[] emailRightSide = email[1].split("\\.",2);
                        // Will do nothing if there is no . after the @ (emailRightSide length 1).
                        System.out.println(emailRightSide[0]);
                        System.out.println(emailRightSide[1]);
                        if (emailRightSide.length == 2){
                                /* Will do nothing if there is a second @ symbol, there are no letters before the @,
                                there are no letters before ., or there are no letters after . */
                            if (!email[1].contains("@") && email[0].length()>0 && emailRightSide[0].length()>0 && emailRightSide[1].length()>0){
                                //tempUser is added to Admin's list.
                                Admin.addUser(tempUser);
                                //Welcome Screen is prepared to display the role and username of the created account.
                                intent.putExtra("username", tempUser.getUsername());
                                intent.putExtra("role", "Home Owner");
                                startActivityForResult(intent, 0);
                            }
                        }
                    }
                }
            }
            else{
                //Checks if the username of tempServiceProvider is found in Admin's list.
                if (Admin.notFoundInServiceProviders(tempServiceProvider)){
                    //tempServiceProvider is added to Admin's list.
                    Admin.addServiceProvider(tempServiceProvider);
                    //Welcome Screen is prepared to display the role and username of the created account.
                    intent.putExtra("username", tempServiceProvider.getUsername());
                    intent.putExtra("role", "Service Provider");
                    startActivityForResult(intent, 0);
                }
            }
        }
    }
}
