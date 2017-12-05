package com.apps.noura.masajd;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final TextView loginlink = (TextView) findViewById(R.id.tvloginlink);
        final TextView websitelink = (TextView) findViewById(R.id.tvwebsitelink);

        //Login link open Login Activity
        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(MainActivity.this,LoginActivity.class);
                MainActivity.this.startActivity(LoginIntent);
            }
        });


        websitelink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 final String websiteurl= "http://www.moia.gov.sa/AboutMinistry/Pages/AboutMinistry.aspx";
                Intent LinkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteurl));
                startActivity(LinkIntent);
            }
        });

    }


}
