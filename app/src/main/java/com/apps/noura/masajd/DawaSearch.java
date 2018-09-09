package com.apps.noura.masajd;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import net.alhazmy13.hijridatepicker.date.hijri.HijriDatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static net.alhazmy13.hijridatepicker.date.hijri.HijriDatePickerDialog.*;


/**
 * Created by Monirah on 2/22/2018.
 */

@SuppressLint("ValidFragment")
public class DawaSearch extends Fragment implements OnDateSetListener
,SearchView.OnQueryTextListener
, android.widget.SearchView.OnCloseListener
{

    private static final String TAG= "Mosque Search";

    private  View view;

    private SearchableSpinner spinner;
    private SearchableSpinner spinnerCities;
    private SearchableSpinner spinnerDistricts;
    private SearchableSpinner spinnerActivity;
    private SearchableSpinner spinnerDomain;
    private SearchableSpinner spinnerLanguage;
    private SearchableSpinner spinneroffice;

    private TextView spinnerfromDate;
    private TextView spinnertoDate;

    private DatePickerDialog picker;


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
    private String dawa_region_id;
    private String CitiesId;

    private String SelectAll ;
    protected String City;
    protected String CitieID2;

    protected String Distric;
    protected String query; //User Key Word



    protected  String DawaActivityId; //Activity Type
    protected  String DawaActivityValue;


    protected  String DomainId;
    protected  String DomainValue;

    protected  String LanguageId;
    protected  String LanguageValue;

    protected  String OfficeId;
    protected  String OfficeValue;
    //--------------------------------------
    //----------------------------------------------------
    private  String lat;
    private  String lon;

    //-----------------------------------------------------------
    protected String RegionName;
    //-----------------------------------------------------------
   protected Sender sender;
    //------------------------------------------------------
    private  Button bu;
    private RadioGroup radioGroup;
    private String ChickedValue;
    private Button clear;

    private SearchView mSearchView;
    protected String SearchQuery;


    @SuppressLint("ValidFragment")
    public DawaSearch(String lat, String lon) {
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

         view = inflater.inflate(R.layout.dawa_search,container,false);



        bu = (Button) view.findViewById(R.id.ButtonSendFeedback);

        //textView = (EditText) view.findViewById(R.id.DawaSeach);
        //First Value of All Array list (used On spinner)
        SelectAll = "الكل";

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

        //Search View

        mSearchView=(SearchView) view.findViewById(R.id.search);
        setupSearchView();

      /*  System.out.println("\n test" + query +"\n");
        //query = null;


        final View SearchView = getActivity().findViewById(R.id.search);
        */

       /* textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                query = textView.getText().toString();

                System.out.print(RejionID + "\n" + "  " + "\n"
                        + City + "\n" + "  " + "\n" +
                        Distric + "\n" + "  " + "\n" +
                        query + "\n" + "  " + "\n" + " Dawa Type ID : " + DawaActivityId);

                String map2;


    if (query.matches("")){
        // Toast.makeText(getContext().getApplicationContext(), "الرجاءادخال كلمة بحث صحيحه", Toast.LENGTH_LONG).show();
        return;
    }else {


        if (dawa_region_id == null) {


            if (DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                    && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'";
                System.out.println("\n sss :  \n" + map2 + "\n");

            } else if (DomainId.equals("1") && LanguageId.equals("1")
                    && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%' AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");

            } else if (LanguageId.equals("1") && OfficeId .equals("1") && spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");

            } else if (OfficeId == null && spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                        + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");

            } else if (spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                        + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'"
                        + "AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";

                System.out.println("\n sss :  \n" + map2 + "\n");
            } else if (spinnertoDate.getText().toString().matches("")) {

                if(DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                        && OfficeId.equals("1")){

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query +
                            "%'AND DawaActivitiesInfo.DawaActivityDateH>= '" +
                            DateFrom + "'";
                }else{

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'"
                            + "AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                }
            } else {


                if(DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                        && OfficeId.equals("1")){

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query +
                            "%'AND DawaActivitiesInfo.DawaActivityDateH>= '" +
                            DateFrom +"'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";
                }else {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'"
                            + "AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";
                    System.out.println("\n Query :  " + map2 + "\n");
                }
            }
  //End First If-----------------------------------------------------------------------
       } else if (City.equals(SelectAll) && Distric.equals(SelectAll)) {


            if (DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                    && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {


                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id + "'";
                System.out.println("\n Dawa Region selected :  " + map2 + "\n");


            } else if (DomainId.equals("1")&& LanguageId.equals("1") && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'AND DawaActivitiesReq.Region = '"
                        + dawa_region_id + "' AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");

            } else if (LanguageId.equals("1") && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "' AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");

            } else if (OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "' AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                        + DomainId + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");
            } else if (spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "' AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                        + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                        + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");
            } else if (spinnertoDate.getText().toString().matches("")) {

                if(DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                        && OfficeId.equals("1")){

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'";
                }else {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                            + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                }

            } else {

                if(DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                        && OfficeId.equals("1")){

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";
                }else {
                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                            + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";

                    System.out.println("\n sss :  \n" + map2 + "\n");

                }
            }


 //end Second if  -----------------------------------------------------------------------

        } else if (Distric.equals(SelectAll)) {

            if (DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                    && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches(""))

            {
                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%" + City +"%'";
                //+"%'AND DawaActivitiesReq.District like N'%"+ Distric +


                        System.out.println("\n Dawa City selected :  " + map2 + "\n");

            } else if (DomainId.equals("1") && LanguageId.equals("1") && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {
                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%" + City
                        + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");
// +"%'AND DawaActivitiesReq.District like N'%"+ Distric


            } else if (LanguageId.equals("1") && OfficeId.equals("1")&& spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%" + City+ "%'AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId
                        + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");

                //+"%'AND DawaActivitiesReq.District like N'%"+ Distric

            } else if (OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%" + City +"%'AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                        + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";

                System.out.println("\n sss :  \n" + map2 + "\n");
                //+"%'AND DawaActivitiesReq.District like N'%"+ Distric +

            } else if (spinnerfromDate.getText().toString().matches("")
                    && spinnertoDate.getText().toString().matches("")) {


                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%" + City+ "%'AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                        + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId +
                       "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");

                ///+"%'AND DawaActivitiesReq.District like N'%"+ Distric


            } else if (spinnertoDate.getText().toString().matches("")) {

               if( DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                        && OfficeId.equals("1")){
                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City +"%'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'";
                }else{

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId +
                            "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                    //+"%'AND DawaActivitiesReq.District like N'%"+ Distric
                }

            } else {


                if( DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                        && OfficeId.equals("1")){
                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City +"%'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";
                }else {
                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId +
                            "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";

                    System.out.println("\n sss :  \n" + map2 + "\n");
                }
                //+"%'AND DawaActivitiesReq.District like N'%"+ Distric
            }

     //--------------------------------------------------------------------------------------
        } else {

            if (DawaActivityId.equals("1") && DomainId.equals("1") && LanguageId.equals("1")
                   && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                   && spinnertoDate.getText().toString().matches("")) {


                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%"
                        + City + "%'AND DawaActivitiesReq.District like N'%"
                        + Distric + "%'";


                System.out.println("\n Dawa City selected :  " + map2 + "\n");}
            else if (DomainId.equals("1") && LanguageId.equals("1") && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                && spinnertoDate.getText().toString().matches("")) {


                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%"
                        + City + "%'AND DawaActivitiesReq.District like N'%"
                        + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "'";

                System.out.println("\n sss :  \n" + map2 + "\n");

        } else if (LanguageId.equals("1") && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                && spinnertoDate.getText().toString().matches("")) {


                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%"
                        + City + "%'AND DawaActivitiesReq.District like N'%"
                        + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";

                System.out.println("\n sss :  \n" + map2 + "\n");

            } else if (OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%"
                        + City + "%'AND DawaActivitiesReq.District like N'%"
                        + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                        + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";

                System.out.println("\n sss :  \n" + map2 + "\n");
            } else if (spinnerfromDate.getText().toString().matches("")
                && spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%"
                        + City + "%'AND DawaActivitiesReq.District like N'%"
                        + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                        + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                        +"'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";
                System.out.println("\n sss :  \n" + map2 + "\n");
        } else if (spinnertoDate.getText().toString().matches("")) {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%"
                        + City + "%'AND DawaActivitiesReq.District like N'%"
                        + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                        + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                        +"'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                        + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                        DateFrom + "'";

                System.out.println("\n sss :  \n" + map2 + "\n");
        } else {

                map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                        + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                        + "'AND DawaActivitiesReq.City_Village like N'%"
                        + City + "%'AND DawaActivitiesReq.District like N'%"
                        + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                        + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                        + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                        +"'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                        + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                        DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";

                System.out.println("\n sss :  \n" + map2 + "\n");
          }

        }


            System.out.println("\n :)  " + lat + " :lat \n lone : " + lon);

            sender.SendMassage(map2);


    }



            }//onclick
        });//onclick

        */

//----------------------------------------------------------------------------------------------

//-----------------------------------------------------------
                spinner = (SearchableSpinner) view.findViewById(R.id.myspinner);
                spinnerCities = (SearchableSpinner) view.findViewById(R.id.spinnerCities);
                spinnerDistricts = (SearchableSpinner) view.findViewById(R.id.spinnerDistricts);
                spinnerActivity = (SearchableSpinner) view.findViewById(R.id.spinnerActivity);

                spinnerDomain = (SearchableSpinner) view.findViewById(R.id.spinnerDomain);
                spinnerLanguage = (SearchableSpinner) view.findViewById(R.id.spinnerLanguage);
                spinneroffice = (SearchableSpinner) view.findViewById(R.id.spinneroffice);
                spinnerfromDate = (TextView) view.findViewById(R.id.spinnerfromDate);
                spinnertoDate = (TextView) view.findViewById(R.id.spinnertoDate);
                //Jason Object:
                Regions.add(SelectAll);

          //------------------------------------------------------
                spinner.setTitle("البحث عن المنطقة");
                spinnerCities.setTitle("البحث عن المدينة");
                spinnerDistricts.setTitle("البحث عن الحي");
                spinnerActivity.setTitle("نوع النشاط");
                spinnerDomain.setTitle("مجال النشاط");
                spinnerLanguage.setTitle("اللغه");
                spinneroffice.setTitle("المكتب");
            //--------------------------------------------------------------

               // Regions.clear();
                loadJSONFromAsset();
                //
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Regions);
                spinner.setAdapter(adapter);


                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        //Code used to hide focus
                        mSearchView.clearFocus();

                         RegionName = parent.getSelectedItem().toString();
                       // Toast.makeText(parent.getContext().getApplicationContext(), Regions + "REJON IS  ", Toast.LENGTH_LONG).show();

                        String RejionID2;
                        RejionID2 = getRijonID(RegionName);

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
                                System.out.println("--------------CitieID2---------------------------------" + CitiesId);


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
                                       // Toast.makeText(getContext().getApplicationContext(), RejionID + " \n" + City + "\n" + Distric, Toast.LENGTH_LONG).show();


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

                        //Code used to hide focus
                        mSearchView.clearFocus();

                        DawaActivityValue = parent.getSelectedItem().toString();
                       // Toast.makeText(getContext().getApplicationContext(), DawaActivityId, Toast.LENGTH_LONG).show();

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

                //Code used to hide focus
                mSearchView.clearFocus();

                DomainValue = parent.getSelectedItem().toString();
                //Toast.makeText(getContext().getApplicationContext(), DomainId, Toast.LENGTH_LONG).show();

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

                //Code used to hide focus
                mSearchView.clearFocus();

                LanguageValue = parent.getSelectedItem().toString();
               // Toast.makeText(getContext().getApplicationContext(), LanguageId, Toast.LENGTH_LONG).show();

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

                //Code used to hide focus
                mSearchView.clearFocus();

                OfficeValue = parent.getSelectedItem().toString();
                //Toast.makeText(getContext().getApplicationContext(), OfficeId, Toast.LENGTH_LONG).show();

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

//-----------------------------------------Date From---------------------------------------------------------
           spinnerfromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UmmalquraCalendar now = new UmmalquraCalendar();
                HijriDatePickerDialog dpd = newInstance(DawaSearch.this,
                        now.get(UmmalquraCalendar.YEAR),
                        now.get(UmmalquraCalendar.MONTH),
                        now.get(UmmalquraCalendar.DAY_OF_MONTH));

                //color:
                now.getDisplayName(Calendar.MONTH, UmmalquraCalendar.LONG, new Locale("ar"));
                dpd.setAccentColor(Color.parseColor("#305252"));
                dpd.vibrate(false);
                dpd.setVersion(Version.VERSION_1);
                //Highest day:
                UmmalquraCalendar date1 = new UmmalquraCalendar();
                UmmalquraCalendar date2 = new UmmalquraCalendar();
                date2.add(Calendar.WEEK_OF_MONTH, -1);
                UmmalquraCalendar date3 = new UmmalquraCalendar();
                date3.add(Calendar.WEEK_OF_MONTH, 1);
                UmmalquraCalendar[] days = {date1, date2, date3};
                dpd.setHighlightedDays(days);

                /*
             final Calendar cldr = Calendar.getInstance();
                FromDay = cldr.get(Calendar.DAY_OF_MONTH);
                 FromMonth = cldr.get(Calendar.MONTH);
                FromYear = cldr.get(Calendar.YEAR);


                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //spinnerfromDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                                DateFrom =String.format("%d-%02d-%02d", year,monthOfYear+1,dayOfMonth);
                                spinnerfromDate.setText(DateFrom);
                            }
                        }, FromYear, FromMonth, FromDay);
                picker.show();
                */
                dpd.show(getActivity().getFragmentManager(),"Test Dialoge Fragment");

            }

           });

        //1439-05-29
