package com.apps.noura.masajd;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MosqueActivity extends AppCompatActivity {


    private static final String TAG = "MosqueActivity";
    private MosqueSectoinsAdapter mosqueSectoinsAdapter;
    private ViewPager mviewPager;

    //To get Mosque Information
    private List<MosquesLatLng> mosquesLatLngs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque);

        //Log to start
        Log.d(TAG,"On creat");

        //Set Up the view Pager with Section Adapter:
        mviewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mviewPager);

        //TabLayout

        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);



        //Retrofit Data

        //1)- Declare Retrofit Builder
        Retrofit builder = new Retrofit.Builder()
                .baseUrl("http://mosquesapi.azurewebsites.net")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
//2)_ Create Object Form Mosque_Lat_lng interface
        final MosquesLatLngClint mosquesLatLngClint = builder.create(MosquesLatLngClint.class);
//3)- Call Function form Inter Face And Send Parameter to it
        Call<List<MosquesLatLng>> call = mosquesLatLngClint.getMosqueLatLng("24.7179970","46.64460257");

//4)- Create Response:
        call.enqueue(new Callback<List<MosquesLatLng>>() {
            @Override
            public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {

                mosquesLatLngs = response.body();

                //Test Result and Print Data
                System.out.println("Responce toString"+ response.toString());
                System.out.println("Responce body"+ response.body());
                System.out.println("Responce headers"+ response.headers());
                System.out.println("Responce errorBody"+ response.errorBody());
                System.out.print("URL" + response.isSuccessful());
                //Storing the data in our list

                System.out.println("Size Is onResponce :----" +mosquesLatLngs.size());
                //-----------------------------------------------------------------------

                showlist();//For test
                //LoadMasijedLocations();
            }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.println("Error bad  ):-----------------------");
            }
        });




    }

//TO DO: Send Data to Map Nad LISt
    private void showlist(){

        String[][] item = new String[mosquesLatLngs.size()][2];


        for(int i =0; i<mosquesLatLngs.size() ; i++){
            for(  int j=0 ; j <2 ; j++) {

                if(j == 0) {
                    System.out.println( item[i][j] = mosquesLatLngs.get(i).getLatitude() + " Lat" );
                }else {
                    System.out.println(item[i][j] = mosquesLatLngs.get(i).getLongitude() + " log");
                }
            }//end Inner for

        }//end for


    }

    //Create Function : Section Page Adapter , then Add Fragment To it

    private void setupViewPager(ViewPager viewPager){

        MosqueSectoinsAdapter adapter = new MosqueSectoinsAdapter(getSupportFragmentManager());

        adapter.AddFragment(new MosqueMap(), "خريطه");
        adapter.AddFragment(new MosqueList(), "قائمة");

        viewPager.setAdapter(adapter);

    }//end Function ViewPager


}//end Class
