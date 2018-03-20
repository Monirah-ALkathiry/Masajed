package com.apps.noura.masajd;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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