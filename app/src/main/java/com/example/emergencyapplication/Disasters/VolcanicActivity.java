package com.example.emergencyapplication.Disasters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.emergencyapplication.Disasters.StormFragments.AfterFragment;
import com.example.emergencyapplication.Disasters.StormFragments.BeforeFragment;
import com.example.emergencyapplication.Disasters.StormFragments.DuringFragment;
import com.example.emergencyapplication.Disasters.StormFragments.StormAdapter;
import com.example.emergencyapplication.Disasters.VolcanicFragments.AfterVolcanicFragment;
import com.example.emergencyapplication.Disasters.VolcanicFragments.BeforeVolcanicFragment;
import com.example.emergencyapplication.Disasters.VolcanicFragments.DuringVolcanicFragment;
import com.example.emergencyapplication.Disasters.VolcanicFragments.VolcanicAdapter;
import com.example.emergencyapplication.DisastersActivity;
import com.example.emergencyapplication.R;
import com.google.android.material.tabs.TabLayout;

public class VolcanicActivity extends AppCompatActivity {


    WebView webView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volcanic);

        bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String intro = getResources().getString(R.string.Intro_volcanic);

        String webText = String.valueOf(Html.fromHtml(
                "<![CDATA]<body style=\"text-align:justify\">"
                        +intro + "</body>"
        ));
        webView = findViewById(R.id.wv_intro_volcanic);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(webText,"text/html;charset=utf-8","UTF-8");



        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);

        VolcanicAdapter volcanicAdapter = new VolcanicAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        volcanicAdapter.addFragment(new BeforeVolcanicFragment(),"Before");
        volcanicAdapter.addFragment(new DuringVolcanicFragment(), "During");
        volcanicAdapter.addFragment(new AfterVolcanicFragment(), "After");
        viewPager.setAdapter(volcanicAdapter);

    }
}