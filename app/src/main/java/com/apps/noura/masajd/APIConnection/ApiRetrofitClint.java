package com.apps.noura.masajd.APIConnection;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Monirah on 21/12/17.
 */

public class ApiRetrofitClint {
    //http://gis.moia.gov.sa
    //old : http://mosquesapi.azurewebsites.net/

    public static final String BASE_URL="http://gis.moia.gov.sa/";
    public static Retrofit retrofit = null;

    public static Retrofit getApiRetrofitClint(){

        if (retrofit == null){

            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
