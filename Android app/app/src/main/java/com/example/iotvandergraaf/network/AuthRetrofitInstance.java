package com.example.iotvandergraaf.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthRetrofitInstance {
    private Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:80/iot_auth/";
    private static AuthRetrofitInstance rInstance;

    private AuthRetrofitInstance(){
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static AuthRetrofitInstance getInstance() {
        if(rInstance == null){
            rInstance = new AuthRetrofitInstance();
        }
        return rInstance;
    }

    public iApiAuth getApi(){
        return retrofit.create(iApiAuth.class);
    }
}