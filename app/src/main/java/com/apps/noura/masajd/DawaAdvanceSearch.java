package com.apps.noura.masajd;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DawaAdvanceSearch extends AppCompatActivity  implements Sender{

    private static final String TAG = "Search dialog";

    private ViewPager mViewPager;
    private AdvanceSearchPageAdapter advanceSearchPageAdapter;

    Intent intent;
    protected String latitude;
    protected String longitude;

    private Button bSearch;
    private Button bExit;

    //Retrofit InterFace:
    private DawaAdvanceSearchClint searchClient;
    //To get Mosque Information
    public List<DawaLatLng> dawaLatLngs;


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

        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

                finish();
            }
        });
   System.out.print(latitude + "  LAT: \n Lone: " +longitude);
        setUpViewPager(mViewPager);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    private void setUpViewPager(ViewPager viewPager) {

        AdvanceSearchPageAdapter adapter = new AdvanceSearchPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new DawaSearch(latitude , longitude), "عنوان المنشط");
        adapter.addFragment(new DaeiaSearch(latitude , longitude), "إسم الداعية" );


        viewPager.setAdapter(adapter);
    }


    protected String Mesage2;
    @Override
    public void SendMassage(String Mesage) {

        Mesage2 = Mesage;
        if (Mesage2 == null) {
            Toast.makeText(DawaAdvanceSearch.this, "لايوجد مدخلات ", Toast.LENGTH_LONG).show();

        }

        Toast.makeText(DawaAdvanceSearch.this, Mesage2, Toast.LENGTH_LONG).show();
        System.out.println("Advance Searc Querey Dawa \n"+Mesage2 + "\n");
////TODO : Advance Search View Result
        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //Make A Connection With API :
                searchClient = ApiRetrofitClint.getApiRetrofitClint().create(DawaAdvanceSearchClint.class);

                //Call SearchRequest interface
                Call<List<DawaLatLng>> call = searchClient.getDawaSearchResult(25,latitude , longitude, Mesage2);
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
                Toast.makeText(DawaAdvanceSearch.this," لاتوجد بيانات" ,Toast.LENGTH_LONG).show();

                    } else {

                        String Newlatitude = dawaLatLngs.get(0).getLocYCoord();
                        String Newlongitude = dawaLatLngs.get(0).getLocXCoord();


                        double latNew = Double.parseDouble(Newlatitude);
                        double lonNew = Double.parseDouble(Newlongitude);




                    }
                    }

                    @Override
                    public void onFailure(Call<List<DawaLatLng>> call, Throwable t) {
                        Toast.makeText(DawaAdvanceSearch.this, "الرجاء ادخال كلمات بحث اخرى", Toast.LENGTH_LONG).show();

                    }
                });

                finish();
            }
        });

    }//end Function
}//end Class
