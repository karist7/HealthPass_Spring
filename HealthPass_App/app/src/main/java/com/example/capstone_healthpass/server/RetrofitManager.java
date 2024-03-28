package com.example.capstone_healthpass.server;

import com.example.capstone_healthpass.server.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private Retrofit retrofit;
    private ApiService apiService;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("localhost:8080")
            .addConverterFactory(GsonConverterFactory.create());

    public RetrofitManager() {
        retrofit = builder.build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
