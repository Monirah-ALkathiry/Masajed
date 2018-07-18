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

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Noura Alsomaikhi on 12/31/2017.
 */

public class DawaActivity extends AppCompatActivity  implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener
,FirstFragmentListenerDawaMAP{



    private static final String TAG = "DawaActivity";//Used in BottomNav
    private static final int ACTIVITY_NUM = 2;//Used in BottomNav

    private ViewPager mviewPager;


    private Intent intent;
    private double latitude;
    private double longitude;
    //-------Search------------

    private DawaAdvanceSearchClint dawaAdvanceSearchClint;


    //sharedpreferences
    private SharedPreferencesConfig sharedConfig;
    //-------------------GPS-------------------------------------
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;
//----------------------------------------------------

    //-------Nav  drawerLayout
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
//--------------------------------------
//Search---
private ImageButton imageButton ;
    //--------------------------------------
    //Retrofit InterFace:
    private DawaAdvanceSearchClint searchClient;
    //To get Mosque Information
    public List<DawaLatLng> dawaLatLngs;

    //--------------Update list View-----------------------------------------
    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DawaListAdapter adapter;
    //------------------------------------------------------

    //Used To Update Map_Marker
    public DawaFragmentCommunicator dawaFragmentCommunicator;



    //Used To Update Map_Marker
    public FragmentCommunicator fragmentCommunicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dawa);


        Log.d(TAG,"Start");

        //viewpager
        mviewPager = (ViewPager) findViewById(R.id.container);
       // setupViewPager(mviewPager);




        //Navigation:----------------
        drawerLayout = (DrawerLayout)findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        navigationView = (NavigationView)findViewById(R.id.navegation);

        //Check If User Login
        sharedConfig = new SharedPreferencesConfig(getApplicationContext());
        if(sharedConfig.readLoginStatus())
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
            //finish();
        }
        else {

            navigationView.getMenu().findItem(R.id.logOut).setVisible(false);

        }

        setupDrawerNavigation();

        //-------------------Bottom Nav:

        setupBottomNavigationView();
        //---------------------------

//----------------------------------------------------------------


        //TabLayout
        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);


        //New
        setupViewPager(mviewPager);

        //MAP
        intent = getIntent();
        //latitude = intent.getDoubleExtra("LAT",0.0);
        //longitude = intent.getDoubleExtra("LONG",0.0);



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
            //Toast.makeText(getApplicationContext(), " FROM FIRST Your Location is - \nLat: "
                 //   + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

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
       // Toast.makeText(getApplicationContext(), "lat From Intent - \n Lat: "
             //   + latitude + "\n Long: " + longitude, Toast.LENGTH_LONG).show();



        //-----Advance Search :
        imageButton = (ImageButton) findViewById(R.id.SearchFilter);
        imageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                String lat= String.valueOf(latitude);
                String lon= String.valueOf(longitude);


                Intent intent = new Intent(DawaActivity.this,DawaAdvanceSearch.class);
                intent.putExtra("LAT",lat);
                intent.putExtra("LON",lon);
                startActivity(intent);

            }
        });

//-----------------------------------------------------

    }

//-----------------------------------SEARCH------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        //------------------------------------------------------------------------------------------------------------
        String s = getIntent().getStringExtra("Query");



        if(s != null) {
//Convert latitude and longitude to String
            final String lat = Double.toString(latitude);
            final String lon = Double.toString(longitude);
            //Toast.makeText(AdvanceSearch.this, Mesage2, Toast.LENGTH_SHORT).show();
            //Make A Connection With API :


            dawaAdvanceSearchClint= ApiRetrofitClint.getApiRetrofitClint().create(DawaAdvanceSearchClint.class);

            Call<List<DawaLatLng>> call = dawaAdvanceSearchClint.getDawaSearchResult(25,lat,lon,s);

            call.enqueue(new Callback<List<DawaLatLng>>() {
                @Override
                public void onResponse(Call<List<DawaLatLng>> call, Response<List<DawaLatLng>> response) {
                    dawaLatLngs = response.body();
                    //Test Result and Print Data
                    //  System.out.println("Search Responce :");
                    // System.out.println("Responce toString" + response.toString());
                     // System.out.println("Responce body" + response.body());
                     //System.out.println("Responce Headers" + response.headers());
                    // System.out.print("URL" + response.isSuccessful());

                    //  Log.e("  URL KK : ", call.request().url().toString());

                    if (dawaLatLngs.size() == 0) {
                        // Log.e("  URL KK : ", "There is NO data ");
                        Toast.makeText(DawaActivity.this," لاتوجد بيانات" +SearchQuery,Toast.LENGTH_LONG).show();

                    } else {

                        String Newlatitude = dawaLatLngs.get(0).getLocYCoord();
                        String Newlongitude = dawaLatLngs.get(0).getLocXCoord();


                        double latNew = Double.parseDouble(Newlatitude);
                        double lonNew = Double.parseDouble(Newlongitude);


                        //Recycler View
                        recyclerView = (RecyclerView) findViewById(R.id.DawaRecyclerView);
                        layoutManager = new LinearLayoutManager(DawaActivity.this);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);
                        //-------------------------------------------------------

                        //Send Data To Fragment List---
                        adapter = new DawaListAdapter(DawaActivity.this, dawaLatLngs, latNew, lonNew);

                        recyclerView.setAdapter(adapter);

                        //Map -----
                        dawaFragmentCommunicator.passData(dawaLatLngs);
                    }
                }

                @Override
                public void onFailure(Call<List<DawaLatLng>> call, Throwable t) {
                    System.out.print(":( :( \n");

                }
            });

        }

         //getMenuInflater().inflate(R.menu.menu_mosque_information,menu);
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mosque_information, menu);

      //  final Context context= this;

        // Get the SearchView and set the searchable configuration

        //SEARCH --------------------------------
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView(); // Menu Item

        // Assumes current activity is the searchable activity
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default





        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //on-click submit
               // Toast.makeText(context,query,Toast.LENGTH_LONG).show();

                try {
                    String Search_by_Name = query;
                    Search(Search_by_Name);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//on change
                return false;
            }
        });
        //-------------------------------------------------------

      MenuItem menuItem = menu.findItem(R.id.search);
      menuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
          @Override
          public boolean onMenuItemActionExpand(MenuItem item) {
              Toast.makeText(DawaActivity.this, "Send data ", Toast.LENGTH_LONG).show();

              return true;


          }

          @Override
          public boolean onMenuItemActionCollapse(MenuItem item) {

              dawaLatLngs = null;
              Toast.makeText(DawaActivity.this, "Send data after back", Toast.LENGTH_LONG).show();
              dawaFragmentCommunicator.passData(dawaLatLngs);

              return true;
          }
      });




        //super.onCreateOptionsMenu(menu)  default return
        return super.onCreateOptionsMenu(menu);
    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();

        //if(id == R.id.collapseActionView)

        return super.onOptionsItemSelected(item);
    }
