package com.ohtu.wearable.wearabledataservice.sensors;

import android.app.Activity;

import com.ohtu.wearable.wearabledataservice.CustomRobolectricRunner;
import com.ohtu.wearable.wearabledataservice.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

/**
 * Created by jannetim on 24/03/15.
 */
@RunWith(CustomRobolectricRunner.class)
public class JSONConverterTest {

    @Before
    public void setup() {

    }

    @Test
    public void convertToJSONShouldNotReturnNullEvenIfInputParameterNull() throws JSONException {
        assertThat(JSONConverter.convertToJSON(null), notNullValue());
    }

    @Test
    public void convertToJSONShouldReturnEmptyJSONifInputParameterNull() throws JSONException {
        assertThat(JSONConverter.convertToJSON(null).toString(), is("{}"));
    }

    @Test
    public void joku() {

    }


}
