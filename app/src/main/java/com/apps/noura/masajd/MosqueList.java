package com.apps.noura.masajd;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MosqueList extends Fragment {
    // the fragment initialization

    private static final String TAG = "MosqueLIST";

    //-----------------------------------------------------------------

    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MosqueListAdapter adapter;

    //Retrofit InterFace:
    private MosquesLatLngClint mosquesLatLngClint;
    //To get Mosque Information
    private List<MosquesLatLng> mosquesLatLngs;

    private View view;



    public MosqueList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable  ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        view = inflater.inflate(R.layout.fragment_mosque_list, container, false);


//Recycler View
        recyclerView = (RecyclerView) view.findViewById(R.id.MosqueRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        //-------------------------------------------------------

        //Make A Connection With API :
        mosquesLatLngClint = ApiRetrofitClint.getApiRetrofitClint().create(MosquesLatLngClint.class);
        //Call Function form Inter Face And Send Parameter to it
        //24.7179970   46.64460257
        //"27.522303","41.700603"
        Call<List<MosquesLatLng>> call = mosquesLatLngClint.getMosqueLatLng("24.7179970 "," 46.64460257",25);
     //  Create Response:
        call.enqueue(new Callback<List<MosquesLatLng>>() {
            @Override
            public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {

                mosquesLatLngs = response.body();

                //Send Data To Fragment List---
               adapter = new MosqueListAdapter(getContext(),mosquesLatLngs);

               recyclerView.setAdapter(adapter);
                //-------

                //Test Result and Print Data
                System.out.println("Responce toString"+ response.toString());
                System.out.println("Responce body"+ response.body());
                System.out.println("Responce headers"+ response.headers());
                System.out.println("Responce errorBody"+ response.errorBody());
                System.out.print("URL" + response.isSuccessful());
                //Storing the data in our list

                System.out.println("Size Is onResponce :----" +mosquesLatLngs.size());
                //-----------------------------------------------------------------------

            }

            @Override
            public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                System.out.println("Error bad  ):-----------------------");
            }
        });




        return  view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);



    }//end On create

/*

    //search

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //  super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_mosque_information, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView(); // Menu Item
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        final Context context= this;
        System.out.println("on create options menus");


        // return super.onCreateOptionsMenu(menu);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//on-click submit
                Toast.makeText(context,query,Toast.LENGTH_LONG).show();
                String KeyWord = query;
                // TextView textView = (TextView) findViewById(R.id.Text);
                //textView.setText(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//on change
                return false;
            }
        });

        //super.onCreateOptionsMenu(menu)  default return
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id= item.getItemId();
        System.out.println("on create options menus OUT OUT OIT" + id);


        return super.onOptionsItemSelected(item);
    }
 */

}






