package com.ohtu.wearable.wearabledataservice.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jannetim on 12/02/15.
 */
public class SensorUnit implements SensorEventListener{

    private SensorManager mSensorManager;
    private Sensor sensor;
    private int sensorId;
    private float[] data;

    /**
     * Registers listener to a sensor and also sets context for it
     * @param sensor
     * @param mContext
     */
    public void setSensor(Sensor sensor, Context mContext){
        this.sensorId = sensor.getType();
        this.sensor = sensor;
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
    }

    /**
     * Unregisters sensor listener
     * @param sensor
     */
    public void stopSensor(Sensor sensor) {
        mSensorManager.unregisterListener(this, sensor);
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == sensorId)
            data = event.values;
    }

    /**
     * Sends native sensor data to JSONconverter that returns the data as a JSONObject
     * @return Sensor data in JSONObject
     * @throws JSONException
     */
    public JSONObject getSensorData() throws JSONException {
        JSONconverter jsoNconverter = new JSONconverter();
        return jsoNconverter.convertToJSON(data);
    }
}