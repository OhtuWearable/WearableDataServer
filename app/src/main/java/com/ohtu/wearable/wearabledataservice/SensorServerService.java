package com.ohtu.wearable.wearabledataservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import com.ohtu.wearable.wearabledataservice.server.SensorHTTPServer;

import java.io.IOException;

/**
 * Run SensorHTTPServer as a bound foreground service
 */
public class SensorServerService extends Service {

    private boolean serverStarted = false;
    private SensorHTTPServer server;

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

        //if service is not started start it as a foreground service
        if (!serverStarted) {
            if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
                Intent notificationIntent = new Intent(this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
                Notification notification = new Notification(R.drawable.common_signin_btn_icon_dark, "foofoo", System.currentTimeMillis());
                notification.setLatestEventInfo(this, "SENSORHTTPSERVER", "service started", pendingIntent);
                startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE, notification);
            }

            serverStarted = startServer();
        }

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    /**
     * try to start the HTTP server, if successful return true
     *
     * @return
     */
    private boolean startServer(){
        server = new SensorHTTPServer(activeSensors, getSensorList());
        try {
            server.start();
            //Shows "Server started" message on screen (doesn't work on wearable?)
            Toast.makeText(this, "Server started", Toast.LENGTH_SHORT).show();
            return true;

        } catch (IOException ioe) {
            //Shows "Server failed to start" message on screen (doesn't work on wearable?)
            Toast.makeText(this, "Server failed to start", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    @Override
    public void onDestroy (){
        //if service is destroyed stop server
        server.stop();
    }



}
