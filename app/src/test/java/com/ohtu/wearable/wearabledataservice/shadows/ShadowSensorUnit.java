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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by jannetim on 31/03/15.
 */
@Implements(SensorUnit.class)
public class ShadowSensorUnit {

    @RealObject private SensorManager sensorManager;
    @RealObject private Sensor sensor;
    private ShadowSensorManager shadow;

    @Implementation
    public void setSensor(Sensor sensor, Context context) {
        this.sensor = sensor;
        sensorManager = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
        shadow = shadowOf(sensorManager);
    }

    @Implementation
    public boolean listenSensor() {
        SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        shadow.registerListener(listener, sensor, 5000);
        if (shadow.hasListener(listener)) {
            return true;
        } else {
            return false;
        }
    }
}
