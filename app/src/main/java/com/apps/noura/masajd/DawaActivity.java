package com.apps.noura.masajd;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Noura Alsomaikhi on 12/31/2017.
 */

public class DawaActivity extends AppCompatActivity  implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "DawaActivity";
    private ViewPager mviewPager;


    private Intent intent;
    private double latitude;
    private double longitude;
    //----------------------

    //-------------------GPS-------------------------------------
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;
//----------------------------------------------------
    //--------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dawa);

        Log.d(TAG,"Start");

        //viewpager
        mviewPager = (ViewPager) findViewById(R.id.container);
       // setupViewPager(mviewPager);

        //MAP
        intent = getIntent();
        latitude = intent.getDoubleExtra("LAT",0.0);
        longitude = intent.getDoubleExtra("LONG",0.0);
//----------------------------------------------------------------------------------------
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


        // double latitude;
        //double longitude;
        // create class object
        gps = new GPSTracker(DawaActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), " FROM FIRST Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

          //  Intent dawaIntent = new Intent(DawaActivity.this,DawaMap.class);
            //Mosque.putExtra("LAT",latitude);
           // Mosque.putExtra("LONG",longitude);
           // DawaActivity.this.startActivity(dawaIntent);

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

//---------------------------------------------------------------------------------------------------------
        Toast.makeText(getApplicationContext(), "lat From Intent - \n Lat: "
                + latitude + "\n Long: " + longitude, Toast.LENGTH_LONG).show();

        //TabLayout
        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);


    }
//-----------------------------------SEARCH------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_mosque_information,menu);

        //SEARCH --------------------------------
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView(); // Menu Item
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final Context context= this;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //on-click submit
                Toast.makeText(context,query,Toast.LENGTH_LONG).show();
                String Search_by_Name = query;

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//on change
                return false;
            }
        });
        //-------------------------------------------------------

        //super.onCreateOptionsMenu(menu)  default return
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();

        //if(id == R.id.collapseActionView)

        return super.onOptionsItemSelected(item);
    }
//---------------------------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        //end check if Not Null
        setupViewPager(mviewPager);
    }

    private void setupViewPager(ViewPager viewPager){

        DawaSectionAdapter adapter = new DawaSectionAdapter(getSupportFragmentManager());

        adapter.AddFragment(new DawaMap(latitude,longitude), "خريطه");
        adapter.AddFragment(new DawaList(latitude,longitude), "قائمة");

        viewPager.setAdapter(adapter);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
