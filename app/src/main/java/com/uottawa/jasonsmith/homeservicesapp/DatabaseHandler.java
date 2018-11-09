package com.uottawa.jasonsmith.homeservicesapp;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    //database Schema
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "projectDB.db";

    //users table properties
    public static final String TABLE_NAME = "People";
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_EMAIL = "email";
    public static final String COL_HOME_ADDRESS = "homeAddress";
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
                + COL_USERNAME + " TEXT,"
                + COL_PASSWORD + " TEXT,"
                + COL_EMAIL + " TEXT,"
                + COL_HOME_ADDRESS + " TEXT"
                + ")";
        db.execSQL(create_table);
    }

    //to upgrade the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addPerson(String username, String password, String email, String homeAddr) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD, password);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_HOME_ADDRESS, homeAddr);

        Log.d("myTag", "addUser: Adding " + username + " to " + TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        if(result == -1){
            return false;
        } else{
            return true;
        }
    }

    public void findAllPeople(){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select '*' FROM " + TABLE_NAME;
        Cursor result = db.rawQuery(query, null );

        Log.d("QueryResult", "Query returned: " + result);
    }

//    public void findAllPeople(String name) {
//
//        List<String> personList = new ArrayList<String>();
//
//        String query = "Select 'username' FROM " + TABLE_NAME + " WHERE " +
//                COL_USERNAME + " = \"" + name + "\"";
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery(query, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Person person = new Person();
//                person.setUsername(cursor.getString(1));
//                person.setEmail(cursor.getString(3));
//                person.setPassword(cursor.getString(2));
//                // Adding person to list
//                personList.add(name);
//            } while (cursor.moveToNext());
//        }
//
//        Log.d("myTag", "Users: " + personList);
//    }


}