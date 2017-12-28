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
 * Created by Monirah on 14/12/17.
 */

//Used In Fragment to View List Information As Card View
public class MosqueListAdapter extends RecyclerView.Adapter<MosqueListAdapter.MosqueViewList>{

    private List<MosquesLatLng> mosquesLatLngs;
    private Context context;



    //constructor
    public MosqueListAdapter(Context context,List<MosquesLatLng> latLngs) {

        this.context =context;
        this.mosquesLatLngs = latLngs;


    }

    @Override
    public MosqueViewList onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mosque_item,parent,false);

        //Mosque Code :

                return new MosqueViewList(view);


    }




    @Override
    public void onBindViewHolder(MosqueViewList holder, final int position) {

        holder.MosqueCode=(mosquesLatLngs.get(position).getCode());


    // ((MosqueViewList) holder).FillList(position);
        holder.mTextView.setText(mosquesLatLngs.get(position).getMosqueName());
        holder.InfoTextView.setText(mosquesLatLngs.get(position).getCityVillage());
        holder.MosqueDistrict.setText(mosquesLatLngs.get(position).getDistrict());

        //Load Image From Inter-Net
        //TODO : Get Mosque Image
        Picasso.with(context)
                .load("http://gis.moia.gov.sa/Mosques/Content/images/mosques/"+holder.MosqueCode+"/IMG_"+holder.i+".JPG")
                 .placeholder(R.drawable.mosqueicon)
                .into(holder.imageView);

        //Test
        System.out.println("URL IS  :) :) :) :)" + "http://gis.moia.gov.sa/Mosques/Content/images/mosques/"+holder.MosqueCode+"/" );

        //Onclick : Open New Activity
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show();
                //Create Object From Activity:

                Intent intent = new Intent(context,MosqueInformationActivity.class);

                intent.putExtra("MOSQUE_CODE",mosquesLatLngs.get(position).getCode());
                //USED IN MAP
                intent.putExtra("MOSQUE_LAT",mosquesLatLngs.get(position).getLatitude());
                intent.putExtra("MOSQUE_LON",mosquesLatLngs.get(position).getLongitude());

                // Mosque Information:
                intent.putExtra("MOSQUE_NAME",mosquesLatLngs.get(position).getMosqueName());
                intent.putExtra("MOSQUE_TYPE",mosquesLatLngs.get(position).getMosqueCatogery());
                intent.putExtra("MOSQUE_REGION",mosquesLatLngs.get(position).getRegion());
                intent.putExtra("CITY_VILLAGE",mosquesLatLngs.get(position).getCityVillage());
                intent.putExtra("DISTRICT",mosquesLatLngs.get(position).getDistrict());
                intent.putExtra("STREET_NAME",mosquesLatLngs.get(position).getStreetName());
               intent.putExtra("IMAM_NAME",mosquesLatLngs.get(position).getImamName());
                intent.putExtra("KHATEEB_NAME", mosquesLatLngs.get(position).getKhateebName());
                intent.putExtra("MOATHEN_NAME", mosquesLatLngs.get(position).getMoathenName());
                intent.putExtra("OBSERVER_NAME", mosquesLatLngs.get(position).getObserverName());

                context.startActivity(intent);
                //Test
            System.out.println("CLICK :) :) :) :)");
            }
        });





    }


    @Override
    public int getItemCount() {

        return mosquesLatLngs.size();
    }




    public class MosqueViewList extends RecyclerView.ViewHolder implements View.OnClickListener {
        //Mosque List:

        private TextView mTextView;
        private TextView InfoTextView;
        private ImageView imageView;
        private TextView MosqueDistrict;
        private LinearLayout linearLayout;
        //private CardView cardView;


        private String MosqueCode;//Form API To IMAGE VIEW


        //Image :
        private String i="2066";
        private String url="http://gis.moia.gov.sa/Mosques/Content/images/mosques/1234";


        public MosqueViewList(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.MosqueName);
            InfoTextView = (TextView) view.findViewById(R.id.MosqueInfo);
            imageView = (ImageView) view.findViewById(R.id.MosqueImage);
            MosqueDistrict =(TextView) view.findViewById(R.id.District);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
            //cardView = (CardView) view.findViewById(R.id.cardView);
           //view.setOnClickListener(this);

        }

        public void FillList(int position) {


                  }

        @Override
        public void onClick(View view) {

        }


    }//end Inner Class
}