*/
  //----------------------Connection With API-----------------------------------

    //Search
    protected String SearchQuery;

    public void Search(String query) {
        SearchQuery = query;
        //Convert latitude and longitude to String
        final String lat = Double.toString(latitude);
        final String lon = Double.toString(longitude);

        //New Test:
        final Map<String, Object> map = new HashMap<>();
        map.put("where", "DawaActivitiesInfo.ActivityExecution ='2' AND DawaActivitiesInfo.AmaraApproval ='2' AND DawaActivitiesInfo.DawaAddress = N" + "\'" + SearchQuery + "\'");
        System.out.println(map + " MAP \n");

        final String dawa;
        dawa = "DawaActivitiesInfo.DawaAddress = N" + "\'" + SearchQuery + "\'";

        //Make A Connection With API :
        searchClient = ApiRetrofitClint.getApiRetrofitClint().create(DawaAdvanceSearchClint.class);

        //Call SearchRequest interface
        Call<List<DawaLatLng>> call = searchClient.getDawaSearchResult(25,lat,lon,dawa);
        //  Create Response:
        call.enqueue(new Callback<List<DawaLatLng>>() {
            @Override
            public void onResponse(Call<List<DawaLatLng>> call, Response<List<DawaLatLng>> response) {
                dawaLatLngs = response.body();
                //Test Result and Print Data
                //  System.out.println("Search Responce :");
                // System.out.println("Responce toString" + response.toString());
                //  System.out.println("Responce body" + response.body());
                // System.out.println("Responce Headers" + response.headers());
                // System.out.print("URL" + response.isSuccessful());

                //  Log.e("  URL KK : ", call.request().url().toString());

                if (dawaLatLngs.size() == 0) {
                    // Log.e("  URL KK : ", "There is NO data ");
                    Toast.makeText(DawaActivity.this," لاتوجد بيانات " +SearchQuery,Toast.LENGTH_LONG).show();

                } else {

                    String Newlatitude = dawaLatLngs.get(0).getLocYCoord();
                    String Newlongitude = dawaLatLngs.get(0).getLocXCoord();


                    double latNew = Double.parseDouble(Newlatitude);
                    double lonNew = Double.parseDouble(Newlongitude);


                    //Recycler View
                    recyclerView = (RecyclerView) findViewById(R.id.DawaRecyclerView);
                    layoutManager = new LinearLayoutManager(DawaActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setHasFixedSize(true);
                    //-------------------------------------------------------

                    //Send Data To Fragment List---
                    adapter = new DawaListAdapter(DawaActivity.this,dawaLatLngs , latNew , lonNew);

                    recyclerView.setAdapter(adapter);

                    //Map -----
                    dawaFragmentCommunicator.passData(dawaLatLngs);


                }
            }

            @Override
            public void onFailure(Call<List<DawaLatLng>> call, Throwable t) {
                System.out.print(":( :( \n");

            }



        });


    }

    //Here is new method
    public void passVal(DawaFragmentCommunicator dawaFragmentCommunicator) {
        this.dawaFragmentCommunicator = dawaFragmentCommunicator;

    }
//---------------------------------------------------------------------------------

    @Override
    protected void onStart() {
        super.onStart();
        //end check if Not Null
        if(sharedConfig.readLoginStatus())
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
        }

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();

        setupViewPager(mviewPager);
        mviewPager.setCurrentItem(0);

        if(sharedConfig.readLoginStatus())
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
        }
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

        DrawerNavigation.enableDrawerNavigation(this, navigationView);
       //Menu menu = navigationView.getMenu();
       // MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        //menuItem.setChecked(true);

    }


    //send Data TO List -------------Idle------------------------
    @Override
    public void sendData(List<DawaLatLng> newData) {

        //Recycler View
        recyclerView = (RecyclerView) findViewById(R.id.DawaRecyclerView);
        layoutManager = new LinearLayoutManager(DawaActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //-------------------------------------------------------

        //Send Data To Fragment List---
         adapter = new DawaListAdapter(DawaActivity.this,newData);

        recyclerView.setAdapter(adapter);
        //Map -----
        //dawaFragmentCommunicator.passData(dawaLatLngs);


    }
}
