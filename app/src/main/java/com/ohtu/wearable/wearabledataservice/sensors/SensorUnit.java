package com.ohtu.wearable.wearabledataservice.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;

import com.ohtu.wearable.wearabledataservice.database.SensorDatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;


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
     *  Sensor's id number stored. Currently not accessed after assigning.
     */
    private int sensorId;
    /**
     * Float array to store latest sensor values
     */
    private double[] data;
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

    private SensorDatabaseHelper dbHelper;
    /**
     * Number telling when last entry to database was saved
     */
    private long lastSaved;
    /**
     * Length of time how often sensor data is saved to database
     */
    static private long saveInterval = 1000;

    /**
     * Registers listener to a sensor and also sets context for it
     * @param sensor Sensor-object to be used
     * @param mContext Context of the activity
     */
    public void setSensor(Sensor sensor, Context mContext){
        handler = new Handler();
        lastSaved = System.currentTimeMillis();
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
        long timenow = System.currentTimeMillis();
        data[event.values.length] = timenow;

        /** Save changed sensor data to database: */
        if (dbHelper != null) {
            if (timenow - lastSaved > saveInterval) {
                Log.d("data saved", "");
                dbHelper.insertSensor(this);
                lastSaved = timenow;
            }
        }

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

    /**
     * Helper method to get sensor name for the database
     * @return Sensor name
     */
    public String getSensorName() {
        return this.sensor.getName();
    }

    /**
     * Helper method to get SensorUnit's Sensor object
     * @return
     */
    public Sensor getSensor() {
        return this.sensor;
    }

    /**
     * Sets dummy data for testing purposes.
     */
    public void setDummyData() {
        data = new double[4];
        for (int i = 0; i < 3; i++) {
            data[i] = i;
        }
        data[3] = System.currentTimeMillis();
    }

    /**
     * Sets SensorDatabaseHelper for this class
     * @param helper SensorDatabaseHelper to be set
     */
    public void setHelper(SensorDatabaseHelper helper) {
        this.dbHelper = helper;
    }
}

