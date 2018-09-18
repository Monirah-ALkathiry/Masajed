package com.apps.noura.masajd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.Toast;

import com.apps.noura.masajd.GPS.GPSTracker;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;


    // GPSTracker class
    private GPSTracker gps;
    private double latitude;
    private double longitude;

//------------Grid
    GridView gridview;

    public static String[] osNameList ;
            /*= {

            "iOS",
            "Linux",
            "MacOS",
            "MS DOS",
            "Symbian",
            "Windows 10",
            "Windows XP",
    };*/
    public static int[] osImages = {
            R.drawable.mosque_,
            R.drawable.dawa,
            R.drawable.ic_praytime,
            R.drawable.emps_ervices,
            R.drawable.observer,
            R.drawable.maintenance,
            R.drawable.aboutapp,
            R.drawable.about,
            R.drawable.messages,
            };

    //-------------------------

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        osNameList =getResources().getStringArray(R.array.races_array);

        //LOCATION:

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        // create class object
        gps = new GPSTracker(MainActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
              //      + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }





    //------End Location---


        gridview = (GridView) findViewById(R.id.customgrid);
        gridview.setAdapter(new CustomAdapter(this, osNameList, osImages));
    }




}
