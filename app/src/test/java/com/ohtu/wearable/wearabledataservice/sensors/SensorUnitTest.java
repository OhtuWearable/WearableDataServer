package com.ohtu.wearable.wearabledataservice.sensors;

import android.content.Context;
import android.hardware.SensorManager;

import com.ohtu.wearable.wearabledataservice.CustomRobolectricRunner;
import com.ohtu.wearable.wearabledataservice.shadows.ShadowSensorUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.shadows.ShadowEnvironment;
import org.robolectric.shadows.ShadowSensorManager;
import org.robolectric.shadows.ShadowVMRuntime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.robolectric.Shadows.shadowOf;

/**
 * Created by jannetim on 24/03/15.
 */
@RunWith(CustomRobolectricRunner.class)
public class SensorUnitTest {

    private SensorManager sensorManager;
    private ShadowSensorManager shadow;
    @Before
    public void setup() {
        sensorManager = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
        shadow = shadowOf(sensorManager);
    }

    @Test
    public void doNothing() {
    }

    @Test
    public void sensorListenerIsRegistered() {
        ShadowSensorUnit shadowSensorUnit = new ShadowSensorUnit();
        shadowSensorUnit.setSensor(shadow.getDefaultSensor(1), RuntimeEnvironment.application.getBaseContext());
        assertThat(shadowSensorUnit.listenSensor(), is(true));
    }
}
