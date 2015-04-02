package com.ohtu.wearable.wearabledataservice;

import android.app.Activity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by sjsaarin on 2.4.2015.
 */
@RunWith(CustomRobolectricRunner.class)
public class StartServerActivityTest {

    @Test
    public void activityIsCreated() {
        Activity activity = Robolectric.buildActivity(StartServerActivity.class).create().get();
        assertThat(activity, notNullValue());
    }
}
