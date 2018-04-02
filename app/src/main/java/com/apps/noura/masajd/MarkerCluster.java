package com.apps.noura.masajd;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Monirah on 3/25/2018.
 */

public class MarkerCluster implements ClusterItem {

    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;


    public MarkerCluster(double lat, double lng, String title, String snippet) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
    }


    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public String getSnippet() {
        return mSnippet;
    }
}
