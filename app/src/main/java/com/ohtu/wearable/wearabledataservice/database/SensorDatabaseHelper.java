package com.ohtu.wearable.wearabledataservice.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.util.Log;

import com.ohtu.wearable.wearabledataservice.sensors.SensorUnit;
import com.ohtu.wearable.wearabledataservice.sensors.SensorsHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * Helper class to create database and access the data in it. Currently drops all previous data when the servers start.
 */
public class SensorDatabaseHelper {
    /** SensorHandler */
    private SensorsHandler sensorsHandler;
    /** SensorDatabase that is being accessed */
    private SensorDatabase sensorDatabase;
    /** SQLiteDatabase which is a writable database from SensorDatabase */
    private SQLiteDatabase db;
    /** List of all the sensors on the device */
    private List<Sensor> sensors;

    public SensorDatabaseHelper(SensorsHandler handler) {
        this.sensorsHandler = handler;
    }

    /**
     * Starts the database; it drops all previously created tables and creates them again.
     * @param context Context of the app
     * @param sensors List of all sensors on device
     */
    public void startDatabase(Context context, List<Sensor> sensors) {
        if (db == null && sensors != null) {
            sensorDatabase = new SensorDatabase(context, sensors);
            this.sensors = sensors;
            /** drop old tables and create new ones: */
            sensorDatabase.restart();
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
        List<JSONObject> data = sensorDatabase.getAllSensorData(sensorName);
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
