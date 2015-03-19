package com.ohtu.wearable.wearabledataservice.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ohtu.wearable.wearabledataservice.R;
import com.ohtu.wearable.wearabledataservice.SensorServerService;

/**
 * Fragment class which shows the opening view of the program with buttons to
 * start or stop the server and monitor the battery.
 */
public class FragmentThree extends Fragment {

    /**
     * Creates the UI of the fragment and assigns click listeners to the two buttons.
     * @param inflater Inflater of the layout.
     * @param container Parent view of the fragment if available.
     * @param savedInstanceState Contains fragment's save state if available.
     * @return View of the fragment's ui.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.button_layout,container,false);

        final LinearLayout layout = (LinearLayout) view.findViewById(R.id.root_layout);
        final Button bn1 = (Button) view.findViewById(R.id.btnaddnewtext1);
        final Button bn2 = (Button) view.findViewById(R.id.btnaddnewtext2);

        bn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Code for Activities what will appears when user click button
                
                //TODO: toistaiseksi jos yrittaa kutsua metodia startServer - kaatuu.
                //kuuntelijaan nappulan tilan tarkistus, onko serveri stopattu vai kaynnissa -
                //jotta tietaa kumpaa metodia kutsuu

                SensorServerService sensorServerService = new SensorServerService();
                sensorServerService.stopServer();
                //pitaa muuttaa niin, etta teksti tulee metodista
                //Toast.makeText(v.getContext(), "Server stopped", Toast.LENGTH_SHORT).show();
                //TextView tv1 = new TextView(v.getContext());
                //tv1.setText("...");
                //layout.addView(tv1);
                
                /*sensorServerService.startServer();
                //tai 
                /*MainActivity  mainActivity= new MainActivity();
                mainActivity.startServerService();
                Toast.makeText(v.getContext(), "Server started", Toast.LENGTH_SHORT).show();*/
            }

        });

        bn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Toast.makeText(v.getContext(), "Battery Level: " + batteryLevel(v.getContext()), Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    /**
     * Method for showing the battery level of the device.
     * @param context Context of the fragment.
     * @return String containing the battery charge.
     */
    private String batteryLevel(Context context) {

        Intent intent;
        intent = context.registerReceiver(null, new
                IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int    level   = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        int    scale   = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
        int    percent = (level*100)/scale;
        return String.valueOf(percent) + "%";
    }

    //TODO: Start and Stop server if needed

}
