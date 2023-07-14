package com.example.iotvandergraaf.view.charts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.iotvandergraaf.R;
import com.example.iotvandergraaf.presenter.charts.PhysicalSensorsAdminPanelPresenter;


public class PhysicalSensorsAdminPanelFragment extends Fragment {

    private PhysicalSensorsAdminPanelPresenter presenter;

    private Button led_on;
    private Button led_off;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_physical_sensors_admin_panel, container, false);

        led_off = (Button) view.findViewById(R.id.led_OFF);
        led_on = (Button) view.findViewById(R.id.led_ON);

        presenter = new PhysicalSensorsAdminPanelPresenter(led_on,led_off);
        presenter.initStateButtons();

        led_off.setOnClickListener(new ButtonClick());
        led_on.setOnClickListener(new ButtonClick());

        return view;
    }

    private class ButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            final int id = view.getId();
            switch (id){
                case R.id.led_OFF:
                    presenter.blink_LED(0);
                    led_off.setEnabled(false);
                    led_on.setEnabled(true);
                    break;
                case R.id.led_ON:
                    presenter.blink_LED(1);
                    led_on.setEnabled(false);
                    led_off.setEnabled(true);
                    break;
                    default:
                        break;
            }
        }
    }

}
