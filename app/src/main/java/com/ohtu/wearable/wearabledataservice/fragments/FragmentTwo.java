package com.ohtu.wearable.wearabledataservice.fragments;

import android.hardware.Sensor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ohtu.wearable.wearabledataservice.R;
import com.ohtu.wearable.wearabledataservice.SelectedSensorsInterface;

import java.util.List;

/**
 * Fragment containing a TextView showing the previously made selections.
 */
public class FragmentTwo extends Fragment {
    List<Sensor> sensorList;
    TextView mTextView;

    /**
     * Fragment which contains the TextView of the selections.
     * @param inflater Inflater of the layout.
     * @param container Parent view of the fragment if available.
     * @param savedInstanceState Contains fragment's save state if available.
     * @return View of the fragment's ui.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_two_layout, container, false);

        mTextView = (TextView) view.findViewById(R.id.textView2);
        mTextView.setText("");

        return view;
    }

    /**
     * Called when the fragment is being resumed. Gets list of sensors from MainActivity and shows them
     * in the TextView.
     */
    @Override
    public void onResume(){
        super.onResume();
        sensorList = ((SelectedSensorsInterface) getActivity()).getSelectedSensors();
        if (sensorList != null) {
            for (Sensor s: sensorList) {
                mTextView.append(s.getName() + "\n");
            }
        }
    }

}