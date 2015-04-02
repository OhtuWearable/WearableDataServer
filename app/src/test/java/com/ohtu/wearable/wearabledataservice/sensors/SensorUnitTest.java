package com.ohtu.wearable.wearabledataservice.sensors;

import android.content.Context;
import android.hardware.SensorManager;

import com.ohtu.wearable.wearabledataservice.CustomRobolectricRunner;
import com.ohtu.wearable.wearabledataservice.shadows.ShadowSensorUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RuntimeEnvironment;

import org.robolectric.annotation.Config;
import org.robolectric.internal.ShadowExtractor;
import org.robolectric.shadows.ShadowSensorManager;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by jannetim on 24/03/15.
 */
@Config(shadows={ShadowSensorUnit.class})
@RunWith(CustomRobolectricRunner.class)
public class SensorUnitTest {

    private SensorManager sensorManager;
    private ShadowSensorManager shadow;
    private SensorUnit sensorUnit;
    private ShadowSensorUnit shadowSensorUnit;

    @Before
    public void setup() {
        sensorManager = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
        shadow = shadowOf(sensorManager);
        //sensorUnit = new SensorUnit();
        //shadowSensorUnit = (ShadowSensorUnit) ShadowExtractor.extract(sensorUnit);
        shadowSensorUnit = new ShadowSensorUnit();
    }

    @After
    public void tearDown() {

    }


    @Test
    public void sensorListenerIsRegistered() {
        shadowSensorUnit.setSensor(shadow.getDefaultSensor(1), RuntimeEnvironment.application.getBaseContext());
        assertThat(shadowSensorUnit.listenSensor(), is(true));
    }

@Test
    public void sensorListenerIsFirstRegisteredAndThenUnregistered() {
        shadowSensorUnit.setSensor(shadow.getDefaultSensor(1), RuntimeEnvironment.application.getBaseContext());
        assertThat(shadowSensorUnit.listenSensor(), is(true));
        //shadowSensorUnit.stopListening();
        assertThat(shadowSensorUnit.stopListening(), is(true));
    }
}
