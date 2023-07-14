package com.example.iotvandergraaf.presenter.charts;

import android.os.Handler;

import com.example.iotvandergraaf.network.ThingsBoardRetrofitInstance;
import com.example.iotvandergraaf.network.iThingsBoardApi;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonObject;
import com.sccomponents.gauges.gr004.GR004;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VirtualSensorsChartsPresenter implements Runnable {

    private Handler handle;
    private Thread t;

    private BarChart barChart1;

    private GR004 cur_gauge;
    private GR004 volt_gaug;
    private GR004 pres_gauge;
    private GR004 vac_gauge ;

    private iThingsBoardApi api;

    public VirtualSensorsChartsPresenter(BarChart barChart, GR004... gauges) {
        this.barChart1 = barChart;

        this.cur_gauge = gauges[0];
        this.volt_gaug = gauges[1];
        this.pres_gauge = gauges[2];
        this.vac_gauge = gauges[3];

        api = ThingsBoardRetrofitInstance.getInstance().getApi();

        handle = new Handler();
        t = new Thread(this);

        t.start();
    }

    public void showData(){
        api.getDataSensors().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    if(response.body().get("client") != null){
                        JsonObject object = response.body().get("client").getAsJsonObject();

                        float temperature_1 = object.get("temperature_1").getAsFloat();
                        float temperature_2 = object.get("temperature_2").getAsFloat();
                        float temperature_3 = object.get("temperature_3").getAsFloat();
                        float temperature_4 = object.get("temperature_4").getAsFloat();

                        float pressure_vacuum = object.get("pressure_vacuum").getAsFloat();
                        float voltage = object.get("voltage").getAsFloat();
                        float current = object.get("current").getAsFloat();
                        float pressure = object.get("pressure").getAsFloat();

                        ArrayList<BarEntry> barEntries1 = new ArrayList<>();
                        barEntries1.add(new BarEntry(0f,temperature_1));
                        barEntries1.add(new BarEntry(1f,temperature_2));
                        barEntries1.add(new BarEntry(2f,temperature_3));
                        barEntries1.add(new BarEntry(3f,temperature_4));

                        BarDataSet dataSet1 = new BarDataSet(barEntries1,"Data sensors");
                        dataSet1.setBarBorderWidth(0.9f);
                        dataSet1.setColors(ColorTemplate.COLORFUL_COLORS);

                        BarData barData1 = new BarData(dataSet1);
                        barChart1.setData(barData1);
                        barChart1.invalidate();

                        cur_gauge.setValue(current);
                        volt_gaug.setValue(voltage);
                        pres_gauge.setValue(pressure);
                        vac_gauge.setValue(pressure_vacuum);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println(t.toString());
            }
        });
    }

    @Override
    public void run() {
        handle.postDelayed(this,3000);
        showData();
    }
}
