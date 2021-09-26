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

import com.example.emergencyapplication.Disasters.TsunamiFragments.AfterTsunamiFragment;
import com.example.emergencyapplication.Disasters.TsunamiFragments.BeforeTsunamiFragment;
import com.example.emergencyapplication.Disasters.TsunamiFragments.DuringTsunamiFragment;
import com.example.emergencyapplication.Disasters.TsunamiFragments.TsunamiAdapter;
import com.example.emergencyapplication.DisastersActivity;
import com.example.emergencyapplication.R;
import com.google.android.material.tabs.TabLayout;

public class TsunamiActivity extends AppCompatActivity {

    WebView webView;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    ImageView bt_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tsunami);

        bt_back = findViewById(R.id.bt_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TsunamiActivity.this, DisastersActivity.class);
                startActivity(intent);
            }
        });

        String intro = getResources().getString(R.string.Intro_landslide);

        String webText = String.valueOf(Html.fromHtml(
                "<![CDATA]<body style=\"text-align:justify\">"
                        +intro + "</body>"
        ));
        webView = findViewById(R.id.wv_intro_landslide);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(webText,"text/html;charset=utf-8","UTF-8");



        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        tabLayout.setupWithViewPager(viewPager);

        TsunamiAdapter tsunamiAdapter = new TsunamiAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tsunamiAdapter.addFragment(new BeforeTsunamiFragment(),"Before");
        tsunamiAdapter.addFragment(new DuringTsunamiFragment(), "During");
        tsunamiAdapter.addFragment(new AfterTsunamiFragment(), "After");
        viewPager.setAdapter(tsunamiAdapter);
    }
}