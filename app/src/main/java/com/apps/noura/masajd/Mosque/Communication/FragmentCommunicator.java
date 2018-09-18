package com.apps.noura.masajd.Mosque.Communication;

import com.apps.noura.masajd.Mosque.MosquesLatLng;

import java.util.List;

/**
 * Created by Monirah on 3/15/2018.
 */

public interface FragmentCommunicator {
     void passData(List<MosquesLatLng> mosquesLatLngs);
}