//-----------------------------------------Date To---------------------------------------------------------
        spinnertoDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                UmmalquraCalendar now = new UmmalquraCalendar();
                HijriDatePickerDialog dpd = newInstance(DawaSearch.this,
                        now.get(UmmalquraCalendar.YEAR),
                        now.get(UmmalquraCalendar.MONTH),
                        now.get(UmmalquraCalendar.DAY_OF_MONTH));

                //color:
                now.getDisplayName(Calendar.MONTH, UmmalquraCalendar.LONG, new Locale("ar"));
                dpd.setAccentColor(Color.parseColor("#305252"));
                dpd.vibrate(false);
                dpd.setVersion(Version.VERSION_1);
                //Highest day:
                UmmalquraCalendar date1 = new UmmalquraCalendar();
                UmmalquraCalendar date2 = new UmmalquraCalendar();
                date2.add(Calendar.WEEK_OF_MONTH, -1);
                UmmalquraCalendar date3 = new UmmalquraCalendar();
                date3.add(Calendar.WEEK_OF_MONTH, 1);
                UmmalquraCalendar[] days = {date1, date2, date3};
                dpd.setHighlightedDays(days);




                dpd.setOnDateSetListener(new OnDateSetListener() {
                    @Override
                    public void onDateSet(HijriDatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        //String date = + dayOfMonth + "/" + (++monthOfYear) + "/" + year;
                        DateTo= String.format("%d/%02d/%02d", year,monthOfYear+1,dayOfMonth);
                        spinnertoDate.setText(DateTo);
                    }
                });
                dpd.show(getActivity().getFragmentManager(),"Test Dialoge Fragment");

               /* final Calendar cldr = Calendar.getInstance();
                 ToDay = cldr.get(Calendar.DAY_OF_MONTH);
                 ToMonth = cldr.get(Calendar.MONTH);
                 ToYear = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //spinnertoDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                DateTo= String.format("%d-%02d-%02d", year,monthOfYear+1,dayOfMonth);
                                spinnertoDate.setText(DateTo);
                            }
                        }, ToYear, ToMonth, ToDay);
                picker.show();
                */
               // dpd.show(getActivity().getFragmentManager(),"Test Dialoge Fragment");
            }
        });
