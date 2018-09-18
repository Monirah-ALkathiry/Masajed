package com.apps.noura.masajd.Mosque.MosqueAdvanceSearch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

import com.apps.noura.masajd.R;
import com.apps.noura.masajd.Mosque.Communication.Sender;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Monirah on 2/22/2018.
 */

@SuppressLint("ValidFragment")
public class MosqueSearch extends Fragment implements OnQueryTextListener
        , android.widget.SearchView.OnCloseListener {

    private static final String TAG= "Mosque Search";

    private  View view;

    private SearchableSpinner spinner;
    private SearchableSpinner spinnerCities;
    private SearchableSpinner spinnerDistricts;
    private SearchableSpinner spinnerMosque;


    private SearchView searchView;

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

    //----------------------------------------------------
    private  String lat;
    private  String lon;

   private EditText textView;
   private View SearchView;
   private  Button bu;
    //-----------------------------------------------------------


    private RadioGroup radioGroup;
    private String ChickedValue;
    private Button clear;

    private SearchView mSearchView;
    protected String SearchQuery;

    //-----------------------------------------------------------

    //------------------------------------------------------

    @SuppressLint("ValidFragment")
    public  MosqueSearch() {

    }


    @SuppressLint("ValidFragment")
    public  MosqueSearch(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    String mTeam;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.mosque_search,container,false);

        // SearchView = getActivity().findViewById(R.id.search);

        Bundle bundle = this.getArguments();
        if(bundle != null) {
            mTeam = bundle.getString("TeamName");
        }


        bu = (Button) view.findViewById(R.id.ButtonSendFeedback);

         //textView = (EditText) view.findViewById(R.id.MosqueSeach);
        //First Value of All Array list (used On spinner)
        SelectAll = "الكل";





//================================================================

       // textView.setText("");


        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = (RadioButton) group.findViewById(checkedId);
                if (null != rb && checkedId > -1) {
                    Toast.makeText(getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
                    ChickedValue= rb.getText().toString();

                    checked();
                }
            }
        });


        //clear = (Button) view.findViewById(R.id.CLEAR);

       /* clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioGroup.clearCheck();
                ChickedValue=null;
                checked();

            }
        });
        */
    //Search View

        mSearchView=(SearchView) view.findViewById(R.id.search);
        setupSearchView();


//-----------------------------------------------------------
        spinner = (SearchableSpinner) view.findViewById(R.id.myspinner);
        spinnerCities = (SearchableSpinner) view.findViewById(R.id.spinnerCities);
        spinnerDistricts = (SearchableSpinner) view.findViewById(R.id.spinnerDistricts);
        spinnerMosque = (SearchableSpinner) view.findViewById(R.id.spinnerMosque);


        spinner.setTitle("البحث عن المنطقة");
        spinnerCities.setTitle("البحث عن المدينة");
        spinnerDistricts.setTitle("البحث عن الحي");
        spinnerMosque.setTitle("نوع المسجد");


        //Jason Object:
        Regions.add(SelectAll);
      //  Cities.add(SelectAll);
      //  Districts.add(SelectAll);

        //Regions.clear();
        loadJSONFromAsset();
        //
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), android.R.layout.simple_spinner_dropdown_item, Regions);
        spinner.setAdapter(adapter);



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Code used to hide focus
                mSearchView.clearFocus();

                String Regions = parent.getSelectedItem().toString();
              //  Toast.makeText(parent.getContext().getApplicationContext(),Regions +"REJON IS  ",Toast.LENGTH_LONG).show();

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

                        //Code used to hide focus
                        mSearchView.clearFocus();

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

                                //Code used to hide focus
                                mSearchView.clearFocus();

                                Distric = parent.getSelectedItem().toString();
                                //Toast.makeText(getContext().getApplicationContext(), RejionID + " \n" + City + "\n" + Distric +" : update", Toast.LENGTH_LONG).show();




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

                                        //Code used to hide focus
                                        mSearchView.clearFocus();
                                        MosqueValue = parent.getSelectedItem().toString();
                                       // Toast.makeText(getContext().getApplicationContext(), MosqueId +" mosquId", Toast.LENGTH_LONG).show();

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

//===========================================================
Sender sender;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sender = (Sender) getActivity();
    }

//View Checked Item
public void checked(){
    Toast.makeText(getContext(), ChickedValue + " الاختيار : ", Toast.LENGTH_SHORT).show();

}



    public void onClear(View v) {
        /* Clears all selected radio buttons to default */
        radioGroup.clearCheck();
    }
