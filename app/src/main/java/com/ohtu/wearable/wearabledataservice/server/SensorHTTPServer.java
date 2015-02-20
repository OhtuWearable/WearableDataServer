package com.ohtu.wearable.wearabledataservice.server;

import android.hardware.Sensor;
import android.util.Log;

import com.example.wearable.wear.controllers.FeedsController;
import com.example.wearable.wear.models.Sensors;

import java.util.HashMap;
import java.util.List;

/**
 *
 */
public class SensorHTTPServer extends NanoHTTPD {

    private FeedsController feedsController;

    /**
     * HTTP server, passes all HTTP requests to FeedsController
     *
     * @param sensors
     * @param sensorList
     */
    public SensorHTTPServer(SensorUnit sensors, List<Sensor> sensorList)
    {
        super(8080);
        this.feedsController = new FeedsController(sensors, sensorList);
    }

    /**
     * @param session The HTTP session
     * @return NanoHTTPD.Response
     */
    @Override
    public Response serve(IHTTPSession session) {
        Log.w("websrvr", session.getHeaders().toString());

        String parsedUri = "";
        String uri = session.getUri();

        String method = session.getMethod().toString();
        Log.w("websrvr", "method: " + method);

        if (uri.length() >= 6) {
            parsedUri = uri.substring(0,6);
        } else {
            return new NanoHTTPD.Response(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Not Found");
        }

        if (parsedUri.equals("/feeds")) {
            String feedsUri = "";
            if (parsedUri.length()>=6){
                feedsUri = uri.substring(6);
            }
            //retrieve answer from FeedsController
            NanoHTTPD.Response response = feedsController.getResponse(feedsUri, method);
            return response;
        } else {
            return new NanoHTTPD.Response(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "Not Found");
        }
    }
}