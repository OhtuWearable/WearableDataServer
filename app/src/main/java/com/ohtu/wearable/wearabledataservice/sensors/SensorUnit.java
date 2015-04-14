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

    /**
     * Android's SensorManager to manage sensors
     */
    private SensorManager mSensorManager;
    /**
     *
     */
    private Sensor sensor;
    /**
     * Lol Ys dis heer, dunno :--) maybe there's gonna be some use eh?
     */
    private int sensorId;
    /**
     * Float array to store latest sensor values
     */
    private double[] data;

    //private int randomi = new Random().nextInt();

    /**
     * Android's Handler for handling and running runnables after some specific time
     */
    private Handler handler;
    /**
     * Android's runnable to be used with Handler
     */
    private Runnable runnable;
    /**
     * Boolean to check if sensor listener is registered ie. changes in sensor values are being detected and stored
     */
    private boolean isListening;
    /**
     * Length of time how long sensor is kept waiting data requests before stopping listening
     */
    private int listenTime;


    /**
     * Registers listener to a sensor and also sets context for it
     * @param sensor Sensor-object to be used
     * @param mContext Context of the activity
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

    /**
     * Registers listener to the sensor that's been set in the instance of SensorUnit
     */
    public void listenSensor() {
        isListening = mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        //isListening = true;
        handler.postDelayed(runnable, 5000);
        listenTime = 5000;
    }

    /**
     * Takes parameter to define time to listen before stopping sensorlistener
     * @param listenTime Length of time defining how long sensor keeps waiting for new requests before
     *                   its listener is unregistered
     */
    public void listenSensor(int listenTime) {
        this.listenTime = listenTime;
        mSensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        isListening = true;
        handler.postDelayed(runnable, listenTime);
    }

    /**
     * Unregisters sensor's listener
     */
    public void stopListening() {
        mSensorManager.unregisterListener(this, sensor);
        data = null;
        isListening = false;
    }

    @Override
    public void onAccuracyChanged(android.hardware.Sensor sensor, int accuracy) {

    }

    /**
     * When sensor state changes new sensor values are assigned to data-array
     * @param event Most recent sensor event
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        data = new double[event.values.length+1];
        for (int i = 0; i<event.values.length;i++) {
            data[i] = event.values[i];
        }
        data[event.values.length] = System.currentTimeMillis();

        /*if (event.sensor.getType() == 1) {
            Log.d("kuuntelija", "voi ei!" + randomi);
        }*/
    }

    /**
     * Sends native sensor data to JSONconverter that returns the data as a JSONObject
     * Renews listening time accordingly to the set listenTime-variable
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

    /**
     * Asks sensor for values and then stops listening immediately.
     * @return JSONConverters output as JSONObject
     * @throws JSONException
     */
    public JSONObject getSensorDataOnce() throws JSONException {
        if (isListening) {
            handler.removeCallbacks(runnable);
        } else {
            listenSensor();
        }
        JSONObject jsonObject = JSONConverter.convertToJSON(data);
        stopListening();
        return jsonObject;
    }

}

