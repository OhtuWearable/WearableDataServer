package com.ohtu.wearable.wearabledataservice;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

/**
 * Created by sjsaarin on 2.4.2015.
 */
@RunWith(CustomRobolectricRunner.class)
public class SensorServerServiceTest {
    /*
    private List<Sensor> sensorList;
    private SensorServerService service;

    @Before
    public void setup() {
        SensorManager sensorManager = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        Intent startIntent = new Intent(Robolectric.buildActivity(MainActivity.class).create().get(), SensorServerService.class);
        service = new SensorServerService();
        service.onCreate();
        service.onStartCommand(startIntent, 0, 42);
    }

    @Test
    public void serviceStartsServer(){
        service.startServer(sensorList);
        assertThat(service.isRunning(), is(true));
    }

    @After
    public void tearDown(){
        service.onDestroy();
    }*/

}
