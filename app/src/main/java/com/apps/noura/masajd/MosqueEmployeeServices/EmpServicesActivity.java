package com.apps.noura.masajd.MosqueEmployeeServices;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.apps.noura.masajd.NavigationDrawer.BasicActivity;
import com.apps.noura.masajd.R;
import com.apps.noura.masajd.Utils.SharedPreferencesConfig;

public class EmpServicesActivity extends BasicActivity {


    //sharedpreferences
    private SharedPreferencesConfig sharedConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setContentView(R.layout.activity_emp_services);


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //inflate your activity layout here!
        @SuppressLint("InflateParams")
        View contentView = inflater.inflate(R.layout.activity_emp_services, null, false);
        drawer.addView(contentView, 0);
        //check in Menu selected :
        navigationView.setCheckedItem(R.id.ic_masijed);

        //Check If User Login
        sharedConfig = new SharedPreferencesConfig(getApplicationContext());
        if(sharedConfig.readLoginStatus())
        {
            navigationView.getMenu().findItem(R.id.login).setVisible(false);
            //finish();


        }
        else {

            navigationView.getMenu().findItem(R.id.logOut).setVisible(false);

        }
        //----------------------------------

    }
}
