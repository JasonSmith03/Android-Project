package com.uottawa.jasonsmith.homeservicesapp;

import java.sql.Timestamp;

public class Booking {

    private User user;
    private Service service;
    private Timestamp date;

    //User instance assigned to an instance of booking will have specified what service they want (service) and when (date)
    //Once service has occurred, User instance may give a rating to the service (setRating), stored in the Booking instance.
    public Booking(Timestamp date, User user, Service service){
        this.date = date;
        this.user = user;
        this.service = service;
    }

    public void setServiceRating(int rating) {
        service.addRating(rating);
    }

    public Timestamp getDate() {
        return date;
    }
    public User getUser() {
        return user;
    }
    public Service getService() {
        return service;
    }
}
