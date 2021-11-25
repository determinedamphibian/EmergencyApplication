package com.example.emergencyapplication.CovidWatcher;

public class User {
    public  String f_name, l_name, email, username, address, number;

    public User(){

    }

    public User(String f_name, String l_name, String email, String username, String address, String number){
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
        this.username = username;
        this.address = address;
        this.number = number;
    }
}
