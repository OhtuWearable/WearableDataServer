package com.ohtu.wearable.wearabledataservice.sensors;

import android.content.Context;
import android.hardware.SensorManager;

import com.ohtu.wearable.wearabledataservice.CustomRobolectricRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import static org.robolectric.Shadows.shadowOf;

/**
 * Created by jannetim on 24/03/15.
 */
@RunWith(CustomRobolectricRunner.class)
public class SensorUnitTest {

    private SensorManager sensorManager;

    @Before
    public void setup() {
        sensorManager = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
    }

    @Test
    public void doNothing() {

    }

    @Test
    public void sensorListenerIsRegistered() {
        SensorUnit sensorUnit = new SensorUnit();
        sensorUnit.setSensor(sensorManager.getDefaultSensor(1), RuntimeEnvironment.application.getBaseContext());

    }
}
