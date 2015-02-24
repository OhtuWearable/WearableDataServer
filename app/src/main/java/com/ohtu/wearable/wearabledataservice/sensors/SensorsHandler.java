package com.ohtu.wearable.wearabledataservice.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;


import org.json.JSONException;
import org.json.JSONObject;

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
        this.jsonConverter = new JSONConverter();
        this.sensorMap = new HashMap<>();
        this.context = context;
        this.sensors = sensors;
        initSensors(sensors);
    }

    /**
     * Initializes the sensors to be listened
     * @param sensors List of Sensor-objects
     */
    public void initSensors(List<Sensor> sensors) {
        for (Sensor s : sensors) {
            sensorMap.put(s.getType(), new SensorUnit());
            sensorMap.get(s.getType()).setSensor(s, this.context);
        }
        this.sensors = sensors;
    }

    /**
     * Returns sensor data from a selected sensor as a JSONObject
     * @param sensorId Integer value to specify a sensor
     * @return Asks a sensor for data, returns JSON
     * @throws JSONException
     */
    public JSONObject getSensorData(int sensorId) throws JSONException {
        return sensorMap.get(sensorId).getSensorData();
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

    public List<Sensor> listAvailableSensors() {
        return sensors;
    }


}