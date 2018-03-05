package com.apps.noura.masajd;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Noura Alsomaikhi on 1/2/2018.
 */

public interface DawaClient2 {

    @GET("mosques/DawaActivity.jsp?")

        Call<JsonObject> getDawaInfo (@Query("lat") String latitude, @Query("lon") String longitude, @Query("limit") int limit );
}
