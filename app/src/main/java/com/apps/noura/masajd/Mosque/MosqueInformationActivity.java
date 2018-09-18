package com.apps.noura.masajd.Mosque;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.apps.noura.masajd.R;


public class MosqueInformationActivity extends AppCompatActivity  {

    //Adapter------------------------
        private static final String TAG = "MosqueInformationActivity";
        private MosqueInformationAdapter mMosqueInformationAdapter;
        private ViewPager mViewPager;
        //private ViewPager mapViewPager;

    //Adapter------------------------
    private TextView MasijedName ;

    private TextView MosquNationalCode;
      //---------------------------------


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque_information);
    //Log to start
        Log.d(TAG,"On Creat");

       // final Context context = this;

        //Set Up the view Pager with Section Adapter:
        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);
        mViewPager.setCurrentItem(0);


            //Map View :
       // mapViewPager = (ViewPager) findViewById(R.id.container2);
      //  setUpViewPager(mapViewPager);

        //TabLayout
       TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        MasijedName = (TextView) findViewById(R.id.MasijedName);
        MosquNationalCode =(TextView) findViewById(R.id.MosquNationalCode);


        //Get Data From Mosque Adapter:
        Intent intent = getIntent();
        MasijedName.setText(intent.getStringExtra("MOSQUE_NAME"));
        MosquNationalCode.setText(intent.getStringExtra("MosqueNationalCode"));



         }

    //create view Pager

    private void setUpViewPager(ViewPager viewPager ){

         MosqueInformationAdapter adapter = new MosqueInformationAdapter(getSupportFragmentManager());

        adapter.AddFragment(new MosqueInformation(), "عام " , this);
        adapter.AddFragment(new MosqueImage(),  "الصور" ,this);
        adapter.AddFragment(new MosqueNote(),  "ملاحظات" , this);

        viewPager.setAdapter(adapter);

    }


}
