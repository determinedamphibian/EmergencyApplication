package com.example.emergencyapplication.CovidWatcher;

public class User {
    public  String f_name, l_name, email, username, address, number, status;
    public  String hasFever, haCough, hasFatigue, hasAches, hasRunnyNose,
            hasSoreThroat, hasShortnessOfBreath, hasDiarrhea, hasHeadAche, hasNoSmellandTaste;


    public User(){

    }

    //User profile
    public User(String f_name, String l_name, String email, String username, String address, String number){
        this.f_name = f_name;
        this.l_name = l_name;
        this.email = email;
        this.username = username;
        this.address = address;
        this.number = number;
    }

    //User Status
    public User(String f_name, String  number, String status){
        this.f_name = f_name;
        this.number = number;
        this.status = status;
    }

    public User(String hasFever, String haCough, String hasFatigue, String hasAches, String hasRunnyNose,
                String hasSoreThroat, String hasShortnessOfBreath, String hasDiarrhea, String hasHeadAche,
                String hasNoSmellandTaste) {
        this.hasFever = hasFever;
        this.haCough = haCough;
        this.hasFatigue = hasFatigue;
        this.hasAches = hasAches;
        this.hasRunnyNose = hasRunnyNose;
        this.hasSoreThroat = hasSoreThroat;
        this.hasShortnessOfBreath = hasShortnessOfBreath;
        this.hasDiarrhea = hasDiarrhea;
        this.hasHeadAche = hasHeadAche;
        this.hasNoSmellandTaste = hasNoSmellandTaste;
    }
}
