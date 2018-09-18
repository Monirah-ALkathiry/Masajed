package com.apps.noura.masajd.UserAccount;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.noura.masajd.APIConnection.RegisterAPI;
import com.apps.noura.masajd.R;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RigisterActivity extends AppCompatActivity {

    //create Retrofit instance
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("http://mosquesapi.azurewebsites.net/")
            .addConverterFactory(GsonConverterFactory.create());
    public static Retrofit retrofit = builder.build();
    RegisterAPI registerAPI =retrofit.create(RegisterAPI.class);

   // public static Retrofit retrofit2 = builder.build();
   // checkUsernameAPI checkUsernameAPI =retrofit2.create(checkUsernameAPI.class);

    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rigister);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);


        updateUI();

/*
        final EditText etFullname = (EditText) findViewById(R.id.tvFullname);
        final EditText etUsername = (EditText) findViewById(R.id.tvUsername);
        final EditText etPass = (EditText) findViewById(R.id.tvPass);
        final EditText etConfirmPass = (EditText) findViewById(R.id.tvConfirmPass);
        final Button bRegister = (Button) findViewById(R.id.btnRegister);

        //Register
        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register(etFullname.getText().toString(),etUsername.getText().toString(), etPass.getText().toString(),etConfirmPass.getText().toString());
            }
        });
 */
    }
    private void updateUI() {
        final EditText etFullname = (EditText) findViewById(R.id.tvFullname);
        final EditText etUsername = (EditText) findViewById(R.id.tvUsername);
        final EditText etPass = (EditText) findViewById(R.id.tvPass);
        final EditText etConfirmPass = (EditText) findViewById(R.id.tvConfirmPass);
        final Button bRegister = (Button) findViewById(R.id.btnRegister);


        String regexPassword = "(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[~`!@#\\$%\\^&\\*\\(\\)\\-_\\+=\\{\\}\\[\\]\\|\\;:\"<>,./\\?]).{6,}";
        awesomeValidation.addValidation(RigisterActivity.this, R.id.tvFullname, "[a-zA-Z\\s]+",R.string.nameerr);
        awesomeValidation.addValidation(RigisterActivity.this,R.id.tvUsername,"[a-zA-Z0-9\\s]+",R.string.usernamerr);
       // awesomeValidation.addValidation(MainActivity.this,R.id.email,android.util.Patterns.EMAIL_ADDRESS,R.string.emailerr);
       // awesomeValidation.addValidation(MainActivity.this,R.id.phno, RegexTemplate.TELEPHONE,R.string.phoneerr);
        awesomeValidation.addValidation(RigisterActivity.this,R.id.tvPass,regexPassword,R.string.passerr);
        awesomeValidation.addValidation(RigisterActivity.this,R.id.tvConfirmPass,R.id.tvPass,R.string.cnfpasserr);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (awesomeValidation.validate()) {
                    // Toast.makeText(RigisterActivity.this, "Data Received Succesfully", Toast.LENGTH_SHORT).show();
                    Register(etFullname.getText().toString(), etUsername.getText().toString(), etPass.getText().toString(), etConfirmPass.getText().toString());
                    //check username :
                   /* boolean checkresult = checkUsername(etUsername.getText().toString());
                    if (checkresult) {
                        Register(etFullname.getText().toString(), etUsername.getText().toString(), etPass.getText().toString(), etConfirmPass.getText().toString());
                    } else {
                        Toast.makeText(RigisterActivity.this, R.string.usernamerr2, Toast.LENGTH_SHORT).show();
                    }
                    */
                }
                else

                {

                    Toast.makeText(RigisterActivity.this, "Error", Toast.LENGTH_SHORT).show();

                }

              //  Register(etFullname.getText().toString(),etUsername.getText().toString(), etPass.getText().toString(),etConfirmPass.getText().toString());
            }
        });

    }
/*
    private Boolean checkUsername(String username){
        final int optioncheck = 2;
        boolean check = true;
        // Calling interface
        Call<JsonObject> call =checkUsernameAPI.check(optioncheck,username);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    System.out.println(response.body().toString());

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }

        });
return check;
    }
*/
    private void Register(String etFullname,String etUsername,String etPass, String etConfirmPass) {
        final int  userCategory= 0;
        final int option = 0;
// Calling interface
        Call<JsonObject> call =registerAPI.insertUser(option,etFullname,etUsername,etPass, userCategory);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {

                    System.out.println(response.body().toString());

                    try {
                        JSONObject InfoResponse = new JSONObject(String.valueOf(response.body()));
                        boolean check  = InfoResponse.getBoolean("Error");
                        if (!check) {
                            String result = InfoResponse.getString("Messsage");
                            System.out.println(result);
                            //Gson gson = new Gson();
                            //User user = gson.fromJson(usernameR, User.class);
                            //String usernameR2 = "Name:" + user.getUsername();
                            //System.out.println(usernameR2);
                           // Toast.makeText(RigisterActivity.this,"Success register" , Toast.LENGTH_SHORT).show();
                        }
                        else{
                            String ErrorMsg = InfoResponse.getString("Messsage");
                            System.out.println(ErrorMsg);
                           // Toast.makeText(RigisterActivity.this,R.string.usernamerr2, Toast.LENGTH_SHORT).show();

                        }
                } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }



        }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }

            });
        }


    }
