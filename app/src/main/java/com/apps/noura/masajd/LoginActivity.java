package com.apps.noura.masajd;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.toolbox.Volley;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    //create Retrofit instance
    private static Retrofit.Builder builder = new Retrofit.Builder()
        .baseUrl("http://mosquesapi.azurewebsites.net/")
        .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit = builder.build();

    UserClient userClient =retrofit.create(UserClient.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRigisterHere);

        //Register link open RegisterActivity
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent registerIntent = new Intent(LoginActivity.this, RigisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        //Login
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            login(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });



    }

//private static String usernameR;
    private void login(String etUsername, String etPassword){
        final int option = 1;
        System.out.println(etUsername);
       // Login login = new Login(option,etUsername,etPassword);

        // Calling interface
        Call<JsonObject> call =userClient.login(option,etUsername,etPassword);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                        System.out.println(response.body().toString());
                        //String jsonString = response.body().toString();

                    try {
                        JSONObject InfoResponse = new JSONObject(String.valueOf(response.body()));
                        boolean check  = InfoResponse.getBoolean("Error");
                        if (!check) {
                            String usernameR = InfoResponse.getString("Result");
                            System.out.println(usernameR);
                            Gson gson = new Gson();
                            User user = gson.fromJson(usernameR, User.class);
                            String usernameR2 = "Name:" + user.getUsername();
                            System.out.println(usernameR2);
                            // Toast.makeText(LoginActivity.this,"Welcome"+ usernameR2 , Toast.LENGTH_SHORT).show();

                            //get user info:
                            int userID= user.getUserAutoID();
                            int userCategory= user.getUserCategory();
                            String username= user.getUsername();

                            //move to home page after success login
                            Intent homeIntent = new Intent(LoginActivity.this, UserAreaActivity.class);
                            LoginActivity.this.startActivity(homeIntent);
                            // pass data to the activity userAutoID and username
                            
                            //session using Account manager


                        }
                        else{
                            String ErrorMsg = InfoResponse.getString("Messsage");
                            Toast.makeText(LoginActivity.this,ErrorMsg, Toast.LENGTH_SHORT).show();

                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                       // Gson gson = new Gson();
                      //  User user = gson.fromJson(usernameR, User.class);
                      // String usernameR  =  "Name:" + user.getUsername();
                     //  System.out.println(usernameR);
                       // Toast.makeText(LoginActivity.this, user.getUsername(), Toast.LENGTH_SHORT).show();



                        //String jsonData = response.body().string();
                       // JSONObject Jobject= new JSONObject(jsonData);
                       // String usernameR  =  "Name:" + Jobject.get("username");
                        //System.out.println(usernameR);

                       // String username = Jobject.getString("username");
                      //  System.out.println(username);

                        //JSONObject Jobject = new JSONObject(response.body().string());






                    //Toast.makeText(LoginActivity.this,response.body().getUsername(), Toast.LENGTH_SHORT).show();
                        // str= response.body().getUsername();
                        // System.out.println(str);

                } else {
                   Toast.makeText(LoginActivity.this, "Login Fail", Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }


        });
    }


}




