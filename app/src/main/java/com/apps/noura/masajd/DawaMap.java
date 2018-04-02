package com.apps.noura.masajd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Noura Alsomaikhi on 12/31/2017.
 */

public class DawaMap extends Fragment implements OnMapReadyCallback
        , GoogleMap.InfoWindowAdapter
        ,GoogleMap.OnInfoWindowClickListener
        ,GoogleMap.OnCameraMoveStartedListener
        , DawaFragmentCommunicator {


    //-----------------------------------------------------------------

    private static final String TAG = "DawaMap";

    protected MapView mapView;
    protected GoogleMap MgoogleMap;
    private View mView;
    // SupportMapFragment mapFragment;
    protected Marker marker;

    private  double lat;
    private  double log;

    private String latitude;
    private String longitude;

    //Location Distance :
    protected Location locationA = new Location("point A");
    protected Location locationB = new Location("point B");


    private DawaClient dawaClient;
    //To get dawa activity Information
    private List<DawaLatLng> dawaLatLngs;
    //To get Mosque Information //Retrofit InterFace:
    protected List<DawaLatLng> NewdawaLatLngs;
    //----------------------------------
    //Used If Map Marker Clicked Open this Activity
    protected DawaListAdapter adapter;


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
        try {
            mView = inflater.inflate(R.layout.fragment_dawa_map, container, false);
            MapsInitializer.initialize(this.getActivity());
            mapView = (MapView) mView.findViewById(R.id.mapView);
            mapView.onCreate(savedInstanceState);
            mapView.getMapAsync(this);


            latitude = Double.toString(lat);
            longitude = Double.toString(log);
        }catch (InflateException e) {
            Log.e(TAG, "Inflate exception");
        }
        return mView;
    }


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((DawaActivity) getActivity()).passVal(new DawaFragmentCommunicator() {
            @Override
            public void passData(List<DawaLatLng> dawaLatLngs) {
                NewdawaLatLngs = dawaLatLngs;
                Toast.makeText(getContext(),
                        "From Inter_Face\n " + NewdawaLatLngs.get(0).getDawaActivitiesInfoId(), Toast.LENGTH_SHORT).show();

                onResume();
            }


        });
    }

 //-----------------------------------------------------------------------------


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

        if (NewdawaLatLngs != null) {
            MgoogleMap.clear();
            onMapReady(MgoogleMap);
        }

  }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//------------------------------------------------------------------------------
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
                .zoom(13)
                .bearing(0)
                .tilt(40)
                .build();
        //Move Camera
       // MgoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));
        MgoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));

        MgoogleMap.getUiSettings().setZoomControlsEnabled(true);
        MgoogleMap.getUiSettings().setRotateGesturesEnabled(true);
        MgoogleMap.getUiSettings().setScrollGesturesEnabled(true);
        MgoogleMap.getUiSettings().setTiltGesturesEnabled(true);

        //  Google Map Onclick:
        MgoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));
        MgoogleMap.setOnCameraMoveStartedListener(this);

        if (NewdawaLatLngs == null) {
            //System.out.println("NULLL Datat : ");
            AddOtherLocation(latitude, longitude);
        } else {

            addMoreMarker(NewdawaLatLngs);
        }

    }

    protected String Newlat;
    protected String Newlon;
    public void AddOtherLocation(String lat, String lon){
        if (NewdawaLatLngs == null) {
            if (marker != null) {
                marker.remove();
            }


            Newlat = lat;
            Newlon = lon;

            //Make A Connection With API :
            dawaClient = ApiRetrofitClint.getApiRetrofitClint().create(DawaClient.class);
            //Call Function form Inter Face And Send Parameter to it


            Call<List<DawaLatLng>> call = dawaClient.getDawaLatLng(latitude,longitude , 25);
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
        else {

            Toast.makeText(getContext(), "لا يوجد إتصال ", Toast.LENGTH_SHORT).show();
        }


    }


    private TextView DawaTitle ;
    private String DawaName;
    private List<DawaLatLng> dawaLatLngs2;

    protected  void addMoreMarker ( List<DawaLatLng> dawa){
        if(marker != null){
            marker.remove();
        }
        dawaLatLngs2 = dawa;

        //Used To calc Distance:
        locationA.setLatitude(lat);
        locationA.setLongitude(log);


        //Add All Dawa
        for(int i=0 ; i< dawaLatLngs2.size(); i++){


            String latAPI=dawaLatLngs2.get(i).getLocYCoord();
            String logAPI= dawaLatLngs2.get(i).getLocXCoord();

            double latd=Double.parseDouble(latAPI);
            double logd= Double.parseDouble(logAPI);
            LatLng latLngAPI = new LatLng(latd,logd);

            System.out.println(latLngAPI + "  Id " + i  +  dawaLatLngs2.size());
            DawaName  = dawaLatLngs2.get(i).getDawaAddress();

//-----------------------------Calc Distance --------------------------------
            locationB.setLatitude(latd);
            locationB.setLongitude(logd);
            float distance = locationA.distanceTo(locationB);
            double dm =distance * Math.PI / 180.0;
            double dk = dm / 10.0;

            //rad * 180.0 / Math.PI
            System.out.println(" Distance is :) :) :0  " + distance  + "\n d by meeter :" +dm + "\n In Kilo : " +dk );




//--------------------------------------------------------------------------------------------------

             marker=  MgoogleMap.addMarker(new MarkerOptions()
                    .position(latLngAPI)
                    .title(DawaName)////title on the marker
                    .snippet("موقعي")//Description

                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icons)));

            System.out.println(DawaName + " Name");
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
        View view = getLayoutInflater().inflate(R.layout.dawa_map_info_window, null, false);


        DawaTitle = (TextView) view.findViewById(R.id.DawaName);
        DawaTitle.setText(marker.getTitle());

        return view;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
       // System.out.print("CLICKED");

    }

    @Override
    public void passData(List<DawaLatLng> dawaLatLngs) {

    }





    //-----------------------Map Cluster---------------------------------------------------------
    protected  double newLat;
    protected  double newLon;


    // Declare a variable for the cluster manager.
    private ClusterManager<MarkerCluster> mClusterManager;

    private void setUpClusterer() {



        MgoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                 Toast.makeText(getContext(), latLng + "\n lat lon on click",
                       Toast.LENGTH_SHORT).show();

                newLat = latLng.latitude;
                newLon = latLng.longitude;
                // Position the map.
                MgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(newLat, newLon), 10));

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
        String lng =Double.toString(newLon);
        AddOtherLocation(lat,lng);

        // Add ten cluster items in close proximity, for purposes of this example.
      /*  for (int i = 0; i < 10; i++) {
            double offset = i / 60d;
            lat = lat + offset;
            lng = lng + offset;
            MarkerCluster offsetItem = new MarkerCluster(newLat, newLon,"tt","ss");
            mClusterManager.addItem(offsetItem);
        }*/

    }

    @Override
    public void onCameraMoveStarted(int i) {
        setUpClusterer();
    }

}







