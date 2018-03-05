package com.apps.noura.masajd;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Monirah on 2/22/2018.
 */

//TODO :Remove Search View

// implements AdapterView.OnItemSelectedListener
@SuppressLint("ValidFragment")
public class ImamaSearch extends Fragment {

    private static final String TAG = "ImamSearch";

    private  View view;

    private Spinner spinner;
    private Spinner spinnerCities;
    private Spinner spinnerDistricts;
    private Spinner spinnerMosque;


   // private SearchView searchView;

    //Spinner Data
    private ArrayList<String> Regions = new ArrayList<>();
    private ArrayList<String> Cities = new ArrayList<>();
    private ArrayList<String> Districts = new ArrayList<>();
    private ArrayList<String> Mosque = new ArrayList<>();



    private String RejionID;
    private String ministry_region_id;
    private String CitiesId;

    private String SelectAll ;
    protected String City;
    protected String CitieID2;

    protected String Distric;
    protected String query;
    protected  String MosqueId;
    protected  String MosqueValue;

    //--------------------------------------
    //Retrofit InterFace:
    private AdvanceSearchClint searchClient;
    //To get Mosque Information
    private List<MosquesLatLng> mosquesLatLngs;

//----------------------------------------------------
    private  String lat;
    private  String lon;

    EditText textView;
 //-----------------------------------------------------------

    //-----------------------------------------------------------
    private MosqueListAdapter adapter;
    //Recycle View (Mosque List)
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    //------------------------------------------------------


//--------Constructor-------------
    public  ImamaSearch(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       view = inflater.inflate(R.layout.imam_search, container, false);
       // searchView = (SearchView) view.findViewById(R.id.search);
        textView = (EditText) view.findViewById(R.id.TestSeach);
        //First Value of All Array list (used On spinner)
        SelectAll = "الكل";

        System.out.println("\n :::: )  "+lat + " :Constractore lat \n lone : " +lon);


        System.out.println("\n test" + query +"\n");

        //----Update Recycler View
        /*
        recyclerView = (RecyclerView) view.findViewById(R.id.MosqueRecyclerView);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        */

        query = null;

//TODO : Onclick Search Do Advance Search:

        View SearchView = getActivity().findViewById(R.id.search);
        SearchView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                    query = textView.getText().toString();

                System.out.print(RejionID + "\n" + "  " + "\n"
                +City + "\n" + "  " + "\n"+
                        Distric + "\n" + "  " + "\n"+
                        query + "\n" + "  " + "\n" + " Mosque Type : " +MosqueId);




//----
//TODO : Advance Search View Result

    String map2;

if(query != null) {
    if (ministry_region_id == null && City == SelectAll && Distric == SelectAll) {


        map2 = "Imam_Name = N" + "\'" + query + "\'";
        System.out.println("\n Query :  " + map2 + "\n");


    } else {
        map2 = "Imam_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric + "%' AND Mosque_Catogery = '" + MosqueId + "'";
    }
}else {
    Toast.makeText(getContext().getApplicationContext(), "الرجاء ادخال اسم مسجد", Toast.LENGTH_LONG).show();
    map2 = null;
}

  System.out.println("\n :)  "+lat + " :lat \n lone : " +lon);

//Make A Connection With API :
                searchClient = ApiRetrofitClint.getApiRetrofitClint().create(AdvanceSearchClint.class);

                //24.70476920400006,46.63042159500003

                //Call SearchRequest interface
                Call<List<MosquesLatLng>> call = searchClient.getMosqueList2(25,lat,lon,map2);
                //  Create Response:
                call.enqueue(new Callback<List<MosquesLatLng>>() {
                    @Override
                    public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {
                        mosquesLatLngs= response.body();
                        //Test Result and Print Data
                        System.out.println("Search Responce :");
                        System.out.println("Responce toString"+ response.toString());
                        System.out.println("Responce body"+ response.body());
                        System.out.println("Responce Headers"+ response.headers());
                        System.out.print("URL" + response.isSuccessful());

                        Log.e("  URL KK : ", call.request().url().toString());

                        if(mosquesLatLngs.size() == 0){
                            Toast.makeText(getContext().getApplicationContext(), "There is No Data", Toast.LENGTH_LONG).show();

                        }else {

                            String latitude = mosquesLatLngs.get(0).getLatitude();
                            String longitude = mosquesLatLngs.get(0).getLongitude();

                            System.out.print("latitude" + latitude + "\n");
                            System.out.print("longitude" + longitude + "\n");


                            double lat = Double.parseDouble(latitude);
                            double lon = Double.parseDouble(longitude);

                        }

                    }

                    @Override
                    public void onFailure(Call<List<MosquesLatLng>> call, Throwable t) {
                        System.out.print(":( :( \n" );

                        Toast.makeText(getContext().getApplicationContext(), "الرجاء اعاده ادخال كلمات بحث اخرى", Toast.LENGTH_LONG).show();


                    }
                });





            }
        });


