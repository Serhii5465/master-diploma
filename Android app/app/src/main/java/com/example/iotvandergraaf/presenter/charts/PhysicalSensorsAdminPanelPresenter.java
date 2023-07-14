package com.example.iotvandergraaf.presenter.charts;

import android.widget.Button;

import com.example.iotvandergraaf.network.ThingsBoardRetrofitInstance;
import com.example.iotvandergraaf.network.iThingsBoardApi;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysicalSensorsAdminPanelPresenter {

    private iThingsBoardApi api;

    private Button btnLedOff;
    private Button btnLedOn;

    public PhysicalSensorsAdminPanelPresenter(Button btn_on,Button btn_off){
        this.btnLedOff = btn_off;
        this.btnLedOn = btn_on;
        api = ThingsBoardRetrofitInstance.getInstance().getApi();
    }

    public void initStateButtons(){
        api.getStatusLed().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    if(response.body().get("client") != null){
                        JsonObject object = response.body().get("client").getAsJsonObject();
                        int status = object.get("led_on").getAsInt();
                        if(status == 1){
                            btnLedOn.setEnabled(false);
                        } else {
                            btnLedOff.setEnabled(false);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void blink_LED(int state){
        JsonObject object = new JsonObject();
        object.addProperty("led_on",state);

        api.blinkLed(object).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }
}
