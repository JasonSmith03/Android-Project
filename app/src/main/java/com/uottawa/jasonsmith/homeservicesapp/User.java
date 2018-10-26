package com.uottawa.jasonsmith.homeservicesapp;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class User extends Person {

    //In addition to storing the Person class information, a User instance also stores an address and a list of the current bookings
    //ATTENTION----------------bookings to be the current bookings or all bookings (history of bookings
    private String address;
    private ArrayList<Booking> bookings;
    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password) {
            super(username, email, password);
    }

    public User(String usernameContent, String passwordContent) {
        super(usernameContent, passwordContent);
    }

    public void bookService(Timestamp date, Service service) {
        bookings.add(new Booking(date, this, service));
    }


    public void rateService(int rating, Booking booking){
        booking.setServiceRating(rating);
    }

    public Service searchServices(String service){
        //PUT CODE HERE
        return new Service("TEST", 1.0);
    }
}
