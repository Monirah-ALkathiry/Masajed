package com.apps.noura.masajd;

import android.support.design.widget.TabLayout;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.Button;


public class AdvanceSearch extends AppCompatActivity {

    private static final String TAG= "Search dialog";

    private ViewPager mViewPager;
    private AdvanceSearchPageAdapter advanceSearchPageAdapter;

    private Button bSearch;
    private Button bExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_search);

        advanceSearchPageAdapter = new AdvanceSearchPageAdapter(getSupportFragmentManager());


         bSearch = (Button) findViewById(R.id.search);
         bExit= (Button) findViewById(R.id.exit);

        //set up the viewpager with Section adapter
        mViewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(mViewPager);

        //Tab
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);


        }

        private void setUpViewPager(ViewPager viewPager){

        AdvanceSearchPageAdapter adapter = new AdvanceSearchPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new ImamaSearch(),"البحث عن امام");
        adapter.addFragment(new KhateebSearch(),"البحث عن خطيب");
        adapter.addFragment(new MosqueSearch(),"البحث عن مسجد");

            viewPager.setAdapter(adapter);
        }

}
