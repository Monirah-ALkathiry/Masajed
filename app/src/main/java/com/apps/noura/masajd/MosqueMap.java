package com.apps.noura.masajd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Console;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


// TO DO: view Location  Mosques on the map (COMPLETED)

public class MosqueMap extends Fragment implements OnMapReadyCallback
    , GoogleMap.InfoWindowAdapter
        ,GoogleMap.OnInfoWindowClickListener
{

//,GoogleMap.OnMarkerClickListener
    //-----------------------------------------------------------------
    // the fragment initialization
    private static final String TAG = "MosqueMap";

    //Initialize Map variables :
    MapView mapView;
    protected GoogleMap MgoogleMap;
    View mView;
    // SupportMapFragment mapFragment;
    //----------------------------------

    private  double lat;
    private  double log;

    String latitude;
    String longitude;

    private Marker MosqueMarker;
    //Used If Map Marker Clicked Open this Activity
    private MosqueListAdapter adapter;


    //Location Distance :
    Location locationA = new Location("point A");
    Location locationB = new Location("point B");


    //Retrofit InterFace:
    private MosquesLatLngClint mosquesLatLngClint;
    //To get Mosque Information
    private List<MosquesLatLng> mosquesLatLngs;
    //private OnFragmentInteractionListener mListener;

    //private OnFragmentInteractionListener mListener;
    //public MosqueMap(){}

    @SuppressLint("ValidFragment")
    public MosqueMap(List<MosquesLatLng> mosquesLatLngs,double lat, double log){

        this.mosquesLatLngs = mosquesLatLngs;
    }


    @SuppressLint("ValidFragment")
    public  MosqueMap(double lat, double log){

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


        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_mosque_map, container, false);

        System.out.print("  \n"+ lat +" Lat From Fragment " + log + "\n");



        latitude= Double.toString(lat);
        longitude= Double.toString(log);




        //System.out.print(latitude +" Lat  " + longitude + "\n");

        return mView;

    }


    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        //Set Map View By Id
        mapView = (MapView) mView.findViewById(R.id.mapView);

        //check if Not Null
        if(mapView != null){
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }//end check if Not Null
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Get Data From API :

        System.out.println("Print----onMapReady-------test------------");

        MgoogleMap = googleMap;
        // Set a listener for marker click.
        //Initialize Map:
        MapsInitializer.initialize(getContext());

        MgoogleMap.setMapType(googleMap.MAP_TYPE_NORMAL);

        //Add Marker and Map Properties (User Location)
        LatLng latLng =new LatLng(lat,log);


        MosqueMarker = googleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .title("موقعك الحالي")////title on the marker
                .snippet("موقعي")//Description
                 );
        //MgoogleMap.setOnMarkerClickListener(this);
       // MgoogleMap.setInfoWindowAdapter(this);


        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.icons))
        //
        //Mosques Locations : Form API

        //-----------------------
        //Set Camera Position:
        CameraPosition CameraMosque = CameraPosition.builder().target(latLng)
                .zoom(14)
                .bearing(0)
                .tilt(45)
                .build();
        //Move Camera
       // googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));
        //Google Map Zoom in zoom out
        MgoogleMap.getUiSettings().setZoomControlsEnabled(true);
        MgoogleMap.getUiSettings().setRotateGesturesEnabled(false);
        MgoogleMap.getUiSettings().setScrollGesturesEnabled(false);
        MgoogleMap.getUiSettings().setTiltGesturesEnabled(false);
      //  MgoogleMap.setOnMarkerClickListener(this);

        //Google Map Onclick:
        MgoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));



       // Toast.makeText(getContext(),"Test_Toast_Massage",Toast.LENGTH_SHORT).show();
        AddOtherLocation();
    }


 //---------



    //---------------
