package com.ohtu.wearable.wearabledataservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;


import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.List;

/**
 * Starts the server service as bound foreground service
 */

public class StartServerActivity extends Activity
{
    private SensorServerService sensorServerService = null;
    private boolean serviceBound = false;

    //this is called when this activity is started
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_startserver);
        //mTextView = (TextView) findViewById(R.id.text);
        //mTextView.setText(getLocalIpAddress());

        //Start server as foreground service
        Intent startIntent = new Intent(this, SensorServerService.class);
        startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(startIntent);

    }

    //this is called when this activity is started/resumed
    @Override
    protected void onResume() {
        super.onResume();
        Intent intentBind = new Intent(this, SensorServerService.class);
        bindService(intentBind, mConnection, 0);
        if (serviceBound){
            sensorServerService.startServer(getSensors());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }

    //this is called when service is bound to this activity
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
     * @param sensors containing all available sensors
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


    //returns devices ip address
    private static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}