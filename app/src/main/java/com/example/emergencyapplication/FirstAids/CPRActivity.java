package com.example.emergencyapplication.FirstAids;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.emergencyapplication.FirstAidActivity;
import com.example.emergencyapplication.R;

public class CPRActivity extends AppCompatActivity {
    WebView webView;
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_p_r);

        btn_back = findViewById(R.id.bt_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CPRActivity.this, FirstAidActivity.class);
                startActivity(intent);
            }
        });

        String intro = getResources().getString(R.string.Intro_cpr);

        String webText = String.valueOf(Html.fromHtml(
                "<![CDATA]<body style=\"text-align:justify\">"
                        +intro + "</body>"
        ));
        webView = findViewById(R.id.wv_intro_cpr);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(webText,"text/html;charset=utf-8","UTF-8");
    }
}