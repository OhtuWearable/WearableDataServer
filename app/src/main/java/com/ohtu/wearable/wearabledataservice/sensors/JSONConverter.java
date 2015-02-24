package com.ohtu.wearable.wearabledataservice.sensors;

import android.hardware.Sensor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static java.lang.String.valueOf;

import java.util.List;

/**
 * Created by jannetim on 10/02/15.
 */
public class JSONConverter {


    public JSONArray getAllSensorsData() throws JSONException {
        JSONArray jsonArray = new JSONArray();
        //jsonArray.put(getAccelerometerData());
        //jsonArray.put(getMagneticFieldData());
        //jsonArray.put(getGravityData());
        //other get<Sensor>Datas

        return jsonArray;
    }

/*    public JSONObject getAccelerometerData(SensorInterface accelerometer) throws JSONException {
        //Accelerometer accelerometer = new Accelerometer();
        //accelerometer.setContext(this);
        return convertToJSON(accelerometer);
    }*/

    /**
     * Converts input float[] to JSONObject
     * @param data
     * @return
     * @throws JSONException
     */
    public JSONObject convertToJSON(float[] data) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        char prefix = 'x';
        for (int i = 0; i<data.length;i++) {
            jsonObject.put(valueOf(prefix), data[i]);
            prefix++;
        }
        return jsonObject;
    }

    public JSONObject convertSensorListToJSON(List<Sensor> sensorList) throws JSONException {
        JSONObject jsonObject = new JSONObject();

        for (Sensor s : sensorList) {
            String prefix = s.getName();
            jsonObject.put(valueOf(prefix), s.getType());
        }
        return jsonObject;
    }
}