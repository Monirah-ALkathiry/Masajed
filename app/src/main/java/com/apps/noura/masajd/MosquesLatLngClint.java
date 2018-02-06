package com.apps.noura.masajd;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Monirah on 11/12/17.
 */

public interface MosquesLatLngClint {

    @GET("mosques/index.jsp?")
    Call<List<MosquesLatLng>>
    getMosqueLatLng(@Query("lat") String latitude , @Query("lon") String longitude, @Query("limit") int limit);

  /* @GET("mosques/index.jsp?")
    Call<List<MosquesLatLng>>
        getMosque_Name(@Query("lat") String latitude , @Query("lon") String longitude, @Query("limit") int limit );
//where=Mosque_Name%20

*/

  //MobileApi/index.jsp
    ///MobileApi/index.jsp?
    // @Query("Mosque_Name")
    @GET("mosques/index.jsp?")
    Call<List<MosquesLatLng>>
    Search_by_Name(@Query("lat") String latitude , @Query("lon") String longitude, @Query("limit") int limit ,@Query("where") String Mosque_Name);
}
