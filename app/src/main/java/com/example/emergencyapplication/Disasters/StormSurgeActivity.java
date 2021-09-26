package com.example.emergencyapplication.Disasters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.emergencyapplication.Disasters.StormFragments.AfterFragment;
import com.example.emergencyapplication.Disasters.StormFragments.BeforeFragment;
import com.example.emergencyapplication.Disasters.StormFragments.DuringFragment;
import com.example.emergencyapplication.Disasters.StormFragments.StormAdapter;
import com.example.emergencyapplication.Disasters.StormSurgeFragments.AfterStormSurgeFragment;
import com.example.emergencyapplication.Disasters.StormSurgeFragments.BeforeStormSurgeFragment;
import com.example.emergencyapplication.Disasters.StormSurgeFragments.DuringStormSurgeFragment;
import com.example.emergencyapplication.Disasters.StormSurgeFragments.StormSurgeAdapter;
import com.example.emergencyapplication.DisastersActivity;
import com.example.emergencyapplication.R;
import com.google.android.material.tabs.TabLayout;

public class StormSurgeActivity extends AppCompatActivity {

    WebView webView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    ImageView bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storm_surge);

        bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StormSurgeActivity.this, DisastersActivity.class);
                startActivity(intent);
            }
        });


        String intro = getResources().getString(R.string.Intro_storm_surge);


        String webText = String.valueOf(Html.fromHtml(
                "<![CDATA]<body style=\"text-align:justify\">"
                        +intro + "</body>"
        ));

        webView = findViewById(R.id.wv_intro_storm_surge);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(webText,"text/html;charset=utf-8","UTF-8");


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);
        StormSurgeAdapter stormSurgeAdapter = new StormSurgeAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        stormSurgeAdapter.addFragment(new BeforeStormSurgeFragment(),"Before");
        stormSurgeAdapter.addFragment(new DuringStormSurgeFragment(), "During");
        stormSurgeAdapter.addFragment(new AfterStormSurgeFragment(), "After");
        viewPager.setAdapter(stormSurgeAdapter);
    }
}