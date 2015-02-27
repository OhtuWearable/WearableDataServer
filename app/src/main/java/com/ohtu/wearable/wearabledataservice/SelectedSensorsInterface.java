package com.ohtu.wearable.wearabledataservice;

import android.hardware.Sensor;

import java.util.List;

/**
 * Created by sjsaarin on 20.2.2015.
 */
public interface SelectedSensorsInterface {
    public List<Sensor> getSelectedSensors();

    public void setSelectedSensors(List<Sensor> sensors);

    //public void setServerSensors(List<Sensor> sensors);
}
