package com.apps.noura.masajd;

import android.app.Fragment;
import android.content.Intent;
import android.content.IntentSender;
import android.support.design.widget.TabLayout;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdvanceSearch extends AppCompatActivity implements Sender{

    private static final String TAG = "Search dialog";

    private ViewPager mViewPager;
    private AdvanceSearchPageAdapter advanceSearchPageAdapter;

    Intent intent;
    protected String latitude;
    protected String longitude;

   private Button bSearch;
   private Button bExit;


   //-----------------------------------------------
   //Retrofit InterFace:
   private AdvanceSearchClint searchClient;
    //To get Mosque Information
    private List<MosquesLatLng> mosquesLatLngs;
    String Mesage2;
    //-----------------------------------------------------------
    private MosqueListAdapter adapter;
    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //-----------------------------------------------------
    //-----------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search);

        advanceSearchPageAdapter = new AdvanceSearchPageAdapter(getSupportFragmentManager());

        //set up the viewpager with Section adapter
        mViewPager = (ViewPager) findViewById(R.id.container);

        //Tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        intent=getIntent();
        latitude = intent.getStringExtra("LAT");
        longitude =  intent.getStringExtra("LON");


        bSearch =(Button) findViewById(R.id.search);
        bExit =(Button) findViewById(R.id.exit);

        System.out.print(latitude + "  LAT: \n Lone: " +longitude);

        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

                finish();
            }
        });


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setUpViewPager(mViewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();

    }



    private void setUpViewPager(ViewPager viewPager) {

        AdvanceSearchPageAdapter adapter = new AdvanceSearchPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new MosqueSearch(latitude , longitude), "إسم  المسجد");
        adapter.addFragment(new ImamaSearch(latitude , longitude), "إسم الإمام" );
        adapter.addFragment(new KhateebSearch(latitude , longitude), "إسم  الخطيب");

        viewPager.setAdapter(adapter);
    }


    @Override
    public void SendMassage(String Mesage) {
        Mesage2 = Mesage;

        if (Mesage2 == null){
            Toast.makeText(AdvanceSearch.this, "لايوجد مدخلات ", Toast.LENGTH_LONG).show();

        }


        //----------------------------------------
//----Update Recycler View

        recyclerView = (RecyclerView) findViewById(R.id.MosqueRecyclerView);
        layoutManager = new LinearLayoutManager(AdvanceSearch.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


//----
        //--------------------------------------



    bSearch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    Toast.makeText(AdvanceSearch.this, Mesage2, Toast.LENGTH_SHORT).show();

    //Make A Connection With API :
    searchClient = ApiRetrofitClint.getApiRetrofitClint().create(AdvanceSearchClint.class);

    //24.70476920400006,46.63042159500003

    //Call SearchRequest interface
    Call<List<MosquesLatLng>> call = searchClient.getMosqueList2(25, latitude, longitude, Mesage2);
        //  Create Response:
        call.enqueue(new Callback<List<MosquesLatLng>>() {
            @Override
            public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {
                mosquesLatLngs = response.body();
                //Test Result and Print Data
                System.out.println("Search Responce :");
                System.out.println("Responce toString" + response.toString());
                System.out.println("Responce body" + response.body());
                System.out.println("Responce Headers" + response.headers());
                System.out.print("URL" + response.isSuccessful());

                Log.e("  URL KK : ", call.request().url().toString());

                if (mosquesLatLngs.size() == 0) {
                    Toast.makeText(AdvanceSearch.this, "لايوجد بيانات", Toast.LENGTH_LONG).show();
                    return;

                }
               else {

                    String latitude = mosquesLatLngs.get(0).getLatitude();
                    String longitude = mosquesLatLngs.get(0).getLongitude();

                    System.out.print("latitude" + latitude + "\n");
                    System.out.print("longitude" + longitude + "\n");


                    double lat = Double.parseDouble(latitude);
                    double lon = Double.parseDouble(longitude);

                    //adapter = new MosqueListAdapter(AdvanceSearch.this, mosquesLatLngs, lat, lon);

                   // recyclerView.setAdapter(adapter);
                }

            }

                @Override
                public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                    System.out.print(":( :( \n");

                    Toast.makeText(AdvanceSearch.this, "الرجاء اعاده ادخال كلمات بحث اخرى", Toast.LENGTH_LONG).show();


                }
            });


            finish();


        }
        });

    } //end Function


}
