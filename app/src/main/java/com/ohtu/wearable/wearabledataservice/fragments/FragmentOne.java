package com.ohtu.wearable.wearabledataservice.fragments;

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

/**
 * Fragment containing a listView which shows the list of all available sensors.
 */
public class FragmentOne extends Fragment implements WearableListView.ClickListener {

    List<Sensor> elements;
    private RecyclerView mRecyclerView;
    private WearableListAdapter mAdapter;

    /**
     * Creates the view by inflating the layout and assigning a custom adapter to the view to track the
     * list and setting a click listener to it.
     * @param inflater inflater of the layout
     * @param container parent view of the fragment
     * @param savedInstanceState contains fragment's save state
     * @return View of the fragment's ui
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        elements = getSensors();
        View view=inflater.inflate(R.layout.fragment_one_layout, container,false);
        WearableListView listView =
                (WearableListView) view.findViewById(R.id.wearable_list);
        mAdapter = new WearableListAdapter(this, elements);
        listView.setAdapter(mAdapter);

        if (listView.getAdapter() == null) {
            Log.d("Nullcheck is null", "");
        }
        listView.setClickListener(this);
        return view;

    }

    /**
     * Passes a list of selected sensors to MainActivity.
     * */
    public void setSensors(List<Sensor> sensors) {
        ((SelectedSensorsInterface)getActivity()).setSelectedSensors(sensors);
    }

    /**
     * Clicklistener for the WearableListView
     * @param v ViewHolder containing the item clicked.
     */
    @Override
    public void onClick(WearableListView.ViewHolder v) {
        //Integer tag = (Integer) v.itemView.getTag();
    }

    /**
     * Called when top of the screen is clicked.
     */
    @Override
    public void onTopEmptyRegionClick() {
    }

    /**
     * Called when the fragment is not connected to its activity.
     */
    @Override
    public void onDetach(){
        super.onDetach();
    }

    /**
     * Fetches all available sensors to be used with the list.
     * @return List of all the sensors on the device.
     */
    public List<Sensor> getSensors() {
        SensorManager mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        return deviceSensors;
    }

}