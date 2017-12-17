package com.apps.noura.masajd;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by Monirah on 12/12/17.
 */

public class UsereLocationListener implements LocationListener {
    public static Location location; // User Location

    //Constructor
    UsereLocationListener(){

        location = new Location("Zero");
        location.setLatitude(0);//Initialize Latitude
        location.setLongitude(0);//Initialize Longitude
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location; // read Location when it is changed
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
