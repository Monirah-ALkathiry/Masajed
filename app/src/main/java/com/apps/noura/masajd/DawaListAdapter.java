package com.apps.noura.masajd;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Noura Alsomaikhi on 1/1/2018.
 */

class DawaListAdapter extends RecyclerView.Adapter<DawaListAdapter.DawaViewList>{

    private List<DawaLatLng> DawaLatLng;
    private Context context;



    //constructor
    public DawaListAdapter(Context context,List<DawaLatLng> LatLng) {

        this.context =context;
        this.DawaLatLng = LatLng;


    }

    @Override
    public DawaViewList onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dawa_item,parent,false);

        //Mosque Code :

        return new DawaListAdapter.DawaViewList(view);


    }

    @Override
    public void onBindViewHolder(DawaListAdapter.DawaViewList holder, final int position) {


        holder.DawaID=(DawaLatLng.get(position).getId());

        holder.mTextView.setText(DawaLatLng.get(position).getDawaAddress());
        holder.InfoTextView.setText(DawaLatLng.get(position).getCityVillage());
        holder.DawaDistrict.setText(DawaLatLng.get(position).getDistrict());


        Picasso.with(context)
                .load("http://gis.moia.gov.sa/Mosques/Content/images/mosques/"+holder.DawaID+"/IMG_"+holder.i+".JPG")
                .placeholder(R.drawable.markericonsmall)
                .into(holder.imageView);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(context,DawaInformationActivity.class);

                intent.putExtra("DAWA_ID",DawaLatLng.get(position).getId());
                intent.putExtra("DAWA_Name",DawaLatLng.get(position).getDawaAddress());
                intent.putExtra("DAWA_LAT",DawaLatLng.get(position).getLocYCoord());
                intent.putExtra("DAWA_LON",DawaLatLng.get(position).getLocXCoord());

                // Dawa Activity Information:
                intent.putExtra("MOSQUE_NAME",DawaLatLng.get(position).getMosqueName());
                intent.putExtra("MOSQUE_REGION",DawaLatLng.get(position).getRegion());
                intent.putExtra("CITY_VILLAGE",DawaLatLng.get(position).getCityVillage());
                intent.putExtra("DISTRICT",DawaLatLng.get(position).getDistrict());
                intent.putExtra("STREET_NAME",DawaLatLng.get(position).getStreetName());
                intent.putExtra("First_NAME",DawaLatLng.get(position).getFirstName());
                intent.putExtra("Father_Name", DawaLatLng.get(position).getFatherName());
                intent.putExtra("Family_NAME", DawaLatLng.get(position).getFamilyName());
                intent.putExtra("Language", DawaLatLng.get(position).getDawaActivLanguage());
                intent.putExtra("Women_Availability", DawaLatLng.get(position).getWomenPlaceAvailability());

                context.startActivity(intent);
                //Test
               // System.out.println("CLICK :) :) :) :)");
            }
        });





    }


    @Override
    public int getItemCount() {

        return DawaLatLng.size();
    }




    public class DawaViewList extends RecyclerView.ViewHolder implements View.OnClickListener {


        private TextView mTextView;
        private TextView InfoTextView;
        private ImageView imageView;
        private TextView DawaDistrict;
        private LinearLayout linearLayout;
        private String DawaID;


        //Image :
        private String i="2066";
        private String url="http://gis.moia.gov.sa/Mosques/Content/images/mosques/1234";


        public DawaViewList(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.DawaName);
            InfoTextView = (TextView) view.findViewById(R.id.DawaInfo);
            imageView = (ImageView) view.findViewById(R.id.DawaImage);
            DawaDistrict =(TextView) view.findViewById(R.id.DawaDistrict);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);


        }

        public void FillList(int position) {


        }

        @Override
        public void onClick(View view) {

        }


    }//end Inner Class
}
