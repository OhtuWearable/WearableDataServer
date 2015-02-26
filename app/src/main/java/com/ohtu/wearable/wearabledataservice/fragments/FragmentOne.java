package com.ohtu.wearable.wearabledataservice.fragments;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.wearable.view.WearableListView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ohtu.wearable.wearabledataservice.R;
import com.ohtu.wearable.wearabledataservice.SelectedSensorsInterface;
import com.ohtu.wearable.wearabledataservice.list.WearableListAdapter;

import java.util.List;

public class FragmentOne extends Fragment implements WearableListView.ClickListener {

    //Sensors for listing
    List<Sensor> elements;

    private RecyclerView mRecyclerView;
    private WearableListAdapter mAdapter;
    private Helper mHelper;

    //Setting context on Helper to help adding adapter in the onCreateView-method
    @Override
    public void onAttach(Activity activity){
        super.onAttach (activity);
        mHelper = new Helper (activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Get sensor list manually
        elements = getSensors();

        //call MainActivitys setSelectedSensors method and pass list of sensors to it
        ((SelectedSensorsInterface)getActivity()).setSelectedSensors(elements);

        View view=inflater.inflate(R.layout.fragment_one_layout, container,false);

        // Get the list component from the layout of the activity
        WearableListView listView =
                (WearableListView) view.findViewById(R.id.wearable_list);
        mAdapter = new WearableListAdapter(this, elements);
        // Assign an adapter to the list
        listView.setAdapter(mAdapter);

        if (listView.getAdapter() == null) {
            Log.d("Nullcheck is null", "");
        }

        // Set a click listener
        listView.setClickListener(this);

        //Add sensors for activity
        return view;

    }

    //Add selected sensors for MainActivity
    public void setSensors(List<Sensor> sensors) {
        ((SelectedSensorsInterface)getActivity()).setSelectedSensors(sensors);
    }


    // WearableListView click listener
    @Override
    public void onClick(WearableListView.ViewHolder v) {
        Integer tag = (Integer) v.itemView.getTag();
    }

    @Override
    public void onTopEmptyRegionClick() {
    }

    @Override
    public void onDetach(){
    }


    //Get sensor data from the device; probably needs a better place
    public List<Sensor> getSensors() {
        SensorManager mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        return deviceSensors;
    }

    //Helper class to get context for onCreateView
    public class Helper {
        Context context;
        Activity activity;

        public Helper(Context context) {
            this.context = context;
        }
        public Context getHelper() {
            return this.context;
        }
    }

}