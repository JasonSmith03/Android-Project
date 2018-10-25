package com.uottawa.jasonsmith.homeservicesapp;

public class Service {

    /**
     * @author Jason Smith
     * @author Joseph Peters
     */
    private String serviceType;
    private double hourlyRate;
    private double averageRating;

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
        //TODO code this method

    }
}
