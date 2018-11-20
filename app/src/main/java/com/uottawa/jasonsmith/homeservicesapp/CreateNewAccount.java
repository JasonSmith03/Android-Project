package com.uottawa.jasonsmith.homeservicesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateNewAccount extends AppCompatActivity {

    Button signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        signUp = (Button) findViewById(R.id.SignUpBtn);

        signUpClick();
    }

    //add Person to database
    public void addPerson(String username, String password, String email, String homeAddress, int userType){
        //initiate new DatabaseHandler
        DatabaseHandler mDBHandler = new DatabaseHandler(this);

        boolean insertData = mDBHandler.addPerson(username, password, email, homeAddress, userType);
        if(insertData){

            String thisTest = "";
            if(userType == 1){thisTest = "Service Provider";}
            else{thisTest = "Home Owner";}

            //toastMessage("Person successfully added to Database , of user type " + thisTest);
            mDBHandler.findAllPeople();

            //int pkVal = mDBHandler.findLastPersonsPK();
            //toastMessage(Integer.toString(pkVal));
            //mDBHandler.findAllPeople();


            //TEST EDIT SERVICE
            //mDBHandler.findAllServices();
            //mDBHandler.editService("gate repair", 50.5);
            //mDBHandler.findAllServices();


            //TEST DELETE USER FROM DBHANDLER
            //boolean isDeleted = mDBHandler.deleteUser("nGardin98");
//            if(isDeleted){
//                toastMessage("user nGardin98 deleted");
//                mDBHandler.findAllPeople();
//            }
//            else{
//                toastMessage("Something went wring while deleting user");
//            }

        }
        else{
            toastMessage("Something went wrong while adding Person to Database");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void signUpClick(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Following lines takes currently typed text in appropriate fields and assigns them to variables.

                //Username field
                EditText usernameInput = (EditText) findViewById(R.id.createUsername);
                String usernameContent = usernameInput.getText().toString();

                //Password field
                EditText passwordInput = (EditText) findViewById(R.id.createPassword);
                String passwordContent = passwordInput.getText().toString();

                //Email field
                EditText emailInput = (EditText) findViewById(R.id.email);
                String emailContent = emailInput.getText().toString();

                //address field
                EditText addressInput = (EditText) findViewById(R.id.address);
                String addressContent = addressInput.getText().toString();

                if(usernameContent.length() != 0
                        & emailContent.length() != 0
                        & addressContent.length() != 0
                        & passwordContent.length() != 0){

                    Pattern p = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
                    Matcher m = p.matcher(emailContent);
                    if (m.find()){
                        if(RegistrationInfo.selection){
                            int userType = 2;
                            //User tempUser = new User(usernameContent, emailContent, passwordContent, addressContent);
                            //if (Admin.notFoundInUser(tempUser)){
                                Intent intent =  new Intent(getApplicationContext(), WelcomeScreen.class);
                                //adding to Admin's list
                                //Admin.addUser(tempUser);
                                //adding to Database
                                addPerson(  usernameContent,
                                        passwordContent,
                                        emailContent,
                                        addressContent,
                                        userType);
                                //go to the service provider information page
                                startActivityForResult(intent, 0);
                                usernameInput.setText("");
                                emailInput.setText("");
                                passwordInput.setText("");
                                addressInput.setText("");
                            //}else{
                            //    toastMessage("Username taken");
                            //}
                        }else{
                            int userType = 1;
                            //Person tempPerson = new Person(usernameContent, emailContent, passwordContent, addressContent);
                            //if (Admin.notFoundInServiceProviders(tempPerson)){
                                Intent intent = new Intent(getApplicationContext(), activity_SP_information.class);
                                //adding to Admin's list
                                //Admin.addServiceProvider(tempServiceProvider);
                                //adding to Database
                                addPerson(  usernameContent,
                                        passwordContent,
                                        emailContent,
                                        addressContent,
                                        userType);
                                //go to the service provider information page
                                startActivityForResult(intent, 0);
                                usernameInput.setText("");
                                emailInput.setText("");
                                passwordInput.setText("");
                                addressInput.setText("");
                            //}else{
                            //    toastMessage("Username taken");
                            //}
                        }
                    }else{
                        toastMessage("Email invalid");
                    }
//
//
//                    //adding to Database
//                    addPerson(  usernameContent,
//                            passwordContent,
//                            emailContent,
//                            addressContent);
//
//                    //go to the service provider information page
//                    startActivityForResult(intent, 0);
//
//                    usernameInput.setText("");
//                    emailInput.setText("");
//                    passwordInput.setText("");
//                    addressInput.setText("");

                } else{
                    toastMessage("All fields must be filled in");
                }
            }
        });
    }
}