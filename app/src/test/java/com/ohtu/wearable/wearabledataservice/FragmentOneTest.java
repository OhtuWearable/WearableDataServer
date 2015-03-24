package com.ohtu.wearable.wearabledataservice;

import android.app.Activity;
import android.hardware.Sensor;
import android.support.wearable.view.WearableListView;
import com.ohtu.wearable.wearabledataservice.fragments.FragmentOne;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.util.SupportFragmentTestUtil;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(CustomRobolectricRunner.class)
public class FragmentOneTest {
    private FragmentOne fragment;


    @Before
    public void setUp() {
        fragment = new FragmentOne();
        SupportFragmentTestUtil.startFragment(fragment);
    }


    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull( fragment );
    }

    @Test
    public void sensorListShouldNotBeNull() throws Exception {
        List<Sensor> sensors = fragment.getSensors();
        assertNotNull(sensors);
    }

    @Test
    public void activityShouldNotBeNull() throws Exception {
        Activity activity = fragment.getActivity();
        assertNotNull(activity);
    }

}
