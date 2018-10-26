package com.uottawa.jasonsmith.homeservicesapp;

public class Service {

    /**
     * @author Jason Smith
     * @author Joseph Peters
     */
    private String serviceType;
    private double hourlyRate;
    private double averageRating = 0.0;
    private int numOfRatings = 0;

    public Service(String serviceType, double hourlyRate){
        this.serviceType = serviceType;
        this.hourlyRate = hourlyRate;
    }

    public String getServiceType(){
        return serviceType;
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
            double orig_rating = averageRating * numOfRatings;
            averageRating = ( (orig_Rating + rating) / (numOfRatings + 1) );
            numOfRatings ++;
        }
    }
}
