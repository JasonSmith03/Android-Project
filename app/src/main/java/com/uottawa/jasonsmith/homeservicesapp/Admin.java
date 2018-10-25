package com.uottawa.jasonsmith.homeservicesapp;

import android.app.Service;

import java.util.ArrayList;

public class Admin{

    private String[] teamMembers = {"Jason Smith", "Joseph Peters", "Bradly Rose", "Nic Gardin"};
    private ArrayList<Service> services;
    private static final String username = "Silver Rivals";
    private static final String password = "admin1";

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String[] getTeamMembers(){
        return this.teamMembers;
    }

    public void addService(Service service){
        services.add(service);
    }

}
