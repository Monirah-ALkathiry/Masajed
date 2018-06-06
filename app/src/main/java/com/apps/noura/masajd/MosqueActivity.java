package com.apps.noura.masajd;

import android.Manifest;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import com.apps.noura.masajd.Utils.BottomNavigationViewHelper;
import com.apps.noura.masajd.Utils.DrawerNavigation;
import com.apps.noura.masajd.Utils.SharedPreferencesConfig;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class MosqueActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = "MosqueActivity";//Used in BottomNav
    private static final int ACTIVITY_NUM = 1;//Used in BottomNav



    private ViewPager mviewPager;


    //sharedpreferences
    private SharedPreferencesConfig sharedConfig;

    ///--------------MAP------------------------------
    Intent intentThatCalled;
    private double latitude;
    private double longitude;


    //--------------------------------------
    //Retrofit InterFace:
    private SearchRequest searchClient;
    //Retrofit InterFace:
    private AdvanceSearchClint AdvanceSearchClient;
    //To get Mosque Information
    public List<MosquesLatLng> mosquesLatLngs;
    public List<MosquesLatLng> AdvanceMosquesLatLngs;

    //Used To Update Map_Marker
    public FragmentCommunicator fragmentCommunicator;


    //-----------------------------------------------------------
    private MosqueListAdapter adapter;
    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //------------------------------------------------------

    //-------Nav  drawerLayout
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    protected NavigationView navigationView;


    //-------------------GPS-------------------------------------
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    protected GPSTracker gps;
//----------------------------------------------------

    //Search---
    private ImageButton imageButton;

    //------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque);

        drawerLayout = (DrawerLayout)findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    //Navigation:----------------
        navigationView = (NavigationView)findViewById(R.id.navegation);

        //Check if User Loge in Or not:
//Check if User Loge in Or not:


        sharedConfig = new SharedPreferencesConfig(getApplicationContext());
        if(sharedConfig.readLoginStatus())
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
        }
        else {

            navigationView.getMenu().findItem(R.id.logOut).setVisible(false);

        }

        setupDrawerNavigation();

   //-------------------Bottom Nav: 

        setupBottomNavigationView();
        
  //----------------------------------------      

        //Set Up the view Pager with Section Adapter:
        mviewPager = (ViewPager) findViewById(R.id.container);

        //TabLayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);

        //MAP
        intentThatCalled = getIntent();

        // latitude = intentThatCalled.getDoubleExtra("LAT",0.0);
        //longitude = intentThatCalled.getDoubleExtra("LONG",0.0);
