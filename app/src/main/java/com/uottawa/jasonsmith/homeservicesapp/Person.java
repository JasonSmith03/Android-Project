package com.uottawa.jasonsmith.homeservicesapp;

public class Person {
    private String  username, email, password;

    public Person(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Person(String usernameContent, String passwordContent) {
        this.username = usernameContent;
        this.password = passwordContent;
    }

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
