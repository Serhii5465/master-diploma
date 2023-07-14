package com.example.iotvandergraaf.network;

import com.example.iotvandergraaf.model.logn.EmailUser;
import com.example.iotvandergraaf.model.logn.ResponseData;
import com.example.iotvandergraaf.model.logn.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface iApiAuth {
    @POST("add_contact.php")
    Call <ResponseData> addUser(@Body User user);

    @POST("get_contact.php")
    Call<ResponseData> getUser(@Body EmailUser emailUser);

    @POST("get_admins.php")
    Call<ResponseData> getCountAdmins();

}