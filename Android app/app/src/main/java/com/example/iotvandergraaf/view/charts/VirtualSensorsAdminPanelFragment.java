package com.example.iotvandergraaf.view.charts;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.iotvandergraaf.R;
import com.example.iotvandergraaf.presenter.charts.VirtualSensorsAdminPanelPresenter;
import com.github.stefanodp91.android.circularseekbar.CircularSeekBar;


public class VirtualSensorsAdminPanelFragment extends Fragment {

    private VirtualSensorsAdminPanelPresenter presenter;

    private CircularSeekBar seekBar;
    private TextView textView;
    private Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_virtual_sensors_admin_panel, container, false);

        seekBar = view.findViewById(R.id.seek);
        textView = view.findViewById(R.id.textView5);
        button = view.findViewById(R.id.button4);

        presenter = new VirtualSensorsAdminPanelPresenter(seekBar,textView);
        presenter.setStateIndicator();

        seekBar.setColorList(new int[]{Color.GREEN,Color.YELLOW,Color.RED});

        button.setOnClickListener(view1 ->
                presenter.setOutputVoltage(seekBar.getProgress()));

        return view;
    }
}
