package com.ohtu.wearable.wearabledataservice.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.wearable.view.WearableListView;
import com.ohtu.wearable.wearabledataservice.CustomRobolectricRunner;
import com.ohtu.wearable.wearabledataservice.R;
import com.ohtu.wearable.wearabledataservice.fragments.FragmentOne;
import com.ohtu.wearable.wearabledataservice.MainActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.util.SupportFragmentTestUtil;

import java.util.List;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(CustomRobolectricRunner.class)
public class FragmentOneTest {
    private SensorManager sensorManager;
    private FragmentOne fragment;
    private MainActivity activity;
    private WearableListView listView;
    private List<Sensor> sensors;

    @Before
    public void setUp() {
        sensorManager = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
        sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        //accelerometer  null
        //orientation  null
        //temperature  null
        sensors.add(sensorManager.getDefaultSensor(1));
        sensors.add(sensorManager.getDefaultSensor(3));
        sensors.add(sensorManager.getDefaultSensor(7));

        activity = Robolectric.buildActivity(MainActivity.class).create().get();
        fragment = new FragmentOne();
        fragment.setFragmentSensors(sensors);
        SupportFragmentTestUtil.startFragment(fragment);
        listView = (WearableListView) fragment.getView().findViewById(R.id.wearable_list);
    }

    @Test
    public void fragmentShouldNotBeNull() throws Exception {
        assertThat(fragment, notNullValue());
    }

    /** Tests that a list of sensors is created and it's length is correct: */
    @Test
    public void sensorListShouldNotBeNull() throws Exception {
        assertThat(sensors, notNullValue());
        assertThat(sensors.size(), equalTo(3));
    }
    @Test
    public void activityShouldNotBeNull() throws Exception {
        assertThat(activity, notNullValue());
    }

    @Test
    public void listViewIsCreated() throws Exception {
        assertThat(listView, notNullValue());
    }

    @Test
    public void adapterIsCreated() throws Exception {
        assertThat(listView.getAdapter(), notNullValue());
    }

    /** Tests that correct list is passed to the adapter */
    @Test
    public void populatedListIsCreated() throws Exception {
        assertThat(listView.getAdapter().getItemCount(), notNullValue());
        assertThat(listView.getAdapter().getItemCount(), not(0));
        assertThat(listView.getAdapter().getItemCount(), equalTo(3));
    }

    /*
    @Test
    public void listViewHasACheckBox() {
        //listView.getAdapter().notifyDataSetChanged();

        CheckBox check = (CheckBox) listView.findViewById(R.id.checkbox);
        assertThat(check, notNullValue());
    }

    /*
    @Test
    public void firstSensorIsNotNull() throws Exception {
        assertThat(sensors.get(0), notNullValue());
    }
    */
    /*
    @Test
    public void checkBoxCanBeClicked() {
        CheckBox check = (CheckBox) listView.findViewById(R.id.checkbox);
        assertThat(check, notNullValue());
        check.performClick();
        //check.setChecked(true);

        assertThat(check.isChecked(), is(true));
        //get sensor list after click
        //assert they are different
    }

    @Test
    public void sensorListShouldNotBeEmpty() {

        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        assertThat(sensors, notNullValue());
        assertThat(sensors.size(), not(0));

    }

    @Test
    public void shadowSensorListIsNotNull() {
        SensorManager sensorManager = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
        ShadowSensorManager shadow = shadowOf(sensorManager);

        assertThat(shadow, notNullValue());
        Sensor sensor = shadow.getDefaultSensor(1);
        sensors.add(shadow.getDefaultSensor(1));
        assertThat(sensor, equalTo(sensors.get(0)));
        //assertThat(sensors, notNullValue());
        sensors.add(sensorManager.getDefaultSensor(3));
    }
    */

}