package com.apps.noura.masajd.UserAccount;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.apps.noura.masajd.R;

/**
 * Created by Noura Alsomaikhi on 1/14/2018.
 */

public class forgetPassActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);

    }
}
