package com.apps.noura.masajd.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

import com.apps.noura.masajd.AboutApp.AboutAppActivity;
import com.apps.noura.masajd.ContactUs.ContactUsActivity;
import com.apps.noura.masajd.MoiaGovView.AboutMoiaGov;
import com.apps.noura.masajd.Mosque.MosqueActivity;
import com.apps.noura.masajd.PrayTime.PrayTime;
import com.apps.noura.masajd.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by Monirah on 4/15/2018.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx){
       // Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");

       // bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);

    }


    public static void enableNavigation(final Context context, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                       case R.id.ic_praytime:
                        Intent intent4 = new Intent(context, PrayTime.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent4);
                           break;

                    case R.id.ic_masijed:
                        Intent intent3 = new Intent(context, MosqueActivity.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        break;

                    case R.id.contactUs:
                        Intent ContactUs = new Intent(context,ContactUsActivity.class);
                        context.startActivity(ContactUs);
                        //Toast.makeText(context, "Mosque",Toast.LENGTH_SHORT).show();

                        break;

                    case R.id.info:
                       /*final String websiteurl= "http://www.moia.gov.sa/AboutMinistry/Pages/AboutMinistry.aspx";
                        Intent LinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteurl));
                        context.startActivity(LinkIntent);
                                */
                        Intent websiteurl = new Intent(context,AboutMoiaGov.class);
                        context.startActivity(websiteurl);

                        //Toast.makeText(context, "Link",Toast.LENGTH_SHORT).show();
                        /* Intent LinkIntent  = new Intent(context, AboutMoiaGov.class);//ACTIVITY_NUM = 3
                        context.startActivity(LinkIntent);
                            */
                        break;


                    case R.id.aboutApp:
                        // Toast.makeText(context, "About App",Toast.LENGTH_SHORT).show();
                        Intent AboutAppIntent = new Intent(context, AboutAppActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(AboutAppIntent);
                        break;
                   /* case R.id.ic_activities:
                        Intent intent2  = new Intent(context, DawaActivity.class);//ACTIVITY_NUM = 3
                        context.startActivity(intent2);
                        break;

                    case R.id.ic_favorit:
                        Intent intent1 = new Intent(context, FavoriteActivity.class);//ACTIVITY_NUM = 4
                        context.startActivity(intent1);
                        break;
                        */

                }

                ((Activity) context).overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                return false;
            }
        });
    }

}
