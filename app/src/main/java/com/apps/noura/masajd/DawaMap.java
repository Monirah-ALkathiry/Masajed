package com.apps.noura.masajd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Noura Alsomaikhi on 12/31/2017.
 */

public class DawaMap extends Fragment implements OnMapReadyCallback, GoogleMap.InfoWindowAdapter
        ,GoogleMap.OnInfoWindowClickListener {


    //-----------------------------------------------------------------

    private static final String TAG = "DawaMap";


    MapView mapView;
    protected GoogleMap MgoogleMap;
    View mView;


    private  double lat;
    private  double log;

    String latitude;
    String longitude;

    //Location Distance :
    Location locationA = new Location("point A");
    Location locationB = new Location("point B");


    private DawaClient dawaClient;
    //To get dawa activity Information
    private List<DawaLatLng> dawaLatLngs;
    //----------------------------------

    public DawaMap() {

    }


    @SuppressLint("ValidFragment")
    public  DawaMap(double lat, double log){

        this.lat = lat;
        this.log = log;
    }

//-----------------------------------------------------------------

//----------------------------------

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_dawa_map, container, false);

        latitude= Double.toString(lat);
        longitude= Double.toString(log);

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

        MgoogleMap.setMapType(googleMap.MAP_TYPE_NORMAL);

        //Add Marker and Map Properties (User Location)
        LatLng latLng =new LatLng(lat,log);

        MgoogleMap.addMarker(new MarkerOptions().position(latLng)
                .title("موقعك الحالي")////title on the marker
                .snippet("موقعي") );//Description
                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.icons)) );
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
       // MgoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));
        MgoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));

        MgoogleMap.getUiSettings().setZoomControlsEnabled(true);
        MgoogleMap.getUiSettings().setRotateGesturesEnabled(true);
        MgoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        MgoogleMap.getUiSettings().setTiltGesturesEnabled(true);

        Toast.makeText(getContext(),"Dawa Map Activity",Toast.LENGTH_SHORT).show();
        AddOtherLocation();

    }


    public void AddOtherLocation(){


        //Make A Connection With API :
        dawaClient = ApiRetrofitClint.getApiRetrofitClint().create(DawaClient.class);
        //Call Function form Inter Face And Send Parameter to it


        Call<List<DawaLatLng>> call = dawaClient.getDawaLatLng(latitude,longitude , "25");
        //  Create Response:
        call.enqueue(new Callback<List<DawaLatLng>>() {
            @Override
            public void onResponse(Call<List<DawaLatLng>> call, Response<List<DawaLatLng>> response) {

                dawaLatLngs = response.body();
                System.out.println(dawaLatLngs.size() + " SIZE IS");
                //Send Data To Fragment List---
                //  adapter = new MosqueListAdapter(getContext(),mosquesLatLngs);

                ///recyclerView.setAdapter(adapter);
                //-------

                //Test Result and Print Data
                System.out.println("Responce toString"+ response.toString());
                System.out.println("Responce body"+ response.body());
                System.out.println("Responce headers"+ response.headers());
                System.out.println("Responce errorBody"+ response.errorBody());
                System.out.print("URL" + response.isSuccessful());
                //Storing the data in our list

                System.out.println("Size Is onResponce :----" +dawaLatLngs.size());
                //-----------------------------------------------------------------------

                addMoreMarker(dawaLatLngs);

            }

            @Override
            public void onFailure(Call<List<DawaLatLng>> call, Throwable t) {
                System.out.println("Error bad  ):-----------------------");
            }
        });

    }


    private TextView MosqueName ;
    private String MosquName;
    private List<DawaLatLng> dawaLatLngs2;

    protected  void addMoreMarker ( List<DawaLatLng> dawa){

        dawaLatLngs2 = dawa;

        //Used To calc Distance:
        locationA.setLatitude(lat);
        locationA.setLongitude(log);


        //Add All mosqu
        for(int i=0 ; i< dawaLatLngs2.size(); i++){


            String latAPI=dawaLatLngs2.get(i).getLocYCoord();
            String logAPI= dawaLatLngs2.get(i).getLocXCoord();

            double latd=Double.parseDouble(latAPI);
            double logd= Double.parseDouble(logAPI);
            LatLng latLngAPI = new LatLng(latd,logd);

            System.out.println(latLngAPI + "  Id " + i  +  dawaLatLngs2.size());
            MosquName  = dawaLatLngs2.get(i).getDawaAddress();

//-----------------------------Calc Distance --------------------------------
            locationB.setLatitude(latd);
            locationB.setLongitude(logd);
            float distance = locationA.distanceTo(locationB);
            double dm =distance * Math.PI / 180.0;
            double dk = dm / 10.0;

            //rad * 180.0 / Math.PI
            System.out.println(" Distance is :) :) :0  " + distance  + "\n d by meeter :" +dm + "\n In Kilo : " +dk );




//--------------------------------------------------------------------------------------------------


            Marker marker=  MgoogleMap.addMarker(new MarkerOptions()
                    .position(latLngAPI)
                    .title(MosquName)////title on the marker
                    .snippet("موقعي")//Description

                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons)));

            System.out.println(MosquName + " Name");
            marker.setTag(dawaLatLngs2.get(i));

//---------------------------------------------------------------------------------

            //Info Window
            MgoogleMap.setInfoWindowAdapter(this);
            //Onclick Info Window
            MgoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    DawaLatLng infoAttached = (DawaLatLng) marker.getTag();

                    Toast.makeText(getContext(),"Test_Toast_Massage: " +infoAttached.getDawaActivitiesInfoId()
                            +"  " +infoAttached.getMosqueName(),Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(getContext(),DawaInformationActivity.class);
                    // Dawa Activity Information:
                    intent.putExtra("DAWA_ID",infoAttached.getId());
                    intent.putExtra("DawaAddress",infoAttached.getDawaAddress());
                    intent.putExtra("DawaOffice",infoAttached.getDawaOffice());
                    intent.putExtra("DawaActivityDateH",infoAttached.getDawaActivityDateH());
                    intent.putExtra("DawaActivityTime",infoAttached.getDawaActivityTime());
                    intent.putExtra("DawaActivityRepDays",infoAttached.getDawaActivityRepDays());
                    intent.putExtra("LocX_Coord",infoAttached.getLocYCoord());
                    intent.putExtra("LocY_Coord",infoAttached.getLocXCoord());


                    intent.putExtra("MOSQUE_NAME",infoAttached.getMosqueName());
                    intent.putExtra("MOSQUE_REGION",infoAttached.getRegion());
                    intent.putExtra("CITY_VILLAGE",infoAttached.getCityVillage());
                    intent.putExtra("DISTRICT",infoAttached.getDistrict());
                    intent.putExtra("STREET_NAME",infoAttached.getStreetName());

                    intent.putExtra("FirstName",infoAttached.getFirstName());
                    intent.putExtra("FatherName",infoAttached.getFatherName());
                    intent.putExtra("GrandFatherName",infoAttached.getGrandFatherName());

                    intent.putExtra("FamilyName",infoAttached.getFamilyName());
                    intent.putExtra("DawaActivLanguage",infoAttached.getDawaActivLanguage());
                    intent.putExtra("WomenPlaceAvailability",infoAttached.getWomenPlaceAvailability());

                    getContext().startActivity(intent);



                }
            });

        }//end for


    }//end function


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = getLayoutInflater().inflate(R.layout.dawa_info_window, null, false);


        MosqueName = (TextView) view.findViewById(R.id.MosqueName);
        MosqueName.setText(marker.getTitle());

        return view;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        System.out.print("CLICKED");

    }
}







