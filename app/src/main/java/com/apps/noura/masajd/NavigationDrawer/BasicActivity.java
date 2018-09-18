package com.apps.noura.masajd.NavigationDrawer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.apps.noura.masajd.AboutApp.AboutAppActivity;
import com.apps.noura.masajd.ContactUs.ContactUsActivity;
import com.apps.noura.masajd.Dawa.DawaActivity;
import com.apps.noura.masajd.Favorite.FavoriteActivity;
import com.apps.noura.masajd.MainActivity;
import com.apps.noura.masajd.MaintenanceEmployeeServices.MaintenanceActivity;
import com.apps.noura.masajd.MosqueEmployeeServices.EmpServicesActivity;
import com.apps.noura.masajd.MosqueObserverServices.ObserverActivity;
import com.apps.noura.masajd.UserAccount.LogOut;
import com.apps.noura.masajd.UserAccount.LoginActivity;
import com.apps.noura.masajd.MoiaGovView.AboutMoiaGov;
import com.apps.noura.masajd.Mosque.MosqueActivity;
import com.apps.noura.masajd.PrayTime.PrayTime;
import com.apps.noura.masajd.R;

public class BasicActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

   protected DrawerLayout drawer;
    protected NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.basic, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        item.setChecked(true);
        drawer.closeDrawers();//Close Drawer After Select
        Context context = this;
        // System.out.print("\n IDI ID :" + id +"\n");
        switch(id)
        {


            case R.id.MainMenu:
                // Toast.makeText(context, "Mosque",Toast.LENGTH_SHORT).show();
                Intent MainMenu = new Intent(context,MainActivity.class);
                context.startActivity(MainMenu);
                break;

            case R.id.login:


                // Toast.makeText(context, "LogIn",Toast.LENGTH_SHORT).show();
                Intent LoginIntent = new Intent(context,LoginActivity.class);
                context.startActivity(LoginIntent);
                break;


            case R.id.ic_masijed:
                // Toast.makeText(context, "Mosque",Toast.LENGTH_SHORT).show();
                Intent Mosque = new Intent(context,MosqueActivity.class);
                context.startActivity(Mosque);
                break;

            case R.id.ic_Dawa:
                // Toast.makeText(context, "Dawa",Toast.LENGTH_SHORT).show();
                Intent dawaIntent = new Intent(context,DawaActivity.class);
                context.startActivity(dawaIntent);

                break;


            case R.id.ic_praytime:
                Intent intent4 = new Intent(context, PrayTime.class);//ACTIVITY_NUM = 1
                context.startActivity(intent4);
                break;

            case R.id.empServices:
                // Toast.makeText(context, "Mosque",Toast.LENGTH_SHORT).show();
                Intent empServices = new Intent(context,EmpServicesActivity.class);
                context.startActivity(empServices);
                break;

            case R.id.observer:
                // Toast.makeText(context, "Mosque",Toast.LENGTH_SHORT).show();
                Intent observer = new Intent(context,ObserverActivity.class);
                context.startActivity(observer);
                break;

            case R.id.maintenance:
                // Toast.makeText(context, "Mosque",Toast.LENGTH_SHORT).show();
                Intent maintenance = new Intent(context,MaintenanceActivity.class);
                context.startActivity(maintenance);
                break;

            case R.id.aboutApp:
                // Toast.makeText(context, "About App",Toast.LENGTH_SHORT).show();
                Intent AboutAppIntent = new Intent(context, AboutAppActivity.class);//ACTIVITY_NUM = 1
                context.startActivity(AboutAppIntent);
                break;

            case R.id.info:


                //  final String websiteurl= "http://www.moia.gov.sa/AboutMinistry/Pages/AboutMinistry.aspx";
                //Intent LinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteurl));
                //context.startActivity(LinkIntent);

                Intent websiteurl = new Intent(context,AboutMoiaGov.class);
                context.startActivity(websiteurl);

                break;

            case R.id.contactUs:
                Intent ContactUs = new Intent(context,ContactUsActivity.class);
                context.startActivity(ContactUs);
                //Toast.makeText(context, "Mosque",Toast.LENGTH_SHORT).show();

                break;
            case R.id.logOut:

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(context,R.style.MyDialogTheme);
                dlgAlert.setTitle("");
                dlgAlert.setMessage("قمت بتسجيل الخروج");
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                Intent LogoutIntent = new Intent(context,LogOut.class);
                context.startActivity(LogoutIntent);

                //Toast.makeText(context, "logout",Toast.LENGTH_SHORT).show();
                break;


            case R.id.ic_favorit:
                // Toast.makeText(context, "Favorite",Toast.LENGTH_SHORT).show();
                Intent FavoriteIntent = new Intent(context,FavoriteActivity.class);
                context.startActivity(FavoriteIntent);
                break;

            default:
                return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
