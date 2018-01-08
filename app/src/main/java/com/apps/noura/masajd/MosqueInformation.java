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
 * Created by Monirah on 13/12/17.
 */

//View Mosque Information (Fragment)
public class MosqueInformation extends Fragment implements OnMapReadyCallback {

    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MosqueInformationRecyclerView adapter;
    //--------------------

    //Google Map
    private  String Latitude;
    private  String Longitude;
    private String  MosqueName;

    private  Double Lat;
    private  Double Lon;

    GoogleMap MgoogleMap;
    MapView mapView;
    View view;


//Constructor:
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
    //Convert To Double
        Lat = Double.parseDouble(Latitude);
        Lon =  Double.parseDouble(Longitude);


        //Recycler View
         recyclerView = (RecyclerView) view.findViewById(R.id.MosqueRecyclerViewINFO);
         layoutManager = new LinearLayoutManager(getContext());
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.setHasFixedSize(true);

    /* Send Data To Fragment List--- */
     //adapter = new MosqueInformationRecyclerView(getContext(), intent);
    adapter = new MosqueInformationRecyclerView( intent);

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
                .tilt(15)
                .build();
        //Move Camera
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));


    }

/*
    class FillList extends BaseAdapter{
        private ArrayList<MosquesLatLng> mosquesLatLngs = new ArrayList<MosquesLatLng>();

        private  Intent intent;
        FillList(Intent intent){
            this.intent = intent;
            this.mosquesLatLngs = mosquesLatLngs;

        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {


            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater layoutInflater = getLayoutInflater();
            View view1= layoutInflater.inflate(R.layout.mosque_information_list,null);
             TextView Masijed_Type2 = (TextView) view1.findViewById(R.id.MosqueType);
             TextView nn = (TextView) view1.findViewById(R.id.Mosque_Type);

            Masijed_Type2.setText("jj");

            return view1;
        }
    }//end Class
    */
}


/*
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

        }else
            if (RegionType ==  2){
            MOSQUE_REGION.setText("مكة المكرمة");
        }else
            if (RegionType == 3){
            MOSQUE_REGION.setText("الشرقية");
        }else
            if (RegionType == 4){
            MOSQUE_REGION.setText("المدينة المنورة");
        }else
            if (RegionType == 5){
            MOSQUE_REGION.setText("الجوف");
        }else
            if (RegionType == 6){
            MOSQUE_REGION.setText("الباحة");
        }else
            if (RegionType == 7){
            MOSQUE_REGION.setText("عسير");
        }else
            if (RegionType == 8){
            MOSQUE_REGION.setText("القصيم");
        }else
            if (RegionType == 9){
            MOSQUE_REGION.setText("حائل");
        }else
            if (RegionType == 10){
            MOSQUE_REGION.setText("تبوك");
        }else
            if (RegionType == 11){
            MOSQUE_REGION.setText("الحدود الشمالية");
        }else
            if (RegionType == 12){
            MOSQUE_REGION.setText("جازان");
        }
        else if (RegionType == 13){
            MOSQUE_REGION.setText("نجران");
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

   */