//-----------------------------------------------------------------

        return view;
    }

//View Checked Item
    public void checked(){
        Toast.makeText(getContext(), ChickedValue + " الاختيار : ", Toast.LENGTH_SHORT).show();

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


    @Override
    public void onDateSet(HijriDatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        DateFrom =String.format("%d/%02d/%02d", year,monthOfYear+1,dayOfMonth);

        spinnerfromDate.setText(DateFrom);
    }


    /**
     * Called immediately after {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     *
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        System.out.println("\n :::: )  "+lat + " : lat \n lone : " +lon);
        System.out.println("\n test : " + query +"\n");

        //New Function
        AdvanceSearch();
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

        bu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());
                if(rb == null){
                    Toast.makeText(getContext(), "الرجاء الاختيار ", Toast.LENGTH_SHORT).show();

                }else {

                    String positionName = rb.getText().toString();


                    switch(positionName) {
                        case "عنوان المنشط":
                            DawaSearchQuery(positionName ,SearchQuery ,dawa_region_id ,City,Distric
                            ,DawaActivityId , DomainId ,LanguageId ,OfficeId ,DateFrom ,DateTo);

                            break;
                        case "إسم الداعية":
                            DaeiaSearchQuery(positionName ,SearchQuery ,dawa_region_id ,City,Distric
                                    ,DawaActivityId , DomainId ,LanguageId ,OfficeId ,DateFrom ,DateTo);
                            break;

                        default:

                    }

                }
            }
        });
    }//end Advance Search Function:

//------------------Dawa Advance search Methods:
    private String map2;
    public void DawaSearchQuery(String Option , String SearchQuery , String dawa_region_id,
    String City , String Distric , String DawaActivityId ,String DomainId , String LanguageId
    ,String OfficeId , String DateFrom , String DateTo)
    {


        if(SearchQuery != null)

        {

            if (dawa_region_id == null) {


                if (DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                        && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery + "%'";
                    System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (DomainId.equals("1") && LanguageId.equals("1")
                        && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery + "%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (LanguageId.equals("1") && OfficeId .equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (OfficeId == null && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'"
                            + "AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";

                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (spinnertoDate.getText().toString().matches("")) {

                    if(DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                            && OfficeId.equals("1")){

                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery +
                                "%'AND DawaActivitiesInfo.DawaActivityDateH>= '" +
                                DateFrom + "'";
                    }else{

                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                                + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                                + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'"
                                + "AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                                DateFrom + "'";
                        System.out.println("\n sss :  \n" + map2 + "\n");
                    }
                } else {


                    if(DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                            && OfficeId.equals("1")){

                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery +
                                "%'AND DawaActivitiesInfo.DawaActivityDateH>= '" +
                                DateFrom +"'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";
                    }else {

                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                                + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                                + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'"
                                + "AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                                DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";
                        System.out.println("\n Query :  " + map2 + "\n");
                    }
                }
                //End First If-----------------------------------------------------------------------
            } else if (City.equals(SelectAll) && Distric.equals(SelectAll)) {


                if (DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                        && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {


                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id + "'";
                    System.out.println("\n Dawa Region selected :  " + map2 + "\n");


                } else if (DomainId.equals("1")&& LanguageId.equals("1") && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery + "%'AND DawaActivitiesReq.Region = '"
                            + dawa_region_id + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (LanguageId.equals("1") && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (spinnertoDate.getText().toString().matches("")) {

                    if(DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                            && OfficeId.equals("1")){

                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                                + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                                DateFrom + "'";
                    }else {

                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                                + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                                + "' AND DawaActivitiesInfo.DawaActivityType = '"
                                + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                                + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                                + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                                + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                                DateFrom + "'";
                        System.out.println("\n sss :  \n" + map2 + "\n");
                    }

                } else {

                    if(DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                            && OfficeId.equals("1")){

                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                                + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                                DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";
                    }else {
                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                                + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                                + "' AND DawaActivitiesInfo.DawaActivityType = '"
                                + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                                + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                                + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                                + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                                DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";

                        System.out.println("\n sss :  \n" + map2 + "\n");

                    }
                }


                //end Second if  -----------------------------------------------------------------------

            } else if (Distric.equals(SelectAll)) {

                if (DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                        && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches(""))

                {
                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City +"%'";
                    //+"%'AND DawaActivitiesReq.District like N'%"+ Distric +


                    System.out.println("\n Dawa City selected :  " + map2 + "\n");

                } else if (DomainId.equals("1") && LanguageId.equals("1") && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {
                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City
                            + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
// +"%'AND DawaActivitiesReq.District like N'%"+ Distric


                } else if (LanguageId.equals("1") && OfficeId.equals("1")&& spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City+ "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId
                            + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");

                    //+"%'AND DawaActivitiesReq.District like N'%"+ Distric

                } else if (OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City +"%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";

                    System.out.println("\n sss :  \n" + map2 + "\n");
                    //+"%'AND DawaActivitiesReq.District like N'%"+ Distric +

                } else if (spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {


                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City+ "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId +
                            "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");

                    ///+"%'AND DawaActivitiesReq.District like N'%"+ Distric


                } else if (spinnertoDate.getText().toString().matches("")) {

                    if( DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                            && OfficeId.equals("1")){
                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                                + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                                + "'AND DawaActivitiesReq.City_Village like N'%" + City +"%'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                                DateFrom + "'";
                    }else{

                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                                + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                                + "'AND DawaActivitiesReq.City_Village like N'%" + City + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                                + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                                + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId +
                                "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                                DateFrom + "'";
                        System.out.println("\n sss :  \n" + map2 + "\n");
                        //+"%'AND DawaActivitiesReq.District like N'%"+ Distric
                    }

                } else {


                    if( DawaActivityId.equals("1") && DomainId .equals("1") && LanguageId.equals("1")
                            && OfficeId.equals("1")){
                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                                + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                                + "'AND DawaActivitiesReq.City_Village like N'%" + City +"%'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                                DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";
                    }else {
                        map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                                + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                                + "'AND DawaActivitiesReq.City_Village like N'%" + City + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                                + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                                + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId +
                                "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                                DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";

                        System.out.println("\n sss :  \n" + map2 + "\n");
                    }
                    //+"%'AND DawaActivitiesReq.District like N'%"+ Distric
                }

                //--------------------------------------------------------------------------------------
            } else {

                if (DawaActivityId.equals("1") && DomainId.equals("1") && LanguageId.equals("1")
                        && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {


                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'";


                    System.out.println("\n Dawa City selected :  " + map2 + "\n");}
                else if (DomainId.equals("1") && LanguageId.equals("1") && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {


                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'";

                    System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (LanguageId.equals("1") && OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {


                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";

                    System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (OfficeId.equals("1") && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                            + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";

                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                            + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            +"'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                            + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            +"'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                            + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'";

                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + SearchQuery
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                            + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            +"'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                            + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";

                    System.out.println("\n sss :  \n" + map2 + "\n");
                }

            }


            System.out.println("\n :)  " + lat + " :lat \n lone : " + lon);

            sender.SendMassage(map2);


        }else {

            System.out.println("\n new query :  " + "No Query had been insert" + "\n");

        }


    }



 // Daeia Advance search :

    public void DaeiaSearchQuery(String Option , String SearchQuery , String dawa_region_id,
                                 String City , String Distric , String DawaActivityId ,String DomainId , String LanguageId
            ,String OfficeId , String DateFrom , String DateTo)

    {

        if(SearchQuery != null)

        {
            if (dawa_region_id == null) {


                if (DawaActivityId == "1" && DomainId == "1" && LanguageId == "1"
                        && OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'";
                    // System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (DomainId == "1" && LanguageId == "1" && OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'";
                    // System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (LanguageId == "1" && OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";
                    //System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (OfficeId == null && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";
                    // System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {
                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'"
                            + "AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";

                    // System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (spinnertoDate.getText().toString().matches("")) {
                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'"
                            + "AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'";
                    // System.out.println("\n sss :  \n" + map2 + "\n");
                } else {
                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'\"%' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'"
                            + "AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";
                    //   System.out.println("\n Query :  " + map2 + "\n");
                }
                //End First If-----------------------------------------------------------------------
            } else if (City.equals(SelectAll) && Distric.equals(SelectAll)) {


                if (DawaActivityId == "1" && DomainId == "1" && LanguageId == "1"
                        && OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {


                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id + "'";
                    //System.out.println("\n Dawa Region selected :  " + map2 + "\n");


                } else if (DomainId == "1" && LanguageId == "1" && OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query + "%'AND DawaActivitiesReq.Region = '"
                            + dawa_region_id + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'";
                    //System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (LanguageId == "1" && OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";
                    //System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";
                    //System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";
                    // System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                            + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'";
                    // System.out.println("\n sss :  \n" + map2 + "\n");

                } else {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "' AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "' AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                            + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";

                    // System.out.println("\n sss :  \n" + map2 + "\n");
                }


                //end Second if  -----------------------------------------------------------------------

            } else if (Distric.equals(SelectAll)) {

                if (DawaActivityId == "1" && DomainId == "1" && LanguageId == "1"
                        && OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches(""))

                {
                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City + "%'";
                    //+"%'AND DawaActivitiesReq.District like N'%"+ Distric +


                    // System.out.println("\n Dawa City selected :  " + map2 + "\n");

                } else if (DomainId == "1" && LanguageId == "1" && OfficeId == "1"
                        && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {
                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City
                            + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'";
                    // System.out.println("\n sss :  \n" + map2 + "\n");
// +"%'AND DawaActivitiesReq.District like N'%"+ Distric


                } else if (LanguageId == "1" && OfficeId == "1" &&
                        spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId
                            + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";
                    //  System.out.println("\n sss :  \n" + map2 + "\n");

                    //+"%'AND DawaActivitiesReq.District like N'%"+ Distric

                } else if (OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";

                    // System.out.println("\n sss :  \n" + map2 + "\n");
                    //+"%'AND DawaActivitiesReq.District like N'%"+ Distric +

                } else if (spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {


                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId +
                            "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";
                    // System.out.println("\n sss :  \n" + map2 + "\n");

                    ///+"%'AND DawaActivitiesReq.District like N'%"+ Distric


                } else if (spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId +
                            "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'";
                    //  System.out.println("\n sss :  \n" + map2 + "\n");
                    //+"%'AND DawaActivitiesReq.District like N'%"+ Distric

                } else {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%" + City + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '"
                            + DomainId + "' AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId +
                            "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "' AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";

                    // System.out.println("\n sss :  \n" + map2 + "\n");

                    //+"%'AND DawaActivitiesReq.District like N'%"+ Distric
                }

                //--------------------------------------------------------------------------------------
            } else {

                if (DawaActivityId == "1" && DomainId == "1" && LanguageId == "1"
                        && OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {


                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'";


                    //  System.out.println("\n Dawa City selected :  " + map2 + "\n");
                } else if (DomainId == "1" && LanguageId == "1" && OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {


                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'";

                    System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (LanguageId == "1" && OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {


                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId + "'";

                    // System.out.println("\n sss :  \n" + map2 + "\n");

                } else if (OfficeId == "1" && spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                            + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId + "'";

                    // System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (spinnerfromDate.getText().toString().matches("")
                        && spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                            + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId + "'";
                    //System.out.println("\n sss :  \n" + map2 + "\n");
                } else if (spinnertoDate.getText().toString().matches("")) {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                            + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                            + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'";

                    // System.out.println("\n sss :  \n" + map2 + "\n");
                } else {

                    map2 = "DawaActivitiesInfo.DawaAddress like N'%" + query
                            + "%'AND DawaActivitiesReq.Region = '" + dawa_region_id
                            + "'AND DawaActivitiesReq.City_Village like N'%"
                            + City + "%'AND DawaActivitiesReq.District like N'%"
                            + Distric + "%'AND DawaActivitiesInfo.DawaActivityType = '"
                            + DawaActivityId + "'AND DawaActivitiesInfo.DawaMainCategory = '" + DomainId
                            + "'AND DawaActivitiesInfo.DawaActivLanguage = '" + LanguageId
                            + "'AND DawaActivitiesReq.DawaOffice = '" + OfficeId
                            + "'AND DawaActivitiesInfo.DawaActivityDateH >= '" +
                            DateFrom + "'AND DawaActivitiesInfo.DawaActivityDateH <= '" + DateTo + "'";

                    //   System.out.println("\n sss :  \n" + map2 + "\n");
                }
            }

            //System.out.println("\n :)  " + lat + " :lat \n lone : " + lon);
            sender.SendMassage(map2);
        }else {

            System.out.println("\n new query :  " + "No Query had been insert" + "\n");

        }

    }
}
