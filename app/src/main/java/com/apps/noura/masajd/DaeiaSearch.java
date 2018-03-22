package com.apps.noura.masajd;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Monirah on 2/22/2018.
 */

@SuppressLint("ValidFragment")
public class DaeiaSearch extends Fragment {

    private static final String TAG= "Mosque Search";

    private  View view;

    private Spinner spinner;
    private Spinner spinnerCities;
    private Spinner spinnerDistricts;
    private Spinner spinnerActivity;
    private Spinner spinnerDomain;
    private Spinner spinnerLanguage;
    private Spinner spinneroffice;
   // private Spinner spinnerfromDate;
    //private Spinner spinnertoDate;

    private TextView simpleDatePicker ;
    private TextView spinnerfromDate;
    private DatePickerDialog picker;



    //private SearchView searchView;

    //Spinner Data
    private ArrayList<String> Regions = new ArrayList<>();
    private ArrayList<String> Cities = new ArrayList<>();
    private ArrayList<String> Districts = new ArrayList<>();
    private ArrayList<String> DawaActivityTypeList = new ArrayList<>();
    private ArrayList<String> DawaActivLanguageList = new ArrayList<>();
    private ArrayList<String> DawaMainCategoryList = new ArrayList<>();
    private ArrayList<String> DawaOfficesList = new ArrayList<>();

    //Data Variables:
    private int FromDay ;
    private int FromMonth ;
    private int FromYear;

    private int ToDay ;
    private int ToMonth ;
    private int ToYear;

    private String DateFrom;
    private String DateTo;

    //-----

    private String RejionID;
    private String dawa_region_id;//TODO change to Dawa
    private String CitiesId;

    private String SelectAll ;
    protected String City;
    protected String CitieID2;

    protected String Distric;
    protected String query;



    protected  String DawaActivityId;
    protected  String DawaActivityValue;


    protected  String DomainId;
    protected  String DomainValue;

    protected  String LanguageId;
    protected  String LanguageValue;

    protected  String OfficeId;
    protected  String OfficeValue;
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
    protected String RegionName;
    //-----------------------------------------------------------
    protected Sender sender;
    //-----------------------------------------------------------

    //------------------------------------------------------



    @SuppressLint("ValidFragment")
    public DaeiaSearch(String lat, String lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        sender = (Sender) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.daeia_search,container,false);

        textView = (EditText) view.findViewById(R.id.DaiaSeach);
        //First Value of All Array list (used On spinner)
        SelectAll = "الكل";

        System.out.println("\n test" + query +"\n");
        query = null;

//TODO : Onclick Search Do Advance Search:

        View SearchView = getActivity().findViewById(R.id.search);
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                query = textView.getText().toString();

                System.out.print(RejionID + "\n" + "  " + "\n"
                        + City + "\n" + "  " + "\n" +
                        Distric + "\n" + "  " + "\n" +
                        query + "\n" + "  " + "\n" + " Dawa Type ID : " + DawaActivityId);


//----
//TODO : Advance Search View Result

                String map2;

                if (query != null) {
                    if (dawa_region_id == null && City == SelectAll && Distric == SelectAll) {


                        map2 = "DawaActivitiesInfo.DawaAddress like N" + "\'" + query + "\'";
                        System.out.println("\n Query :  " + map2 + "\n");


                    } else {
                     //   map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + dawa_region_id
                       //         + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric + "%' AND Mosque_Catogery = '" + DawaActivityId + "'";


                        //TODO : Search Query (Daia Name)
                        map2 = "DawaActivitiesInfo.ActivityExecution = '2'"+
                                "AND DawaActivitiesInfo.AmaraApproval = '2'"+
                                "AND DawaActivitiesInfo.DawaAddress like N'%"+query+
                                "%' AND DawaActivitiesReq.Region ="+ RejionID+
                                "AND DawaActivitiesReq.City_Village like N'%"+City+
                                "%' AND DawaActivitiesReq.District like N'%"+Distric+
                                "%' AND DawaActivitiesInfo.DawaActivityType ="+DawaActivityId
                                +" AND DawaActivitiesInfo.DawaMainCategory ="+DomainId
                                +"AND DawaActivitiesInfo.DawaActivLanguage = "+LanguageId
                                +"AND DawaActivitiesReq.DawaOffice ="+OfficeId
                                +"AND DawaActivitiesInfo.DawaActivityDateH >= '"+
                                "1439-05-29"+"' AND DawaActivitiesInfo.DawaActivityDateH <= '"+
                                "1439-06-29"+"'";
                    }
                } else {
                    Toast.makeText(getContext().getApplicationContext(), "الرجاء ادخال اسم الداعية", Toast.LENGTH_LONG).show();
                    map2 = null;
                }

