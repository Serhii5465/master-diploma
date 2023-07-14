package com.example.iotvandergraaf.view.charts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.iotvandergraaf.R;
import com.example.iotvandergraaf.presenter.charts.VirtualSensorsChartsPresenter;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.sccomponents.gauges.gr004.GR004;


public class VirtualSensorsChartsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_virtual_sensors_charts, container, false);

        BarChart barChart1 =  view.findViewById(R.id.barchart1);
        XAxis xAxis1 = barChart1.getXAxis();
        xAxis1.setPosition(XAxis.XAxisPosition.BOTTOM);
        final String[] temprSens = new String[]{"temp1", "temp2", "temp3", "temp4"};
        IndexAxisValueFormatter formatter1 = new IndexAxisValueFormatter(temprSens);
        xAxis1.setGranularity(1f);
        xAxis1.setValueFormatter(formatter1);
        barChart1.setFitBars(true);

        GR004 cur_gauge = view.findViewById(R.id.cur_gauge);
        GR004 volt_gauge = view.findViewById(R.id.volt_gauge);
        GR004 pres_gauge = view.findViewById(R.id.pres_gauge);
        GR004 vac_gauge = view.findViewById(R.id.vac_gauge);

        cur_gauge.setText("Current,mA");
        cur_gauge.setMaxValue(40);
        cur_gauge.setMajorTicks(5);

        volt_gauge.setText("Voltage,V");
        volt_gauge.setMaxValue(5);
        volt_gauge.setMajorTicks(0.5f);

        pres_gauge.setText("Pressure,kPa");
        pres_gauge.setMaxValue(35);
        pres_gauge.setMajorTicks(5);

        vac_gauge.setText("Vacuum,Pa");
        vac_gauge.setMaxValue(1);
        vac_gauge.setMajorTicks(0.1f);

        VirtualSensorsChartsPresenter presenter =
                new VirtualSensorsChartsPresenter(barChart1,
                        cur_gauge,
                        volt_gauge,
                        pres_gauge,
                        vac_gauge);

        presenter.showData();

        return view;
    }

}
