package com.example.iotvandergraaf.view.charts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.iotvandergraaf.R;
import com.example.iotvandergraaf.presenter.charts.PhysicalSensorsChartsPresenter;
import com.sccomponents.gauges.gr004.GR004;

public class PhysicalSensorsChartsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_physical_sensors_charts, container, false);

        GR004 temp_gauge = view.findViewById(R.id.temperature_gauge);
        TextView textView = view.findViewById(R.id.textView6);

        temp_gauge.setText("Temperature,°C");
        temp_gauge.setMinValue(-10);
        temp_gauge.setMaxValue(40);
        temp_gauge.setMajorTicks(10);
        temp_gauge.setMinorTicks(1);

        PhysicalSensorsChartsPresenter presenter =
                    new PhysicalSensorsChartsPresenter(this,temp_gauge,textView);
        presenter.showTemperature();

        return view;
    }

    public void serverFailed(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
