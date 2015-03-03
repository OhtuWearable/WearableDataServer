package com.ohtu.wearable.wearabledataservice;

import android.app.Activity;
import android.hardware.Sensor;

import com.ohtu.wearable.wearabledataservice.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by sjsaarin on 3.3.2015.
 */
@RunWith(CustomRobolectricRunner.class)
public class MainActivityTest {

    //private final ActivityController<MainActivity> controller = buildActivity(MainActivity.class);

    @Test
    public void testIt() {
        Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();
        assertThat(activity, notNullValue());
    }

    @Test
    public void whenSensorAreNotSetGetSelectedSensorsReturnsNull() {
        SelectedSensorsInterface ssi = Robolectric.buildActivity(MainActivity.class).create().get();
        assertThat(ssi.getSelectedSensors(), nullValue());
    }

    @Test
    public void whenSensorAreSetGetSelectedSensorsDoesntReturnsNull() {
        SelectedSensorsInterface ssi = Robolectric.buildActivity(MainActivity.class).create().get();
        List<Sensor> sensors = new ArrayList<>();
        ssi.setSelectedSensors(sensors);
        assertThat(ssi.getSelectedSensors(), notNullValue());
    }

    @Test
    public void whenMainActivityIsStartedSomethingHappens() {
        Activity activity = Robolectric.buildActivity(MainActivity.class).create().get();

    }

}
