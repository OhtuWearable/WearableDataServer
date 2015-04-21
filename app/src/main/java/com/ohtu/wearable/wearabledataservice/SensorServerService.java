package com.ohtu.wearable.wearabledataservice;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.ohtu.wearable.wearabledataservice.sensors.SensorUnit;
import com.ohtu.wearable.wearabledataservice.sensors.SensorsHandler;
import com.ohtu.wearable.wearabledataservice.server.FeedsController;
import com.ohtu.wearable.wearabledataservice.sensors.SensorDatabase;
import com.ohtu.wearable.wearabledataservice.server.SensorHTTPServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Run SensorHTTPServer as a bound foreground service
 */
public class SensorServerService extends Service {

    private boolean serverStarted = false;
    private boolean serverRunning = false;
    private boolean serviceStarted = false;
    private SensorHTTPServer server;
    private SensorsHandler sensorsHandler;
    SensorDatabase sensorDatabase;
    SQLiteDatabase db;

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        SensorServerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return SensorServerService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //if service is not already started start it as a foreground service
        if (!serviceStarted) {
            if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {

                Notification notification = new Notification.Builder(this).build();
                //ToDo: custom icon for notification, when notification is clicked start ui
                /*
                Intent notificationIntent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
                Notification notification = new Notification(R.drawable.common_signin_btn_icon_dark, "service running", System.currentTimeMillis());
                notification.setLatestEventInfo(this, "DataServer", "service started", pendingIntent);
                */
                startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);
                serviceStarted = true;
            }
        }

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }



    /**
     * Start the HTTP server and database if it's not running,
     * Otherwise updates used sensors and database entries.
     */
    public void startServer(List<Sensor> sensors){
        if (db == null && sensors != null) {
            sensorDatabase = new SensorDatabase(this, sensorsHandler.getAllSensorsOnDevice());
            sensorDatabase.deleteEntries();
            db = sensorDatabase.getWritableDatabase();
            Log.w("DB", "started");
            testDummyData(sensorDatabase);
        }

        if (serverStarted && serverRunning){
            if (sensors != null) {
                sensorsHandler.initSensors(sensors);

                //Add selected sensor items to database:
                if (sensorDatabase != null) {
                    //on list update, add all sensor units to database:
                    List<SensorUnit> units = sensorsHandler.getSensorUnits(sensors);
                    String s = "";
                    for (SensorUnit unit : units) {
                        //Log.d("Adding sensor items", "* * * "  + unit.getSensorName());
                        s = unit.getSensorName();
                        sensorDatabase.addSensorUnit(unit);
                    }
                    /*
                    try {
                        if (s != "") {
                            Log.d("Sensor name", s);
                            Log.d("ADDED DATA: ", sensorDatabase.getAllSensorData(s).toString());
                        }
                    } catch (JSONException e) {

                    }
                    */
                    //testDummyData(sensorDatabase);
                }
            }
            Log.w("SERVER", "sensors updated");
        } else if (serverStarted && !serverRunning) {
            tryToStartServer();
        } else {
            sensorsHandler = new SensorsHandler(sensors, this);
            FeedsController feedsController = new FeedsController(sensorsHandler);
            server = new SensorHTTPServer(feedsController);

            tryToStartServer();
        }
    }

    //test database with dummy data:
    private void testDummyData(SensorDatabase helper) {
        //TODO: remove dummy data testing
        //---- dummy data for testing the database, remove

        List<Sensor> sensorList = sensorsHandler.getAllSensorsOnDevice();
        SensorUnit unit = new SensorUnit();
        unit.setSensor(sensorList.get(2), this);
        unit.setDummyData();
        //helper.addSensorUnit(unit);
        try {
            //helper.addSensorUnit(unit);
            List<JSONObject>  a = helper.getAllSensorData(unit.getSensorName());
            //Log.d("getJSONSensorData: ", helper.getJSONSensorData(unit.getSensorName(), 0).toString());
            Log.d("JSONOBJECTS AS A LIST: ", a.toString());
            //helper.emptySensorTable(unit.getSensorName());
            //List<JSONObject>  b = helper.getAllSensorData(unit.getSensorName());
            //Log.d("JSONOBJECTS AS A LIST: ", b.toString());

            //helper.createTables();
                /*
                List<JSONObject>  b = helper.getAllSensorData(unit.getSensorName());
                Log.d("JSONOBJECTS AS A LIST: ", b.toString());
                unit.setDummyData2();
                Log.d("asdf" , unit.getSensorData().toString());
                helper.updateDataEntry(unit, 0);
                a = helper.getAllSensorData(unit.getSensorName());
                Log.d("JSONOBJECTS AS A LIST: ", a.toString());

                /*
                helper.addSensorUnit(unit);
                a = helper.getAllSensorData(unit.getSensorName());
                Log.d("JSONOBJECTS AS A LIST: ", a.toString());
                helper.deleteEntries();
                a = helper.getAllSensorData(unit.getSensorName());
                Log.d("JSONOBJECTS AS A LIST: ", a.toString());
                */
        } catch (JSONException e) {

        }
    }

    /**
     * Stops the server if it is running
     */
    public void stopServer(){
        if (serverStarted) {
            server.stop();
            serverRunning = false;
            Toast.makeText(this, "Server stopped", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Is server running
     *
     * @return true if server running
     */
    public boolean isRunning(){
        return serverRunning;
    }

    /** If service is destroyed, stop server and empty and close the database */
    @Override
    public void onDestroy (){
        sensorsHandler.stopSensors();
        server.stop();
        sensorDatabase.deleteEntries();
        db.close();
    }

    private void tryToStartServer(){
        try {
            server.start();
            serverStarted = true;
            serverRunning = true;
            //Shows "Server started" message on screen
            Toast.makeText(this, "Server started", Toast.LENGTH_SHORT).show();

        } catch (IOException ioe) {
            //Shows "Server failed to start" message on screen
            Toast.makeText(this, "Server failed to start", Toast.LENGTH_SHORT).show();
        }
    }




}
