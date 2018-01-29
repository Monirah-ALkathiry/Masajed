package com.apps.noura.masajd;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MosqueActivity extends AppCompatActivity  {


    private static final String TAG = "MosqueActivity";
    private ViewPager mviewPager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mosque);

        //Log to start
        Log.d(TAG,"Start");


        //Set Up the view Pager with Section Adapter:
        mviewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mviewPager);

        //TabLayout

        TabLayout tabLayout =(TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mviewPager);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_mosque_information,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView(); // Menu Item
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final Context context= this;

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//on-click submit
                Toast.makeText(context,query,Toast.LENGTH_LONG).show();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//on change
                return false;
            }
        });

        //super.onCreateOptionsMenu(menu)  default return
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();

        //if(id == R.id.collapseActionView)

        return super.onOptionsItemSelected(item);
    }




    //Create Function : Section Page Adapter , then Add Fragment To it

    private void setupViewPager(ViewPager viewPager){

        MosqueSectoinsAdapter adapter = new MosqueSectoinsAdapter(getSupportFragmentManager());

        adapter.AddFragment(new MosqueMap(), "خريطه");
        adapter.AddFragment(new MosqueList(), "قائمة" );

        viewPager.setAdapter(adapter);

    }//end Function ViewPager





}//end Class
