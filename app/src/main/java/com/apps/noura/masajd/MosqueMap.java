package com.apps.noura.masajd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.InflateException;
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
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//SupportMapFragment
public class MosqueMap extends Fragment implements OnMapReadyCallback
        , GoogleMap.InfoWindowAdapter
        , GoogleMap.OnInfoWindowClickListener
        , FragmentCommunicator
    ,GoogleMap.OnCameraIdleListener



{

    //,GoogleMap.OnMarkerClickListener
    //-----------------------------------------------------------------
    // the fragment initialization
    private static final String TAG = "MosqueMap";

    //Initialize Map variables :
    protected MapView mapView;
    protected GoogleMap MgoogleMap;
    private View mView;
    // SupportMapFragment mapFragment;
    protected Marker marker;
    //----------------------------------

    private double lat;
    private double log;

    private String latitude;
    private String longitude;

    protected Marker MosqueMarker;
    //Used If Map Marker Clicked Open this Activity
    protected MosqueListAdapter adapter;

    //Location Distance :
    protected Location locationA = new Location("point A");
    protected Location locationB = new Location("point B");

    //Retrofit InterFace:
    protected MosquesLatLngClint mosquesLatLngClint;
    //To get Mosque Information
    protected List<MosquesLatLng> mosquesLatLngs;
    protected List<MosquesLatLng> NewmosquesLatLngs;


    //User Location:
    protected LatLng latLng;


//Communication'
    FirstFragmentListenerMAP firstFragmentListenerMAP;

    /**
     * Called when a fragment is first attached to its context.
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof FirstFragmentListenerMAP){
            firstFragmentListenerMAP = (FirstFragmentListenerMAP)context;
        }
    }

    @SuppressLint("ValidFragment")
    public MosqueMap(List<MosquesLatLng> test) {
        NewmosquesLatLngs = test;
    }


    @SuppressLint("ValidFragment")
    public MosqueMap(double lat, double log) {

        this.lat = lat;
        this.log = log;

    }

    public MosqueMap() {
        // Required empty public constructor
    }




    //Retrofit
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }//end on create

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {
            mView = inflater.inflate(R.layout.fragment_mosque_map, container, false);
            //Maps Initializer
            MapsInitializer.initialize(this.getActivity());
            mapView = (MapView) mView.findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);
            latitude = Double.toString(lat);
            longitude = Double.toString(log);


        } catch (InflateException e) {
            Log.e(TAG, "Inflate exception");
        }


        return mView;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Set Map View By Id
        //check if Not Null

        ((MosqueActivity) getActivity()).passVal(new FragmentCommunicator() {
            @Override
            public void passData(List<MosquesLatLng> mosquesLatLngs) {
                NewmosquesLatLngs = mosquesLatLngs;

                onResume();

               // addMoreMarker(NewmosquesLatLngs);
            }


        });


    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();


    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();

        if (NewmosquesLatLngs != null) {

            MgoogleMap.clear();
            onMapReady(MgoogleMap);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {


        //Get Data From API :
        MgoogleMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {


            return;
        }
        MgoogleMap.setMyLocationEnabled(true);

        // Set a listener for marker click.

        //Initialize Map:
        MapsInitializer.initialize(getContext());
        MgoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        //Add Marker and Map Properties (User Location)
        latLng = new LatLng(lat, log);


       /* MosqueMarker = googleMap.addMarker
                (new MarkerOptions()
                        .position(latLng)
                        .title("موقعك الحالي")////title on the marker

                        .snippet("موقعي")//Description
                );
        */

        //--------------------------------------------------------------------------------------------
        //Set Camera Position:
        CameraPosition CameraMosque = CameraPosition.builder().target(latLng)
                .zoom(13)
                .bearing(0)
                .tilt(40)
                .build();

        //Move Camera
        // googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));
        //Google Map Zoom in zoom out
        //Zoom controller position:
        //leftPadding, topPadding, rightPadding, bottomPadding
        MgoogleMap.setPadding(0, 0, 0, 100);
        MgoogleMap.getUiSettings().setZoomControlsEnabled(true);
        MgoogleMap.getUiSettings().setRotateGesturesEnabled(true);
        MgoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        MgoogleMap.getUiSettings().setTiltGesturesEnabled(true);

        //USER LOCATION :
        MgoogleMap.setMyLocationEnabled(true);
        //  MgoogleMap.setOnMarkerClickListener(this);

        //  Google Map Onclick:
        MgoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));


        //MapMove---
        // MgoogleMap.setOnCameraMoveListener(this);
        // MgoogleMap.setOnCameraMoveStartedListener(this);
         MgoogleMap.setOnCameraIdleListener(this);


       if (NewmosquesLatLngs == null) {
            //System.out.println("NULLL Datat : ");
            AddOtherLocation(latitude, longitude);
           //Change Position:

       } else {

            addMoreMarker(NewmosquesLatLngs);
        }

    }


    protected String Newlat;
    protected String Newlon;

    public void AddOtherLocation(String lat, String lon) {

        //  if (NewmosquesLatLngs == null) {
        if (marker != null) {
            marker.remove();
        }
        Newlat = lat;
        Newlon = lon;

        //Make A Connection With API :
        mosquesLatLngClint = ApiRetrofitClint.getApiRetrofitClint().create(MosquesLatLngClint.class);
        //Call Function form Inter Face And Send Parameter to it


        Call<List<MosquesLatLng>> call = mosquesLatLngClint.getMosqueLatLng(Newlat, Newlon, 25);
        //  Create Response:
        call.enqueue(new Callback<List<MosquesLatLng>>() {
            @Override
            public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {

                if (response.isSuccessful()) {
                    mosquesLatLngs = response.body();
                    System.out.println(mosquesLatLngs.size() + " SIZE IS");
                    //Send Data To Fragment List---
                    //  adapter = new MosqueListAdapter(getContext(),mosquesLatLngs);

                    ///recyclerView.setAdapter(adapter);
                    //-------

                    //Test Result and Print Data
                    // System.out.println("Responce toString" + response.toString());
                    // System.out.println("Responce body" + response.body());
                    //  System.out.println("Responce headers" + response.headers());
                    //  System.out.println("Responce errorBody" + response.errorBody());
                    //  System.out.print("URL" + response.isSuccessful());
                    //Storing the data in our list

                    //  System.out.println("Size Is onResponce :----" + mosquesLatLngs.size());
                    //-----------------------------------------------------------------------

                    firstFragmentListenerMAP.sendData(mosquesLatLngs);
                    addMoreMarker(mosquesLatLngs);

                    //System.out.println("Size Is onResponce :----" + mosquesLatLngs.size());
                } else {

                }

            }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.println("Error bad  ):-----------------------");
            }
        });

        // } else {

        //  Toast.makeText(getContext(), "لا يوجد إتصال ", Toast.LENGTH_SHORT).show();


        //}

    }


    private TextView MosqueName;
    private String MosquName;

    private List<MosquesLatLng> mosquesLatLngs2;


    public void addMoreMarker(List<MosquesLatLng> mosques) {



        if (marker != null) {
            //to remove All marker from Map when user change camera position
            MgoogleMap.clear();
            //Map Movment Marker
        }

        mosquesLatLngs2 = mosques;
        System.out.println(mosquesLatLngs2 + "\n Test Mosque List\n");
        //Used To calc Distance:
        locationA.setLatitude(lat);
        locationA.setLongitude(log);


        //Add All mosqu
        for (int i = 0; i < mosquesLatLngs2.size(); i++) {


            String latAPI = mosquesLatLngs2.get(i).getLatitude();
            String logAPI = mosquesLatLngs2.get(i).getLongitude();

            double latd = Double.parseDouble(latAPI);
            final double logd = Double.parseDouble(logAPI);
            LatLng latLngAPI = new LatLng(latd, logd);

            System.out.println(" Distance is :) :) :0  " + latLngAPI);

            System.out.println(latLngAPI + "  Id " + i + mosquesLatLngs2.size());
            MosquName = mosquesLatLngs2.get(i).getMosqueName();

//-----------------------------Calc Distance --------------------------------
            locationB.setLatitude(latd);
            locationB.setLongitude(logd);
            float distance = locationA.distanceTo(locationB);
            double dm = distance * Math.PI / 180.0;
            double dk = dm / 10.0;

            //rad * 180.0 / Math.PI
           // System.out.println(" Distance is :) :) :0  " + distance + "\n d by meeter :" + dm + "\n In Kilo : " + dk);


//--------------------------------------------------------------------------------------------------

            if (MgoogleMap != null) {
                //addItems();

                marker = MgoogleMap.addMarker(
                        new MarkerOptions()
                                .position(latLngAPI)
                                .title(MosquName)////title on the marker
                                .snippet("")//Description
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons)));

                // System.out.println(MosquName + " Name");
                marker.setTag(mosquesLatLngs2.get(i));


                //Info Window
                MgoogleMap.setInfoWindowAdapter(this);
                //Onclick Info Window
                MgoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick(Marker marker) {
                        MosquesLatLng infoAttached = (MosquesLatLng) marker.getTag();

                        // Toast.makeText(getContext(), "Test_Toast_Massage: " + infoAttached.getCode() + "  " + infoAttached.getMosqueName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MosqueInformationActivity.class);


                        intent.putExtra("MOSQUE_CODE", infoAttached.getCode());
                        //USED IN MAP
                        intent.putExtra("MOSQUE_LAT", infoAttached.getLatitude());
                        intent.putExtra("MOSQUE_LON", infoAttached.getLongitude());

                        // Mosque Information:
                        intent.putExtra("MOSQUE_NAME", infoAttached.getMosqueName());
                        intent.putExtra("MOSQUE_TYPE", infoAttached.getMosqueCatogery());
                        intent.putExtra("MOSQUE_REGION", infoAttached.getRegion());
                        intent.putExtra("CITY_VILLAGE", infoAttached.getCityVillage());
                        intent.putExtra("DISTRICT", infoAttached.getDistrict());
                        intent.putExtra("STREET_NAME", infoAttached.getStreetName());
                        intent.putExtra("IMAM_NAME", infoAttached.getImamName());
                        intent.putExtra("KHATEEB_NAME", infoAttached.getKhateebName());
                        intent.putExtra("MOATHEN_NAME", infoAttached.getMoathenName());
                        intent.putExtra("OBSERVER_NAME", infoAttached.getObserverName());


                        getContext().startActivity(intent);

                    }
                });
            }//end if
            else {
                Toast.makeText(getContext(), "/n لا توجد بيانات /n", Toast.LENGTH_SHORT).show();


            }

        }//end for

    }//end function


    //Info Window (Used On Marker)
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }


    @Override
    public View getInfoContents(Marker marker) {

        View view = getLayoutInflater().inflate(R.layout.map_info_window, null, false);


        MosqueName = (TextView) view.findViewById(R.id.MosqueName);
        MosqueName.setText(marker.getTitle());

        return view;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {

        // System.out.print("CLICKED");

    }

    //-----------------Communication with Fragment (Search) -----------------------
    @Override
    public void passData(List<MosquesLatLng> mosquesLatLngs) {
        //Toast.makeText(getContext(), "From Inter Face\n "+mosquesLatLngs.get(0).getMosqueName(), Toast.LENGTH_SHORT).show();
    }



    //------------------------------

   private String nlat;
   private String nlng;

    @Override
    public void onCameraIdle() {
        //Toast.makeText(getContext(), "The camera has stopped moving. Fetch the data from the server!", Toast.LENGTH_SHORT).show();
        LatLngBounds bounds = MgoogleMap.getProjection().getVisibleRegion().latLngBounds;

        LatLng nl= bounds.getCenter();
        double lon= nl.longitude;
        double lat = nl.latitude;

        nlat= String.valueOf(lat);
         nlng = String.valueOf(lon);
    if(NewmosquesLatLngs == null ) {
        AddOtherLocation(nlat, nlng);
    }


}




/*
    //-----------------------Map Cluster---------------------------------------------------------
    protected double newLat;
    protected double newLon;

    // Declare a variable for the cluster manager.
    private ClusterManager<MarkerCluster> mClusterManager;

    private void setUpClusterer() {


        MgoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //  Toast.makeText(getContext(), latLng + "\nlat lon.",
                //       Toast.LENGTH_SHORT).show();


                newLat = latLng.latitude;
                newLon = latLng.longitude;
                // Position the map.
                MgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLat, newLon), 13));

                // Initialize the manager with the context and the map.
                // (Activity extends context, so we can pass 'this' in the constructor.)
                mClusterManager = new ClusterManager<MarkerCluster>(getContext(), MgoogleMap);

                // Point the map's listeners at the listeners implemented by the cluster
                //  // manager.
                MgoogleMap.setOnCameraIdleListener(mClusterManager);
                MgoogleMap.setOnMarkerClickListener(mClusterManager);

                // Add cluster items (markers) to the cluster manager.
                addItems();
            }
        });


    }

    private void addItems() {

        // Set some lat/lng coordinates to start with.
        String lat = Double.toString(newLat);
        String lng = Double.toString(newLon);
        AddOtherLocation(lat, lng);

      /*  // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MarkerCluster offsetItem = new MarkerCluster(newLat, newLon,"tt","ss");
            mClusterManager.addItem(offsetItem);
        }
        * /
    }

    @Override
    public void onCameraMoveStarted(int reason) {
        // setUpClusterer();
        //  setUpClusterer();
        //  Toast.makeText(getContext(), "@@@@@@@@@@@@@@@@@@.",
        //   Toast.LENGTH_SHORT).show();

        MgoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {

                Toast.makeText(getContext(), "ddddت ", Toast.LENGTH_SHORT).show();

              /*  newLat = latLng.latitude;
                newLon = latLng.longitude;
                // Position the map.
                MgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLat, newLon), 13));

                // Initialize the manager with the context and the map.
                // (Activity extends context, so we can pass 'this' in the constructor.)
                mClusterManager = new ClusterManager<MarkerCluster>(getContext(), MgoogleMap);

                // Point the map's listeners at the listeners implemented by the cluster
                //  // manager.
                MgoogleMap.setOnCameraIdleListener(mClusterManager);
                MgoogleMap.setOnMarkerClickListener(mClusterManager);



                // Add cluster items (markers) to the cluster manager.
                addItems();
                * /

            }
        });



        /*if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            setUpClusterer();
        } else
            if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            Toast.makeText(getContext(), "The user tapped something on the map.",
                    Toast.LENGTH_SHORT).show();
              //  setUpClusterer();

        } else
            if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            Toast.makeText(getContext(), "The app moved the camera.",
                    Toast.LENGTH_SHORT).show();


            }* /


    }

    //------------ latLng = new LatLng(lat, log);------------------------------

    private Location currentLocation;
    private Location UserLocation;
    private LatLng UserLatlon;


    @Override
    public void onCameraIdle() {

        Toast.makeText(getContext(), "dsssssss", Toast.LENGTH_SHORT).show();


    }


  / *  @Override
    public void onCameraMove() {
       // setUpClusterer();

         //Toast.makeText(getContext(), "The camera is moving :) :) :)  :)  :).",Toast.LENGTH_SHORT).show();

        MgoogleMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {


                VisibleRegion visibleRegion = MgoogleMap.getProjection().getVisibleRegion();

                Point x = MgoogleMap.getProjection().toScreenLocation(visibleRegion.farRight);
                Point y = MgoogleMap.getProjection().toScreenLocation(visibleRegion.nearLeft);

//                Log.i(TAG, "x , y : " + x + " , " + y);
                Point center = new Point(x.x / 2, y.y / 2);


                LatLng NewLocation = MgoogleMap.getProjection().fromScreenLocation(center);
                currentLocation = new Location("NewLocatoin");
                //NewLocation;
                currentLocation.setLatitude(NewLocation.latitude);
                currentLocation.setLongitude(NewLocation.longitude);



                UserLocation =  new Location("UserLocation");
                UserLocation.setLatitude(lat);
                UserLocation.setLatitude(log);



             //   Toast.makeText(getContext(), "The camera is moving :) :) :)  :)  :).\n"+ currentLocation,Toast.LENGTH_SHORT).show();


          //  Toast.makeText(getContext(), newLat+"\n"+ newLon,Toast.LENGTH_SHORT).show();


                // AddOtherLocation(newLat,newLon);

            }
        });


    }* /
    */

}
