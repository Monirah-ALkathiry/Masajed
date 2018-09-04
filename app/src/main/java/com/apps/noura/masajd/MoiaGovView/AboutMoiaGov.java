package com.apps.noura.masajd.MoiaGovView;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.apps.noura.masajd.MosqueList;
import com.apps.noura.masajd.MosqueMap;
import com.apps.noura.masajd.MosqueSectoinsAdapter;
import com.apps.noura.masajd.R;
import com.apps.noura.masajd.Utils.DrawerNavigation;
import com.apps.noura.masajd.Utils.SharedPreferencesConfig;

public class AboutMoiaGov extends AppCompatActivity {



    //-------Nav  drawerLayout
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    protected NavigationView navigationView;

    //sharedpreferences
    private SharedPreferencesConfig sharedConfig;

    private AboutMoiaGovSectionAdapter SectoinsPageAdapter;
    private ViewPager mViewPager;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_moia_gov);

        /* myWebView = (WebView) findViewById(R.id.webview);
        myWebView = new WebView(this);
        myWebView.getSettings().setLoadsImagesAutomatically(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        myWebView.loadUrl("http://www.moia.gov.sa/AboutMinistry/Pages/AboutMinistry.aspx");
        */


        //Navigation:----------------
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        drawerLayout = findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        navigationView = findViewById(R.id.navegation);

        actionBarDrawerToggle.syncState();



        //Check If User Login
        sharedConfig = new SharedPreferencesConfig(getApplicationContext());
        if(sharedConfig.readLoginStatus())
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
            //finish();


        }
        else {

            navigationView.getMenu().findItem(R.id.logOut).setVisible(false);

        }

        setupDrawerNavigation();


       /* Configuration config = getResources().getConfiguration();

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MoiaGovWebView pm_fragment = new MoiaGovWebView();
        fragmentTransaction.replace(android.R.id.content, pm_fragment);
        fragmentTransaction.commit();
        */

        //myWebView.loadUrl("http://beta.html5test.com/");




        SectoinsPageAdapter = new AboutMoiaGovSectionAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);

        //TabLayout
       // TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
       // tabLayout.setupWithViewPager(mViewPager);
        setupViewPager(mViewPager);

    }


    private void setupDrawerNavigation() {
        DrawerNavigation.enableDrawerNavigation(this, navigationView);

        }




    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Update Drawer Nav
        if(sharedConfig.readLoginStatus())
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sharedConfig.readLoginStatus())
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }


//Set View  Page
    private void setupViewPager(ViewPager viewPager) {

        AboutMoiaGovSectionAdapter adapter = new AboutMoiaGovSectionAdapter(getSupportFragmentManager());

        adapter.AddFragment(new MoiaGovWebView(),"Ti");

        viewPager.setAdapter(adapter);

    }//end Function ViewPager


}
