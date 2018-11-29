package com.uottawa.jasonsmith.homeservicesapp;

public class Person {
    private String  username, email, address, password;
    private int id, userType;

    //empty constructor
    public Person(){}

    public Person(int id, String username, String email, String address, String password){
        this.id = id;
        this.username = username;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public Person(String username, String email, String address, String password) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public Person(String usernameContent, String passwordContent) {
        this.username = usernameContent;
        this.password = passwordContent;
    }

    public void setID(int id){this.id = id;}

    public void setUsername(String username){this.username = username;}

    public void setEmail(String email){this.email = email;}

    public void setPassword(String password){this.password = password;}

    public void setPersonType(int userType) { this.userType = userType; }

    public int getID(){ return id; }

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }

    public int getUserType() { return userType; }


}