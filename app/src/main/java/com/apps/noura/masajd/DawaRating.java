package com.apps.noura.masajd;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Monirah on 4/8/2018.
 */

public interface DawaRating {

   // http://gis.moia.gov.sa/MobileApi/users.jsp?option=26&forID=18&idType=3&userID=47

    @GET("MobileApi/users.jsp?")
    Call<JsonObject>
    getRating(@Query("option") int option,
              @Query("forID") int forID,
              @Query("idType") int idType);

    @GET("MobileApi/users.jsp?")
    Call<JsonObject> getRating(
            @Query("option") int option,
            @Query("forID") int forID,
            @Query("idType") int idType,
            @Query("userID") Integer userID

    );
}
