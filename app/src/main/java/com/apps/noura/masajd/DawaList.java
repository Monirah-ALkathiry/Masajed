package com.apps.noura.masajd;

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
    // Retrofit
    //create Retrofit instance
//http://mosquesapi.azurewebsites.net/mosques/DawaActivity.jsp?lat=24.0006&lon=46.000&limit=5
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://mosquesapi.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit = builder.build();

    DawaClient dawaClient =retrofit.create(DawaClient.class);
   // DawaClient2 dawaClient2 =retrofit.create(DawaClient2.class);

    //-----------------------------------------------------------------

    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private DawaListAdapter adapter;


    private DawaClient DawaClient;
    //To get dawa activity Information
   private List<DawaLatLng> dawaLatLngs;


    public DawaList() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.fragment_dawa_list, container, false);

        //Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.DawaRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //-------------------------------------------------------

/*
        // try jason object
        Call<JsonObject> call2 = dawaClient2.getDawaInfo("24.0006","46.000");

        call2.enqueue(new Callback<JsonObject>(){
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("111111111111111111111");

                System.out.println(response.body().toString());
                Log.d("Ttt","SSSSSSSSSSSSSSSSSSSSWWWWWWWWWWWWWWW");
                try {

                    System.out.println("555555555555555555555555");
                    Log.d("Ttt","Teeeeeee");

                    JSONObject InfoResponse = new JSONObject(String.valueOf(response.body()));

                    String Dawaname = InfoResponse.getString("DawaAddress");
                    System.out.println("Dawa Activity Name " + Dawaname);
                    //  Toast.makeText(DawaActivity.this, "Welcome" + Dawaname, Toast.LENGTH_SHORT).show();
                    //2
                    String jsonData = response.body().toString();
                    JSONObject Jobject = new JSONObject(jsonData);
                    String Dawaname2 = "Name:" + Jobject.get("Id");
                    System.out.println(Dawaname2);

                    String DawaAdress = Jobject.getString("DawaAddress");
                    System.out.println(DawaAdress);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("Failure");
                Log.d("Ttt","FFFFFFFFFFFFFFFF");
            }


        });

*/

        //Connect to API :
        //DawaClient = ApiRetrofitClint.getApiRetrofitClint().create(DawaClient.class);

        Call<List<DawaLatLng>> call = dawaClient.getDawaLatLng("24.000.6","46.000");

        call.enqueue(new Callback<List<DawaLatLng>>(){

            @Override
            public void onResponse(Call<List<DawaLatLng>> call, Response<List<DawaLatLng>> response) {

                dawaLatLngs = response.body();

                //Send Data To Fragment List---
               adapter = new DawaListAdapter(getContext(),dawaLatLngs);

                recyclerView.setAdapter(adapter);


              //dawaLatLngs.get(1).getCityVillage();
              // System.out.println( dawaLatLngs.get(1).getCityVillage() + " DDDDDDDDDDDDDDDDDDDDDDD");
                //-------
               // System.out.println("Hi");
                //----
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