//-------------------------
    public void onSubmit(View v) {
        RadioButton rb = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
        Toast.makeText(getContext(), rb.getText(), Toast.LENGTH_SHORT).show();
    }
//--------------------
    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        System.out.println("\n :::: )  "+lat + " : lat \n lone : " +lon);
        System.out.println("\n test : " + query +"\n");

        //New Function
        AdvanceSearch();

       /*

       textView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                query = textView.getText().toString();
                if (query.matches("")){
                   // Toast.makeText(getContext().getApplicationContext(), "الرجاءادخال كلمة بحث صحيحه", Toast.LENGTH_LONG).show();
                    return;
                }else {
                    // Toast.makeText(getContext().getApplicationContext(), query, Toast.LENGTH_LONG).show();
                    String map2;
                    Map<String, Object> map = new HashMap<>();
                    //----
                    //
                    System.out.print(RejionID + "\n" + "  " + "\n"
                            + City + "\n" + "  " + "\n" +
                            Distric + "\n" + "  " + "\n" +
                            query + "\n" + "  " + "\n" + " Mosque Type : " + MosqueId + "\n minstry rajion id : "+ ministry_region_id);

                    if(ministry_region_id == null ){

                        if(MosqueId ==(null)) {

                            map2 = "Mosque_Name = N" + "\'" + query + "\'";
                            System.out.println("\n sss :  \n" + map2 + "\n");
                        }else{
                            map2 = "Mosque_Name = N" + "\'" + query + "\' AND Mosque_Catogery = '" + MosqueId + "'";
                            System.out.println("\n sss :  \n" + map2 + "\n");
                        }

                    }else  if (City.equals(SelectAll)
                            && Distric.equals( SelectAll)) {

                        if(MosqueId ==(null)) {

                            map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                                    + "'";
                            System.out.println("\n sss :  \n" + map2 + "\n");
                        }else{
                            map2 = "Mosque_Name = N" + "\'" + query + "\'"+ "%' AND Region = '" + ministry_region_id
                                    + "'AND Mosque_Catogery = '" + MosqueId + "'";
                            System.out.println("\n sss :  \n" + map2 + "\n");
                        }


                        //map.put("where", "Mosque_Name = N" + "\'" + query + "\'");
                        System.out.println("\n Query ttt :  " + map2 + "\n");


                    }else if (Distric.equals( SelectAll)){

                        if(MosqueId ==(null)) {

                            map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                                    + "' AND City_Village like N'%" + City + "%'";

                            System.out.println("\n sss :  \n" + map2 + "\n");
                        }else{
                            map2 = "Mosque_Name = N" + "\'" + query + "\'"+ "%' AND Region = '" + ministry_region_id
                                    + "' AND City_Village like N'%" + City + "%'AND Mosque_Catogery = '" + MosqueId + "'";
                            System.out.println("\n sss :  \n" + map2 + "\n");
                        }


                        System.out.println("\n Query bbb :  " + map2 + "\n");

                    }else if( MosqueId==null){

                        map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                                + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                                "%'";
                        System.out.println("\n Query jjj :  " + map2 + "\n");

                    } else{

                    map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                            + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                            "%' AND Mosque_Catogery = '" + MosqueId + "'";
                    System.out.println("\n Query xxx :  " + map2 + "\n");

                }

                /*  if(RejionID == null && City.equals(SelectAll) ||  City == null
                           && Distric.equals( SelectAll) || Distric ==(null)
                           && MosqueId.equals (SelectAll) ||  MosqueId==(null)){

                       map2 = "Mosque_Name = N" + "\'" + query + "\'";
                       //map.put("where", "Mosque_Name = N" + "\'" + query + "\'");
                       System.out.println("\n sss :  \n" + map2 + "\n");
                   }else {

                       if (City.equals(SelectAll)
                               && Distric.equals( SelectAll)
                               &&  MosqueId==null) {

                           map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                                   + "'\'";
                           //map.put("where", "Mosque_Name = N" + "\'" + query + "\'");
                           System.out.println("\n Query xxx :  " + map2 + "\n");
                       } else {
                           map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                                   + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                                   "%' AND Mosque_Catogery = '" + MosqueId + "'";
                       }
                   } * /
                    System.out.println("\n new query :  " + map2 + "\n");
                    sender.SendMassage(map2);

                }//end outer else
            }
        });
*/

