package com.apps.noura.masajd;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

public class FavoriteActivity extends AppCompatActivity {

    private FavoriteAdapter favoriteAdapter;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        favoriteAdapter = new FavoriteAdapter(getSupportFragmentManager());
        viewPager =(ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
        setViewPager(viewPager);
        viewPager.setCurrentItem(1);
    }


    public void  setViewPager(ViewPager viewPager){

        FavoriteAdapter adapter = new FavoriteAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FavoriteDawa(),"المناشط");
        adapter.AddFragment(new FavoriteMosque(),"المساجد");

        viewPager.setAdapter(adapter);
    }

}
