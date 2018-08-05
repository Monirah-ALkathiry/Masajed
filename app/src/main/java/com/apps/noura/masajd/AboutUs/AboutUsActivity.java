package com.apps.noura.masajd.AboutUs;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.apps.noura.masajd.R;
import com.apps.noura.masajd.Utils.BottomNavigationViewHelper;
import com.apps.noura.masajd.Utils.DrawerNavigation;
import com.apps.noura.masajd.Utils.SharedPreferencesConfig;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class AboutUsActivity extends AppCompatActivity {

    private static final String TAG = "AboutUsActivity";//Used in BottomNav
    private static final int ACTIVITY_NUM = 1;//Used in BottomNav

    //-------Nav  drawerLayout
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
//--------------------------------------


    //sharedpreferences
    private SharedPreferencesConfig sharedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        //Navigation:----------------
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        drawerLayout = (DrawerLayout)findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = (NavigationView)findViewById(R.id.navegation);

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



        //-------------------Bottom Nav:

        setupBottomNavigationView();
    }

    //Drawer
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    //Bottom Nav
    private void setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
       BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(false);
    }

    //Drawer Nav
    private void setupDrawerNavigation() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");

        DrawerNavigation.enableDrawerNavigation(this, navigationView);
        //Menu menu = navigationView.getMenu();
        // MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        // menuItem.setChecked(true);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if(sharedConfig.readLoginStatus())
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
        }
    }
}
