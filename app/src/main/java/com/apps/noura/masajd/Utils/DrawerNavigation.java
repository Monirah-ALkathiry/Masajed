package com.apps.noura.masajd.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.apps.noura.masajd.DawaActivity;
import com.apps.noura.masajd.FavoriteActivity;
import com.apps.noura.masajd.LoginActivity;
import com.apps.noura.masajd.MosqueActivity;
import com.apps.noura.masajd.R;

/**
 * Created by Monirah on 4/15/2018.
 */

public class DrawerNavigation {

    public static void enableDrawerNavigation(final Context context , NavigationView view){

            view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {



                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int id = item.getItemId();

                    // set item as selected to persist highlight
                    item.setChecked(true);
                    

                   // System.out.print("\n IDI ID :" + id +"\n");
                    switch(id)
                    {
                        case R.id.login:

                            Toast.makeText(context, "LogIn",Toast.LENGTH_SHORT).show();
                            Intent LoginIntent = new Intent(context,LoginActivity.class);
                            context.startActivity(LoginIntent);
                            break;


                        case R.id.info:
                            final String websiteurl= "http://www.moia.gov.sa/AboutMinistry/Pages/AboutMinistry.aspx";
                            Intent LinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteurl));
                            context.startActivity(LinkIntent);
                            Toast.makeText(context, "Link",Toast.LENGTH_SHORT).show();
                            break;


                        case R.id.contactUs:
                            Toast.makeText(context, "Mosque",Toast.LENGTH_SHORT).show();

                            break;


                        case R.id.ic_Dawa:
                            Toast.makeText(context, "Dawa",Toast.LENGTH_SHORT).show();
                            Intent dawaIntent = new Intent(context,DawaActivity.class);
                            context.startActivity(dawaIntent);

                            break;

                        case R.id.ic_favorit:
                            Toast.makeText(context, "Favorite",Toast.LENGTH_SHORT).show();
                            Intent FavoriteIntent = new Intent(context,FavoriteActivity.class);
                            context.startActivity(FavoriteIntent);
                            break;

                        case R.id.ic_masijed:
                            Toast.makeText(context, "Mosque",Toast.LENGTH_SHORT).show();
                            Intent Mosque = new Intent(context,MosqueActivity.class);
                            context.startActivity(Mosque);
                            break;

                        case R.id.aboutApp:
                            Toast.makeText(context, "About App",Toast.LENGTH_SHORT).show();

                            break;

                        default:
                            return true;
                    }

                    return false;
                }
            });

    }
}
