package com.apps.noura.masajd;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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



    private DawaClient DawaClient;
    //To get dawa activity Information
    private List<DawaLatLng> dawaLatLngs;

    private View view;

    private  double lat;
    private  double log;

    String latitude;
    String longitude;

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

        View view;
        view = inflater.inflate(R.layout.fragment_dawa_list, container, false);

        latitude= Double.toString(lat);
        longitude= Double.toString(log);

        System.out.print(lat +" Lat from dawa List " + log + "\n");

        System.out.print(latitude +" Lat from dawa List  " + longitude + "\n");


        //Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.DawaRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //-------------------------------------------------------

        //Connect to API :
        //DawaClient = ApiRetrofitClint.getApiRetrofitClint().create(DawaClient.class);
        //Make A Connection With API :
        DawaClient= ApiRetrofitClint.getApiRetrofitClint().create(DawaClient.class);
        Call<List<DawaLatLng>> call = DawaClient.getDawaLatLng(latitude,longitude, "25");

        call.enqueue(new Callback<List<DawaLatLng>>(){

            @Override
            public void onResponse(Call<List<DawaLatLng>> call, Response<List<DawaLatLng>> response) {

                dawaLatLngs = response.body();

                //Send Data To Fragment List---
                adapter = new DawaListAdapter(getContext(),dawaLatLngs , lat , log);

                recyclerView.setAdapter(adapter);



                if (response.isSuccessful())
                {
                    System.out.print("Success ");
                }
                else
                {
                    System.out.print(" Not Success ");
                }

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



        return  view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }





}

