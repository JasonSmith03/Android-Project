package com.uottawa.jasonsmith.homeservicesapp;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {

    BCrypt bycrypt_hash = new BCrypt();
    String hashed_password;
    String salt;

    //database Schema
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "projectDB64.db";


    //PEOPLE
    public static final String TABLE_NAME_PEOPLE = "People";
    public static final String COL_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD_HASH = "password_hash";
    public static final String COL_SALT = "salt";
    public static final String COL_EMAIL = "email";
    public static final String COL_ADDRESS = "address";
    public static final String COL_PERSON_TYPE = "personType";  //if 1 then SP, if 2 then HomeOwner


    //SERVICE PROVIDER
    public static final String TABLE_NAME_SERVICE_PROVIDERS = "serviceProviders";
    public static final String COL_SERVICE_PROVIDER_ID = "serviceProviderID";
    public static final String COL_COMP_NAME = "companyName";
    public static final String COL_PHONE_NUM = "phoneNumber";
    public static final String COL_LICENSE = "license";
    public static final String COL_DESCRIPTION = "description";


    //HOME OWNER
    public static final String TABLE_NAME_HOME_OWNERS = "homeOwners";
    public static final String COL_HOME_OWNER_ID = "homeOwnerID";

    //SERVICES
    public static final String TABLE_NAME_SERVICES = "Services";
    public static final String COL_SID = "sid";
    public static final String COL_SERVICE_NAME = "serviceName";
    public static final String COL_SERVICE_RATE = "serviceHourlyRate";

    //AVAILABILITIES
    public static final String TABLE_NAME_AVAILABILITIES = "Availabilities";
    public static final String COL_AID = "aid";
    public static final String COL_AVAILABILITY = "date";

    //INTERMEDIATE TABLE - links Service Provider ID to a Service ID
    public static final String TABLE_NAME_INTER_SID = "serviceAndProvider";
    public static final String COL_SP_ID = "serviceProviderID";
    public static final String COL_SERVICE_ID = "serviceID";

    //INTERMEDIATE TABLE - links Service Provider and their availibilities
    public static final String TABLE_NAME_INTER_AVAILABILITIES = "availabilitiesList";
    public static final String COL_SP_IDENTIFIER = "serviceProId";
    public static final String COL_TIME = "time";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //CREATE RELATIONS
    @Override
    public void onCreate(SQLiteDatabase db) {

        //Create People table
        String create_people_table = "CREATE TABLE " + TABLE_NAME_PEOPLE +
                "("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_USERNAME + " TEXT,"
                + COL_PASSWORD_HASH + " TEXT,"
                + COL_SALT + " TEXT,"
                + COL_EMAIL + " TEXT,"
                + COL_ADDRESS + " TEXT,"
                + COL_PERSON_TYPE + " INTEGER"
                + ")";
        db.execSQL(create_people_table);

        //Create ServiceProvider table ADD AVAILABILITY AND DESCRIPTION AND SET FK
        String create_serProvider_table = "CREATE TABLE " + TABLE_NAME_SERVICE_PROVIDERS +
                "("
                + COL_SERVICE_PROVIDER_ID + " INTEGER," //foreign key of type person
                + COL_COMP_NAME + " TEXT,"
                + COL_PHONE_NUM + " TEXT,"
                + COL_LICENSE + " TEXT,"
                + COL_DESCRIPTION + " TEXT"
                + ")";
        db.execSQL(create_serProvider_table);

        //Create home owner table

        //Create Service table
        String create_service_table = "CREATE TABLE " + TABLE_NAME_SERVICES +
                "("
                + COL_SID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_SERVICE_NAME + " TEXT,"
                + COL_SERVICE_RATE + " REAL"
                + ")";
        db.execSQL(create_service_table);

        //Create Availability table
        String create_avail_table = "CREATE TABLE " + TABLE_NAME_AVAILABILITIES +
                "("
                + COL_AID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COL_AVAILABILITY + " TEXT"
                + ")";
        db.execSQL(create_avail_table);

        //Create intermediate table subscribe
        String create_intermediate_table = "CREATE TABLE " + TABLE_NAME_INTER_SID +
                "("
                + COL_SP_ID + " TEXT,"
                + COL_SERVICE_ID + " TEXT"
                + ")";
        db.execSQL(create_intermediate_table);

        //Create intermediate table availabilities
        String create_inter_avail_table = "CREATE TABLE " + TABLE_NAME_INTER_AVAILABILITIES +
                "("
                + COL_SP_IDENTIFIER + " TEXT,"
                + COL_TIME + " TEXT"
                + ")";
        db.execSQL(create_inter_avail_table);
    }

    //to upgrade the table
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PEOPLE);
        onCreate(db);
    }




    //-------- ADD TO DB ----------------------------------------------------

    //Add a Person
    public boolean addPerson(String username, String password, String email, String address, int userType) {

        salt = bycrypt_hash.gensalt();
        hashed_password = BCrypt.hashpw(password, salt);

        Log.d("genSalt", "Salt value: " + salt);

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_USERNAME, username);
        contentValues.put(COL_PASSWORD_HASH, hashed_password);
        contentValues.put(COL_SALT, salt);
        contentValues.put(COL_EMAIL, email);
        contentValues.put(COL_ADDRESS, address);
        contentValues.put(COL_PERSON_TYPE, userType);

        Log.d("myTag", "addUser: Adding " + username + " to " + TABLE_NAME_PEOPLE +
                    "of type: " + userType);
        long result = db.insert(TABLE_NAME_PEOPLE, null, contentValues);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    //add a Service Provider
    public boolean addServiceProvider(int fk, String companyName, String phoneNum, String license, String description) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SERVICE_PROVIDER_ID, fk);
        contentValues.put(COL_COMP_NAME, companyName);
        contentValues.put(COL_PHONE_NUM, phoneNum);
        contentValues.put(COL_LICENSE, license);
        contentValues.put(COL_DESCRIPTION, description);

        Log.d("Service Provider", "Adding SP: " + companyName + " with ID " + fk);
        long result = db.insert(TABLE_NAME_SERVICE_PROVIDERS, null, contentValues);
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

    //Associate SP with service
    public boolean subscribeToService(int sp_id, int service_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SERVICE_PROVIDER_ID, sp_id);
        contentValues.put(COL_SERVICE_ID, service_id);

        long result = db.insert(TABLE_NAME_INTER_SID, null, contentValues);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    //Add an availability
    public boolean addAvailability(int sp_id, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        Log.d("TESTING1", "contentValues.put: ");
        contentValues.put(COL_SP_IDENTIFIER, sp_id);
        contentValues.put(COL_TIME, time);

        long result = db.insert(TABLE_NAME_INTER_AVAILABILITIES, null, contentValues);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Add a time
    public boolean addTime(String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_AVAILABILITY, time);

        long result = db.insert(TABLE_NAME_AVAILABILITIES, null, contentValues);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }




    //-------- DELETE FROM DB ----------------------------------------------------


    //Unassociated SP with service
    public void unsubscribeFromService(int sp_id, int service_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_INTER_SID + " WHERE " +
                COL_SP_ID + " = \"" + sp_id + "\"";

        Cursor cursor = db.rawQuery(query, null);
        IntermediateTable inter = new IntermediateTable();
        if (cursor.moveToFirst()) {
            int serviceID = Integer.parseInt(cursor.getString(1));
            db.delete(TABLE_NAME_INTER_SID, COL_SERVICE_ID + " = " + service_id, null);
        }
        cursor.close();
        db.close();
    }

    //Delete a users availability
    public void deleteAvailability(int sp_id, String time) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME_INTER_AVAILABILITIES + " WHERE " +
                COL_SP_IDENTIFIER + " = \"" + sp_id + "\"";

        Cursor cursor = db.rawQuery(query, null);
        IntermediateAvailabilitiesTable inter = new IntermediateAvailabilitiesTable();
        if (cursor.moveToFirst()) {
            db.delete(TABLE_NAME_INTER_AVAILABILITIES, COL_TIME + " = '" + time + "'", null);
        }
        cursor.close();
        db.close();
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

    //DELETE SERVICE
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



    //DELETE REMOVED SERVICE
    public boolean deleteRemovedService(int pk) {
        boolean result = false;

        String query = "Select * FROM " + TABLE_NAME_INTER_SID + " WHERE " +
                COL_SERVICE_ID + " = \"" + pk + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            db.delete(TABLE_NAME_INTER_SID, COL_SERVICE_ID + " = " + pk, null);
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }


    //FIND USER TYPE
    public int getUserType(int pk) {
        boolean result = false;

        String query = "Select * FROM " + TABLE_NAME_PEOPLE + " WHERE " +
                COL_PERSON_TYPE+ " = \"" + pk + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Person person = new Person();
        if (cursor.moveToFirst()) {
            int userType;
            person.setPersonType(Integer.parseInt(cursor.getString(6)));
            cursor.close();
            return person.getUserType();
        }
        db.close();
        return -1;
    }








    //-------- QUERIES ----------------------------------------------------

    //QUERY: Get login information
    public int findLoginInfo(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query = "Select * FROM " + TABLE_NAME_PEOPLE + " WHERE " +
                COL_USERNAME + " = \"" + name + "\"" + " AND " +
                COL_PASSWORD_HASH + " = \"" + password + "\"";

        Cursor cursor = db.rawQuery(query, null);

        Person person = new Person();
            if (cursor.moveToFirst()) {
                Log.d("PersonFound", "Person found");
            person.setID(Integer.parseInt(cursor.getString(0)));
            person.setUsername(cursor.getString(1));
            person.setPassword(cursor.getString(2));

            Log.d("PersonFound", "***PERSON FOUND***");
            cursor.close();
            db.close();
            return person.getID();
        } else {
            return -1;
        }
    }


    //QUERY: Find a salt from login value
    public String findLoginSalt(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        query = "Select * FROM " + TABLE_NAME_PEOPLE + " WHERE " +
                COL_USERNAME + " = \"" + name + "\"";

        Cursor cursor = db.rawQuery(query, null);

        Person person = new Person();
        if (cursor.moveToFirst()) {
            person.setSalt((cursor.getString(3)));
            cursor.close();
            db.close();
            return person.getSalt();
        } else {
            return "";
        }
    }



    //QUERY: FIND A SP
    public ServiceProvider findSPInfo(int pk) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_SERVICE_PROVIDERS + " WHERE " +
                COL_SERVICE_PROVIDER_ID + " = \"" + pk + "\"";;
        Cursor cursor = db.rawQuery(query, null);

        ServiceProvider sp;
        if (cursor.moveToFirst()) {
            sp = new ServiceProvider();
            sp.setID(Integer.parseInt(cursor.getString(0)));
            sp.setCompanyName(cursor.getString(1));
            sp.setPhoneNumber(cursor.getString(2));
            sp.setLicensed(Boolean.parseBoolean(cursor.getString(3)));
            sp.setDescrition(cursor.getString(4));
            return sp;
        } else {
            sp = null;
        }
        cursor.close();
        db.close();
        return sp;
    }



    //QUERY: Find Primary key (ID) for Service and SP
    public int findID(String name, String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query;

        if (table.equals("Services")) {
            query = "Select sid FROM " + TABLE_NAME_SERVICES + " WHERE " +
                    COL_SERVICE_NAME + " = \"" + name + "\"";

            //query = "SELECT sid FROM Services WHERE serviceName = 'Deck Repair'";
            Cursor cursor = db.rawQuery(query, null);

            Service service = new Service();
            if (cursor.moveToFirst()) {
                service.setSid(Integer.parseInt(cursor.getString(0)));
                Log.d("RECEIVED", "Service ID " + service.getSid());
            } else {
                service = null;
            }
            cursor.close();
            db.close();
            return service.getSid();
        }

        else if(table.equals("ServiceProviders")) {
            query = "Select serviceProviderID FROM " + TABLE_NAME_SERVICE_PROVIDERS + " WHERE " +
                    COL_COMP_NAME +  " = \"" + name + "\"";

            Cursor cursor = db.rawQuery(query, null);
            ServiceProvider sp = new ServiceProvider();;
            if (cursor.moveToFirst()) {
                sp.setServiceProviderID(Integer.parseInt(cursor.getString(0)));
                Log.d("RECEIVED", "Ser Provider ID " + sp.getServiceProviderID());
            } else {
                sp = null;
            }
            cursor.close();
            db.close();
            return sp.getServiceProviderID();
        }
        else{
            //should never enter else
            return 0;
        }
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
                person.setID(Integer.parseInt(cursor.getString(0)));
                person.setUsername(cursor.getString(1));
                person.setPassword(cursor.getString(2));
                person.setEmail(cursor.getString(4));
                person.setPersonType(Integer.parseInt(cursor.getString(6)));

                Log.d("---------", "-------------");
                Log.d("QueryResult", "Query returned: "
                        + " ID: " + person.getID()
                        + " | username: " + person.getUsername()
                        + " | email: " + person.getEmail()
                        + " | password: " + person.getPassword()
                        + " | userType: " + person.getUserType());
            }
            while (cursor.moveToNext());
        } else {
            person = null;
        }
        cursor.close();
        db.close();
    }


    //QUERY: FIND ALL TIMES
    public ArrayList<String> findAllTimes() {

        ArrayList<String> allAvailList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_AVAILABILITIES;
        Cursor cursor = db.rawQuery(query, null);

        Availability avail;
        if (cursor.moveToFirst()) {
            do {
                avail = new Availability();
                avail.setTime(cursor.getString(1));
                allAvailList.add(avail.getTime());
            }
            while (cursor.moveToNext());
        } else {
            avail = null;
        }
        cursor.close();
        db.close();
        return allAvailList;
    }


    //UPDATE SP
    public void updateSP(int pk, String sp_name, String phone_num, boolean licensed, String description){

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME_SERVICE_PROVIDERS + " " +
                "SET " + COL_COMP_NAME + " = '" + sp_name + "', " +
                COL_PHONE_NUM + " = '" + phone_num + "', " +
                COL_LICENSE + " = '" + licensed + "', " +
                COL_DESCRIPTION + " = '" + description + "' WHERE " + COL_SERVICE_PROVIDER_ID + " = '" + pk + "'";
        db.execSQL(query);

//        String query1 = "UPDATE " + TABLE_NAME_SERVICE_PROVIDERS + " SET " + COL_COMP_NAME + " = '" +
//                sp_name + "' WHERE " + COL_SERVICE_PROVIDER_ID + " = '" + pk + "'";
//        db.execSQL(query1);
//
//        String query2 = "UPDATE " + TABLE_NAME_SERVICE_PROVIDERS + " SET " + COL_PHONE_NUM + " = '" +
//                phone_num + "' WHERE " + COL_SERVICE_PROVIDER_ID + " = '" + pk + "'";
//        db.execSQL(query2);
//
//        String query3 = "UPDATE " + TABLE_NAME_SERVICE_PROVIDERS + " SET " + COL_LICENSE + " = '" +
//                licensed + "' WHERE " + COL_SERVICE_PROVIDER_ID + " = '" + pk + "'";
//        db.execSQL(query3);
//
//        String query4 = "UPDATE " + TABLE_NAME_SERVICE_PROVIDERS + " SET " + COL_DESCRIPTION+ " = '" +
//                description + "' WHERE " + COL_SERVICE_PROVIDER_ID + " = '" + pk + "'";
//        db.execSQL(query4);
    }

    //QUERY: FIND ALL SP's
    public ArrayList<ServiceProvider> findAllServiceProviders() {

        ArrayList<ServiceProvider> allSPList = new ArrayList<ServiceProvider>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_SERVICE_PROVIDERS;
        Cursor cursor = db.rawQuery(query, null);

        ServiceProvider sp;
        if (cursor.moveToFirst()) {
            do {

                sp = new ServiceProvider();
                sp.setID(Integer.parseInt(cursor.getString(0)));
                sp.setCompanyName(cursor.getString(1));
                sp.setPhoneNumber(cursor.getString(2));
                sp.setLicensed(Boolean.parseBoolean(cursor.getString(3)));
                sp.setDescrition(cursor.getString(4));
                allSPList.add(sp);

                Log.d("---------", "-------------");
                Log.d("QueryResultSP", "Query returned: "
                        + " ID: " + sp.getID()
                        + " | company name: " + sp.getCompanyName()
                        + " | phone number: " + sp.getPhoneNumber()
                        + " | Licensed?: " + sp.getLicensed()
                        + " | Description: " + sp.getDescrition());
            }
            while (cursor.moveToNext());
        } else {
            sp = null;
        }
        cursor.close();
        db.close();

        return allSPList;
    }

    //Query find last person added
    public int findLastPersonsPK(){

        int lastPersonsPK = 0;
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select id FROM " + TABLE_NAME_PEOPLE + " ORDER BY id DESC LIMIT '1'";
        Cursor cursor = db.rawQuery(query, null);

        Person person = new Person();
        if (cursor.moveToFirst()) {
            person.setID(Integer.parseInt(cursor.getString(0)));
            lastPersonsPK = person.getID();

        } else {
            person = null;
        }
        cursor.close();
        db.close();

        Log.d("Last PK", "Query returned: | pk = " + lastPersonsPK);
        return lastPersonsPK;
    }


    //QUERY: FIND SERVICE FROM PK
    public ArrayList<Integer> findServicesFromPk(int pk) {

        ArrayList<Integer> allServicesList = new ArrayList<Integer>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_INTER_SID + " WHERE " +
                COL_SP_ID + " = \"" + pk + "\"";

        Cursor cursor = db.rawQuery(query, null);

        IntermediateTable inter;
        if (cursor.moveToFirst()) {
            do {
                inter = new IntermediateTable();
                inter.setSID(Integer.parseInt(cursor.getString(1)));
                allServicesList.add(inter.getSID());
            }
            while (cursor.moveToNext());
        } else {
            inter = null;
        }
        cursor.close();
        db.close();
        return allServicesList;
    }


    //QUERY: FIND AVAILABILITIES FROM PK
    public ArrayList<String> findAvailabilitiesFromPk(int pk) {

        ArrayList<String> allServicesList = new ArrayList<String>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_INTER_AVAILABILITIES + " WHERE " +
                COL_SP_IDENTIFIER + " = \"" + pk + "\"";

        Cursor cursor = db.rawQuery(query, null);

        IntermediateAvailabilitiesTable inter;
        if (cursor.moveToFirst()) {
            do {
                inter = new IntermediateAvailabilitiesTable();
                inter.setDate(cursor.getString(1));
                allServicesList.add(inter.getDate());
            }
            while (cursor.moveToNext());
        } else {
            inter = null;
        }
        cursor.close();
        db.close();
        return allServicesList;
    }


    //QUERY: FIND SERVICE FROM PK
    public int findServicesFromName(String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM " + TABLE_NAME_SERVICES + " WHERE " +
                COL_SERVICE_NAME + " = \"" + name + "\"";

        Cursor cursor = db.rawQuery(query, null);
        Service service;
        if (cursor.moveToFirst()) {
                service = new Service();
                service.setSid(Integer.parseInt(cursor.getString(0)));
                return service.getSid();
        } else {
            service = null;
        }
        cursor.close();
        db.close();
        return service.getSid();
    }


    //QUERY: FIND SPECIFIC SERVICE FROM PK
    public Service findSpecificService(int pk) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_SERVICES + " WHERE " +
                COL_SID + " = \"" + pk + "\"";

        Cursor cursor = db.rawQuery(query, null);

        Service service = new Service();
        if (cursor.moveToFirst()) {
            service.setSid(Integer.parseInt(cursor.getString(0)));
            service.setService(cursor.getString(1));
            service.setHourlyRate(Double.parseDouble(cursor.getString(2)));
            cursor.close();
            db.close();
            return service;
        } else {
            service = null;
        }
        return service;
    }





    //QUERY: FIND ALL SERVICES
    public ArrayList<Service> findAllServices() {

        ArrayList<Service> allServicesList = new ArrayList<Service>();
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_SERVICES;
        Cursor cursor = db.rawQuery(query, null);

        Service service;
        if (cursor.moveToFirst()) {
            do {
                service = new Service();
                service.setSid(Integer.parseInt(cursor.getString(0)));
                service.setService(cursor.getString(1));
                service.setHourlyRate(Double.parseDouble(cursor.getString(2)));

                allServicesList.add(service);

                Log.d("---------", "-------------");
                Log.d("QueryResult", "Query returned: "
                        + "ID: " + service.getSid()
                        + "| service: " + service.getService()
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

    //get Intermediate table
    public void getIntermediateTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_INTER_SID;
        Cursor cursor = db.rawQuery(query, null);

        IntermediateTable intTable = new IntermediateTable();
        if (cursor.moveToFirst()) {
            do {
                intTable.setSp_id(Integer.parseInt(cursor.getString(0)));
                intTable.setSID(Integer.parseInt(cursor.getString(1)));

                Log.d("QueryResultInt", " --------------------------");
                Log.d("QueryResultInt", "| "
                        + "SP id: " + intTable.getSP_id()
                        + " | Service id: " + intTable.getSID() +  " |");
            }
            while (cursor.moveToNext());
            Log.d("QueryResultInt", " --------------------------");
        } else {
            intTable = null;
        }
        cursor.close();
        db.close();
    }


    //get Intermediate table
    public String getAvailabilitiesTable() {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_INTER_AVAILABILITIES;
        Cursor cursor = db.rawQuery(query, null);

        IntermediateAvailabilitiesTable intTable = new IntermediateAvailabilitiesTable();
        if (cursor.moveToFirst()) {
            do {
                intTable.setSp_id(Integer.parseInt(cursor.getString(0)));
                intTable.setDate(cursor.getString(1));

                Log.d("QueryResultInt", " A--------------------------");
                Log.d("QueryResultInt", "| "
                        + "SP id: " + intTable.getSP_id()
                        + " | Service id: " + intTable.getDate() +  " |");
            }
            while (cursor.moveToNext());
            Log.d("QueryResultInt", " --------------------------");
        } else {
            intTable = null;
        }
        cursor.close();
        db.close();
        return "";
    }



    public boolean alreadyExists(int sp_PK, int servicePK){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_INTER_SID + " WHERE " +
                COL_SP_ID + " = \"" + sp_PK + "\"" + " AND " +
                COL_SERVICE_ID + " = \"" + servicePK + "\"";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }

    public boolean availAlreadyExists(int sp_id, String time){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_INTER_AVAILABILITIES + " WHERE " +
                COL_SP_IDENTIFIER + " = \"" + sp_id + "\"" + " AND " +
                COL_TIME + " = \"" + time + "\"";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }

    public boolean availAlreadyExists(String time){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "Select * FROM " + TABLE_NAME_AVAILABILITIES + " WHERE " +
                COL_AVAILABILITY + " = \"" + time + "\"";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }

}