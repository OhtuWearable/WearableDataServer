package com.ohtu.wearable.wearabledataservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.ohtu.wearable.wearabledataservice.fragments.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements SelectedSensorsInterface {

    private SensorServerService sensorServerService = null;
    private boolean serviceBound = false;

    ViewPager viewpager;

    List<Sensor> sensors;

    public void setSelectedSensors(List<Sensor> sensors){
        this.sensors = sensors;
        setServerSensors(sensors);
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

        startServerService();
    }

    //starts server as foreground service
    private void startServerService() {
        Intent startIntent = new Intent(this, SensorServerService.class);
        startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(startIntent);
    }



    //called when this activity is started/resumed
    @Override
    protected void onResume() {
        super.onResume();
        Intent intentBind = new Intent(this, SensorServerService.class);
        bindService(intentBind, mConnection, 0);
        /*
        if (serviceBound){
            sensorServerService.startServer(sensors);
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }

    //called when service is bound to this activity
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SensorServerService.LocalBinder b = (SensorServerService.LocalBinder) service;
            sensorServerService = b.getService();
            sensorServerService.startServer(getSensors());
            serviceBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            sensorServerService = null;
        }
    };


    /**
     * Sets sensors available to server
     * @param List containing all available sensors
     */
    public void setServerSensors(List<Sensor> sensors){
        if (serviceBound){
            sensorServerService.startServer(sensors);
        }
    }


    //remove this when passing list from UI is working
    private List<Sensor> getSensors() {
        SensorManager mSensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        return deviceSensors;
    }

}
