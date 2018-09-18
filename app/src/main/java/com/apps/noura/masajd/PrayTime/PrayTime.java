package com.apps.noura.masajd.PrayTime;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.icu.util.Calendar;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.apps.noura.masajd.GPS.GPSTracker;
import com.apps.noura.masajd.NavigationDrawer.BasicActivity;
import com.apps.noura.masajd.R;
import com.apps.noura.masajd.Utils.BottomNavigationViewHelper;
import com.apps.noura.masajd.Utils.DrawerNavigation;
import com.apps.noura.masajd.Utils.SharedPreferencesConfig;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


//pray time code
public class PrayTime extends BasicActivity {

    private TextView txtPrayerTime1,txtPrayerTime2, txtPrayerTime3,
            txtPrayerTime4,txtPrayerTime5,txtPrayerTime6,txtPrayerTime7,txtPrayerTime1_1,txtPrayerTime2_1, txtPrayerTime3_1,
            txtPrayerTime4_1,txtPrayerTime5_1,txtPrayerTime6_1,txtPrayerTime7_1, TitleText;
    String PrayerName []= new String [7];
    String PrayerTime []= new String [7];
    View v;

    GPSTracker gps;
    double latitude ;
    double longitude;

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    private static final String TAG = "PrayTimeActivity";//Used in BottomNav
    private static final int ACTIVITY_NUM = 1;//Used in BottomNav



    //shared preferences
    private SharedPreferencesConfig sharedConfig;
    //-------Nav  drawerLayout

   // private DrawerLayout drawerLayout;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    //private NavigationView navigationView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

       // setContentView(R.layout.activity_pray_time);


//------------------------------------------------
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_pray_time, null, false);
        drawer.addView(contentView, 0);
        //check in Menu selected :
        navigationView.setCheckedItem(R.id.ic_praytime);


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
        //---------------------------------------------------

        txtPrayerTime1 = findViewById(R.id.txtPrayerTime1);
        txtPrayerTime2 = findViewById(R.id.txtPrayerTime2);
        txtPrayerTime3 = findViewById(R.id.txtPrayerTime3);
        txtPrayerTime4 = findViewById(R.id.txtPrayerTime4);
        //txtPrayerTime5 = (TextView) findViewById(R.id.txtPrayerTime5);
        txtPrayerTime6 = findViewById(R.id.txtPrayerTime6);
        txtPrayerTime7 = findViewById(R.id.txtPrayerTime7);

        txtPrayerTime1_1 = findViewById(R.id.txtPrayerTime1_1);
        txtPrayerTime2_1 = findViewById(R.id.txtPrayerTime2_1);
        txtPrayerTime3_1 = findViewById(R.id.txtPrayerTime3_1);
        txtPrayerTime4_1 = findViewById(R.id.txtPrayerTime4_1);
        //txtPrayerTime5 = (TextView) findViewById(R.id.txtPrayerTime5);
        txtPrayerTime6_1 = findViewById(R.id.txtPrayerTime6_1);
        txtPrayerTime7_1 = findViewById(R.id.txtPrayerTime7_1);
        TitleText = findViewById(R.id.title);

        //-------------------------------------------------------------------------------

        try {

            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will execute every time, else your else part will work
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //Navigation:----------------


        //Navigation:----------------
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       /* if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/

/*
        drawerLayout = (DrawerLayout)findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

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
        */

        //-------------------Bottom Nav:

        setupBottomNavigationView();

        this.getTime(v);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getTime(View v) {

        // Retrive lat, lng using location API
        gps = new GPSTracker(PrayTime.this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            latitude = gps.getLatitude();
            longitude = gps.getLongitude();


            // \n is for new line
            //Toast.makeText(getApplicationContext(), "Your Location is - \nLat: "
            //+ latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

            Geocoder gcd = new Geocoder(PrayTime.this, Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = gcd.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses.size() > 0) {
                // Toast.makeText(getApplicationContext(), addresses.get(0).getLocality(), Toast.LENGTH_LONG).show();
                TitleText.append(addresses.get(0).getLocality() + "\n");
            } else {
                // do your stuff

                TitleText.append("لا يوجد بيانات"+ "\n");

            }

        } else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }

        //double latitude = 24.71;
        //double longitude = 46.68;
        //24.716246, 46.644440

        double timezone = (Calendar.getInstance().getTimeZone()
                .getOffset(Calendar.getInstance().getTimeInMillis()))
                / (1000 * 60 * 60);

        PrayTimeClass prayers = new PrayTimeClass();
        prayers.setTimeFormat(prayers.Time12);
        prayers.setCalcMethod(prayers.Makkah);
        prayers.setAsrJuristic(prayers.Shafii);
        prayers.setAdjustHighLats(prayers.AngleBased);
        int[] offsets = { 0, 0, 0, 0, 0, 0, 0 }; // {Fajr,Sunrise,Dhuhr,Asr,Sunset,Maghrib,Isha}
        prayers.tune(offsets);

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        //System.out.println("------------------------------"+now);


        ArrayList prayerTimes = prayers.getPrayerTimes(cal, latitude,
                longitude, timezone);
        ArrayList prayerNames = prayers.getTimeNames();

        for (int i = 0; i < prayerTimes.size(); i++) {
            PrayerName[i]= (String)prayerNames.get(i);
            PrayerTime[i]=(String)prayerTimes.get(i);
        }

        txtPrayerTime1.append(PrayerName[0]+"   ");
        txtPrayerTime2.append(PrayerName[1]);
        txtPrayerTime3.append(PrayerName[2]+"   ");
        txtPrayerTime4.append(PrayerName[3]+"  ");
        // txtPrayerTime5.append(PrayerName[4]+ "                "+PrayerTime[4]);
        txtPrayerTime6.append(PrayerName[5]);
        txtPrayerTime7.append(PrayerName[6]);

        txtPrayerTime1_1.append(PrayerTime[0]);
        txtPrayerTime2_1.append(PrayerTime[1]);
        txtPrayerTime3_1.append(PrayerTime[2]);
        txtPrayerTime4_1.append(PrayerTime[3]);
        // txtPrayerTime5.append(PrayerName[4]+ "                "+PrayerTime[4]);
        txtPrayerTime6_1.append(PrayerTime[5]);
        txtPrayerTime7_1.append(PrayerTime[6]);
    }


    //Drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    //Bottom Nav
    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(false);
    }

    //Drawer Nav
    private void setupDrawerNavigation() {
        Log.d("setupDrawerNavigation", "setupBottomNavigationView: setting BottomNavigationView");
        DrawerNavigation.enableDrawerNavigation(this,navigationView);
        //Menu menu = navigationView.getMenu();
        //MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        //  menuItem.setChecked(true);

    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
      /*  if(sharedConfig.readLoginStatus())
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
        }*/
    }
}