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

import com.example.emergencyapplication.Disasters.EarthquakeFragments.AfterEarthquakeFragments;
import com.example.emergencyapplication.Disasters.EarthquakeFragments.BeforeEarthquakeFragments;
import com.example.emergencyapplication.Disasters.EarthquakeFragments.DuringEarthquakeFragments;
import com.example.emergencyapplication.Disasters.EarthquakeFragments.EarthquakeAdapter;
import com.example.emergencyapplication.Disasters.StormSurgeFragments.AfterStormSurgeFragment;
import com.example.emergencyapplication.Disasters.StormSurgeFragments.BeforeStormSurgeFragment;
import com.example.emergencyapplication.Disasters.StormSurgeFragments.DuringStormSurgeFragment;
import com.example.emergencyapplication.Disasters.StormSurgeFragments.StormSurgeAdapter;
import com.example.emergencyapplication.DisastersActivity;
import com.example.emergencyapplication.R;
import com.google.android.material.tabs.TabLayout;

public class EarthquakeActivity extends AppCompatActivity {

    WebView webView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        ImageView bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        String intro = getResources().getString(R.string.Intro_earthquake);


        String webText = String.valueOf(Html.fromHtml(
                "<![CDATA]<body style=\"text-align:justify\">"
                        +intro + "</body>"
        ));

        webView = findViewById(R.id.wv_intro_earthquake);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(webText,"text/html;charset=utf-8","UTF-8");

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);
        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        earthquakeAdapter.addFragment(new BeforeEarthquakeFragments(),"Before");
        earthquakeAdapter.addFragment(new DuringEarthquakeFragments(), "During");
        earthquakeAdapter.addFragment(new AfterEarthquakeFragments(), "After");
        viewPager.setAdapter(earthquakeAdapter);
    }
}