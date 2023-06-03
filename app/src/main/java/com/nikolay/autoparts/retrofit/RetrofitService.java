package com.nikolay.autoparts.retrofit;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {

    private Retrofit retrofit;

    public RetrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.8.111:8080/")
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

}
