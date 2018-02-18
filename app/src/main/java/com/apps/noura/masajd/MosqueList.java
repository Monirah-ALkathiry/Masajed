package com.apps.noura.masajd;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.v7.widget.RecyclerView.*;

public class MosqueList extends Fragment{
    // the fragment initialization

    private static final String TAG = "MosqueLIST";

    //-----------------------------------------------------------------

    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private LayoutManager layoutManager;
    private MosqueListAdapter adapter;

    //Retrofit InterFace:
    private MosquesLatLngClint mosquesLatLngClint;
    //To get Mosque Information
    private List<MosquesLatLng> mosquesLatLngs;

    private View view;


    private  double lat;
    private  double log;

    String latitude;
    String longitude;



    @SuppressLint("ValidFragment")
    public  MosqueList(double lat, double log){

        this.lat = lat;
        this.log = log;
    }

    public MosqueList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable  ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        view = inflater.inflate(R.layout.fragment_mosque_list, container, false);

        latitude= Double.toString(lat);
        longitude= Double.toString(log);

        System.out.print(lat +" Lat kkkk " + log + "\n");

        System.out.print(latitude +" Lat STRING  " + longitude + "\n");


//Recycler View


        recyclerView = (RecyclerView) view.findViewById(R.id.MosqueRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


        ConnectWithAPI(latitude,longitude);





        return  view;
    }


    double New_lat;
    double New_log;
    String lat2;
    String long2;
    public void ConnectWithAPI(String latitude , String longitude){

         lat2 = latitude;
        long2 = longitude;
        //----------------------------------
        System.out.println("----:" + lat2 + ")P)----------------" + long2);



        //Make A Connection With API :
        mosquesLatLngClint = ApiRetrofitClint.getApiRetrofitClint().create(MosquesLatLngClint.class);
        //Call Function form Inter Face And Send Parameter to it
//24.710684, 46.630387
        Call<List<MosquesLatLng>> call = mosquesLatLngClint.getMosqueLatLng(lat2,long2,25);
        //  Create Response:
        call.enqueue(new Callback<List<MosquesLatLng>>() {
            @Override
            public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {

                mosquesLatLngs = response.body();

                New_lat= Double.parseDouble(lat2);
                New_log=Double.parseDouble(long2);

                System.out.print("lat : "+ New_lat);
                System.out.print("long: "+ New_log + " \n CONTEXT IS : " +getContext() +"\n");

                //Send Data To Fragment List---
                if(getContext()  != null) {
                    adapter = new MosqueListAdapter(getContext(), mosquesLatLngs, New_lat, New_log);

                    recyclerView.setAdapter(adapter);
                }else{
                    System.out.println("Context null\n" );
                }
                //-------

                //Test Result and Print Data
                System.out.println("Responce toString: " + response.toString());
                System.out.println("Responce body : "+ response.body());
                System.out.println("Responce headers : "+ response.headers());
                System.out.println("Responce errorBody : "+ response.errorBody());
                System.out.print("URL : " + response.isSuccessful());
                //Storing the data in our list

                System.out.println("Size Is on Responce :----" +mosquesLatLngs.size());
                //-----------------------------------------------------------------------

            }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.println("Error bad  ):-----------------------\n");
            }
        });


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }//end On create


//--------- TODO : Search ----------------



    public void searchQuery(String lat , String lon ) {
       // String strtext = getArguments().getString("MOSQUE_LAT");
       // System.out.print("LAt From BUNDEL : " + strtext);
        Log.e(" After Search lat : ", lat);
        Log.e(" After Search long : ", lon);

       //ConnectWithAPI(lat,lon);

    }


}






