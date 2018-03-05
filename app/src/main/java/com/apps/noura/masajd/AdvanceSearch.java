package com.apps.noura.masajd;

import android.app.Fragment;
import android.content.Intent;
import android.support.design.widget.TabLayout;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AdvanceSearch extends AppCompatActivity  {

    private static final String TAG = "Search dialog";

    private ViewPager mViewPager;
    private AdvanceSearchPageAdapter advanceSearchPageAdapter;

    Intent intent;
    protected String latitude;
    protected String longitude;

    private Button bSearch;
    private Button bExit;

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


   System.out.print(latitude + "  LAT: \n Lone: " +longitude);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setUpViewPager(mViewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {

        AdvanceSearchPageAdapter adapter = new AdvanceSearchPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new MosqueSearch(latitude , longitude), "إسم  المسجد");
        adapter.addFragment(new ImamaSearch(latitude , longitude), "إسم الإمام" );
        adapter.addFragment(new KhateebSearch(latitude , longitude), "إسم  الخطيب");

        viewPager.setAdapter(adapter);
    }



}
