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
 * Class for handling multiple sensors
 */
public class SensorsHandler {

    /**
     * Android's SensorManager to manage sensors
     */
    private SensorManager sensorManager;
    /**
     * JSONConverter for converting native data to JSON
     */
    private JSONConverter jsonConverter;
    /**
     * HashMap for storing all sensors of the device
     */
    private HashMap<Integer, SensorUnit> sensorMap;
    /**
     * List of sensors that are currently selected for listening
     */
    private List<Sensor> sensors;
    /**
     * Context of the activity
     */
    private Context context;

    /**
     * Constructor for SensorsHandler
     * @param sensors List of selected Sensor-objects (actually an empty list)
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
        for (Sensor s : getAllSensorsOnDevice()) {
            if (!sensorMap.containsKey(s.getType())) {
                sensorMap.put(s.getType(), new SensorUnit());
                sensorMap.get(s.getType()).setSensor(s, this.context);
            }
        }
        //initSensors(this.sensors);
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
     * @param sensorId Integer value of the sensor to specify it
     * @return If sensor is set 'active' sensor is asked for data; returns JSONObject either with values or empty
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
     * @param sensorId Integer value of the sensor to specify it
     */
    public void stopSensor(int sensorId) {
        sensorMap.get(sensorId).stopListening();
    }

    /**
     * Returns JSONobject containing all available sensors
     * @return JSONObject listing available sensors
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

    /**
     *
     * @param sensorId Integer value of the sensor to specify it
     * @return True if sensor is supported by the device, otherwise false
     */
    public boolean sensorIsActive(int sensorId){
        return sensorMap.containsKey(sensorId);
    }

    /**
     * Returns list of sensors currently set 'active'
     * @return List of sensors (objects) that are currently set 'active'
     */
    public List<Sensor> getAvailableSensors() {
        return sensors;
    }

    /**
     * Returns list of sensors that are supported by the device
     * @return List of sensors (objects) that are supported by the device
     */
    public List<Sensor> getAllSensorsOnDevice() {
        return sensorManager.getSensorList(Sensor.TYPE_ALL);
    }

    /**
     * Returns all SensorUnits of selected sensors.
     * @param sensors
     * @return
     */
    public List<SensorUnit> getSensorUnits(List<Sensor> sensors) {
        List<SensorUnit> sList = new ArrayList<>();
        for (Sensor s : sensors) {
            if (this.sensors.contains(s)) {
                SensorUnit unit = sensorMap.get(s.getType());
                //set dummyData for testing:
                //unit.setDummyData();
                sList.add(unit);
            }
        }
        return sList;
    }


    /**
     *
     * @param sensorDatabase
     * @param sensors
     */
    public SensorDatabase updateSensorUnitDatabase(SensorDatabase sensorDatabase, List<Sensor> sensors) {
        for (Sensor s : sensors) {
            if (!this.sensors.contains(s)) {
                Log.d("Adding sensor ", s.getName());
                sensorDatabase.addSensorUnit(sensorMap.get(s.getType()));
            }
        }
       return sensorDatabase;
    }
}