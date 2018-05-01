package com.apps.noura.masajd;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apps.noura.masajd.Utils.SharedPreferencesConfig;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {



    //sharedpreferences
    private SharedPreferencesConfig sharedConfig;

     /*  Old login Version
    //create Retrofit instance
    private static Retrofit.Builder builder = new Retrofit.Builder()
        .baseUrl("http://mosquesapi.azurewebsites.net/")
        .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit = builder.build();

    UserClient userClient =retrofit.create(UserClient.class); */

     // New Login using moia API
    //create Retrofit instance
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://stg.moia.gov.sa/PortalAPIs/")
            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit = builder.build();

    LoginRequest loginClient =retrofit.create(LoginRequest.class);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRigisterHere);
        final TextView forgetPassLink = (TextView) findViewById(R.id.forgetPass);


        //Check if User Loge in Or not:
        sharedConfig = new SharedPreferencesConfig(getApplicationContext());

        if(sharedConfig.readLoginStatus())
        {
            Toast.makeText(this,"أهلا بك ",Toast.LENGTH_SHORT).show();
            finish();
        }


        //Register link open RegisterActivity
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // link to register from the website
                final String registerurl= "http://stg.moia.gov.sa/Pages/NewUser.aspx";
                Intent LinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(registerurl));
                startActivity(LinkIntent);

               // link to register activity
               // Intent registerIntent = new Intent(LoginActivity.this, RigisterActivity.class);
             //   LoginActivity.this.startActivity(registerIntent);
            }
        });

        //Login
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Call Login Function:
            login(etUsername.getText().toString(), etPassword.getText().toString());

                  }
        });

        //Forget password
        forgetPassLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent forgetPassIntent = new Intent(LoginActivity.this, forgetPassActivity.class);
                 LoginActivity.this.startActivity(forgetPassIntent);
            }
        });



    }

//private static String usernameR;
    private void login(String etUsername, String etPassword){
        final String grant_type = "password";
        System.out.println("Name : " +etUsername);
        System.out.println(" User Pasword : "+etPassword + "\n");

        // Login login = new Login(option,etUsername,etPassword);

        // Calling interface
       // Call<JsonObject> call =userClient.login(option,etUsername,etPassword);
        Call<JsonObject> call =loginClient.login(grant_type,etUsername,etPassword);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                //System.out.println(response.body().toString());
                //String Rurl= call.request().url().toString();
                //Log.i("test 22 : ", Rurl);

                try {

                      if (response.isSuccessful()) {
                          System.out.println(" : : : : \n" + response.body().toString());
                          //JSONObject InfoResponse = new JSONObject(String.valueOf(response.body()));
                          //boolean error = InfoResponse.getBoolean("error");

                          JSONObject InfoResponse = new JSONObject(String.valueOf(response.body()));
                        //  boolean error = InfoResponse.getBoolean("error");

                              String usernameR = InfoResponse.getString("FullName");
                              //System.out.println("Welcome" + usernameR);
                              Toast.makeText(LoginActivity.this, "السلام عليكم : "
                                      + usernameR, Toast.LENGTH_SHORT).show();
                              //2
                              String jsonData = response.body().toString();
                              JSONObject Jobject = new JSONObject(jsonData);
                              String usernameR2 = "إسم المستخدم :" + Jobject.get("UserName");
                             // System.out.println(usernameR2);

                              String username = Jobject.getString("UserName");
                              System.out.println(username);
                        //--------------------------------
                          sharedConfig.writeLoginStatus(true);
                          finish();


                      }else {

                          //String ErrorMsg = InfoResponse.getString("Messsage");
                          //Toast.makeText(LoginActivity.this,ErrorMsg, Toast.LENGTH_SHORT).show();
                          // Username or password false, display and an error
                          AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginActivity.this,R.style.MyDialogTheme);
                          dlgAlert.setIcon(R.drawable.warning);

                          dlgAlert.setTitle("تنبيه");
                          dlgAlert.setMessage("الرجاء التاكد من اسم المستخدم و كلمة المرور");
                          dlgAlert.setPositiveButton("موافق", null);
                          dlgAlert.setCancelable(true);
                          dlgAlert.create().show();

                          dlgAlert.setPositiveButton("موافق",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int which) {

                                      }
                                  });


                      }
                      //JSONObject Jobject = new JSONObject(response.body().string());
                   }
                   catch (JSONException e) {
                       e.printStackTrace();

                   }
/*
                    try {
                    if (response.isSuccessful()) {

                        System.out.println(response.body().toString());
                        //String jsonString = response.body().toString();
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
                    } */

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

                }




            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                System.out.println("there is no connection");

            }


        });
    }


}




