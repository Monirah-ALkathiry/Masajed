package com.apps.noura.masajd;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Monirah on 14/12/17.
 */

//Used In Fragment to View List Information As Card View
public class MosqueListAdapter extends RecyclerView.Adapter<MosqueListAdapter.MosqueViewList>{

    private List<MosquesLatLng> mosquesLatLngs;
    private Context context;
    String MosqueCode;



    //constructor
    public MosqueListAdapter(Context context,List<MosquesLatLng> latLngs) {

        this.context =context;
        this.mosquesLatLngs = latLngs;

    }

    @Override
    public MosqueViewList onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_mosque_item,parent,false);
        return new MosqueViewList(view);


    }




    @Override
    public void onBindViewHolder(MosqueViewList holder, int position) {
       // ((MosqueViewList) holder).FillList(position);
        holder.mTextView.setText(mosquesLatLngs.get(position).getMosqueName());
        holder.InfoTextView.setText(mosquesLatLngs.get(position).getCityVillage());
        holder.MosqueDistrict.setText(mosquesLatLngs.get(position).getDistrict());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"TTT",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,MosqueInformationActivity.class);
                context.startActivity(intent);
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


        //TextView mTextView;
        //TextView InfoTextView;
       // ImageView imageView;
       // CardView cardView;

        public MosqueViewList(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.MosqueName);
            InfoTextView = (TextView) view.findViewById(R.id.MosqueInfo);
            imageView = (ImageView) view.findViewById(R.id.MosqueImage);
            MosqueDistrict =(TextView) view.findViewById(R.id.District);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
            //cardView = (CardView) view.findViewById(R.id.cardView);

            view.setOnClickListener(this);
        }

        public void FillList(int position) {


                  }

        @Override
        public void onClick(View view) {

        }

        /*
          Mosque.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent Mosque = new Intent(MainActivity.this,MosqueActivity.class);
                    MainActivity.this.startActivity(Mosque);
                }
            });
         */
    }//end Inner Class
}
