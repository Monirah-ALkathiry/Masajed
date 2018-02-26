package com.apps.noura.masajd;

import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Noura Alsomaikhi on 12/31/2017.
 */

public interface DawaClient {
//mosques/DawaActivity.jsp?
    //MobileApi/index.jsp?

    @GET("MobileApi/DawaActivity.jsp?")
   // Call<JsonObject> getDawaInfo (@Query("lat") String latitude, @Query("lon") String longitude );
   Call<List<DawaLatLng>>
    getDawaLatLng(@Query("lat") String locYCoord , @Query("lon") String locXCoord);

}
