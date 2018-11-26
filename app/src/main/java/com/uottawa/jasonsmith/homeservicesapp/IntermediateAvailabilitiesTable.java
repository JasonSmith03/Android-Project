package com.uottawa.jasonsmith.homeservicesapp;

public class IntermediateAvailabilitiesTable {

    private int sp_id;
    private String date;

    //empty constructor
    public IntermediateAvailabilitiesTable(){}

    public void setSp_id(int sp_id){this.sp_id = sp_id;}
    public int getSP_id(){ return sp_id; }

    public void setDate(String date){this.date = date;}
    public String getDate(){ return date; }
}
