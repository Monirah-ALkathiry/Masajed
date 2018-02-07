package com.apps.noura.masajd;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;


import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import static com.apps.noura.masajd.UsereLocationListener.location;


public class MosqueActivity extends AppCompatActivity implements LocationListener {


    private static final String TAG = "MosqueActivity";
    private ViewPager mviewPager;


    ///--------------MAP------------------------------
    Intent intentThatCalled;
    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;


    LocationRequest mLocationRequest;
    Location location;
    GoogleApiClient mGoogleApiClient;

    //--------------------------------------
//Retrofit InterFace:
private MosquesLatLngClint mosquesLatLngClint;
    //To get Mosque Information
    private List<MosquesLatLng> mosquesLatLngs;

    //
    protected Intent mapIntent ;
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

        System.out.print(intentThatCalled + " \n INTENT");
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getLocation();

        }
;

        //TabLayout

        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);

//-------------------------------------------------------

//-----------------------------------------------------


         }

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


               /*
                String lat= Double.toString(latitude);
                 String  lon= Double.toString(longitude);
                System.out.print("  \n"+ lat +"Lat And Long " + lon + "\n");

                //Add Retrofit :

                //Make A Connection With API :
                mosquesLatLngClint = ApiRetrofitClint.getApiRetrofitClint().create(MosquesLatLngClint.class);
                //Call Function form Inter Face And Send Parameter to it


                Call<List<MosquesLatLng>> call = mosquesLatLngClint.Search_by_Name(lat,lon,25,Search_by_Name);
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
                      //  System.out.println("Responce toString"+ response.toString());
                       // System.out.println("Responce body"+ response.body());
                       System.out.println("Responce headers"+ response.headers());
                       // System.out.println("Responce errorBody"+ response.errorBody());
                       System.out.print("URL" + response.isSuccessful());
                        //Storing the data in our list

                        System.out.println("Size Is onResponce :----" +mosquesLatLngs.size());
                        //-----------------------------------------------------------------------
                        System.out.println("NUMBER :----" +mosquesLatLngs.get(1).getMoathenEmployeeNo());

                    }

                    @Override
                    public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                        System.out.println("Error bad  ):--------Search");
                    }
                });

                */
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

    protected void getLocation() {
        if (isLocationEnabled(this)) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Toast.makeText(this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
                //ConnectWithAPI(latitude,longitude);
            }
            else{
                //This is what you need:
                //(android.location.LocationListener)
              //  Toast.makeText(this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();

                locationManager.requestLocationUpdates(bestProvider, 1000, 0, (android.location.LocationListener) this);
            }

        }
        else
        {
            //prompt user to enable location
            //.................
            Toast.makeText(this, "الرجاء منكم تفعيل احداثي الموقع GPS", Toast.LENGTH_SHORT).show();

        }


    }

    @Override
    public void onLocationChanged(Location location) {
        //Hey, a non null location! Sweet!

        //remove location callback:
        locationManager.removeUpdates((android.location.LocationListener) this);

        //open the map:
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Toast.makeText(this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();


    }

    public  void ConnectWithAPI(double lat, double lon){

        double LocationLat= lat;
        double Locationlon = lon;
        String latitude;
        String longitude;

        latitude= Double.toString(LocationLat);
        longitude= Double.toString(Locationlon);




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

                setupViewPager(mviewPager);

           /*(() Intent intent = new Intent(getContext(),MosqueActivity.class);
                intent.putExtra("MsqResult", String.valueOf(mosquesLatLngs));
            startActivity(intent);
            */
            }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.println("Error bad  ):-----------------------");
            }
        });




    }


//----------------------------------------------------------
@Override
protected void onPause() {
    super.onPause();
    // locationManager.removeUpdates(MosqueActivity);

}
    //Create Function : Section Page Adapter , then Add Fragment To it

    private void setupViewPager(ViewPager viewPager ){


        MosqueSectoinsAdapter adapter = new MosqueSectoinsAdapter(getSupportFragmentManager());

        //List<MosquesLatLng> mosquesLatLngsT = mosquesLatLngs;
      // System.out.print("LIST IS Empty Check " + mosquesLatLngsT.isEmpty());

        adapter.AddFragment(new MosqueMap(latitude,longitude), "خريطه" );
        adapter.AddFragment(new MosqueList(latitude,longitude), "قائمة" );

        viewPager.setAdapter(adapter);

    }//end Function ViewPager



}//end Class
