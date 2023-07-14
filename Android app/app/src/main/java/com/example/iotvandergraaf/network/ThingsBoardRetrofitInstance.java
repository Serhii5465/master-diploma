package com.example.iotvandergraaf.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ThingsBoardRetrofitInstance {

    private Retrofit retrofit;
    private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static ThingsBoardRetrofitInstance instance;

    public ThingsBoardRetrofitInstance() {
        retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ThingsBoardRetrofitInstance getInstance() {
        if(instance == null){
            instance = new ThingsBoardRetrofitInstance();
        }
        return instance;
    }

    public iThingsBoardApi getApi(){
        return retrofit.create(iThingsBoardApi.class);
    }
}
