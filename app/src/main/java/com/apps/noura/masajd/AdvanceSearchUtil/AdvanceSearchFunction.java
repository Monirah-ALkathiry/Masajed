package com.apps.noura.masajd.AdvanceSearchUtil;


import android.os.Bundle;
import android.widget.Toast;

import com.apps.noura.masajd.MosqueSearch;
import com.apps.noura.masajd.Sender;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Monirah on 9/6/2018.
 */

public class AdvanceSearchFunction {

    private String map2;
    private Map<String, Object> map = new HashMap<>();


    private String selectOption;
    private String query;

    private String ministry_region_id;

    private String City;

    private String Distric;
    private  String MosqueId;

    private final String SelectAll="الكل" ;

    Sender sender;


    public AdvanceSearchFunction() {
    }

    public AdvanceSearchFunction(String selectOption, String query,
                                 String ministry_region_id,  String mosqueId,String citieID, String distric) {
        this.selectOption = selectOption;
        this.query = query;
        this.ministry_region_id = ministry_region_id;
        this.City = citieID;
        this.Distric = distric;
        this.MosqueId = mosqueId;
         }



    public void MosqueSearch(){

        System.out.print("\n the vlaue is "+
                " from " + selectOption +
                "\n Search Query is :" +query
                +
                "\n Rejion ID  is :" +ministry_region_id  +
                "\n Mosque ID  is :" +MosqueId
                +
                "\n City ID  is :" +City
                +
                "\n Distric ID  is :" +Distric +"\n00\n");



        if(query != null)

        {
            if (ministry_region_id == null) {

                if (MosqueId == (null)) {

                    map2 = "Mosque_Name = N" + "\'" + query + "\'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else {
                    map2 = "Mosque_Name = N" + "\'" + query + "\' AND Mosque_Catogery = '" + MosqueId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                }

            } else if (City.equals(SelectAll)
                    && Distric.equals(SelectAll)) {

                if (MosqueId == (null)) {

                    map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                            + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else {
                    map2 = "Mosque_Name = N" + "\'" + query + "\'" + "%' AND Region = '" + ministry_region_id
                            + "'AND Mosque_Catogery = '" + MosqueId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                }


                //map.put("where", "Mosque_Name = N" + "\'" + query + "\'");
                System.out.println("\n Query ttt :  " + map2 + "\n");


            } else if (Distric.equals(SelectAll)) {

                if (MosqueId == (null)) {

                    map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                            + "' AND City_Village like N'%" + City + "%'";

                    System.out.println("\n sss :  \n" + map2 + "\n");
                } else {
                    map2 = "Mosque_Name = N" + "\'" + query + "\'" + "%' AND Region = '" + ministry_region_id
                            + "' AND City_Village like N'%" + City + "%'AND Mosque_Catogery = '" + MosqueId + "'";
                    System.out.println("\n sss :  \n" + map2 + "\n");
                }


                System.out.println("\n Query bbb :  " + map2 + "\n");

            } else if (MosqueId == null) {

                map2 = "Mosque_Name like N'%" + query + "%' AND Region = '" + ministry_region_id
                        + "' AND City_Village like N'%" + City + "%' AND District like N'%" + Distric +
                        "%'";
                System.out.println("\n Query jjj :  " + map2 + "\n");

            } else {

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
                   } */
            System.out.println("\n new query :  " + map2 + "\n");

            //sender.SendMassage(map2);



        }else {

            System.out.println("\n new query :  " + "No Query had been insert" + "\n");

        }



    }

}
