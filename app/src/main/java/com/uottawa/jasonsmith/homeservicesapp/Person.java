package com.uottawa.jasonsmith.homeservicesapp;

public class Person {
    private String  username, email, address, password;

    public Person(String username, String email, String address, String password){
        this.username = username;
        this.email = email;
        this.address = address;
        this.password = password;
    }

    public Person(String usernameContent, String passwordContent) {
        this.username = usernameContent;
        this.password = passwordContent;
    }

    private void setUsername(String username){this.username = username;}

    private void setEmail(String email){this.email = email;}

    private void setPassword(String password){this.password = password;}

    public String getUsername(){
        return username;
    }

    public String getEmail(){
        return email;
    }

    public String getPassword(){
        return password;
    }
}