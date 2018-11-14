package com.uottawa.jasonsmith.homeservicesapp;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ServiceProvider extends Person {

    private ArrayList<Timestamp> availabilities;
    private String username;
    private String password;
    private String address;
    private String email;
    private String phoneNumber;
    private String companyName;
    private boolean licensed;


    public ServiceProvider(String username, String email, String address, String password) {
        super(username, email, address, password);
    }

    public ServiceProvider(String usernameContent, String passwordContent) {
        super(usernameContent, passwordContent);
    }

    public void associateWithService(){
        //TODO code this method
    }

    //getters and setters needed in the case that a service provider wants to change their information.
    public String getUsername(){
        return this.username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public String getEmail(){
        return this.email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName(){
        return this.companyName;
    }
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    public boolean getLicensed(){
        return licensed;
    }
    public void setLicensed(boolean licensed){
        this.licensed = licensed;
    }
}