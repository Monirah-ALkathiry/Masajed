package com.apps.noura.masajd;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Noura Alsomaikhi on 12/31/2017.
 */

public interface DawaClient {

    @GET("MobileApi/DawaActivity.jsp?")
   Call<List<DawaLatLng>>
    getDawaLatLng(@Query("lat") String locYCoord , @Query("lon") String locXCoord , @Query("limit") int limit);

}
