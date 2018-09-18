package com.apps.noura.masajd.Mosque.Communication;

import com.apps.noura.masajd.Mosque.MosquesLatLng;

import java.util.List;

/**
 * Created by Monirah on 7/12/2018.
 */

public interface FirstFragmentListenerMAP {
     void sendData(List<MosquesLatLng> newData);
}
