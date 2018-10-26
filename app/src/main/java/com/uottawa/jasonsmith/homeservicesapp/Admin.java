package com.uottawa.jasonsmith.homeservicesapp;

import android.app.Service;

import java.util.ArrayList;

public class Admin{

    private static ArrayList<Service> services = new ArrayList<Service>();
    private static ArrayList<User> users = new ArrayList<User>();;
    private static ArrayList<ServiceProvider> serviceProviders = new ArrayList<ServiceProvider>();
    private static final String username = "Silver Rivals";
    private static final String password = "admin1";

    //add to the user list
    public static void addUser(User user){
        users.add(user);
    }

    //add to the service provider list
    public static void addServiceProvider(ServiceProvider serviceProvider){
        serviceProviders.add(serviceProvider);
    }

    //check to see if the username exists
    public static boolean notFoundInUser(User user){
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

    public static boolean notFoundInServiceProviders(ServiceProvider serviceProvider){
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

    public boolean passwordMatchUser(User user){
        if (users.size() == 0){
            return false;
        }
        for (int i = 0; i < users.size(); i++){
            if(user.getUsername().equals(users.get(i).getUsername()) && user.getPassword().equals(users.get(i).getPassword())){
                return true;
            }
        }
        return false;
    }

    public boolean passwordMatchSP(ServiceProvider serviceProvider){
        if (serviceProviders.size() == 0){
            return false;
        }
        for (int i = 0; i < serviceProviders.size(); i++){
            if(serviceProvider.getUsername().equals(users.get(i).getUsername()) && serviceProvider.getPassword().equals(users.get(i).getPassword())){
                return true;
            }
        }
        return false;
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
