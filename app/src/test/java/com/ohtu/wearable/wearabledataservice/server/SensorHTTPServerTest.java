package com.ohtu.wearable.wearabledataservice.server;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.ohtu.wearable.wearabledataservice.CustomRobolectricRunner;
import com.ohtu.wearable.wearabledataservice.sensors.SensorsHandler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by sjsaarin on 27.3.2015.
 */
@RunWith(CustomRobolectricRunner.class)
public class SensorHTTPServerTest {

    private SensorsHandler sensorsHandler;
    private FeedsController feedsController;
    private SensorHTTPServer server;
    @Before
    public void setup(){
        SensorManager sensorManager = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorsHandler = new SensorsHandler(sensorList, RuntimeEnvironment.application.getBaseContext());
        feedsController = new FeedsController(sensorsHandler);
        server = new SensorHTTPServer(feedsController);
    }

    @Test
    public void serverStarts(){
        try {
            server.start();
        } catch (IOException e) {
        }
        assertThat(server.wasStarted(), is(true));
    }

}
