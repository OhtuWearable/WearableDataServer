package com.ohtu.wearable.wearabledataservice.shadows;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.ohtu.wearable.wearabledataservice.sensors.SensorUnit;

import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.RealObject;
import org.robolectric.shadows.ShadowSensorManager;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by jannetim on 31/03/15.
 */
@Implements(SensorUnit.class)
public class ShadowSensorUnit {

    @RealObject private SensorManager sensorManager;
    private Sensor sensor;
    private ShadowSensorManager shadow;
    private SensorEventListener listener;

    @Implementation
    public void setSensor(Sensor sensor, Context context) {
        this.sensor = sensor;
        sensorManager = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
        shadow = shadowOf(sensorManager);
    }

    @Implementation
    public boolean listenSensor() {
        listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        shadow.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (shadow.hasListener(listener)) {
            return true;
        } else {
            return false;
        }
    }

    @Implementation
    public boolean stopListening() {
        shadow.unregisterListener(listener);
        if (shadow.hasListener(listener)) {
            return false;
        } else {
            return true;
        }

    }
}
