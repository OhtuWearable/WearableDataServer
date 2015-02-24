package com.ohtu.wearable.wearabledataservice;

import android.hardware.Sensor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.List;

public class MainActivity extends FragmentActivity implements SelectedSensorsInterface {
    ViewPager viewpager;

    List<Sensor> sensors;

    public void setSelectedSensors(List<Sensor> sensors){
        this.sensors = sensors;
    }

    public List<Sensor> getSelectedSensors(){
        return this.sensors;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter padapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(padapter);

    }

}
