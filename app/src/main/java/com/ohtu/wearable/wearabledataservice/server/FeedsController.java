package com.ohtu.wearable.wearabledataservice.server;

/**
 * Returns device sensors data as JSON in NanoHTTPD.Response based on uri
 */
public class FeedsController {

    private List<Sensor> sensorList;
    private SensorUnit sensors;
    private List<Integer> sensorIds;
    private SensorsHandler sensorsHandler;

    public FeedsController(SensorUnit sensors, List<Sensor> sensorList){

        this.sensorList = sensorList;
        this.sensorIds = generateSensorIds(sensorList);
        this.sensors = sensors;
        this.sensorsHandler = new SensorsHandler();
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
            try {
                return new NanoHTTPD.Response(sensorsHandler.sensorListToJSON(sensorList).toString());
            } catch (JSONException e) {
                return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR, NanoHTTPD.MIME_PLAINTEXT, "Error");
            }
        } else if ( sensorIds.contains(sensor) && uri.equalsIgnoreCase("/" + sensor) && method.equals("GET")){
            try {
                sensors.setSensor(sensor);
                return new NanoHTTPD.Response(sensors.getSensorData().toString());
            } catch (JSONException e) {
                return notFoundResponse();
            }
        } else {
            return notFoundResponse();
        }

    }

    //Returns NOT FOUND HTTP response
    private NanoHTTPD.Response notFoundResponse(){
        return new NanoHTTPD.Response(NanoHTTPD.Response.Status.NOT_FOUND, NanoHTTPD.MIME_PLAINTEXT, "Not Found");
    }


}