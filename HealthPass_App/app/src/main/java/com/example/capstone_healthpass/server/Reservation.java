package com.example.capstone_healthpass.server;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Field;

public class Reservation {
    @SerializedName("date")
    private String date;
    @SerializedName("time")
    private String time;
    @SerializedName("minute")
    private String minute;

    @SerializedName("seat")
    private String seat;
    @SerializedName("ex_name")
    private String ex_name;

    @SerializedName("stringAccount")
    private String stringAccount;



    public String getDay() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getMinute() {
        return minute;
    }



    public String getSeat() {
        return seat;
    }

    public String getEx_name() {
        return ex_name;
    }


}
