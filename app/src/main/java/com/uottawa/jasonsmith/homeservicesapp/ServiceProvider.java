package com.uottawa.jasonsmith.homeservicesapp;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ServiceProvider extends Person {

    private int sp_id, rating;
    private ArrayList<Timestamp> availabilities;
    private String companyName, phoneNumber, descrition;
    private boolean licensed;


    public ServiceProvider(String usernameContent, String passwordContent) {
        super(usernameContent, passwordContent);
    }

    public ServiceProvider() {
    }

    public ServiceProvider(String companyName, String phoneNumber, boolean licensed) {
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.licensed = licensed;
    }

    public ServiceProvider(int sp_id, String companyName, String phoneNumber, boolean licensed) {
        this.sp_id = sp_id;
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.licensed = licensed;
    }

    public void associateWithService() {
        //TODO code this method
    }

    //getters and setters needed in the case that a service provider wants to change their information.
    public int getServiceProviderID() {
        return sp_id;
    }

    public void setServiceProviderID(int sp_id) {
        this.sp_id = sp_id;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public boolean getLicensed() {
        return licensed;
    }

    public void setLicensed(boolean licensed) {
        this.licensed = licensed;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String toString() {
        return "Company name: " + getCompanyName() + "\n" + "Phone number: " + getPhoneNumber() + "\n" + "Licensed: " + getLicensed() + "\n" + "Rating: " + getRating();
    }
}