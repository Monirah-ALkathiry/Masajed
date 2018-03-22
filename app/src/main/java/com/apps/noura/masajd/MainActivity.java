package com.apps.noura.masajd;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockPackageManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;

    // GPSTracker class
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



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


        final TextView loginlink = (TextView) findViewById(R.id.tvloginlink);
        final TextView websitelink = (TextView) findViewById(R.id.tvwebsitelink);
        final TextView Mosque = (TextView) findViewById(R.id.Mosqu);
        final TextView dawa = (TextView) findViewById(R.id.tvdawalink);

        //Monirah Added
        final TextView Favorite = (TextView) findViewById(R.id.Favorite);


        //DawaActivity link open Mnasht Dawa Activity
        dawa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            /*   double latitude;
                double longitude;
                // create class object
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), " FROM FIRST Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                    Intent dawaIntent = new Intent(MainActivity.this,DawaActivity.class);
                    dawaIntent.putExtra("LAT",latitude);
                    dawaIntent.putExtra("LONG",longitude);
                    MainActivity.this.startActivity(dawaIntent);

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }
*/

                Intent dawaIntent = new Intent(MainActivity.this,DawaActivity.class);
                MainActivity.this.startActivity(dawaIntent);
            }
        });

//-----------------------------------------------------------------------------------

//Mosque link open Mosque Activity
        Mosque.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* double latitude;
                double longitude;
                // create class object
                gps = new GPSTracker(MainActivity.this);

                // check if GPS enabled
                if(gps.canGetLocation()){

                     latitude = gps.getLatitude();
                     longitude = gps.getLongitude();

                    // \n is for new line
                    Toast.makeText(getApplicationContext(), " FROM FIRST Your Location is - \nLat: "
                            + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
                    Intent Mosque = new Intent(MainActivity.this,MosqueActivity.class);
                    Mosque.putExtra("LAT",latitude);
                    Mosque.putExtra("LONG",longitude);
                    MainActivity.this.startActivity(Mosque);

                }else{
                    // can't get location
                    // GPS or Network is not enabled
                    // Ask user to enable GPS/network in settings
                    gps.showSettingsAlert();
                }*/

                Intent Mosque = new Intent(MainActivity.this,MosqueActivity.class);
                MainActivity.this.startActivity(Mosque);
            }
        });
//----------------------------------------------------------------------------------------------------------


        //Login link open Login Activity
        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(MainActivity.this,LoginActivity.class);
                MainActivity.this.startActivity(LoginIntent);
            }
        });

        websitelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 final String websiteurl= "http://www.moia.gov.sa/AboutMinistry/Pages/AboutMinistry.aspx";
                Intent LinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteurl));
                startActivity(LinkIntent);
            }
        });
//------------------------------Monirah Added------------------------------------------------

        Favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FavoriteIntent = new Intent(MainActivity.this,FavoriteActivity.class);
                MainActivity.this.startActivity(FavoriteIntent);
            }
        });
    }


}
