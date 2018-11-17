package com.uottawa.jasonsmith.homeservicesapp;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ServiceProvider extends Person {

    private ArrayList<Timestamp> availabilities;
    private String phoneNumber;
    private String companyName;
    private boolean licensed;


    public ServiceProvider(String usernameContent, String passwordContent) {
        super(usernameContent, passwordContent);
    }

    public ServiceProvider(){}


    public void associateWithService(){
        //TODO code this method
    }

    //getters and setters needed in the case that a service provider wants to change their information.
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