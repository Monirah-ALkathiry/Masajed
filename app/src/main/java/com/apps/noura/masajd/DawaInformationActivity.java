package com.apps.noura.masajd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Noura Alsomaikhi on 1/1/2018.
 */

public class DawaInformationActivity extends AppCompatActivity {

    //Adapter------------------------
    private static final String TAG = "DawaInformationActivity";
    private DawaInformationAdapter dawaInformationAdapter;
    private ViewPager mViewPager;
    private TextView DawaAdress ;
    //---------------------------------


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dawa_information);
        //Log to start
        Log.d(TAG,"On Creat");

        // final Context context = this;

        //Set Up the view Pager with Section Adapter:
        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);

        //TabLayout
        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        DawaAdress = (TextView) findViewById(R.id.DawaAdress);

        //Get Data From Mosque Adapter:
        Intent intent = getIntent();
        DawaAdress.setText(intent.getStringExtra("DawaAddress"));




    }

    //create view Pager

    private void setUpViewPager(ViewPager viewPager ){

        DawaInformationAdapter adapter = new DawaInformationAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);


    }


}
