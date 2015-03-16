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

public class FragmentThree extends Fragment {

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
                
                //TODO: toistaiseksi jos yrittää kutsua metodia startServer - kaatuu.
                //kuuntelijaan nappulan tilan tarkistus, onko serveri stopattu vai käynnissä - 
                //jotta tietää kumpaa metodia kutsuu

                SensorServerService sensorServerService = new SensorServerService();
                sensorServerService.stopServer();
                //pitää muuttaa niin, että teksti tulee metodista
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
                //TODO: Code for Activities what will appears when user click button
                //Bn2 Have to show Battery Level in Tv2(TextArea) or in Notification

                //TextView tv2 = new TextView(v.getContext());
                Toast.makeText(v.getContext(), "Battery Level: " + batteryLevel(v.getContext()), Toast.LENGTH_LONG).show();
                //tv2.setText("Battery Level: " + batteryLevel(v.getContext()));
                //layout.addView(tv2);

            }
        });

        return view;
    }

    //Method Shows Battery Level
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
