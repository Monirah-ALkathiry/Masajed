package com.apps.noura.masajd;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.noura.masajd.AboutApp.AboutAppActivity;
import com.apps.noura.masajd.ContactUs.ContactUsActivity;
import com.apps.noura.masajd.Dawa.DawaActivity;
import com.apps.noura.masajd.MaintenanceEmployeeServices.MaintenanceActivity;
import com.apps.noura.masajd.MoiaGovView.AboutMoiaGov;
import com.apps.noura.masajd.Mosque.MosqueActivity;
import com.apps.noura.masajd.MosqueEmployeeServices.EmpServicesActivity;
import com.apps.noura.masajd.MosqueObserverServices.ObserverActivity;
import com.apps.noura.masajd.PrayTime.PrayTime;

/**
 * Created by Monirah on 9/17/2018.
 */

public class CustomAdapter extends BaseAdapter {

    String [] result;
    Context context;
    int [] imageId;

    public CustomAdapter(MainActivity mainActivity, String[] osNameList, int[] osImages) {
        // TODO Auto-generated constructor stub
        result=osNameList;
        context=mainActivity;
        imageId=osImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    private static LayoutInflater inflater=null;

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }



    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }



    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
        }

    public class Holder
    {
        TextView os_text;
        ImageView os_img;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.sample_gridlayout, null);
        holder.os_text =(TextView) rowView.findViewById(R.id.card_view_image_title);
        holder.os_img =(ImageView) rowView.findViewById(R.id.card_view_image);

        holder.os_text.setText(result[position]);
        holder.os_img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int id =position;
                // TODO Auto-generated method stub
               // Toast.makeText(context, "You Clicked "+result[position]
                       // +"\n"+id
                       // +"\n     the : "+position+"   ", Toast.LENGTH_SHORT).show();

                switch(id)
                {
                    case 0:
                        Intent Mosque = new Intent(context,MosqueActivity.class);
                        context.startActivity(Mosque);
                        break;

                    case 1:
                        Intent dawaIntent = new Intent(context,DawaActivity.class);
                        context.startActivity(dawaIntent);
                        break;
                    case 2:
                        Intent intent4 = new Intent(context, PrayTime.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent4);
                        break;
                    case 3:
                        Intent empServices = new Intent(context,EmpServicesActivity.class);
                        context.startActivity(empServices);
                        break;
                    case 4:
                        Intent observer = new Intent(context,ObserverActivity.class);
                        context.startActivity(observer);
                        break;
                    case 5:
                        Intent maintenance = new Intent(context,MaintenanceActivity.class);
                        context.startActivity(maintenance);
                        break;
                    case 6:
                        Intent AboutAppIntent = new Intent(context, AboutAppActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(AboutAppIntent);
                        break;
                    case 7:
                        Intent websiteurl = new Intent(context,AboutMoiaGov.class);
                        context.startActivity(websiteurl);

                        break;
                    case 8:
                        Intent ContactUs = new Intent(context,ContactUsActivity.class);
                        context.startActivity(ContactUs);
                        break;


                }
            }
        });

        return rowView;
    }
}
