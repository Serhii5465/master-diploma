package com.example.iotvandergraaf.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface iThingsBoardApi {

    @GET("/api/v1/TYFdvwR11i9eemn5FAXB/attributes?clientKeys=temperature")
    Call<JsonObject> getTempature();

    @GET("/api/v1/TYFdvwR11i9eemn5FAXB/attributes?clientKeys=led_on")
    Call<JsonObject> getStatusLed();

    @POST("/api/v1/TYFdvwR11i9eemn5FAXB/attributes")
    Call<Void> blinkLed(@Body JsonObject object);

    @GET("/api/v1/R1Oj3AzX0ABTVHsf7L15/attributes?clientKeys=rec_volt_out")
    Call<JsonObject> getVoltage();

    @POST("/api/v1/R1Oj3AzX0ABTVHsf7L15/attributes")
    Call<Void> setVoltage(@Body JsonObject object);

    @GET("/api/v1/R1Oj3AzX0ABTVHsf7L15/attributes?clientKeys=temperature_1," +
            "temperature_2,temperature_3,temperature_4,current,pressure_vacuum,voltage,pressure")
    Call<JsonObject> getDataSensors();
}
