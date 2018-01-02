package com.apps.noura.masajd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
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

import java.util.List;

/**
 * Created by Monirah on 13/12/17.
 */

//View Mosque Information (Fragment)
public class MosqueInformation extends Fragment implements OnMapReadyCallback {


    private TextView Masijed_Type;
    private TextView MOSQUE_REGION;
    private TextView CITY_VILLAGE;
    private TextView DISTRICT;
    private TextView STREET_NAME;
    private TextView IMAM_NAME;
    private TextView KHATEEB_NAME;
    private TextView MOATHEN_NAME;
    private TextView OBSERVER_NAME;


    private  String Latitude;
    private  String Longitude;
    private String  MosqueName;

    private  Double Lat;
    private  Double Lon;
     //Google Map
    GoogleMap MgoogleMap;
    MapView mapView;
    View view;

    public MosqueInformation(){

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fragment_mosque_information, container, false);

        Intent intent = getActivity().getIntent();

        //Set Lat  and Long :

        Latitude= intent.getStringExtra("MOSQUE_LAT");
        Longitude =intent.getStringExtra("MOSQUE_LON");
        MosqueName =intent.getStringExtra("MOSQUE_NAME");

        Lat = Double.parseDouble(Latitude);
        Lon =  Double.parseDouble(Longitude);


        Masijed_Type = (TextView) view.findViewById(R.id.MosqueType);
        String MOSQUE_TYPE =intent.getStringExtra("MOSQUE_TYPE");

        //TODO : Lay_Out :
        //ToDO Check NUll Values:
        //TODO : Check The Error
        int MTyop= Integer.parseInt(MOSQUE_TYPE);
        if(MTyop == 1){
            Masijed_Type.setText("مسجد");
        }else if(MTyop == 2){
            Masijed_Type.setText("مصلى");

        }
        else if(MTyop == 3){
            Masijed_Type.setText("جامع");

        }
        else if(MTyop == 4){
            Masijed_Type.setText("مصلى عيد");

        }else{
            Masijed_Type.setText("لايوجد ");

        }

//TODO : Get Region Name
        MOSQUE_REGION  = (TextView) view.findViewById(R.id.MosqueRegion);
        String REGION =intent.getStringExtra("MOSQUE_REGION");
        //TEST
        System.out.println(REGION + " +++++++++++++++++++++++++++");
        int RegionType= Integer.parseInt(REGION);

        if(RegionType == 1){
            MOSQUE_REGION.setText("الرياض");

        }
        else if (RegionType != 1){
            MOSQUE_REGION.setText("لا يوجد");
        }

        CITY_VILLAGE = (TextView) view.findViewById(R.id.CityVillage);
        CITY_VILLAGE.setText(intent.getStringExtra("CITY_VILLAGE"));


        DISTRICT = (TextView) view.findViewById(R.id.District);
        DISTRICT.setText(intent.getStringExtra("DISTRICT"));

        STREET_NAME =(TextView) view.findViewById(R.id.StreetName);
        STREET_NAME.setText(intent.getStringExtra("STREET_NAME"));


        IMAM_NAME = (TextView) view.findViewById(R.id.ImamName);
        IMAM_NAME.setText(intent.getStringExtra("IMAM_NAME"));

        KHATEEB_NAME = (TextView) view.findViewById(R.id.KhateebName);
        KHATEEB_NAME.setText(intent.getStringExtra("KHATEEB_NAME"));


        MOATHEN_NAME = (TextView) view.findViewById(R.id.MoathenName);
        MOATHEN_NAME.setText(intent.getStringExtra("MOATHEN_NAME"));

        OBSERVER_NAME = (TextView) view.findViewById(R.id.ObserverName);
        OBSERVER_NAME.setText(intent.getStringExtra("OBSERVER_NAME"));



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
                .title(MosqueName)////title on the marker
                .snippet("موقعي")//Description
                 );
        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.icons))
        //
        //Mosques Locations : Form API

        //-----------------------
        //Set Camera Position:
        CameraPosition CameraMosque = CameraPosition.builder().target(latLng)
                .zoom(15)
                .bearing(0)
                .tilt(20)
                .build();
        //Move Camera
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));


    }
}


