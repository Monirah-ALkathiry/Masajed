package com.apps.noura.masajd.Dawa;

import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.apps.noura.masajd.APIConnection.ApiRetrofitClint;
import com.apps.noura.masajd.APIConnection.DawaRating;
import com.apps.noura.masajd.R;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Noura Alsomaikhi on 1/10/2018.
 */

class DawaInformationRecyclerView extends RecyclerView.Adapter<DawaInformationRecyclerView.DawaViewInformationList> {

    Intent intent;

    public DawaInformationRecyclerView(Intent intent) {
        this.intent = intent;
    }

    @Override
    public DawaInformationRecyclerView.DawaViewInformationList onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dawa_information_list, parent, false);

        return new DawaInformationRecyclerView.DawaViewInformationList(view);
    }

    protected  String DawaActivity_Type;
    protected  String DawaMain_Category;
    protected String DawaSub_Category;


    protected String LocX_Coord;
    protected String LocY_Coord;

    //---Connect to API Yo get Rating:
    protected DawaRating dawaRating;
    private int ForID;// Dawa ID After Convert
    private String DawaID;
    protected  float Average_Rating;

    protected String Average_R;
    protected RatingBar ratingBar2;


    @Override
    public void onBindViewHolder(DawaInformationRecyclerView.DawaViewInformationList holder, int position) {
// Dawa Activity information :


        //1:DawaActivityType
        DawaActivity_Type =intent.getStringExtra("DawaActivityType");
        holder.DawaActivityType.setText(DawaActivity_Type);

        //System.out.println("\n DawaActivity_Type Place DawaActivity_Type : " + DawaActivity_Type);

        //2: Dawa Main Category

        DawaMain_Category = intent.getStringExtra("DawaMainCategory");
        holder.DawaMainCategory.setText(DawaMain_Category);
        //3: DawaSubCategory
        DawaSub_Category = intent.getStringExtra("DawaSubCategory");
        holder.DawaSubCategory.setText(DawaSub_Category);



        // #4 Office
        String DawaOffice;
        DawaOffice = intent.getStringExtra("DawaOffice");
        holder.DawaOffice.setText(intent.getStringExtra("DawaOffice"));

        //#5 Date DawaActivityDateH
        String DawaReqDateH;
        holder.DawaActivityDateH.setText(intent.getStringExtra("DawaActivityDateH"));

        //#6 Time  DawaActivityTime
        holder.DawaActivityTime.setText(intent.getStringExtra("DawaActivityTime"));

        //#7 Days  DawaActivityRepDays
        holder.DawaActivityRepDays.setText(intent.getStringExtra("DawaActivityRepDays"));

        //#8 Daiah name FirstName + FatherName + GrandFatherName + FamilyName
        String FirstName, FatherName, GrandFatherName, FamilyName , Name;
        FirstName = intent.getStringExtra("FirstName");
        FatherName = intent.getStringExtra("FatherName");
        GrandFatherName = intent.getStringExtra("GrandFatherName");
        FamilyName = intent.getStringExtra("FamilyName");
        Name = FirstName+" "+FatherName+" "+GrandFatherName+ " " +FamilyName;
        holder.Name.setText(Name);

        //#9 language DawaActivLanguage
        holder.DawaActivLanguage.setText(intent.getStringExtra("DawaActivLanguage"));

        //#10 WomenPlaceAvailability
        String WomenPlaceAvailability;
        WomenPlaceAvailability = intent.getStringExtra("WomenPlaceAvailability");
        System.out.println("\n Women Place Availability : " + WomenPlaceAvailability);
        int WA;
        if(WomenPlaceAvailability != null) {
             WA = Integer.parseInt(WomenPlaceAvailability);
             if ( WA == 0 ) {
            holder.WomenPlaceAvailability.setText("غير متوفر");
        } else {
                holder.WomenPlaceAvailability.setText("متوفر");
            }
        }
        else {
            holder.WomenPlaceAvailability.setText("غير متوفر");

        }
        //11 : Distance :
       // DistanceFromUser = intent.getStringExtra("Distance");

        LocX_Coord = intent.getStringExtra("LocX_Coord");
        LocY_Coord = intent.getStringExtra("LocY_Coord");

        String Userlat = intent.getStringExtra("LAT");
        String Userlon =  intent.getStringExtra("LON");

        Location locationA = new Location("point A");
        Location locationB = new Location("point B");
//Check If Not Null
        if(Userlat != null && Userlon != null) {
            double lat = Double.parseDouble(Userlat);
            double log = Double.parseDouble(Userlon);
            //Used To calc Distance:
            locationA.setLatitude(lat);
            locationA.setLongitude(log);

            double latd = Double.parseDouble(LocY_Coord);
            double logd = Double.parseDouble(LocX_Coord);
            locationB.setLatitude(logd);
            locationB.setLongitude(latd);


            float distance = locationA.distanceTo(locationB);
            double dm = distance * Math.PI / 180.0;
            double dk = dm / 10.0;

            //Convert To String:
            dk = Math.floor(dk * 100) / 100;
            String Dstance = Double.toString(dk);

            holder.Distance.setText(Dstance);
        }else{

            holder.Distance.setText(" ");

        }
      //  holder.ratingBar.setNumStars(2);

        //Dawa ID :

        DawaID = intent.getStringExtra("DAWA_ID");
        ForID = Integer.parseInt(DawaID);
        //System.out.print( "\n\n\n responce DAWA_ID"+ DawaOffice);

        ratingBar2 = holder.ratingBar;
        //Connect to API :

        //TODO : Check If User Loged in To Get User ID
        dawaRating = ApiRetrofitClint.getApiRetrofitClint().create(DawaRating.class);
        Call<JsonObject> call = dawaRating.getRating(26,ForID,3);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                String Rurl= call.request().url().toString();
                Log.i("test: ", Rurl);

            //Test Result
               // System.out.print( "\n\n\n responce \n\n\n"+response.body().toString());
               // System.out.print(response.toString());
               // System.out.print(response.headers());
              //  System.out.print(response.isSuccessful());

              // Log.e("  URL KK : ", call.request().url().toString());

                try {
                    System.out.println(response.body().toString());

                    //JSONObject InfoResponse = new JSONObject(String.valueOf(response.body()));

                    String jsonData = response.body().toString();
                    JSONObject Jobject= new JSONObject(jsonData);
                    Average_R = Jobject.getJSONObject("Result").getString("Average_Rating");
                    Average_Rating = Float.parseFloat(Average_R);

                    //System.out.print("\n Rating : " + Average_Rating + "\n");

                    ratingBar2.setRating(Average_Rating);
                    //JSONObject Jobject = new JSONObject(response.body().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.print("\nThere is No Data\n");



            }
        });




        holder.ratingBar.setRating(Average_Rating);
    }






    @Override
    public int getItemCount() {
        return 1;

    }

    public class DawaViewInformationList extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView DawaOffice;
        private TextView DawaActivityDateH;
        private TextView DawaActivityTime;
        private TextView DawaActivityRepDays;
        private TextView Name;
        private TextView DawaActivLanguage;
        private TextView WomenPlaceAvailability;

        private TextView DawaActivityType;
        private TextView DawaMainCategory;
        private TextView DawaSubCategory;
        private TextView Distance;
        private RatingBar ratingBar;

        private LinearLayout linearLayout;


        public DawaViewInformationList(View itemView) {
            super(itemView);

            DawaOffice = (TextView) itemView.findViewById(R.id.DawaOffice);
            DawaActivityDateH = (TextView) itemView.findViewById(R.id.DawaActivityDateH);
            DawaActivityTime = (TextView) itemView.findViewById(R.id.DawaActivityTime);
            DawaActivityRepDays = (TextView) itemView.findViewById(R.id.DawaActivityRepDays);
            Name = (TextView) itemView.findViewById(R.id.Name);
            DawaActivLanguage = (TextView) itemView.findViewById(R.id.DawaActivLanguage);
            WomenPlaceAvailability = (TextView) itemView.findViewById(R.id.WomenPlaceAvailability);

            DawaActivityType = (TextView) itemView.findViewById(R.id.ActivityType);
            DawaMainCategory = (TextView) itemView.findViewById(R.id.MainClassification);
            DawaSubCategory = (TextView) itemView.findViewById(R.id.SubClassification);
            Distance = (TextView) itemView.findViewById(R.id.Distance);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);

            //linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayoutInfo);


        }

        @Override
        public void onClick(View view) {

        }
    }
}