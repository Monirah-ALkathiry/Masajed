package com.apps.noura.masajd.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.apps.noura.masajd.R;

/**
 * Created by Monirah on 5/1/2018.
 */

public class SharedPreferencesConfig {
    private SharedPreferences sharedPre;
    private Context context;

    public SharedPreferencesConfig(){}

    public SharedPreferencesConfig(Context c )
    {
        this.context = c;
        this.sharedPre = c.getSharedPreferences(c.getResources().getString(R.string.login_preferences),Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status){
        SharedPreferences.Editor editor = sharedPre.edit();

        //key Values :   key , data
        editor.putBoolean(context.getResources().getString(R.string.login_status_preferences),status);
        editor.apply();

    }

    public boolean readLoginStatus(){

        boolean status = false;
        status = sharedPre.getBoolean(context.getResources().getString(R.string.login_status_preferences),false);
        return status;
    }
}
