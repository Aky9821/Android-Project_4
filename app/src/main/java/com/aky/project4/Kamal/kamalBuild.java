package com.aky.project4.Kamal;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class kamalBuild {
    public static Retrofit getRetrofitInstance() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://v6.exchangerate-api.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }
}

