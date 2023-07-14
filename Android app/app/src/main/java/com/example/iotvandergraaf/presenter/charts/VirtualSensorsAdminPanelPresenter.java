package com.example.iotvandergraaf.presenter.charts;

import android.widget.TextView;

import com.example.iotvandergraaf.network.ThingsBoardRetrofitInstance;
import com.example.iotvandergraaf.network.iThingsBoardApi;
import com.github.stefanodp91.android.circularseekbar.CircularSeekBar;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VirtualSensorsAdminPanelPresenter {

    private CircularSeekBar seekBar;
    private TextView textView;
    private iThingsBoardApi api;

    public VirtualSensorsAdminPanelPresenter(CircularSeekBar seekBar, TextView textView) {
        this.seekBar = seekBar;
        this.textView = textView;
        api = ThingsBoardRetrofitInstance.getInstance().getApi();
    }

    public void setStateIndicator(){
        api.getVoltage().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    if(response.body().get("client") != null) {
                        JsonObject object = response.body().get("client").getAsJsonObject();
                        float volt = object.get("rec_volt_out").getAsFloat();
                        textView.setText("Voltage " + String.valueOf(volt) + " V");
                        seekBar.setProgress(volt / 5 * 100);

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }

    public void setOutputVoltage(float percent){
        JsonObject object = new JsonObject();
        float volt = (percent * 5) / 100;
        object.addProperty("andr_output_volt",volt);
        textView.setText("Voltage " + String.valueOf(volt) + " V");

        api.setVoltage(object).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
            }
        });
    }

}
