package com.apps.noura.masajd;

import android.Manifest;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;


import android.os.Bundle;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MosqueActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
  {

    private GoogleMap mMap;


    private static final String TAG = "MosqueActivity";
    private ViewPager mviewPager;
    private ViewPager mviewPager2;

    ///--------------MAP------------------------------
    Intent intentThatCalled;
    private double latitude;
    private double longitude;


    //--------------------------------------
    //Retrofit InterFace:
    private SearchRequest searchClient;
    //To get Mosque Information
    public List<MosquesLatLng> mosquesLatLngs;


    //-----------------------------------------------------------
    private MosqueListAdapter adapter;
    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //------------------------------------------------------

//-------------------GPS-------------------------------------
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;
//----------------------------------------------------
   public  MosqueMap mosqueMap;

    //Search---
    private ImageButton imageButton ;
//------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque);

        //Log to start
        Log.d(TAG, "Start");

        //Set Up the view Pager with Section Adapter:
        mviewPager = (ViewPager) findViewById(R.id.container);
        mviewPager2 = (ViewPager) findViewById(R.id.container);

        //MAP
        intentThatCalled = getIntent();

        //    latitude = intentThatCalled.getDoubleExtra("LAT",0.0);
        //longitude = intentThatCalled.getDoubleExtra("LONG",0.0);


 //-----------------------------------------------------
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            } else {

                System.out.println("Please Check Your Location");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // create class object
        gps = new GPSTracker(MosqueActivity.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            Toast.makeText(getApplicationContext(), " FROM FIRST Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();


        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
    //----------------------------------------------------
        Toast.makeText(getApplicationContext(), "lat From Intent - \n Lat: "
                + latitude + "\n Long: " + longitude, Toast.LENGTH_LONG).show();

        //TabLayout

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);

        TabLayout tabLayout2 = (TabLayout) findViewById(R.id.tabs);
        tabLayout2.setupWithViewPager(mviewPager2);

        //-----Advance Search :
        imageButton = (ImageButton) findViewById(R.id.SearchFilter);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String lat = String.valueOf(latitude);
                String lon = String.valueOf(longitude);


                Intent intent = new Intent(MosqueActivity.this, AdvanceSearch.class);
                intent.putExtra("LAT", lat);
                intent.putExtra("LON", lon);
                startActivity(intent);

                //startActivity(new Intent(MosqueActivity.this,AdvanceSearch.class));
            }
        });

  //------------------------------------------------------------------------------------------------------------



    }
//-----------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        //end check if Not Null

        setupViewPager(mviewPager);

    }

      @Override
      protected void onPostResume() {
          super.onPostResume();
          setupViewPager(mviewPager);


      }
//Search--------------------------------------------------------------------------------------

  @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_mosque_information,menu);

        //SEARCH --------------------------------
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView(); // Menu Item
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//----Update Recycler View

        recyclerView = (RecyclerView) findViewById(R.id.MosqueRecyclerView);
        layoutManager = new LinearLayoutManager(MosqueActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


//----

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
          public boolean onQueryTextSubmit(String query) {
        //on-click submit
        // Toast.makeText(context,query,Toast.LENGTH_LONG).show();
                try {
                    String Search_String = query;
                    Search(Search_String);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            return false;
    }

    // Search Method
    private void Search(String Search_String) {

         final String MosqueName = Search_String;
        System.out.println(MosqueName);  //Test Print
        //Convert latitude and longitude to String
         final String lat= Double.toString(latitude);
         final String lon= Double.toString(longitude);

        //New Test:
        final Map<String, Object> map = new HashMap<>();
        //map.put("WHERE"," user_name LIKE :name AND last_name LIKE :last")
        map.put("where","Mosque_Name = N"+"\'"+MosqueName+"\'");
        System.out.println(map + " MAP \n");

//Make A Connection With API :
        searchClient = ApiRetrofitClint.getApiRetrofitClint().create(SearchRequest.class);

        //Call SearchRequest interface
        Call<List<MosquesLatLng>> call = searchClient.getMosqueList(lat,lon,map,25);
        //  Create Response:
        call.enqueue(new Callback<List<MosquesLatLng>>() {
            @Override
            public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {
                mosquesLatLngs= response.body();
                //Test Result and Print Data
                System.out.println("Search Responce :");
                System.out.println("Responce toString"+ response.toString());
                System.out.println("Responce body"+ response.body());
                System.out.println("Responce Headers"+ response.headers());
                System.out.print("URL" + response.isSuccessful());

                Log.e("  URL KK : ", call.request().url().toString());

                if(mosquesLatLngs.size() == 0){
                    Log.e("  URL KK : ", "There is NO data ");

                }else {

                    String Newlatitude = mosquesLatLngs.get(0).getLatitude();
                    String Newlongitude = mosquesLatLngs.get(0).getLongitude();

                    System.out.print("Newlatitude" + Newlatitude + "\n");
                    System.out.print("longitude" + Newlongitude + "\n");

                    System.out.print("MOSQU NAME ++++ 0000000000 " +mosquesLatLngs.get(0).getMosqueName() + "\n"
                    + "Array Size" + mosquesLatLngs.size() );

                    for (int i=0 ; i < mosquesLatLngs.size() ; i++){

                        System.out.print("New  NAME ++++ AAAAAA : " + mosquesLatLngs.get(i).getMosqueName());

                    }
                    double latNew = Double.parseDouble(Newlatitude);
                    double lonNew = Double.parseDouble(Newlongitude);

                  mosqueMap = new MosqueMap(mosquesLatLngs);
                    mosqueMap.showPointerOnMap(mosquesLatLngs);


                    adapter = new MosqueListAdapter(MosqueActivity.this, mosquesLatLngs, latNew, lonNew);
//
                   recyclerView.setAdapter(adapter);

                  //  setupViewPager(mviewPager2);



                }
        }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.print(":( :( \n" );

            }
        });

}

@Override
    public boolean onQueryTextChange(String newText)
{//on change

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

        return super.onOptionsItemSelected(item);
    }

//--------------------END Search------------------------------------------

    //Create Function : Section Page Adapter , then Add Fragment To it

    private void setupViewPager(ViewPager viewPager ){

              MosqueSectoinsAdapter adapter = new MosqueSectoinsAdapter(getSupportFragmentManager());

            System.out.print("List item Empity\n");

            System.out.println(latitude + " : lat \n lone : " + longitude);


            adapter.AddFragment(new MosqueMap(latitude, longitude), "خريطه");
            adapter.AddFragment(new MosqueList(latitude, longitude), "قائمة");

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



    /*
protected List<MosquesLatLng> mo;

            MarkerOptions marker;

            public void onSearch ( List<MosquesLatLng> mosquesLatLngs){

        mo = mosquesLatLngs;
    System.out.println( mo.get(0).getMosqueName()+"[[[[[[[[[[[[[[[[[[[[[[[[[[[[");

    double lat = Double.parseDouble(mo.get(0).getLatitude());
    double lon = Double.parseDouble(mo.get(0).getLongitude());
    String mosq =mo.get(0).getMosqueName();
    LatLng latLng= new LatLng(lat,lon);
                marker = new MarkerOptions()
                     .position(latLng)
                    .title(mosq)////title on the marker
                    .snippet("NNNNNNNNNNNNNNNNN");



}
GoogleMap map;
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                map.addMarker(marker);
            }
//--------------------------------------------------------------------------------------
*/
        }//end Class
