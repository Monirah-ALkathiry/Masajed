package com.apps.noura.masajd.APIConnection;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Noura Alsomaikhi on 12/5/2017.
 */

public interface checkUsernameAPI {

    @FormUrlEncoded
    @POST("mosques/users.jsp")
    Call<JsonObject> check(
            @Field("option")int optioncheck,
            @Field("username")String username);
}
