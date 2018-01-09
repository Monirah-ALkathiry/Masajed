package com.apps.noura.masajd;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Noura Alsomaikhi on 12/31/2017.
 */

public class DawaActivity extends AppCompatActivity {
    private static final String TAG = "DawaActivity";
    private ViewPager mviewPager;


    //----------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dawa);

        Log.d(TAG,"Start");

        //viewpager
        mviewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mviewPager);

        //TabLayout
        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);


    }



    private void setupViewPager(ViewPager viewPager){

        DawaSectionAdapter adapter = new DawaSectionAdapter(getSupportFragmentManager());

        adapter.AddFragment(new DawaMap(), "خريطه");
        adapter.AddFragment(new DawaList(), "قائمة");

        viewPager.setAdapter(adapter);

    }
}
