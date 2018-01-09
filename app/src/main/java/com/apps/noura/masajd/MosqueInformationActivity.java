package com.apps.noura.masajd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class MosqueInformationActivity extends AppCompatActivity  {

    //Adapter------------------------
        private static final String TAG = "MosqueInformationActivity";
        private MosqueInformationAdapter mMosqueInformationAdapter;
        private ViewPager mViewPager;
        //private ViewPager mapViewPager;

    //Adapter------------------------
    private TextView MasijedName ;
    //Get Data From Mosque_List ---
           /* private String    MOSQUE_CODE;
            private String  Masijed_Name;
            private String Latitude;
            private String Longitude;
            private String MOSQUE_TYPE;
            private String MOSQUE_REGION;
            private String CITY_VILLAGE;
            private String DISTRICT;
            private String STREET_NAME;
             */

      //---------------------------------


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque_information);
    //Log to start
        Log.d(TAG,"On Creat");

       // final Context context = this;

        //Set Up the view Pager with Section Adapter:
        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);

            //Map View :
       // mapViewPager = (ViewPager) findViewById(R.id.container2);
      //  setUpViewPager(mapViewPager);

        //TabLayout
       TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        MasijedName = (TextView) findViewById(R.id.MasijedName);

        //Get Data From Mosque Adapter:
        Intent intent = getIntent();
        MasijedName.setText(intent.getStringExtra("MOSQUE_NAME"));




         }

    //create view Pager

    private void setUpViewPager(ViewPager viewPager ){

        //TODO : Arrange The Links:
        //ToDo: Add onclick BackGround
         MosqueInformationAdapter adapter = new MosqueInformationAdapter(getSupportFragmentManager());
        adapter.AddFragment(new MosqueInformation(), "عام " , this);
        adapter.AddFragment(new MosqueNote(),  "ملاحظات" , this);
        adapter.AddFragment(new MosqueImage(),  "الصور" ,this);
        viewPager.setAdapter(adapter);

    }


}
