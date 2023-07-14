package com.example.iotvandergraaf.presenter.charts;

import android.os.Handler;
import android.widget.TextView;

import com.example.iotvandergraaf.network.ThingsBoardRetrofitInstance;
import com.example.iotvandergraaf.network.iThingsBoardApi;
import com.example.iotvandergraaf.view.charts.PhysicalSensorsChartsFragment;
import com.google.gson.JsonObject;
import com.sccomponents.gauges.gr004.GR004;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhysicalSensorsChartsPresenter implements Runnable {

    private PhysicalSensorsChartsFragment fragment;

    private Handler handle;
    private Thread t;

    private GR004 temp_gauge;
    private TextView textView;

    public PhysicalSensorsChartsPresenter(PhysicalSensorsChartsFragment fragment, GR004 temp_gauge, TextView textView) {
        this.fragment = fragment;
        this.temp_gauge = temp_gauge;
        this.textView = textView;

        handle = new Handler();
        t = new Thread(this);

        t.start();
    }


    public void showTemperature(){
        iThingsBoardApi api = ThingsBoardRetrofitInstance.getInstance().getApi();

        api.getTempature().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    if(response.body().get("client") != null){
                        JsonObject object = response.body().get("client").getAsJsonObject();
                        float temperature = object.get("temperature").getAsFloat();
                        temp_gauge.setValue(temperature);
                        textView.setText(String.valueOf(temperature) + " °C");
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                fragment.serverFailed(t.toString());
            }
        });
    }

    @Override
    public void run() {
        handle.postDelayed(this,3000);
        showTemperature();
    }
}
