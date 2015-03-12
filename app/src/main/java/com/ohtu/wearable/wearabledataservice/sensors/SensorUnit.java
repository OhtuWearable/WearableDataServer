package com.ohtu.wearable.wearabledataservice.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Random;


/**
 * Created by jannetim on 12/02/15.
 */
public class SensorUnit implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor sensor;
    private int sensorId;
    private float[] data;
    private int randomi = new Random().nextInt();
    private Handler handler;
    private boolean isListening;
    private int listenTime;
    private Runnable runnable;

    /**
     * Registers listener to a sensor and also sets context for it
     * @param sensor
     * @param mContext
     */
    public void setSensor(Sensor sensor, Context mContext){
        handler = new Handler();
        this.sensorId = sensor.getType();
        this.sensor = sensor;
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        runnable = new Runnable() {
            @Override
            public void run() {
                stopListening();
            }
        };
    }

    public void listenSensor() {
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        isListening = true;
        handler.postDelayed(runnable, 5000);
        listenTime = 5000;
    }

    public void listenSensor(int listenTime) {
        this.listenTime = listenTime;
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        isListening = true;
        handler.postDelayed(runnable, listenTime);
    }

    /**
     * Unregisters sensor listener
     * @param
     */
    public void stopListening() {
        mSensorManager.unregisterListener(this, sensor);
        data = null;
        isListening = false;
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        data = event.values;

        if (event.sensor.getType() == 1) {
            Log.d("kuuntelija", "voi ei!" + randomi);
        }
    }

    /**
     * Sends native sensor data to JSONconverter that returns the data as a JSONObject
     * @return Sensor data in JSONObject
     * @throws JSONException
     */
    public JSONObject getSensorData() throws JSONException {
        if (isListening) {
            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, listenTime);
        } else {
            listenSensor();
        }
        return JSONConverter.convertToJSON(data);
    }
}

