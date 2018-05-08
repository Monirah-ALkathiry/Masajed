package com.apps.noura.masajd;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
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

    private double lat;
    private double log;

    double latd;
    double logd;


    //constructor
    public DawaListAdapter(Context context,List<DawaLatLng> LatLng, double lat , double log) {

        this.context =context;
        this.DawaLatLng = LatLng;

        this.lat =lat;
        this.log=log;


    }

    @Override
    public DawaViewList onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_dawa_item,parent,false);

        //Mosque Code :

        return new DawaListAdapter.DawaViewList(view);


    }
    protected  String Dstance;

    @Override
    public void onBindViewHolder(DawaListAdapter.DawaViewList holder, final int position) {


        holder.DawaID=(DawaLatLng.get(position).getId());

        holder.mTextView.setText(DawaLatLng.get(position).getDawaAddress());
      //  holder.InfoTextView.setText(DawaLatLng.get(position).getCityVillage());
      //  holder.DawaDistrict.setText(DawaLatLng.get(position).getDistrict());


        Picasso.with(context)
                .load("http://gis.moia.gov.sa/Mosques/Content/images/mosques/"+holder.DawaID+"/IMG_"+holder.i+".JPG")
                .placeholder(R.drawable.ic_book)
                .into(holder.imageView);

        String FirstName, FatherName, GrandFatherName, FamilyName , Name;
        FirstName = DawaLatLng.get(position).getFirstName();
        FatherName =  DawaLatLng.get(position).getFatherName();
        GrandFatherName =  DawaLatLng.get(position).getGrandFatherName();
        FamilyName =  DawaLatLng.get(position).getFamilyName();
        Name = FirstName+" "+FatherName+" "+GrandFatherName+ " " +FamilyName;

     holder.Preacher.setText("الداعية :"+Name);



        //-----------------------------Calc Distance --------------------------------
//Location Distance :
        Location locationA = new Location("point A");
        Location locationB = new Location("point B");
        //Used To calc Distance:
        locationA.setLatitude(lat);
        locationA.setLongitude(log);

        String latAPI= DawaLatLng.get(position).getLocYCoord();
        String logAPI= DawaLatLng.get(position).getLocXCoord();


        //  System.out.println(" Distance is :) :) :0  ******* " + logAPI  + "\n d by meeter :" +latAPI + "\n In Kilo **********: " );

        latd=Double.parseDouble(latAPI);
        logd= Double.parseDouble(logAPI);

        locationB.setLatitude(latd);
        locationB.setLongitude(logd);
        float distance = locationA.distanceTo(locationB);
        double dm =distance * Math.PI / 180.0;
        double dk = dm / 10.0;

        //rad * 180.0 / Math.PI
        System.out.println(" Distance is :) :) :0  ******* " + distance  + "\n d by meeter :" +dm + "\n In Kilo **********: " +dk );


        //Convert To String:
        dk = Math.floor(dk * 100) / 100;
        Dstance= Double.toString(dk);
        holder.Distance.setText(Dstance + "كيلو");

//--------------------------------------------------------------------------------------------------


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(context,DawaInformationActivity.class);
                // Dawa Activity Information:


                intent.putExtra("DawaActivityType", DawaLatLng.get(position).getDawaActivityType());
                intent.putExtra("DawaMainCategory",DawaLatLng.get(position).getDawaMainCategory());
                intent.putExtra("DawaSubCategory",DawaLatLng.get(position).getDawaSubCategory());
                //intent.putExtra("Distance",Dstance);

                intent.putExtra("DAWA_ID",DawaLatLng.get(position).getId());
                intent.putExtra("DawaAddress",DawaLatLng.get(position).getDawaAddress());
                intent.putExtra("DawaOffice",DawaLatLng.get(position).getDawaOffice());
                intent.putExtra("DawaActivityDateH",DawaLatLng.get(position).getDawaActivityDateH());
                intent.putExtra("DawaActivityTime",DawaLatLng.get(position).getDawaActivityTime());
                intent.putExtra("DawaActivityRepDays",DawaLatLng.get(position).getDawaActivityRepDays());
                intent.putExtra("LocX_Coord",DawaLatLng.get(position).getLocYCoord());
                intent.putExtra("LocY_Coord",DawaLatLng.get(position).getLocXCoord());
                //Send Current Location :
                //Used To calc Distance:
               // locationA.setLatitude(lat);
                //locationA.setLongitude(log);
                String Userlat = String.valueOf(lat);
                String Userlon = String.valueOf(log);

                intent.putExtra("LAT",Userlat);
                intent.putExtra("LON",Userlon);

                intent.putExtra("MOSQUE_NAME",DawaLatLng.get(position).getMosqueName());
                intent.putExtra("MOSQUE_REGION",DawaLatLng.get(position).getRegion());
                intent.putExtra("CITY_VILLAGE",DawaLatLng.get(position).getCityVillage());
                intent.putExtra("DISTRICT",DawaLatLng.get(position).getDistrict());
                intent.putExtra("STREET_NAME",DawaLatLng.get(position).getStreetName());
                //
                intent.putExtra("FirstName",DawaLatLng.get(position).getFirstName());
                intent.putExtra("FatherName", DawaLatLng.get(position).getFatherName());
                intent.putExtra("GrandFatherName", DawaLatLng.get(position).getGrandFatherName());
                intent.putExtra("FamilyName", DawaLatLng.get(position).getFamilyName());

                intent.putExtra("DawaActivLanguage", DawaLatLng.get(position).getDawaActivLanguage());
                intent.putExtra("WomenPlaceAvailability", DawaLatLng.get(position).getWomenPlaceAvailability());

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
     //   private TextView InfoTextView;
        private ImageView imageView;
        //private TextView DawaDistrict;
        private LinearLayout linearLayout;
        private String DawaID;
        private TextView Distance;
        private TextView Preacher;


        //Image :
        private String i="2066";
        private String url="http://gis.moia.gov.sa/Mosques/Content/images/mosques/1234";


        public DawaViewList(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.DawaName);
           // InfoTextView = (TextView) view.findViewById(R.id.DawaInfo);
            imageView = (ImageView) view.findViewById(R.id.DawaImage);
           // DawaDistrict =(TextView) view.findViewById(R.id.DawaDistrict);
            linearLayout = (LinearLayout) view.findViewById(R.id.linearLayout);
            Distance = (TextView) view.findViewById(R.id.Distance);
            Preacher = (TextView) view.findViewById(R.id.PreacherName);

        }

        public void FillList(int position) {


        }

        @Override
        public void onClick(View view) {

        }


    }//end Inner Class
}
