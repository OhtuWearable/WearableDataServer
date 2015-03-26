package com.ohtu.wearable.wearabledataservice.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.ohtu.wearable.wearabledataservice.CustomRobolectricRunner;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by jannetim on 24/03/15.
 */
@RunWith(CustomRobolectricRunner.class)
public class SensorsHandlerTest {
    private SensorManager sensorManager;
    private SensorsHandler sensorsHandler;
    private List<Sensor> sensorList;

    @Before
    public void setup() {
        sensorManager = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorsHandler = new SensorsHandler(sensorList, RuntimeEnvironment.application.getBaseContext());
    }

    @Test
    public void shouldReturnListOfSensorsSupportedByDevice() {
        assertThat(sensorsHandler.getAllSensorsOnDevice(), is(sensorList));
    }

    @Test
    public void listNotEmpty() {
        sensorList.add(sensorManager.getDefaultSensor(2));
        assertThat(sensorList.isEmpty(), is(false));
    }

    @Test
    public void sensorsAreAvailableAfterInitSensors() {
        sensorsHandler.initSensors(sensorList);
        assertThat(sensorsHandler.getAvailableSensors(), is(sensorList));
    }

    /*@Test
    public void sensorIsActiveAfterCreatingSensorsHandler() {
        List<Sensor> list = new ArrayList();
        list.add(sensorManager.getDefaultSensor(2));
        SensorsHandler sh2 = new SensorsHandler(list, RuntimeEnvironment.application.getBaseContext());
        assertThat(sh2.sensorIsActive(2), is(true));
    } */

}
