package com.apps.noura.masajd;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.SearchView;
import android.widget.Toast;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;



import java.util.List;




public class MosqueActivity extends AppCompatActivity implements
GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{


    private static final String TAG = "MosqueActivity";
    private ViewPager mviewPager;


    ///--------------MAP------------------------------
    Intent intentThatCalled;
    public double latitude;
    public double longitude;



    //--------------------------------------
//Retrofit InterFace:
private MosquesLatLngClint mosquesLatLngClint;
    //To get Mosque Information
    private List<MosquesLatLng> mosquesLatLngs;


//------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque);

        //Log to start
        Log.d(TAG,"Start");

        //Set Up the view Pager with Section Adapter:
        mviewPager = (ViewPager) findViewById(R.id.container);

        //MAP
        intentThatCalled = getIntent();

        latitude = intentThatCalled.getDoubleExtra("LAT",0.0);
        longitude = intentThatCalled.getDoubleExtra("LONG",0.0);

        Toast.makeText(getApplicationContext(), "lat From Intent - \n Lat: "
                + latitude + "\n Long: " + longitude, Toast.LENGTH_LONG).show();

          //TabLayout

        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);

    }
//-----------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        //end check if Not Null
      setupViewPager(mviewPager);
    }

//Search-------

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

//--------------------END Search------------------------------------------

// ----------------MAP--------------------------------

    public static boolean isLocationEnabled(Context context) {
        //...............
        return true;
    }

//----------------------------------------------------------
@Override
protected void onPause() {
    super.onPause();

}
    //Create Function : Section Page Adapter , then Add Fragment To it

    private void setupViewPager(ViewPager viewPager ){


        MosqueSectoinsAdapter adapter = new MosqueSectoinsAdapter(getSupportFragmentManager());

       adapter.AddFragment(new MosqueMap(latitude,longitude), "خريطه" );
       adapter.AddFragment(new MosqueList(latitude,longitude), "قائمة" );

        viewPager.setAdapter(adapter);

    }//end Function ViewPager


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}//end Class
