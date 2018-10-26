package com.uottawa.jasonsmith.homeservicesapp;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ServiceProvider extends Person {

    private ArrayList<Timestamp> availabilities;
    private String username;
    private String password;
    private String email;

    public ServiceProvider(String username, String email, String password) {
        super(username, email, password);
    }

    public void associateWithService(){
        //TODO code this method
        
    }
}
