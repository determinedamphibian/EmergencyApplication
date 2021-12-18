package com.example.emergencyapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

    public class AnimationMainActivity extends AppCompatActivity {
        private static int  SPLASH_SCREEN = 2000;

        ImageView iv_archer;
        TextView tv_archerTitle, tv_archerSubtitle;
        Animation topAnim, bottomAnim;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        iv_archer = (ImageView) findViewById(R.id.greenArcher);
        tv_archerTitle = (TextView) findViewById(R.id.tvArcherTitle);
        tv_archerSubtitle= (TextView) findViewById(R.id.tvArcherTitle2);

        //Animation
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        iv_archer.setAnimation(topAnim);
        tv_archerTitle.setAnimation(bottomAnim);
        tv_archerSubtitle.setAnimation(bottomAnim);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AnimationMainActivity.this, MainDashboardActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }

    }