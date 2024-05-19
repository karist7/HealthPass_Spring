package com.example.capstone_healthpass.server;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("/register/")
    Call<JSONObject> register(
            @Body RequestBody body
    );

    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("/login/")
    Call<ResponseBody> loginPost(
            @Body RequestBody body
    );



    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("/reservation/")
    Call<JSONObject> reserved(

            @Body RequestBody body
    );
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("reserveTime/")
    Call<JSONObject> reservedTime(
            @Body RequestBody body

    );
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("machine/")
    Call<JSONObject> reservedMachine(
            @Body RequestBody body

            );
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("info/")
    Call<List<Reservation>> reservedInfo(
            @Field("email") String email

    );
    @DELETE("info/")
    Call<ResponseBody> reservedCancel(
            @Query("day") String day,
            @Query("time") String time,
            @Query("minute")String minute,
            @Query("seat") String seat,
            @Query("ex_name") String ex_name

    );


}
