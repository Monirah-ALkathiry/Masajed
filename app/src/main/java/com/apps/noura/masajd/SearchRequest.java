package com.apps.noura.masajd;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Monirah on 2/13/2018.
 */

public interface SearchRequest {

    @GET("MobileApi/index.jsp?")
    Call<List<MosquesLatLng>>
    getMosqueList(@Query("lat") String latitude , @Query("lon") String longitude, @Query("Mosque_Name") String MosqueName, @Query("limit") int limit);
}
