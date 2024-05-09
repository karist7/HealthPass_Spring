package com.example.capstone_healthpass.server;

import com.example.capstone_healthpass.server.ApiService;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private Retrofit retrofit;
    private ApiService apiService;

    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://220.69.208.121:8080aa")
            .addConverterFactory(GsonConverterFactory.create());

    public RetrofitManager() {
        retrofit = builder.build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
