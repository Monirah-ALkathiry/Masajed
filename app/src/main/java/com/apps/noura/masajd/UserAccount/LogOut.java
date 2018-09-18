package com.apps.noura.masajd.UserAccount;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apps.noura.masajd.Mosque.MosqueActivity;
import com.apps.noura.masajd.R;
import com.apps.noura.masajd.Utils.SharedPreferencesConfig;

public class LogOut extends AppCompatActivity {


    private SharedPreferencesConfig sharedPrefrenseConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

       sharedPrefrenseConfig  = new SharedPreferencesConfig(getApplicationContext());
       sharedPrefrenseConfig.writeLoginStatus(false);
       startActivity( new Intent(LogOut.this,MosqueActivity.class));
       finish();


    }

}
