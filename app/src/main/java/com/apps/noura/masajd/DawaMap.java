package com.apps.noura.masajd;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Noura Alsomaikhi on 12/31/2017.
 */

public class DawaMap extends Fragment implements OnMapReadyCallback {


    //-----------------------------------------------------------------

    private static final String TAG = "DawaMap";


    MapView mapView;
    protected GoogleMap MgoogleMap;
    View mView;

    //----------------------------------

    public DawaMap() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_dawa_map, container, false);
        return mView;
    }


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) mView.findViewById(R.id.mapView);

        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Initialize Map:
        MapsInitializer.initialize(getContext());
        MgoogleMap = googleMap;

        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);

        //Add Marker and Map Properties (User Location)
        LatLng latLng =new LatLng(24.7214819,46.6444971);

        googleMap.addMarker(new MarkerOptions().position(latLng)
                .title("موقعك الحالي")////title on the marker
                .snippet("موقعي")//Description
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons)) );
        //
        //Mosques Locations : Form API

        //-----------------------
        //Set Camera Position:
        CameraPosition CameraMosque = CameraPosition.builder().target(latLng)
                .zoom(16)
                .bearing(0)
                .tilt(45)
                .build();
        //Move Camera
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));

        Toast.makeText(getContext(),"Test_Toast_Massage",Toast.LENGTH_SHORT).show();
    }

}