//---------
public void AddOtherLocation(){


        //Make A Connection With API :
        mosquesLatLngClint = ApiRetrofitClint.getApiRetrofitClint().create(MosquesLatLngClint.class);
        //Call Function form Inter Face And Send Parameter to it


        Call<List<MosquesLatLng>> call = mosquesLatLngClint.getMosqueLatLng(latitude,longitude,25);
        //  Create Response:
        call.enqueue(new Callback<List<MosquesLatLng>>() {
            @Override
            public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {

                mosquesLatLngs = response.body();
                System.out.println(mosquesLatLngs.size() + " SIZE IS");
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

                System.out.println("Size Is onResponce :----" +mosquesLatLngs.size());
                //-----------------------------------------------------------------------

                addMoreMarker (mosquesLatLngs);

            }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.println("Error bad  ):-----------------------");
            }
        });

    }

    private TextView MosqueName ;
   private String MosquName;
   private List<MosquesLatLng> mosquesLatLngs2;

    protected  void addMoreMarker ( List<MosquesLatLng> mosques){

       mosquesLatLngs2 = mosques;

        //Used To calc Distance:
        locationA.setLatitude(lat);
        locationA.setLongitude(log);


        //Add All mosqu
   for(int i=0 ; i< mosquesLatLngs2.size(); i++){


            String latAPI=mosquesLatLngs2.get(i).getLatitude();
            String logAPI= mosquesLatLngs2.get(i).getLongitude();

            double latd=Double.parseDouble(latAPI);
            double logd= Double.parseDouble(logAPI);
            LatLng latLngAPI = new LatLng(latd,logd);

            System.out.println(latLngAPI + "  Id " + i  +  mosquesLatLngs2.size());
             MosquName  = mosquesLatLngs2.get(i).getMosqueName();

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
                    marker.setTag(mosquesLatLngs2.get(i));



                //Info Window
            MgoogleMap.setInfoWindowAdapter(this);
            //Onclick Info Window
            MgoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    MosquesLatLng infoAttached = (MosquesLatLng) marker.getTag();

                    Toast.makeText(getContext(),"Test_Toast_Massage: " +infoAttached.getCode() +"  " +infoAttached.getMosqueName(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), MosqueInformationActivity.class);


                    intent.putExtra("MOSQUE_CODE", infoAttached.getCode());
                    //USED IN MAP
                    intent.putExtra("MOSQUE_LAT",infoAttached.getLatitude());
                    intent.putExtra("MOSQUE_LON", infoAttached.getLongitude());

                    // Mosque Information:
                    intent.putExtra("MOSQUE_NAME", infoAttached.getMosqueName());
                    intent.putExtra("MOSQUE_TYPE", infoAttached.getMosqueCatogery());
                    intent.putExtra("MOSQUE_REGION", infoAttached.getRegion());
                    intent.putExtra("CITY_VILLAGE", infoAttached.getCityVillage());
                    intent.putExtra("DISTRICT", infoAttached.getDistrict());
                    intent.putExtra("STREET_NAME",infoAttached.getStreetName());
                    intent.putExtra("IMAM_NAME", infoAttached.getImamName());
                    intent.putExtra("KHATEEB_NAME", infoAttached.getKhateebName());
                    intent.putExtra("MOATHEN_NAME", infoAttached.getMoathenName());
                    intent.putExtra("OBSERVER_NAME", infoAttached.getObserverName());


                    getContext().startActivity(intent);

                }
            });



          /*  MgoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

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

                    });
            */




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

        System.out.print("CLICKED");
    }


    //


}


      /* //Retrofit Data

        //1)- Declare Retrofit Builder
        Retrofit builder = new Retrofit.Builder()
                .baseUrl("http://mosquesapi.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//2)_ Create Object Form Mosque_Lat_lng interface
        final MosquesLatLngClint mosquesLatLngClint = builder.create(MosquesLatLngClint.class);
//3)- Call Function form Inter Face And Send Parameter to it
        Call<List<MosquesLatLng>> call = mosquesLatLngClint.getMosqueLatLng("24.7179970","46.64460257");

//4)- Create Response:
        call.enqueue(new Callback<List<MosquesLatLng>>() {
            @Override
            public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {

                mosquesLatLngs = response.body();

                //Test Result and Print Data
                System.out.println("Responce toString"+ response.toString());
                System.out.println("Responce body"+ response.body());
                System.out.println("Responce headers"+ response.headers());
                System.out.println("Responce errorBody"+ response.errorBody());
                System.out.print("URL" + response.isSuccessful());
                //Storing the data in our list

                System.out.println("Size Is onResponce :----" +mosquesLatLngs.size());
                //-----------------------------------------------------------------------

                showlist();//For test
                //LoadMasijedLocations();
            }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.println("Error bad  ):-----------------------");
            }
        });
*/
         /*
    //Insert Data to array
private void showlist(){

    String[][] item = new String[mosquesLatLngs.size()][2];


    for(int i =0; i<mosquesLatLngs.size() ; i++){
        for(  int j=0 ; j <2 ; j++) {

            if(j == 0) {
                System.out.println( item[i][j] = mosquesLatLngs.get(i).getLatitude() + " Lat" );
            }else {
                System.out.println(item[i][j] = mosquesLatLngs.get(i).getLongitude() + " log");
            }
        }//end Inner for

    }//end for


}*/


