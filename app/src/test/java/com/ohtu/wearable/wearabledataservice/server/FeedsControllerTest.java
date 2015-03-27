package com.ohtu.wearable.wearabledataservice.server;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.ohtu.wearable.wearabledataservice.CustomRobolectricRunner;
import com.ohtu.wearable.wearabledataservice.sensors.SensorsHandler;

import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RuntimeEnvironment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by sjsaarin on 20.2.2015.
 */
@RunWith(CustomRobolectricRunner.class)
public class FeedsControllerTest {

    private FeedsController feedsController;
    private SensorsHandler sensorsHandler;

    @Before
    public void setup(){
        SensorManager sensorManager = (SensorManager) RuntimeEnvironment.application.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        sensorsHandler = new SensorsHandler(sensorList, RuntimeEnvironment.application.getBaseContext());
        feedsController = new FeedsController(sensorsHandler);
    }

    @Test
    public void invalidURIWithGETMethodReturnsNotFoundResponse(){
        NanoHTTPD.Response response = new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Not Found");
        NanoHTTPD.Response responseFromFeedsController = feedsController.getResponse("foofoo", "GET");
        assertThat(response.getStatus(), is(responseFromFeedsController.getStatus()));
    }

    @Test
    public void POSTRequestReturnsNotAllowedResponse(){
        NanoHTTPD.Response response = new NanoHTTPD.Response(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED, NanoHTTPD.MIME_PLAINTEXT, "Not implemented");
        NanoHTTPD.Response responseFromFeedsController = feedsController.getResponse("feeds", "POST");
        assertThat(response.getStatus(), is(responseFromFeedsController.getStatus()));
    }

    @Test
    public void emptyURIReturnsSensorList() throws JSONException, IOException {
        NanoHTTPD.Response responseFromFeedsController = feedsController.getResponse("", "GET");
        assertThat(readISToString(responseFromFeedsController.getData(), "UTF8"), is(sensorsHandler.getSensorsList().toString()));
    }
    /*
    @Test
    public void alwaysFailingTest(){
        assertThat(true, is(false));
    }*/

    //helper methods
    public String readISToString(InputStream istream, String encoding) throws IOException {
        return new String(readISFully(istream), encoding);
    }

    private byte[] readISFully(InputStream istream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = istream.read(buffer)) != -1) {
            baos.write(buffer, 0, length);
        }
        return baos.toByteArray();
    }
}
