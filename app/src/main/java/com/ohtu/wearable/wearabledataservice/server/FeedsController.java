package com.ohtu.wearable.wearabledataservice.server;

import com.ohtu.wearable.wearabledataservice.sensors.SensorsHandler;

import org.json.JSONException;

/**
 * Returns device sensors data as JSON in NanoHTTPD.Response based on uri
 */
public class FeedsController {

    private SensorsHandler sensorsHandler;

    public FeedsController(SensorsHandler sensorsHandler){

        this.sensorsHandler = sensorsHandler;
    }

    /**
     * Returns NanoHTTPD.Response containing sensor data as JSON
     * If uri is feeds or feeds/ returns list of all available sensors
     * If uri is feeds/[sensorId] returns data from that sensor if sensor is available
     * if sensor is not availabe returns NOT FOUND response
     *
     * @param uri, URI
     * @param method, HTTP method
     * @return
     */
    public NanoHTTPD.Response getResponse(String uri, String method) {

        int sensor = -1;

        //try to parse sensor number from feeds/[sensor] if not integer catch exception
        if (uri.length() > 1){
            try {
                sensor = Integer.parseInt(uri.substring(1));
            } catch (NumberFormatException e){
            }
        }

        //if uri is / or empty return list of sensor
        //else if list of sensors contain sensor number parsed from uri return sensor data
        //otherwise return not found
        if ((uri.equalsIgnoreCase("/") || uri.isEmpty()) && method.equals("GET")) {
            return listResponse();
        } else if ( sensorsHandler.sensorIsActive(sensor) && uri.equalsIgnoreCase("/" + sensor) && method.equals("GET")){
            return sensorDataResponse(sensor);
        } else if (method.equals("POST")) {
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.METHOD_NOT_ALLOWED, NanoHTTPD.MIME_PLAINTEXT, "Not implemented");
        } else {
            return notFoundResponse();
        }

    }

    private NanoHTTPD.Response listResponse(){
        try {
            return new NanoHTTPD.Response(sensorsHandler.getSensorsList().toString());
        } catch (JSONException e) {
            return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "Error");
        }
    }

    private NanoHTTPD.Response sensorDataResponse(int sensor){
        try {
            return new NanoHTTPD.Response(sensorsHandler.getSensorData(sensor).toString());
        } catch (JSONException e) {
            return errorResponse(e.toString());
        }
    }

    //Returns NOT FOUND HTTP response
    private NanoHTTPD.Response notFoundResponse(){
        return new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Not Found");
    }

    private NanoHTTPD.Response errorResponse(String error){
        return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, error);
    }

}