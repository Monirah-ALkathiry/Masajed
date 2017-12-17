package com.apps.noura.masajd;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MosqueInformationActivity extends AppCompatActivity implements OnMapReadyCallback {


    //Initialize Map variables :
     //   MapView mapView;
     //   private GoogleMap MgoogleMap;
     //   View mView;
    //-------------------


    //Adapter------------------------
        private static final String TAG = "MosqueInformationActivity";
        private MosqueInformationAdapter mMosqueInformationAdapter;
        private ViewPager mViewPager;
    //Adapter------------------------


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque_information);
    //Log to start
        Log.d(TAG,"On Creat");


       //Map Fragment:
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        //Set Up the view Pager with Section Adapter:
        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);

        //TabLayout
       TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


    }

    //create view Pager

    private void setUpViewPager(ViewPager viewPager){

        MosqueInformationAdapter adapter = new MosqueInformationAdapter(getSupportFragmentManager());

        adapter.AddFragment(new MosqueNote(), "ملاحظات");
        adapter.AddFragment(new MosqueImage(), "الصور");
        adapter.AddFragment(new MosqueInformation(), "عام ");

        viewPager.setAdapter(adapter);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


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
        CameraPosition CameraMosque = CameraPosition.builder()
                .target(latLng)
                .zoom(16)
                .bearing(0)
                .tilt(45)
                .build();
        //Move Camera
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(CameraMosque));

    }


}