/*

    void LoadMasijedLocations(){
        System.out.println("New :Location-----------------------");

        mosquesLatLngs.add(new MosquesLatLng(R.drawable.icons,mosquesLatLngs.get(1).getCityVillage(),"","24.719644","46.657211"));

    }




//Get User Location
final int REQUEST_CODE_ASK_PERMITION =10;
    //Check If Version >=23
    void checkPermition(){

        if (Build.VERSION.SDK_INT >= 23){
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            {requestPermissions(new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION},
            REQUEST_CODE_ASK_PERMITION);

               return;
            }


        }//end If Check 23
        GetLocation();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode){
            case REQUEST_CODE_ASK_PERMITION:
                if(grantResults [0] == PackageManager.PERMISSION_GRANTED){
                    GetLocation();
                }else {

                   // Toast.makeText(this,"): Can not Accesss Map", Toast.LENGTH_SHORT).show();

                    System.out.println("): Can not Accesss Map");
                }
                break;
                default:
                    super.onRequestPermissionsResult(requestCode,permissions,grantResults);



        }//end Switch


    }

    //Get User Location From User_Location_Listener Class
    void GetLocation(){

        LocationManager locationManager;
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        UsereLocationListener usereLocationListener = new UsereLocationListener();

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3,3,usereLocationListener);

        UserThread userThread = new UserThread();
        userThread.start();
    }

    //View Location On Map (Use Threadl) Class

    class UserThread extends Thread{
        @Override
        public void run() {
            MgoogleMap.clear();

            //Read User Location
            while (true){

                try{
                   runOnUiThread(new Runnable(){
                       @Override
                       public void run() {

                           //Initialize Map:
                           MapsInitializer.initialize(getContext());

                          // MgoogleMap = googleMap;
                           MgoogleMap.setMapType(MgoogleMap.MAP_TYPE_NORMAL);

                           //Add Marker and Map Properties (User Location)
                         //  LatLng latLng= new LatLng(UsereLocationListener.location.getLatitude(),UsereLocationListener.location.getLongitude());
                           LatLng latLng =new LatLng(24.7214819,46.6444971);

                           MgoogleMap.addMarker(new MarkerOptions()
                                   .position(latLng)
                                   .title("موقعك الحالي  hgpp")////title on the marker
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
                          MgoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));
                         //  MgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14)  );

                       }
                   });
                   Thread.sleep(1000);
               }//end Try
                catch(Exception ex){
                    System.out.println("):مشكله ");

                }//end Catch

            }//end While

            //super.run();
        }
    }

*/