//---------------------------------------------------------------------

        //-----------------------------------------------------
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                //execute every time, else your else part will work
            } else {

                //Toast.makeText(getApplicationContext(), "الرجاء تفعيل الموقع", Toast.LENGTH_LONG).show();

                //System.out.println("Please Check Your Location");
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

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        //----------------------------------------------------


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


  }



    //-------------------------Search -----------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        //------------------------------------------------------------------------------------------------------------
        String s = getIntent().getStringExtra("Query");
       // Toast.makeText(MosqueActivity.this," New Query \n"+s,Toast.LENGTH_LONG).show();

        if(s != null) {
//Convert latitude and longitude to String
            final String lat = Double.toString(latitude);
            final String lon = Double.toString(longitude);
            //Toast.makeText(AdvanceSearch.this, Mesage2, Toast.LENGTH_SHORT).show();
            //Make A Connection With API :
            AdvanceSearchClient = ApiRetrofitClint.getApiRetrofitClint().create(AdvanceSearchClint.class);

            //Call SearchRequest interface
            Call<List<MosquesLatLng>> call = AdvanceSearchClient.getMosqueList2(25, lat, lon, s);
            //Create Response:
            call.enqueue(new Callback<List<MosquesLatLng>>() {
                @Override
                public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {
                    AdvanceMosquesLatLngs = response.body();
                    //Test Result and Print Data
                   //System.out.println("Search Responce :");
                  // System.out.println("Responce toString" + response.toString());
                  //  System.out.println("Responce body" + response.body());
                  //  System.out.println("Responce Headers" + response.headers());
                 //  System.out.print("URL" + response.isSuccessful());

                    //Log.e("  URL KK : ", call.request().url().toString());

                    if (AdvanceMosquesLatLngs.size() == 0) {
                        Toast.makeText(MosqueActivity.this, "لايوجد بيانات", Toast.LENGTH_LONG).show();
                        return;

                    }
                    else {

                        String latitude = AdvanceMosquesLatLngs.get(0).getLatitude();
                        String longitude = AdvanceMosquesLatLngs.get(0).getLongitude();

                     //Map -----
                        double latNew = Double.parseDouble(latitude);
                        double lonNew = Double.parseDouble(longitude);


                        recyclerView = (RecyclerView) findViewById(R.id.MosqueRecyclerView);
                        layoutManager = new LinearLayoutManager(MosqueActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);

                        adapter = new MosqueListAdapter(MosqueActivity.this, AdvanceMosquesLatLngs, latNew, lonNew);
//
                        recyclerView.setAdapter(adapter);

                        //Map -----
                         fragmentCommunicator.passData(AdvanceMosquesLatLngs);

                    }
                }

                @Override
                public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                    //System.out.print(":( :( \n");
                    Toast.makeText(MosqueActivity.this, "الرجاء ادخال كلمات بحث اخرى", Toast.LENGTH_LONG).show();
                }
            });

        }


        // getMenuInflater().inflate(R.menu.menu_mosque_information,menu);
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mosque_information, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        // Assumes current activity is the searchable activity
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                //on-click submit
                //Toast.makeText(MosqueActivity.this, query, Toast.LENGTH_LONG).show();
                try {
                    String Search_String = query;
                    Search(Search_String);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    //Search
    protected String SearchQuery;

    public void Search(String query) {
        SearchQuery = query;
        //Convert latitude and longitude to String
        final String lat = Double.toString(latitude);
        final String lon = Double.toString(longitude);

        //New Test:
        final Map<String, Object> map = new HashMap<>();

        map.put("where", "Mosque_Name = N" + "\'" + SearchQuery + "\'");
        System.out.println(map + " MAP \n");

        //Make A Connection With API :
        searchClient = ApiRetrofitClint.getApiRetrofitClint().create(SearchRequest.class);

        //Call SearchRequest interface
        Call<List<MosquesLatLng>> call = searchClient.getMosqueList(lat, lon, map, 25);
        //  Create Response:
        call.enqueue(new Callback<List<MosquesLatLng>>() {
            @Override
            public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {
                mosquesLatLngs = response.body();
                //Test Result and Print Data
              //  System.out.println("Search Responce :");
               // System.out.println("Responce toString" + response.toString());
              //  System.out.println("Responce body" + response.body());
               // System.out.println("Responce Headers" + response.headers());
               // System.out.print("URL" + response.isSuccessful());

              //  Log.e("  URL KK : ", call.request().url().toString());

                if (mosquesLatLngs.size() == 0) {
                   // Log.e("  URL KK : ", "There is NO data ");
                    Toast.makeText(MosqueActivity.this," لاتوجد بيانات" +SearchQuery,Toast.LENGTH_LONG).show();

                } else {

                    String Newlatitude = mosquesLatLngs.get(0).getLatitude();
                    String Newlongitude = mosquesLatLngs.get(0).getLongitude();

                    // System.out.print("Newlatitude" + Newlatitude + "\n");
                    //  System.out.print("longitude" + Newlongitude + "\n");

                    //  System.out.print("MOSQU NAME ++++ 0000000000 " +mosquesLatLngs.get(0).getMosqueName() + "\n"
                    //   + "Array Size" + mosquesLatLngs.size() );

                    // for (int i=0 ; i < mosquesLatLngs.size() ; i++){

                    // System.out.print("New  NAME ++++ AAAAAA : " + mosquesLatLngs.get(i).getMosqueName());

                    // }
                    double latNew = Double.parseDouble(Newlatitude);
                    double lonNew = Double.parseDouble(Newlongitude);


                    recyclerView = (RecyclerView) findViewById(R.id.MosqueRecyclerView);
                    layoutManager = new LinearLayoutManager(MosqueActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);

                    adapter = new MosqueListAdapter(MosqueActivity.this, mosquesLatLngs, latNew, lonNew);
//
                    recyclerView.setAdapter(adapter);

                    //Map -----
                    fragmentCommunicator.passData(mosquesLatLngs);

                }
            }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.print("لا يوجد اتصال \n");

            }
        });


}

    //Here is new method
    public void passVal(FragmentCommunicator fragmentCommunicator) {
        this.fragmentCommunicator = fragmentCommunicator;

    }

//-----------------------------------------------------
@Override
    protected void onStart() {
      super.onStart();
    //end check if Not Null
    //setupViewPager(mviewPager);
    if(sharedConfig.readLoginStatus())
    {
        navigationView.getMenu().findItem(R.id.login).setVisible(false);
    }

 }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setupViewPager(mviewPager);
        //tabs
        mviewPager.setCurrentItem(0);

        //Update Drawer Nav
        if(sharedConfig.readLoginStatus())
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
        }
    }


//Create Function : Section Page Adapter , then Add Fragment To it

    private void setupViewPager(ViewPager viewPager) {

        MosqueSectoinsAdapter adapter = new MosqueSectoinsAdapter(getSupportFragmentManager());

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


    //Bottom Nav
    private void setupBottomNavigationView() {
        //Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }

    //Drawer Nav
    private void setupDrawerNavigation() {
        //Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
//Check if User Loge in Or not:


            DrawerNavigation.enableDrawerNavigation(this, navigationView);


      //  Menu menu = navigationView.getMenu();
        //MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
       // menuItem.setChecked(true);

    }
}

