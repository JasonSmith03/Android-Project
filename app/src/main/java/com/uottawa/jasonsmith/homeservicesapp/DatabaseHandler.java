package com.uottawa.jasonsmith.homeservicesapp;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    //database Schema
    private static final int DATABASE_VERSION = 8;
    private static final String DATABASE_NAME = "projectDB4.db";

    //people table properties
    public static final String TABLE_NAME_PEOPLE = "People";
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_EMAIL = "email";
    public static final String COL_HOME_ADDRESS = "homeAddress";
    //type of user?

    //service table properties
    public static final String TABLE_NAME_SERVICES = "Services";
    public static final String COL_SID = "sid";
    public static final String COL_SERVICE_NAME = "serviceName";
    public static final String COL_SERVICE_RATE = "serviceHourlyRate";
    public static final String COL_SERVICE_RATING = "serviceUserRating";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create the table
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create People table
        String create_people_table = "CREATE TABLE " + TABLE_NAME_PEOPLE +
                "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USERNAME + " TEXT,"
                + COL_PASSWORD + " TEXT,"
                + COL_EMAIL + " TEXT,"
                + COL_HOME_ADDRESS + " TEXT"
                + ")";
        db.execSQL(create_people_table);

        //Create Service table
        String create_service_table = "CREATE TABLE " + TABLE_NAME_SERVICES +
                "("
                + COL_SID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_SERVICE_NAME + " TEXT,"
                + COL_SERVICE_RATE + " REAL,"
                + COL_SERVICE_RATING + " REAL"
                + ")";
        db.execSQL(create_service_table);
    }

    //to upgrade the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PEOPLE);
        onCreate(db);
    }

    //add a Person
    public boolean addPerson(String username, String password, String email, String homeAddr) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_HOME_ADDRESS, homeAddr);

        Log.d("myTag", "addUser: Adding " + username + " to " + TABLE_NAME_PEOPLE);
        long result = db.insert(TABLE_NAME_PEOPLE, null, contentValues);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Add service
    public boolean addService(String serviceName, double serviceRate) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SERVICE_NAME, serviceName);
        contentValues.put(COL_SERVICE_RATE, serviceRate);

        Log.d("myTag", "addService: Adding " + serviceName + " to " + TABLE_NAME_SERVICES);
        long result = db.insert(TABLE_NAME_SERVICES, null, contentValues);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //DELETE USER
    public boolean deleteUser(String username) {
        boolean result = false;

        String query = "Select * FROM " + TABLE_NAME_PEOPLE + " WHERE " +
                COL_USERNAME + " = \"" + username + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String idStr = cursor.getString(0);
            db.delete(TABLE_NAME_PEOPLE, COL_ID + " = " + idStr, null);
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    //DELETE USER
    public boolean deleteService(String serviceName) {
        boolean result = false;

        String query = "Select * FROM " + TABLE_NAME_SERVICES + " WHERE " +
                COL_SERVICE_NAME + " = \"" + serviceName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            String idStr = cursor.getString(0);
            db.delete(TABLE_NAME_SERVICES, COL_SID + " = " + idStr, null);
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public void editService(String serviceName, double newRate){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME_SERVICES+ " SET " + COL_SERVICE_RATE + " = '" +
                newRate + "' WHERE " + COL_SERVICE_NAME + " = '" + serviceName + "'";

        db.execSQL(query);
        Log.d("ServiceEdited", "New service rate:" + newRate);
    }



    //QUERY: FIND ALL PEOPLE
    public void findAllPeople() {
        SQLiteDatabase db = this.getWritableDatabase();

        //String query = "Select 'username' FROM " + TABLE_NAME + " WHERE " +
        //COL_USERNAME + " = \"" + name + "\"";
        String query = "Select * FROM " + TABLE_NAME_PEOPLE;
        Cursor cursor = db.rawQuery(query, null);

        Person person = new Person();
        if (cursor.moveToFirst()) {
            do {
                person.setUsername(cursor.getString(1));
                person.setPassword(cursor.getString(2));
                person.setEmail(cursor.getString(3));

                Log.d("---------", "-------------");
                Log.d("QueryResult", "Query returned: | username: "
                        + person.getUsername()
                        + " | email: " + person.getEmail()
                        + " | password: " + person.getPassword());
            }
            while (cursor.moveToNext());
        } else {
            person = null;
        }
        cursor.close();
        db.close();
    }


    //QUERY: FIND ALL SERVICES
    public ArrayList<Service> findAllServices() {

        ArrayList<Service> allServicesList = new ArrayList<Service>();
        SQLiteDatabase db = this.getWritableDatabase();

        //String query = "Select 'username' FROM " + TABLE_NAME + " WHERE " +
        //COL_USERNAME + " = \"" + name + "\"";
        String query = "Select * FROM " + TABLE_NAME_SERVICES;
        Cursor cursor = db.rawQuery(query, null);

        Service service;
        if (cursor.moveToFirst()) {
            do {
                service = new Service();
                service.setService(cursor.getString(1));
                service.setHourlyRate(Double.parseDouble(cursor.getString(2)));

                allServicesList.add(service);

                Log.d("---------", "-------------");
                Log.d("QueryResult", "Query returned: | service: "
                        + service.getService()
                        + " | hourly rate: " + service.getHourlyRate());
            }
            while (cursor.moveToNext());
        } else {
            service = null;
        }
        cursor.close();
        db.close();

        return allServicesList;
    }

}