/*
 if (ministry_region_id == null || City == SelectAll || Distric == SelectAll || MosqueId == SelectAll) {
                           map2 = "Mosque_Name = N" + "\'" + query + "\'";
                           //map.put("where", "Mosque_Name = N" + "\'" + query + "\'");
                           System.out.println("\n Query :  " + map2 + "\n");
                       } else {
                           map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                                   + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                                   "%' AND Mosque_Catogery = '" + MosqueId + "'";
                       }
 */


    }



    @Override
    public void onDestroyView() {
        // Clean up resources related to the View.
        super.onDestroyView();


    }
    //---------------------------------------------------------
    @Override
    public void onResume() {
        super.onResume();
//        textView.getText().clear();

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
                   // System.out.println("json Object Selected ID is REJION : " + RejionID);
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
                Cities.add(SelectAll);  //
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


    //Search View:
    private void setupSearchView()
    {
        mSearchView.setIconifiedByDefault(true);

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setOnCloseListener(this);
        mSearchView.setQueryHint("بحث");
    }

    /**
     * Called when the user submits the query. This could be due to a key press on the
     * keyboard or due to pressing a submit button.
     * The listener can override the standard behavior by returning true
     * to indicate that it has handled the submit request. Otherwise return false to
     * let the SearchView handle the submission by launching any associated intent.
     *
     * @param query the query text that is to be submitted
     * @return true if the query has been handled by the listener, false to let the
     * SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        try {
            String Search_String = query;

            Search(Search_String);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }



    //Search----------------------------------

    public void Search(String query) {

        SearchQuery = query;

         Toast.makeText(getContext(),SearchQuery,Toast.LENGTH_LONG).show();
    }


    private void AdvanceSearch(){
      //  Toast.makeText(getContext(),SearchQuery+" from Advance search",Toast.LENGTH_LONG).show();

        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(getContext(),SearchQuery+" الزر search",Toast.LENGTH_LONG).show();
               // Toast.makeText(getContext(), ChickedValue + " الاختيار : ", Toast.LENGTH_SHORT).show();

                RadioButton rb = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                if(rb == null){
                    Toast.makeText(getContext(), "الرجاء الاختيار ", Toast.LENGTH_SHORT).show();

                }else {

                    String positionName = rb.getText().toString();


                    switch(positionName) {
                        case "إسم المسجد":
                            MosqueSearchQuery(positionName , SearchQuery ,ministry_region_id , MosqueId ,City ,Distric);

                            break;
                        case "إسم الإمام":
                            ImamSearchQuery(positionName,SearchQuery ,ministry_region_id , MosqueId ,City ,Distric);
                            break;
                        case "إسم الخطيب":
                            KhateebSearchQuery(positionName,SearchQuery ,ministry_region_id , MosqueId ,City ,Distric);
                            break;
                        default:

                    }

                }
            }//end Function
        });
    }

    private String map2;

    public void MosqueSearchQuery(String Option , String SearchQuery , String ministry_region_id
            , String MosqueId , String City , String Distric){


        Toast.makeText(getContext(), " from " + Option +
                "\n Search Query is :" +SearchQuery
                    +
                "\n Rejion ID  is :" +ministry_region_id  +
                "\n Mosque ID  is :" +MosqueId
                +
                "\n City ID  is :" +City
                +
                "\n Distric ID  is :" +Distric, Toast.LENGTH_SHORT).show();

        //------------------------------
        if(SearchQuery != null)

        {
            if (ministry_region_id == null) {

                if (MosqueId == (null)) {

                    map2 = "Mosque_Name = N" + "\'" + SearchQuery + "\'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else {
                    map2 = "Mosque_Name = N" + "\'" + SearchQuery + "\' AND Mosque_Catogery = '" + MosqueId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                }

            } else if (City.equals(SelectAll)
                    && Distric.equals(SelectAll)) {

                if (MosqueId == (null)) {

                    map2 = "Mosque_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                            + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else {
                    map2 = "Mosque_Name = N" + "\'" + SearchQuery + "\'" + "%' AND Region = '" + ministry_region_id
                            + "'AND Mosque_Catogery = '" + MosqueId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                }


                //map.put("where", "Mosque_Name = N" + "\'" + query + "\'");
                System.out.println("\n Query ttt :  " + map2 + "\n");


            } else if (Distric.equals(SelectAll)) {

                if (MosqueId == (null)) {

                    map2 = "Mosque_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                            + "' AND City_Village like N'%" + City + "%'";

                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else {
                    map2 = "Mosque_Name = N" + "\'" + SearchQuery + "\'" + "%' AND Region = '" + ministry_region_id
                            + "' AND City_Village like N'%" + City + "%'AND Mosque_Catogery = '" + MosqueId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                }


                System.out.println("\n Query bbb :  " + map2 + "\n");

            } else if (MosqueId == null) {

                map2 = "Mosque_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                        + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                        "%'";
                System.out.println("\n Query jjj :  " + map2 + "\n");

            } else {

                map2 = "Mosque_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                        + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                        "%' AND Mosque_Catogery = '" + MosqueId + "'";
                System.out.println("\n Query xxx :  " + map2 + "\n");

            }


            System.out.println("\n new query :  " + map2 + "\n");

            sender.SendMassage(map2);




        }else {

            System.out.println("\n new query :  " + "No Query had been insert" + "\n");

        }


       // AdvanceSearchFunction advanceSearchFunction = new AdvanceSearchFunction(Option
          //      ,SearchQuery,ministry_region_id,MosqueId,City,Distric);
       // advanceSearchFunction.MosqueSearch();
    }


    public void ImamSearchQuery(String Option , String SearchQuery , String ministry_region_id
            , String MosqueId , String City , String Distric){
        Toast.makeText(getContext(), " from " + Option +
                "\n Search Query is :" +SearchQuery
                +
                "\n Rejion ID  is :" +ministry_region_id  +
                "\n Mosque ID  is :" +MosqueId
                +
                "\n City ID  is :" +City
                +
                "\n Distric ID  is :" +Distric, Toast.LENGTH_SHORT).show();

        if(SearchQuery != null) {
            if (ministry_region_id == null) {

                if (MosqueId == (null)) {

                    map2 = "Imam_Name  like N'%" + SearchQuery + "%'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else {
                    map2 = "Imam_Name  like N'%" + SearchQuery + "%' AND Mosque_Catogery = '" + MosqueId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                }
//------------------------------------------------------------------------------------------------------
            } else if (City.equals(SelectAll)
                    && Distric.equals(SelectAll)) {

                if (MosqueId == (null)) {

                    map2 = "Imam_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                            + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else {
                    map2 = "Imam_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                            + "'AND Mosque_Catogery = '" + MosqueId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                }


                //map.put("where", "Imam_Name = N" + "\'" + query + "\'");
                System.out.println("\n Query ttt :  " + map2 + "\n");

//----------------------------------------------------------------------------------------
            } else if (Distric.equals(SelectAll)) {

                if (MosqueId == (null)) {

                    map2 = "Imam_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                            + "' AND City_Village like N'%" + City + "%'";

                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else {
                    map2 = "Imam_Name  like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                            + "' AND City_Village like N'%" + City + "%'AND Mosque_Catogery = '" + MosqueId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                }


                System.out.println("\n Query bbb :  " + map2 + "\n");
//---------------------------------------------------------------------------------------------------------
            } else if (MosqueId == null) {

                map2 = "Imam_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                        + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                        "%'";
                System.out.println("\n Query jjj :  " + map2 + "\n");

            }
            //---------------------------------------------------------------------------------------------------
            else {

                map2 = "Imam_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                        + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                        "%' AND Mosque_Catogery = '" + MosqueId + "'";
                System.out.println("\n Query xxx :  " + map2 + "\n");

            }

            sender.SendMassage(map2);
        }else {
            System.out.println("\n new query :  " + "No Query had been insert" + "\n");

        }

    }

    public void KhateebSearchQuery(String Option , String SearchQuery , String ministry_region_id
            , String MosqueId , String City , String Distric) {
        Toast.makeText(getContext(), " from " + Option +
                "\n Search Query is :" + SearchQuery
                +
                "\n Rejion ID  is :" + ministry_region_id +
                "\n Mosque ID  is :" + MosqueId
                +
                "\n City ID  is :" + City
                +
                "\n Distric ID  is :" + Distric, Toast.LENGTH_SHORT).show();

        if(SearchQuery != null) {
        if (ministry_region_id == null) {

            if (MosqueId == (null)) {

                map2 = "Khateeb_Name like N'%" + SearchQuery + "%'";
                System.out.println("\n sss :  \n" + map2 + "\n");
            } else {
                map2 = "Khateeb_Name like N'%" + SearchQuery + "%' AND Mosque_Catogery = '" + MosqueId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");
            }

        } else if (City.equals(SelectAll)
                && Distric.equals(SelectAll)) {

            if (MosqueId == (null)) {

                map2 = "Khateeb_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                        + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");
            } else {
                map2 = "Khateeb_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                        + "'AND Mosque_Catogery = '" + MosqueId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");
            }


            //map.put("where", "Khateeb_Name = N" + "\'" + query + "\'");
            System.out.println("\n Query ttt :  " + map2 + "\n");


        } else if (Distric.equals(SelectAll)) {

            if (MosqueId == (null)) {

                map2 = "Khateeb_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                        + "' AND City_Village like N'%" + City + "%'";

                System.out.println("\n sss :  \n" + map2 + "\n");
            } else {
                map2 = "Khateeb_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                        + "' AND City_Village like N'%" + City + "%'AND Mosque_Catogery = '" + MosqueId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");
            }


            System.out.println("\n Query bbb :  " + map2 + "\n");

        } else if (MosqueId == null) {

            map2 = "Khateeb_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                    + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                    "%'";
            System.out.println("\n Query jjj :  " + map2 + "\n");

        } else {

            map2 = "Khateeb_Name like N'%" + SearchQuery + "%' AND Region = '" + ministry_region_id
                    + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                    "%' AND Mosque_Catogery = '" + MosqueId + "'";
            System.out.println("\n Query xxx :  " + map2 + "\n");

        }

                  /*  if (ministry_region_id == null || City == SelectAll || Distric == SelectAll || MosqueId == SelectAll){
                        map2 = "Khateeb_Name = N" + "\'" + query + "\'";
                        //  map.put("where", "Imam_Name like N'%" + query+ "\'");

                    }else {
                        // map.put("where", "Imam_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                        //       + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric + "%' AND Mosque_Catogery = '" + MosqueId + "'");


                        map2 = "Khateeb_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                                + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric + "%' AND Mosque_Catogery = '" + MosqueId + "'";

                        System.out.println("\n Query :  " + map2 + "\n");

                    }
                    */
        sender.SendMassage(map2);
    }
    else{
            System.out.println("\n new query :  " + "No Query had been insert" + "\n");

        }

    }

    /**
     * The user is attempting to close the SearchView.
     *
     * @return true if the listener wants to override the default behavior of clearing the
     * text field and dismissing it, false otherwise.
     */
    @Override
    public boolean onClose() {
        try {
            String Search_String = null;
            Search(Search_String);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}//end class


 /*  SearchView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                query = textView.getText().toString();
                System.out.println("\n test 2 dddddddddd : " + query +"\n");

                System.out.print(RejionID + "\n" + "  " + "\n"
                        + City + "\n" + "  " + "\n" +
                        Distric + "\n" + "  " + "\n" +
                        query + "\n" + "  " + "\n" + " Mosque Type : " + MosqueId);

                String map2;
                Map<String, Object> map = new HashMap<>();

                    //----
                    //

                    if (ministry_region_id == null || City == SelectAll || Distric == SelectAll || MosqueId == SelectAll)
                    {
                        map2 = "Mosque_Name = N" + "\'" + query + "\'";
                        //map.put("where", "Mosque_Name = N" + "\'" + query + "\'");
                        System.out.println("\n Query :  " + map + "\n");
                    }
                    else {

                       /* map.put("where", "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                                + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                                "%' AND Mosque_Catogery = '" + MosqueId + "'");
                        */
                    /* map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                                + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                                "%' AND Mosque_Catogery = '" + MosqueId + "'";
                    }


                    System.out.println("\n :)  " + lat + " :lat \n lone : " + lon);

//Make A Connection With API :
                    searchClient = ApiRetrofitClint.getApiRetrofitClint().create(AdvanceSearchClint.class);

                    //24.70476920400006,46.63042159500003

                    //Call SearchRequest interface
                    Call<List<MosquesLatLng>> call = searchClient.getMosqueList2(25, lat, lon, map2);
                    //  Create Response:
                    call.enqueue(new Callback<List<MosquesLatLng>>() {
                        @Override
                        public void onResponse(Call<List<MosquesLatLng>> call, Response<List<MosquesLatLng>> response) {
                            mosquesLatLngs = response.body();
                            //Test Result and Print Data
                            System.out.println("Search Responce :");
                            System.out.println("Responce toString" + response.toString());
                            System.out.println("Responce body" + response.body());
                            System.out.println("Responce Headers" + response.headers());
                            System.out.print("URL" + response.isSuccessful());

                            Log.e("  URL KK : ", call.request().url().toString());

                            if (mosquesLatLngs.size() == 0) {
                                Toast.makeText(getContext().getApplicationContext(), "لايوجد بيانات", Toast.LENGTH_LONG).show();

                            } else {

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
                            System.out.print(":( :( \n");

                            Toast.makeText(getContext().getApplicationContext(), "الرجاء اعاده ادخال كلمات بحث اخرى", Toast.LENGTH_LONG).show();


                        }
                    });


                }

        });

*/
//=======================