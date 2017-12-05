package com.apps.noura.masajd;

import com.google.gson.JsonObject;

import retrofit2.Callback;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Noura Alsomaikhi on 12/4/2017.
 */

public interface RegisterAPI {
    @FormUrlEncoded
    @POST("mosques/users.jsp")
    Call<JsonObject> insertUser(
            @Field("option")int option,
            @Field("fullName")String fullname,
            @Field("username")String username,
            @Field("password")String password,
            @Field("userCategory")int userCategory );

}
