package com.apps.noura.masajd.APIConnection;

import com.apps.noura.masajd.Mosque.MosquesLatLng;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Monirah on 2/28/2018.
 */

public interface AdvanceSearchClint {

    @GET("MobileApi/index.jsp?")
    Call<List<MosquesLatLng>>

    getMosqueList2(@Query("limit") int limit , @Query("lat") String latitude , @Query("lon") String longitude,
                   @Query("where") String mosqu);

}
 /*@QueryMap(encoded=true) Map<String, Object> map */