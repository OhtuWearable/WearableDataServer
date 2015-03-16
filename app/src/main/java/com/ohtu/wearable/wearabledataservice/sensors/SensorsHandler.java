package com.ohtu.wearable.wearabledataservice.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jannetim on 12/02/15.
 * Class for testing different ideas on how to solve multiple sensors and dealing with them dynamically
 */
public class SensorsHandler {

    private SensorManager sensorManager;
    private JSONConverter jsonConverter;
    private HashMap<Integer, SensorUnit> sensorMap;
    private List<Sensor> sensors;
    private Context context;

    /**
     * Constructor for SensorsHandler
     * @param sensors List of selected Sensor-objects
     * @param context Context of the service
     */
    public SensorsHandler(List<Sensor> sensors, Context context) {
        if (sensors==null) {
            this.sensors = new ArrayList<>();
        } else {
            this.sensors = sensors;
        }
        this.jsonConverter = new JSONConverter();
        this.context = context;
        this.sensorMap = new HashMap<>();
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        //initSensors(this.sensors);
        for (Sensor s : getAllSensorsOnDevice()) {
            if (!sensorMap.containsKey(s.getType())) {
                sensorMap.put(s.getType(), new SensorUnit());
                sensorMap.get(s.getType()).setSensor(s, this.context);
            }
        }
    }

    /**
     * Initializes the sensors to be listened
     * @param sensors List of Sensor-objects
     */
    public void initSensors(List<Sensor> sensors) {
        //stopSensors();
        for (Sensor s : sensors) {
            if (!this.sensors.contains(s)) {
                sensorMap.get(s.getType()).listenSensor();
            }
        }
        for (Sensor s : this.sensors) {
            if (!sensors.contains(s)) {
                sensorMap.get(s.getType()).stopListening();
            }
        }
        this.sensors = sensors;
    }

    /**
     * Stops listening of all set sensors
     */
    public void stopSensors() {
        for (SensorUnit sensorUnit : sensorMap.values()) {
            sensorUnit.stopListening();
        }
    }

    /**
     * Returns sensor data from a selected sensor as a JSONObject
     * @param sensorId Integer value to specify a sensor
     * @return Asks a sensor for data, returns JSON
     * @throws JSONException
     */
    public JSONObject getSensorData(int sensorId) throws JSONException {
        for (Sensor s : sensors) {
            if (s.getType() == sensorId) {
                return sensorMap.get(sensorId).getSensorData();
            }
        }
        return new JSONObject();
    }

    /**
     * Stops listening the sensor given in input
     * @param sensorId
     */
    public void stopSensor(int sensorId) {
        sensorMap.get(sensorId).stopListening();
    }

    /**
     * Returns JSONobject containing all available sensors
     * @return
     * @throws JSONException
     */
    public JSONObject getSensorsList() throws JSONException {
        try {
            return jsonConverter.convertSensorListToJSON(this.sensors);
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public boolean sensorIsActive(int sensorId){
        return sensorMap.containsKey(sensorId);
    }

    public List<Sensor> getAvailableSensors() {
        return sensors;
    }

    public List<Sensor> getAllSensorsOnDevice() {
        return sensorManager.getSensorList(Sensor.TYPE_ALL);
    }

}