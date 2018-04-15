package com.apps.noura.masajd;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Monirah on 11/12/17.
 */

public interface MosquesLatLngClint {

    @GET("MobileApi/index.jsp?")
    Call<List<MosquesLatLng>>
    getMosqueLatLng(@Query("lat") String latitude ,
                    @Query("lon") String longitude,
                    @Query("limit") int limit);


}
