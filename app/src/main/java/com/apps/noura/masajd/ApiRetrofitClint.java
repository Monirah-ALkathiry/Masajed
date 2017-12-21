package com.apps.noura.masajd;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Monirah on 21/12/17.
 */

public class ApiRetrofitClint {
    public static final String BASE_URL="http://mosquesapi.azurewebsites.net/";
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