                System.out.println("\n :)  " + lat + " :lat \n lone : " + lon);

                sender.SendMassage(map2);



            }//onclick
        });//onclick

//----------------------------------------------------------------------------------------------

//-----------------------------------------------------------
        spinner = (Spinner) view.findViewById(R.id.myspinner);
        spinnerCities = (Spinner) view.findViewById(R.id.spinnerCities);
        spinnerDistricts = (Spinner) view.findViewById(R.id.spinnerDistricts);
        spinnerActivity = (Spinner) view.findViewById(R.id.spinnerActivity);

        spinnerDomain = (Spinner) view.findViewById(R.id.spinnerDomain);
        spinnerLanguage = (Spinner) view.findViewById(R.id.spinnerLanguage);
        spinneroffice = (Spinner) view.findViewById(R.id.spinneroffice);
       // spinnerfromDate = (Spinner) view.findViewById(R.id.spinnerfromDate);
        //spinnertoDate = (Spinner) view.findViewById(R.id.spinnertoDate);
//-----------------------------------------------------------------------------------------
        simpleDatePicker = (TextView) view.findViewById(R.id.simpleDatePicker);
        spinnerfromDate = (TextView) view.findViewById(R.id.spinnerfromDate);

//-----------------------------------------------------------------------------------------------
        //Jason Object:
        Regions.add(SelectAll);
        //Cities.add(SelectAll);
        //  Districts.add(SelectAll);

        //Regions.clear();
        loadJSONFromAsset();
        //
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Regions);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String Regions = parent.getSelectedItem().toString();
                Toast.makeText(parent.getContext().getApplicationContext(), Regions + "REJON IS  ", Toast.LENGTH_LONG).show();

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
                        System.out.println("--------------CitieID2---------------------------------" + CitiesId);


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


        //---------------------DawaActivityTypeList-------------------
        DawaActivityTypeList.clear();
        DawaActivityTypeList.add(SelectAll);
        DawaActivityTypeList.add("درس");
        DawaActivityTypeList.add("محاضرة");
        DawaActivityTypeList.add("دورة علمية");
        DawaActivityTypeList.add("مخيم");
        DawaActivityTypeList.add("كلمات وعظية");
        DawaActivityTypeList.add("دورة علمية - متعددة");
        DawaActivityTypeList.add("اخرى");


        ArrayAdapter<String> adapterActivity = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, DawaActivityTypeList);
        spinnerActivity.setAdapter(adapterActivity);


        spinnerActivity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DawaActivityValue = parent.getSelectedItem().toString();
                Toast.makeText(getContext().getApplicationContext(), DawaActivityId, Toast.LENGTH_LONG).show();

                switch (DawaActivityValue) {
                    case "الكل":
                        DawaActivityId = "1";
                        break;
                    case "دورة علمية -متعددة":
                        DawaActivityId = "7";
                        break;
                    case "كلمات وعظية":
                        DawaActivityId = "6";
                        break;
                    case "مخيم":
                        DawaActivityId = "5";
                        break;

                    case "دورة علمية":
                        DawaActivityId = "4";
                        break;

                    case "محاضرة":
                        DawaActivityId = "3";
                        break;
                    case "درس":
                        DawaActivityId = "2";
                        break;

                    case "اخرى":
                        DawaActivityId = "7";
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//----------------------------DawaMainCategoryList--------------------------------------------

        DawaMainCategoryList.clear();
        DawaMainCategoryList.add(SelectAll);
        DawaMainCategoryList.add("العقيدة");
        DawaMainCategoryList.add("الفقه");
        DawaMainCategoryList.add("التفسير");
        DawaMainCategoryList.add("الحديث");
        DawaMainCategoryList.add("اللغة العربية");
        DawaMainCategoryList.add("السيرة النبوية");
        DawaMainCategoryList.add("لتاريخ الإسلامي");

        DawaMainCategoryList.add("الوعظ والرقائق");
        DawaMainCategoryList.add("الأخلاق والسلوك");
        DawaMainCategoryList.add("قضايا الأسرة والمرأة");
        DawaMainCategoryList.add("قضايا المجتمع");
        DawaMainCategoryList.add("قضايا الأمن الفكري والإرهاب");
        DawaMainCategoryList.add("موضوعات عامة");

        ArrayAdapter<String> adapterDawaMainCategoryList = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, DawaMainCategoryList);
        spinnerDomain.setAdapter(adapterDawaMainCategoryList);


        spinnerDomain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                DomainValue = parent.getSelectedItem().toString();
                Toast.makeText(getContext().getApplicationContext(), DomainId, Toast.LENGTH_LONG).show();

                switch (DomainValue) {
                    case "الكل":
                        DomainId = "1";
                        break;
                    case "العقيدة":
                        DomainId = "2";
                        break;
                    case "الفقه":
                        DomainId = "3";
                        break;
                    case "التفسير":
                        DomainId = "4";
                        break;

                    case "الحديث":
                        DomainId = "5";
                        break;

                    case "اللغة العربية":
                        DomainId = "6";
                        break;
                    case "السيرة النبوية":
                        DomainId = "7";
                        break;

                    case "لتاريخ الإسلامي":
                        DomainId = "8";
                        break;

                    case "الوعظ والرقائق":
                        DomainId = "9";
                        break;
                    case "الأخلاق والسلوك":
                        DomainId = "10";
                        break;
                    case "قضايا الأسرة والمرأة":
                        DomainId = "11";
                        break;
                    case "قضايا المجتمع":
                        DomainId = "12";
                        break;

                    case "قضايا الأمن الفكري والإرهاب":
                        DomainId = "13";
                        break;

                    case "موضوعات عامة":
                        DomainId = "14";
                        break;

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//---------------------------------------Language-------------------------------------------
        DawaActivLanguageList.clear();
        DawaActivLanguageList.add(SelectAll);
        DawaActivLanguageList.add("اللغة العربية");
        DawaActivLanguageList.add("الانجليزية");
        DawaActivLanguageList.add("الاردو");
        DawaActivLanguageList.add("الفلبينية");
        DawaActivLanguageList.add("البنغالية");



        ArrayAdapter<String> adapterDawaActivLanguageList = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, DawaActivLanguageList);
        spinnerLanguage.setAdapter(adapterDawaActivLanguageList);


        spinnerLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                LanguageValue = parent.getSelectedItem().toString();
                Toast.makeText(getContext().getApplicationContext(), LanguageId, Toast.LENGTH_LONG).show();

                switch (LanguageValue) {
                    case "الكل":
                        LanguageId = "1";
                        break;
                    case "اللغة العربية":
                        LanguageId = "2";
                        break;
                    case "الانجليزية":
                        LanguageId = "3";
                        break;
                    case "الاردو":
                        LanguageId = "4";
                        break;

                    case "الفلبينية":
                        LanguageId = "5";
                        break;

                    case "البنغالية":
                        LanguageId = "6";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//-------------------------------------DawaOfficesList---------------------------------------

        DawaOfficesList.clear();
        DawaOfficesList.add(SelectAll);
        DawaOfficesList.add("المكتب التعاوني بحي الروضة");
        DawaOfficesList.add("المكتب التعاوني بحي السلام");



        ArrayAdapter<String> adapterDawaOfficesList = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, DawaOfficesList);
        spinneroffice.setAdapter(adapterDawaOfficesList);


        spinneroffice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                OfficeValue = parent.getSelectedItem().toString();
                Toast.makeText(getContext().getApplicationContext(), OfficeId, Toast.LENGTH_LONG).show();

                switch (OfficeValue) {
                    case "الكل":
                        OfficeId = "1";
                        break;
                    case "المكتب التعاوني بحي الروضة":
                        OfficeId = "2";
                        break;
                    case "المكتب التعاوني بحي السلام":
                        OfficeId = "3";
                        break;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //TODO : ONclik open date picker
        //TODO : Add um Al qura Date Picker
//-----------------------------------------Date From---------------------------------------------------------

       spinnerfromDate.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

             final Calendar cldr =Calendar.getInstance();
               FromDay = cldr.get(Calendar.DAY_OF_MONTH);
               FromMonth = cldr.get(Calendar.MONTH);
               FromYear = cldr.get(Calendar.YEAR);


               // date picker dialog
               picker = new DatePickerDialog(getContext(),
                       new DatePickerDialog.OnDateSetListener() {
                           @Override
                           public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                              // spinnerfromDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                               DateFrom =String.format("%d-%02d-%02d", year,monthOfYear+1,dayOfMonth);
                               spinnerfromDate.setText(DateFrom);
                           }
                       }, FromYear, FromMonth, FromDay);

               picker.show();

           }
       });
//-----------------------------------------Date To---------------------------------------------------------
        simpleDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                ToDay = cldr.get(Calendar.DAY_OF_MONTH);
                ToMonth = cldr.get(Calendar.MONTH);
                ToYear = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                              //  simpleDatePicker.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                DateTo= String.format("%d-%02d-%02d", year,monthOfYear+1,dayOfMonth);
                                simpleDatePicker.setText(DateTo);
                            }
                        }, ToYear, ToMonth, ToDay);
                picker.show();

            }
        });

        //--------------------------------------------------------------------------------------------------------

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
                    dawa_region_id = (jsonObject.getString("dawa_region_id"));
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
