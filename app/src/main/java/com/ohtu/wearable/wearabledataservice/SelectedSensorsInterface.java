package com.ohtu.wearable.wearabledataservice;

import android.hardware.Sensor;

import java.util.List;

/**
 * Used to call MainActivity's methods from UI classes
 */
public interface SelectedSensorsInterface {
    public List<Sensor> getSelectedSensors();

    public void setSelectedSensors(List<Sensor> sensors);

}
