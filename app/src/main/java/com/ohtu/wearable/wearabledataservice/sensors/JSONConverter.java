package com.ohtu.wearable.wearabledataservice.sensors;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.String.valueOf;

import java.util.Date;
import java.util.List;

/**
 * Class that converts native sensor value from java array format to JSON-format
 */
public class JSONConverter {

    /**
     * Converts input float[] to JSONObject
     * @param data
     * @return
     * @throws JSONException
     */
    public static JSONObject convertToJSON(double[] data) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (data==null) {
            return jsonObject;
        } else {
            char prefix = 'x';
            for (int i = 0; i < data.length; i++) {
                jsonObject.put(valueOf(prefix), data[i]);
                if (prefix == 'z') {
                    prefix = 'a';
                } else {
                    prefix++;
                }
            }
            //Log.d("JSON", jsonObject.toString());
            return jsonObject;
        }
    }

    /**
     * Converts list of sensor objects to readable JSON format
     * @param sensorList List of Sensor-objects
     * @return JSONObject containing information of all if any sensors given in the list
     * @throws JSONException
     */
    public static JSONObject convertSensorListToJSON(List<Sensor> sensorList) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        for (Sensor s : sensorList) {
            String prefix = s.getName();
            jsonObject.put(valueOf(prefix), s.getType());
        }
        return jsonObject;
    }

    /** Add SensorUnit data to database
     * Currently converts SensorUnits name as a string and double[] data as JSONObject to a
     * JSONObject, which is saved to the database.
     * */
    public static JSONObject convertToDatabaseJSON(SensorUnit unit) {
        try {
            JSONObject jEntry = new JSONObject();
            //jEntry.put("sensor", unit.getSensorName());
            jEntry.put("data", unit.getSensorData());

            return jEntry;
        } catch (JSONException e) {
            Log.e("convertToDatabaseJSON", "Could not generate the JSON object for the database entry");
            return null;
        }
    }


}