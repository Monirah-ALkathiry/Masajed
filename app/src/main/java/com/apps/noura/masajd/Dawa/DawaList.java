package com.apps.noura.masajd.Dawa;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.apps.noura.masajd.APIConnection.ApiRetrofitClint;
import com.apps.noura.masajd.APIConnection.DawaClient;
import com.apps.noura.masajd.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Noura Alsomaikhi on 1/1/2018.
 */

public class DawaList extends Fragment {


    private static final String TAG = "DawaLIST";
    //------------------------------------------------------
    //------------------------------------------------------

    //-----------------------------------------------------------------

    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DawaListAdapter adapter;



    private com.apps.noura.masajd.APIConnection.DawaClient DawaClient;
    //To get dawa activity Information
    private List<DawaLatLng> dawaLatLngs;

    private View view;

    private  double lat;
    private  double log;

    protected String latitude;
    protected String longitude;


    //Connect With API :
    private double New_lat;
    private double New_log;
    private   String lat2;
    private String long2;


    @SuppressLint("ValidFragment")
    public  DawaList(double lat, double log){

        this.lat = lat;
        this.log = log;
    }


    public DawaList() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_dawa_list, container, false);

        latitude = Double.toString(lat);
        longitude = Double.toString(log);

        System.out.print(lat + " Lat from dawa List " + log + "\n");

        System.out.print(latitude + " Lat from dawa List  " + longitude + "\n");


        //Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.DawaRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //-------------------------------------------------------
        ConnectWithAPI(latitude,longitude);
        return  view;
    }


    public void ConnectWithAPI(String latitude , String longitude){


        lat2 = latitude;
        long2 = longitude;

        //Connect to API :
        //DawaClient = ApiRetrofitClint.getApiRetrofitClint().create(DawaClient.class);
        //Make A Connection With API :
        DawaClient= ApiRetrofitClint.getApiRetrofitClint().create(DawaClient.class);
        Call<List<DawaLatLng>> call = DawaClient.getDawaLatLng(lat2,long2, 25);

        call.enqueue(new Callback<List<DawaLatLng>>(){

            @Override
            public void onResponse(Call<List<DawaLatLng>> call, Response<List<DawaLatLng>> response) {

                dawaLatLngs = response.body();

                New_lat= Double.parseDouble(lat2);
                New_log=Double.parseDouble(long2);

                System.out.print("lat : "+ New_lat);
                System.out.print("long: "+ New_log + " \n CONTEXT IS : " +getContext() +"\n");

                if (getContext() != null) {
                    //Send Data To Fragment List---
                    adapter = new DawaListAdapter(getContext(), dawaLatLngs, lat, log);

                    recyclerView.setAdapter(adapter);
                } else {


                    Toast.makeText(getContext(), "لا توجد بيانات", Toast.LENGTH_SHORT).show();
                }
             /* if (response.isSuccessful())
                {
                    System.out.print("Success ");
                }
                else
                {
                    System.out.print(" Not Success ");
                }*/



                //Test Result and Print Data
                System.out.println("Responce Value "+response.body().toString());
                // System.out.println("Responce toString "+ response.body().String());
                System.out.println("Responce body toString ---- "+ response.body());
                //System.out.println("Responce headers"+ response.headers());
                // System.out.println("Responce errorBody"+ response.errorBody());
                // System.out.print("Success ? --- " + response.isSuccessful());
                //Storing the data in our list

                // System.out.println("Size Is onResponce :----" +dawaLatLngs.size());
                //-----------------------------------------------------------------------

            }

            @Override
            public void onFailure(Call<List<DawaLatLng>> call, Throwable t) {
                System.out.println("Failure");
                // Log.d("Ttt","Teeeeeee");
            }

        });




    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

}

