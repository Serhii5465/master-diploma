package com.example.iotvandergraaf.view.charts;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.iotvandergraaf.R;
import com.example.iotvandergraaf.presenter.ApplicationModePresenter;
import com.google.android.material.tabs.TabLayout;

public class SensorsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensors);

        ViewPager viewPager = findViewById(R.id.pager);
        TabLayout tableLayout =  findViewById(R.id.tab_layout);
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        ApplicationModePresenter presenter = ApplicationModePresenter.getInstance();
        ApplicationModePresenter.MODE_APP mode_app = presenter.getCurrentMode();

        switch (mode_app){
            case USER_ARDUINO_PHYSICAL:
                adapter.addFragment(new PhysicalSensorsChartsFragment(),"Charts",0);
                break;
            case ADMIN_ARDUINO_PHYSICAL:
                adapter.addFragment(new PhysicalSensorsChartsFragment(),"Charts",0);
                adapter.addFragment(new PhysicalSensorsAdminPanelFragment(),"Control panel",1);
                break;
            case USER_ARDUINO_EMULATE:
                adapter.addFragment(new VirtualSensorsChartsFragment(),"Charts",0);
                break;
            case ADMIN_ARDUINO_EMULATE:
                adapter.addFragment(new VirtualSensorsChartsFragment(),"Charts",0);
                adapter.addFragment(new VirtualSensorsAdminPanelFragment(),"Control panel",1);
                break;
        }

        viewPager.setAdapter(adapter);
        tableLayout.setupWithViewPager(viewPager);
    }
}
