package com.ohtu.wearable.wearabledataservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Starts the server service as bound foreground service
 */

public class StartServerActivity extends Activity
{
    //private TextView mTextView;
    SensorServerService serverService = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.hello);
        mTextView.setText(getLocalIpAddress());
        */

        //Start server as foreground service
        Intent startIntent = new Intent(this, SensorServerService.class);
        startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
        startService(startIntent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intentBind = new Intent(this, SensorServerService.class);
        bindService(intentBind, mConnection, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unbindService(mConnection);
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            SensorServerService.LocalBinder b = (SensorServerService.LocalBinder) service;
            serverService = b.getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            serverService = null;
        }
    };

    /*
    //returns devices ip address
    private static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    */

}