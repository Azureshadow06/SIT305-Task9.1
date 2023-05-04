package com.example.task71.model;

public class User { //Declare a User Class to store user data

    private int user_id;
    private String username;
    private String password;
    private String location;
    private String describe;
    private String date;
    private String type;

    // Constructor to initialize user data
    public User(String username, String password, String location, String describe, String date, String type) {
        this.username = username;
        this.password = password;
        this.location = location;
        this.describe = describe;
        this.date = date;
        this.type = type;
    }

    // Getter and Setter methods for the all the variables
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public User() {
    }

    public String getLocation() { return location;}

    public void setLocation(String location) {this.location = location;}

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
