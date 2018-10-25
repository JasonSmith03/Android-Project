package com.uottawa.jasonsmith.homeservicesapp;

import android.app.Service;

import java.util.ArrayList;

public class Admin{

    private ArrayList<Service> services;
    private static final String username = "Silver Rivals";
    private static final String password = "admin1";

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public void addService(Service service){
        services.add(service);
    }

}
