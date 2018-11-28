package com.uottawa.jasonsmith.homeservicesapp;

public class Availability {

    private int aid;
    private String time;

    //empty constructor
    public Availability(){}

    public void set_aid(int aid){this.aid = aid;}
    public int getAid(){ return aid; }

    public void setTime(String time){this.time = time;}
    public String getTime(){ return time; }
}
