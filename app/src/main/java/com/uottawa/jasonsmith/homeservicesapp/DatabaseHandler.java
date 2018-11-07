package com.uottawa.jasonsmith.homeservicesapp;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;

import android.util.Log;


public class DatabaseHandler extends SQLiteOpenHelper {

    //database Schema
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "projectDB.db";

    //users table properties
    public static final String TABLE_NAME = "users";
    public static final String COL_ID = "_id";
    public static final String COL_USERNAME = "username";
    //public static final String COL_PASSWORD = "password";
    //public static final String COL_EMAIL = "email";
    //type of user?


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    //create the table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE " + TABLE_NAME +
                "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USERNAME + " TEXT" +
                ")";

        db.execSQL(create_table);
    }


    //to upgrade the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addUser(String some_username) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, some_username);

        //add username and test if it was correctly added
        Log.d("myTag", "addUser: Adding " + some_username + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        if(result == -1){
            return false;
        } else{
            return true;
        }
    }


}

