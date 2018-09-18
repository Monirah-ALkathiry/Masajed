package com.apps.noura.masajd.APIConnection;

import com.apps.noura.masajd.Dawa.DawaLatLng;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Monirah on 2/28/2018.
 */

public interface DawaAdvanceSearchClint {

    //http://gis.moia.gov.sa/MobileApi/DawaActivity.jsp?
    @GET("MobileApi/DawaActivity.jsp?")
    Call<List<DawaLatLng>>

    getDawaSearchResult(@Query("limit") int limit, @Query("lat") String latitude, @Query("lon") String longitude,
                   @Query("where") String dawa);

}
 /*@QueryMap(encoded=true) Map<String, Object> map */

 /*  @Query("where") Map<String, Object> dawa)*/