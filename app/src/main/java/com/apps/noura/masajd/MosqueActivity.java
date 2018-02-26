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

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MosqueActivity extends AppCompatActivity implements
GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener{


    private static final String TAG = "MosqueActivity";
    private ViewPager mviewPager;


    ///--------------MAP------------------------------
    Intent intentThatCalled;
    private double latitude;
    private double longitude;


    //--------------------------------------
    //Retrofit InterFace:
    private MosquesLatLngClint mosquesLatLngClint;
    //To get Mosque Information
    private List<MosquesLatLng> mosquesLatLngs;


    //-----------------------------------------------------------
    private MosqueListAdapter adapter;
    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //------------------------------------------------------


    //create Retrofit instance
    //TODO :CAHNGE
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://gis.moia.gov.sa/")
            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit = builder.build();

    SearchRequest searchClient =retrofit.create(SearchRequest.class);
    //-----------------------------------------------------

//-------------------GPS-------------------------------------
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;
//----------------------------------------------------


    //Search---
    private ImageButton imageButton ;
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

        //latitude = intentThatCalled.getDoubleExtra("LAT",0.0);
       // longitude = intentThatCalled.getDoubleExtra("LONG",0.0);



//-----------------------------------------------------
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            }else {

                System.out.println("Please Check Your Location");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


       // double latitude;
        //double longitude;
        // create class object
        gps = new GPSTracker(MosqueActivity.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();

            // \n is for new line
            Toast.makeText(getApplicationContext(), " FROM FIRST Your Location is - \nLat: "
                    + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

           // Intent Mosque = new Intent(MosqueActivity.this,MosqueMap.class);
           // Mosque.putExtra("LAT",latitude);
            //Mosque.putExtra("LONG",longitude);
          //  MosqueActivity.this.startActivity(Mosque);

        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
//----------------------------------------------------
        Toast.makeText(getApplicationContext(), "lat From Intent - \n Lat: "
                + latitude + "\n Long: " + longitude, Toast.LENGTH_LONG).show();

          //TabLayout

        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);


    //-----Puop:
        imageButton = (ImageButton) findViewById(R.id.SearchFilter);
            imageButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v) {


                   startActivity(new Intent(MosqueActivity.this,AdvanceSearch.class));
                }
            });
    }
//-----------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        //end check if Not Null
      setupViewPager(mviewPager);
    }

//Search-------
//Search-------

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
         String lat= Double.toString(latitude);
         String lon= Double.toString(longitude);

        //New Test:
        Map<String, Object> map = new HashMap<>();
        //map.put("WHERE"," user_name LIKE :name AND last_name LIKE :last")
        map.put("where","Mosque_Name = N"+"\'"+MosqueName+"\'");
        System.out.println(map + " MAP \n");



        //Call SearchRequest interface
        Call<List<MosquesLatLng>> call = searchClient.getMosqueList(lat,lon,map,5);
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



                String latitude= mosquesLatLngs.get(1).getLatitude();
                String longitude =mosquesLatLngs.get(1).getLongitude();

                System.out.print("latitude" +latitude+"\n");
                System.out.print("longitude" +longitude+"\n");



                double lat=  Double.parseDouble(latitude);
                double lon =Double.parseDouble( longitude);

                adapter = new MosqueListAdapter(MosqueActivity.this, mosquesLatLngs, lat, lon);

                recyclerView.setAdapter(adapter);
               // MosqueMap myObj = new MosqueMap();

              //  myObj.addMoreMarker(mosquesLatLngs);




/*
@Override
			public void onClick(View arg0) {

				// get prompts.xml view
				LayoutInflater li = LayoutInflater.from(context);
				View promptsView = li.inflate(R.layout.prompts, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						context);

				// set prompts.xml to alertdialog builder
				alertDialogBuilder.setView(promptsView);

				final EditText userInput = (EditText) promptsView
						.findViewById(R.id.editTextDialogUserInput);

				// set dialog message
				alertDialogBuilder
					.setCancelable(false)
					.setPositiveButton("OK",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						// get user input and set it to result
						// edit text
						result.setText(userInput.getText());
					    }
					  })
					.setNegativeButton("Cancel",
					  new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog,int id) {
						dialog.cancel();
					    }
					  });

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show it
				alertDialog.show();

			}
		});
	}












                //Send Data To Fragment List---

                adapter = new MosqueListAdapter(MosqueActivity.this, mosquesLatLngs, lat, lon);
                recyclerView.setAdapter(adapter);
*/
               /*
               Bundle bundle = new Bundle();
                bundle.putString("LAT", latitude);
                bundle.putString("LONG", longitude);

                    // set MyFragment Arguments
                MosqueList myObj = new MosqueList();
                myObj.setArguments(bundle);
                */



               //Send Data To RecyclerView
        }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.print(":( :( \n" );

            }
        });

}   //

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


    //________open Advance Search----------------



}//end Class
