package com.apps.noura.masajd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class WelcomMosqueApp extends AppCompatActivity {
ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom_mosque_app);

        imageView = (ImageView) findViewById(R.id.welcom);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.welocm_mosque_animation);
        imageView.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                startActivity(new Intent(getApplicationContext(),MosqueActivity.class));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                finish();
                startActivity(new Intent(getApplicationContext(),MosqueActivity.class));
            }
        });
    }
}
