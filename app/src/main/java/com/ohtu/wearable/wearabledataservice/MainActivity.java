package com.ohtu.wearable.wearabledataservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.ohtu.wearable.wearabledataservice.fragments.PagerAdapter;

import java.util.List;

public class MainActivity extends FragmentActivity implements SelectedSensorsInterface, ServerControlInterface {

    private SensorServerService sensorServerService = null;
    private boolean serviceBound = false;

    ViewPager viewpager;

    List<Sensor> sensors;

    /**
     * Sets enabled sensors
     *
     * @param sensors List of sensor objects
     */
    public void setSelectedSensors(List<Sensor> sensors){
        this.sensors = sensors;
        setServerSensors(sensors);
    }

    /**
     * Returns list of enabled sensors
     *
     * @return List of sensor objects
     */
    public List<Sensor> getSelectedSensors(){
        return this.sensors;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sensors = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager = (ViewPager) findViewById(R.id.pager);
        PagerAdapter padapter = new PagerAdapter(getSupportFragmentManager());
        viewpager.setAdapter(padapter);

        startServerService();
    }

    //Starts server as a foreground service.
    private void startServerService() {
        Intent startIntent = new Intent(this, SensorServerService.class);
        startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(startIntent);
    }

    //Called when this activity is started or resumed.
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

    //Called when activity is paused.
    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }

    //Called when service is bound to this acitivity
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SensorServerService.LocalBinder b = (SensorServerService.LocalBinder) service;
            sensorServerService = b.getService();
            sensorServerService.startServer(sensors);
            serviceBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            sensorServerService = null;
        }
    };


    /**
     * Sets sensors available to server
     * @param sensors List of sensors.
     */
    public void setServerSensors(List<Sensor> sensors){
        if (serviceBound){
            sensorServerService.startServer(sensors);
        }
    }

    /**
     * Starts server
     */
    @Override
    public void startServer() {
        if (serviceBound){
            sensorServerService.startServer(sensors);
        } else {
            startServerService();
        }
    }

    /**
     * Stops server
     */
    @Override
    public void stopServer() {
        if (serviceBound) {
            sensorServerService.stopServer();
        }
    }

    /**
     * Tells if server is running
     *
     * @return true if service is running
     */
    @Override
    public boolean isRunning(){
        if (serviceBound){
            return sensorServerService.isRunning();
        }
        return false;
    }
}
