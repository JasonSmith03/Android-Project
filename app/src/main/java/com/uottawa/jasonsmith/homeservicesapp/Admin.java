package com.uottawa.jasonsmith.homeservicesapp;

import android.app.Service;

import java.util.ArrayList;

public class Admin{

    private static ArrayList<Service> services;
    private static ArrayList<User> users;
    private static ArrayList<ServiceProvider> serviceProviders;
    private static final String username = "Silver Rivals";
    private static final String password = "admin1";

    //add to the user list
    public void addUser(User user){
        users.add(user);
    }

    //add to the service provider list
    public void addServiceProvider(ServiceProvider serviceProvider){
        serviceProviders.add(serviceProvider);
    }

    //check to see if the username exists
    public boolean notFoundInUser(User user){
        if (users.size() == 0){
            return true;
        }
        for (int i = 0; i < users.size(); i++){
            if(user.getUsername().equals(users.get(i).getUsername())){
                return false;
            }
        }
        return true;
    }

    public boolean notFoundInServiceProviders(ServiceProvider serviceProvider){
        if (serviceProviders.size() == 0){
            return true;
        }
        for (int i = 0; i < serviceProviders.size(); i++){
            if(serviceProvider.getUsername().equals(serviceProviders.get(i).getUsername())){
                return false;
            }
        }
        return true;
    }

    public static ArrayList<User> getUsers(){
        return users;
    }

    public static String getUsername(){
        return username;
    }

    public static String getPassword(){
        return password;
    }

    public void addService(Service service){
        services.add(service);
    }

}
