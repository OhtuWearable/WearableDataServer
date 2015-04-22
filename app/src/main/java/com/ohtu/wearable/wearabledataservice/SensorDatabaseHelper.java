package com.ohtu.wearable.wearabledataservice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.util.Log;

import com.ohtu.wearable.wearabledataservice.sensors.SensorDatabase;
import com.ohtu.wearable.wearabledataservice.sensors.SensorUnit;
import com.ohtu.wearable.wearabledataservice.sensors.SensorsHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * Helper class to create database and access it
 */
public class SensorDatabaseHelper {
    private SensorsHandler sensorsHandler;
    private SensorDatabase sensorDatabase;
    private SQLiteDatabase db;
    private List<Sensor> sensors;

    public SensorDatabaseHelper(SensorsHandler handler) {
        this.sensorsHandler = handler;
    }

    /**
     * Starts the database; at the beginning it drops all tables and creates them again.
     * @param context context
     * @param sensors all sensors on device
     */
    public void startDatabase(Context context, List<Sensor> sensors) {
        if (db == null && sensors != null) {
            sensorDatabase = new SensorDatabase(context, sensors);
            this.sensors = sensors;
            //drop old tables and create new ones:
            sensorDatabase.restart();
            //not-so-hard reset:
            //sensorDatabase.deleteEntries();
            db = sensorDatabase.getWritableDatabase();
            Log.w("DB", "started");
        }
    }

    /**
     * Insert data for sensors to be updated
     * @param sensors List of sensors to be updated
     */
    public void insertSensorData(List<Sensor> sensors) {
        if (sensorDatabase != null) {
            List<SensorUnit> units = sensorsHandler.getSensorUnits(sensors);
            for (SensorUnit unit : units) {
                //unit.setDummyData();
                sensorDatabase.addSensorUnit(unit);
            }
        }
    }

    /**
     * Enters data from single SensorUnit to database.
     * @param unit SensorUnit to be added.
     */
    public void insertSensor(SensorUnit unit) {
        if (sensorDatabase != null && unit != null) {
            Log.d("Insertsensor", "");
            sensorDatabase.addSensorUnit(unit);
        }
    }

    /**
     * Return all data as JSONObject list
     * @param sensor Sensor
     * @return List of JSONObjects bind to the sensor
     * @throws JSONException
     */
    public List<JSONObject> getAllSensorData(Sensor sensor) throws JSONException{
        return sensorDatabase.getAllSensorData(sensor.getName());
    }

    /**
     * Return all data as JSONObject list
     * @param sensorName Name of the sensor
     * @return List of JSONObjects bind to the sensor
     * @throws JSONException
     */
    public List<JSONObject> getAllSensorData(String sensorName) throws JSONException{
        return sensorDatabase.getAllSensorData(sensorName);
    }

    /**
     * Return all sensor data as JSONArray
     * @param sensorName Sensor name
     * @return JSONArray
     * @throws JSONException
     */
    public JSONArray getJSONArray(String sensorName) throws JSONException {
        if (sensorName == null) {
            return null;
        }

        Log.d("getJSONArray", sensorName);
        List<JSONObject> data = sensorDatabase.getAllSensorData(sensorName);
        Log.d("JSONDATA", data.toString());

        JSONArray jsonArray = new JSONArray();
        for (JSONObject o : data) {
           jsonArray.put(o);
        }
        Log.d("JSONArray: ", jsonArray.toString());
        return jsonArray;
    }

    /**
     * Method to empty the database and close it:
     */
    public void close() {
        sensorDatabase.deleteEntries();
        db.close();
    }
}
