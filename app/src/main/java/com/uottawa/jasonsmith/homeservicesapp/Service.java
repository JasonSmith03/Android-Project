package com.uottawa.jasonsmith.homeservicesapp;

import android.text.Editable;

public class Service {

    /**
     * @author Jason Smith
     * @author Joseph Peters
     */
    private String service;
    private double hourlyRate;
    private double averageRating = 0.0;
    private int numOfRatings = 0;

    public Service(){}

    public Service(String service, double hourlyRate){
        this.service = service;
        this.hourlyRate = hourlyRate;
    }

    public void setService(String service){this.service = service;}

    public void setHourlyRate(double hourlyRate){this.hourlyRate = hourlyRate;}

    public String getService(){
        return service;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void addRating(int rating){
        if(averageRating == 0.0){
            averageRating = rating;
            numOfRatings = 1;
        }
        else{
            double origRating = averageRating * numOfRatings;
            averageRating = ( (origRating + rating) / (numOfRatings + 1) );
            numOfRatings ++;
        }
    }

    public String toString(){
        return getService() + "\n" +  "$" + String.format("%.2f", getHourlyRate());
    }
}
