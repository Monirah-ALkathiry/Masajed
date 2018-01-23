package com.apps.noura.masajd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Noura Alsomaikhi on 1/10/2018.
 */
 public class DawaInformation extends Fragment implements OnMapReadyCallback {

        //Recycle View (Mosque List)
        private RecyclerView recyclerView;
        private RecyclerView.LayoutManager layoutManager;
        private DawaInformationRecyclerView adapter;
        //--------------------

        //Google Map
        private  String Latitude;
        private  String Longitude;
        private String  MosqueName;
        private String DawaAddress;

        private  Double Lat;
        private  Double Lon;

        GoogleMap MgoogleMap;
        MapView mapView;
        View view;


        //Constructor:
        public DawaInformation(){

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            view = inflater.inflate(R.layout.fragment_dawa_information, container, false);
            Intent intent = getActivity().getIntent();


            //Set Lat  and Long :
            Latitude= intent.getStringExtra("LocY_Coord");
            Longitude =intent.getStringExtra("LocX_Coord");

            MosqueName =intent.getStringExtra("MOSQUE_NAME");
            DawaAddress =intent.getStringExtra("DawaAddress");

            //Convert To Double
           Lat = Double.parseDouble(Latitude);
           Lon =  Double.parseDouble(Longitude);
           // Lon = 46.5536;
            //Lat = 24.6421;

            //Recycler View
            recyclerView = (RecyclerView) view.findViewById(R.id.DawaRecyclerViewINFO);
            layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            adapter = new DawaInformationRecyclerView( intent);
            recyclerView.setAdapter(adapter);

            return view;
        }


        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            mapView = (MapView) view.findViewById(R.id.mapView);
            if (mapView != null){
                mapView.onCreate(null);
                mapView.onResume();
                mapView.getMapAsync(this);

            }

        }

        @Override
        public void onMapReady(GoogleMap googleMap) {

            MapsInitializer.initialize(getContext());
            MgoogleMap = googleMap;

            googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);

            //Add Marker and Map Properties (User Location)

            LatLng latLng =new LatLng(Lat,Lon);

            googleMap.addMarker(new MarkerOptions().position(new LatLng(Lat,Lon))
                    .title(DawaAddress)////title on the marker
                    .snippet("موقعي")//Description
            );

            //Set Camera Position:
            CameraPosition CameraMosque = CameraPosition.builder().target(latLng)
                    .zoom(15)
                    .bearing(0)
                    .tilt(15)
                    .build();
            //Move Camera
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));

        }

    }



