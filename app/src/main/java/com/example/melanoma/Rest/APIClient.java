package com.example.melanoma.Rest;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
    private static Retrofit ourInstace;

    public static Retrofit getInstace() {
        if (ourInstace == null)
            ourInstace = new Retrofit.Builder()
                    .baseUrl("https://ewak.anakinskywalk1.repl.co/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        return ourInstace;
    }
    private APIClient(){

    }
}