//----------------------------------------------------------------------------------------------




       /*
       query(true, DATABASE_NAMES_TABLE, new String[] { KEY_ROWID,
                        KEY_NAME }, KEY_NAME + " LIKE ?",
                new String[] {"%"+ filter+ "%" }, null, null, null,
                null);
        */
//-----------------------------------------------------------
        spinner = (Spinner) view.findViewById(R.id.myspinner);
        spinnerCities = (Spinner) view.findViewById(R.id.spinnerCities);
        spinnerDistricts = (Spinner) view.findViewById(R.id.spinnerDistricts);
        spinnerMosque = (Spinner) view.findViewById(R.id.spinnerMosque);


        //Jason Object:
        Regions.add(SelectAll);
        //  Cities.add(SelectAll);
        // Districts.add(SelectAll);

        //Regions.clear();
        loadJSONFromAsset();
        //
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Regions);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String Regions = parent.getSelectedItem().toString();
                Toast.makeText(parent.getContext().getApplicationContext(),Regions +"REJON IS  ",Toast.LENGTH_LONG).show();

                String RejionID2;
                RejionID2 = getRijonID(Regions);

                //----------------------Cities -----------------
                Cities.clear();
                loadJSONFromAssetCities(RejionID2);
                System.out.println("--------------------RejionID---------------------------" + RejionID2);


                ArrayAdapter<String> adapterCities = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Cities);
                spinnerCities.setAdapter(adapterCities);
                System.out.println("-------------------------------------------------------");
                //------------------------------Select District-----------------------------------------
                spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        City = parent.getSelectedItem().toString();


                        CitieID2 = getCityID2ID(City);
                        System.out.println("--------------CitieID2---------------------------------" +CitiesId);


                        //---------------------Districts-------------------
                        Districts.clear();

                        loadJSONFromAssetDistricts(CitieID2);
                        ArrayAdapter<String> adapterDistricts = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Districts);
                        spinnerDistricts.setAdapter(adapterDistricts);


                        spinnerDistricts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                Distric = parent.getSelectedItem().toString();
                                Toast.makeText(getContext().getApplicationContext(), RejionID + " \n" + City + "\n" + Distric, Toast.LENGTH_LONG).show();




                                //---------------------Mosque-------------------

                                Mosque.clear();
                                Mosque.add(SelectAll);
                                Mosque.add("مسجد");
                                Mosque.add("مصلى");
                                Mosque.add("جامع");
                                Mosque.add("مصلى عيد");


                                ArrayAdapter<String> adapterMosque = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Mosque);
                                spinnerMosque.setAdapter(adapterMosque);


                                spinnerMosque.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        MosqueValue = parent.getSelectedItem().toString();
                                        Toast.makeText(getContext().getApplicationContext(), MosqueId, Toast.LENGTH_LONG).show();

                                        switch(MosqueValue){
                                            case "مسجد":
                                                MosqueId ="1";
                                                break;
                                            case "مصلى":
                                                MosqueId ="2";
                                                break;
                                            case "جامع":
                                                MosqueId ="3";
                                                break;
                                            case "مصلى عيد":
                                                MosqueId ="4";
                                                break;
                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }

    //---------------------------------------------------------------------------------------------------------
    public void loadJSONFromAsset() {
        String json = null;
        // Regions.clear();

        try {
            InputStream is = getActivity().getAssets().open("regions.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //Region_id.add(jsonObject.getString("region_id"));
                // Regions.add(SelectAll);
                Regions.add(jsonObject.getJSONObject("name").getString("ar"));

                System.out.println("json Object Regions: " + Regions.get(i));
            }
            System.out.println("-------------------------------------------------------");

            // Toast.makeText(getContext().getApplicationContext(),test.toString(),Toast.LENGTH_LONG).show();

        } catch (IOException ex) {

            ex.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //-------------------------------------------------------------------------------------------------------------------------
    public String getRijonID(String rejoin) {
        String json = null;
        String SelectedRejoin = rejoin;


        try {
            InputStream is = getActivity().getAssets().open("regions.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getJSONObject("name").getString("ar").equals(SelectedRejoin)) {
                    // Region_id.add(jsonObject.getString("region_id"));
                    RejionID = (jsonObject.getString("region_id"));
                    ministry_region_id = (jsonObject.getString("ministry_region_id"));
                    System.out.println("json Object Selected ID is REJION : " + RejionID);
                }
            }


            // Toast.makeText(getContext().getApplicationContext(),test.toString(),Toast.LENGTH_LONG).show();

        } catch (IOException ex) {

            ex.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RejionID;
    }


//---------------------------------------------------------------

    public void loadJSONFromAssetCities(String Id) {
        String json = null;
        String SelectedRejoinId = Id;

        System.out.println("SelectedRejoinId : " + SelectedRejoinId);
        try {
            InputStream is = getActivity().getAssets().open("cities.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            Cities.add(SelectAll);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getString("region_id").equals(SelectedRejoinId)) {

                    //Cities_id.add(jsonObject.getString("city_id"));
                    //Cities.add(SelectAll);
                    Cities.add(jsonObject.getJSONObject("name").getString("ar"));
                }

                // Log.d("json Object Cities: " , Cities.get(i));

            }
            System.out.println("-------------------------------------------------------");
            // Toast.makeText(getContext().getApplicationContext(),test.toString(),Toast.LENGTH_LONG).show();

        } catch (IOException ex) {

            ex.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


//-----------------------------------------------------------------------------------------------------

    public String getCityID2ID(String city) {

        String json = null;
        String SelectedCitie = city;


        try {
            InputStream is = getActivity().getAssets().open("cities.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.getJSONObject("name").getString("ar").equals(SelectedCitie)) {
                    // Region_id.add(jsonObject.getString("region_id"));
                    CitiesId = (jsonObject.getString("city_id"));
                    System.out.println("json Object Selected ID is : " + CitiesId);
                }
            }

            // Toast.makeText(getContext().getApplicationContext(),test.toString(),Toast.LENGTH_LONG).show();

        } catch (IOException ex) {

            ex.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return CitiesId;
    }

    //--------------------
    public void loadJSONFromAssetDistricts(String City) {

        String json = null;
        String SelectedCityId = City;
        System.out.println("SelectedRejoinId : " + SelectedCityId);

        try {
            InputStream is = getActivity().getAssets().open("districts.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");
            JSONArray jsonArray = new JSONArray(json);
            Districts.add(SelectAll);
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);


                if (jsonObject.getString("city_id").equals(SelectedCityId)) {
                    //Districts.add(SelectAll);
                    Districts.add(jsonObject.getJSONObject("name").getString("ar"));


                }
            }

            // Toast.makeText(getContext().getApplicationContext(),test.toString(),Toast.LENGTH_LONG).show();

        } catch (IOException ex) {

            ex.printStackTrace();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}

