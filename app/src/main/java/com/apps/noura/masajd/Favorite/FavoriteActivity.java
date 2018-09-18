package com.apps.noura.masajd.Favorite;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.apps.noura.masajd.NavigationDrawer.BasicActivity;
import com.apps.noura.masajd.R;
import com.apps.noura.masajd.Utils.BottomNavigationViewHelper;
import com.apps.noura.masajd.Utils.DrawerNavigation;
import com.apps.noura.masajd.Utils.SharedPreferencesConfig;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class FavoriteActivity extends BasicActivity {

    private static final String TAG = "FavoriteActivity";//Used in BottomNav
    private static final int ACTIVITY_NUM = 3;//Used in BottomNav

    private FavoriteAdapter favoriteAdapter;
    private ViewPager viewPager;


    //sharedpreferences
    private SharedPreferencesConfig sharedConfig;

    //-------Nav  drawerLayout
    //private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
   // private NavigationView navigationView;
//--------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // setContentView(R.layout.activity_favorite);

        //------------------------------------------------
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_favorite, null, false);
        drawer.addView(contentView, 0);
        //check in Menu selected :
        navigationView.setCheckedItem(R.id.ic_praytime);

        //----

        favoriteAdapter = new FavoriteAdapter(getSupportFragmentManager());
        viewPager =(ViewPager) findViewById(R.id.container);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);


        tabLayout.setupWithViewPager(viewPager);
        setViewPager(viewPager);
        viewPager.setCurrentItem(0);


        //Navigation:----------------
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      /*
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        drawerLayout = (DrawerLayout)findViewById(R.id.DrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = (NavigationView)findViewById(R.id.navegation);

            */
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

      //  setupDrawerNavigation();

        //-------------------Bottom Nav:

        setupBottomNavigationView();
        //---------------------------
        /*
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                System.out.print("\n IDI ID :" + id +"\n");
                switch(id)
                {
                    case R.id.login:

                        Toast.makeText(FavoriteActivity.this, "LogIn",Toast.LENGTH_SHORT).show();
                        Intent LoginIntent = new Intent(FavoriteActivity.this,LoginActivity.class);
                        FavoriteActivity.this.startActivity(LoginIntent);
                        break;


                    case R.id.info:
                        final String websiteurl= "http://www.moia.gov.sa/AboutMinistry/Pages/AboutMinistry.aspx";
                        Intent LinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteurl));
                        startActivity(LinkIntent);
                        Toast.makeText(FavoriteActivity.this, "Link",Toast.LENGTH_SHORT).show();
                        break;


                    case R.id.contactUs:
                        Toast.makeText(FavoriteActivity.this, "Mosque",Toast.LENGTH_SHORT).show();

                        break;


                    case R.id.ic_Dawa:
                        Toast.makeText(FavoriteActivity.this, "Dawa",Toast.LENGTH_SHORT).show();
                        Intent dawaIntent = new Intent(FavoriteActivity.this,DawaActivity.class);
                        FavoriteActivity.this.startActivity(dawaIntent);

                        break;

                    case R.id.ic_favorit:
                        Toast.makeText(FavoriteActivity.this, "Favorite",Toast.LENGTH_SHORT).show();
                        Intent FavoriteIntent = new Intent(FavoriteActivity.this,FavoriteActivity.class);
                        FavoriteActivity.this.startActivity(FavoriteIntent);
                        break;

                    case R.id.ic_masijed:
                        Toast.makeText(FavoriteActivity.this, "Mosque",Toast.LENGTH_SHORT).show();
                        Intent Mosque = new Intent(FavoriteActivity.this,MosqueActivity.class);
                        FavoriteActivity.this.startActivity(Mosque);
                        break;

                    case R.id.aboutApp:
                        Toast.makeText(FavoriteActivity.this, "About App",Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        return true;
                }


                return false;
            }
        });
        */
//----------------------------------------------------------------

    }


    public void  setViewPager(ViewPager viewPager){

        FavoriteAdapter adapter = new FavoriteAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FavoriteMosque(),"المساجد");

        adapter.AddFragment(new FavoriteDawa(),"المناشط");

        viewPager.setAdapter(adapter);
    }


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
        menuItem.setChecked(true);
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
