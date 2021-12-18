package com.example.emergencyapplication.CovidWatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.emergencyapplication.R;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class CovidGuidelineActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private WebView webView;
    private ImageView btn_menu;
    private Button btn_logout;
    public static ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_guideline);

        //initialization of drawerLayout and RecyclerViews
        drawerLayout = findViewById(R.id.drawer_covid_layout);
        recyclerView = findViewById(R.id.covid_recycler_view);
        arrayList.clear();

        //setting the recyclerView to the current's activity layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new CovidAdapter(this, CovidDashboard.arrayList));

        //menu
        btn_menu = findViewById(R.id.bt_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //Used in introduction info for COVID
        String intro = getResources().getString(R.string.Intro_covid);

        //giving attributes of html in a string
        String webText = String.valueOf(Html.fromHtml(
                "<![CDATA]<body style=\"text-align:justify\">"
                        +intro + "</body>"
        ));

        //inatializing and setting the string value on webView
        webView = findViewById(R.id.wv_intro_covid);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.loadData(webText,"text/html;charset=utf-8","UTF-8");


        btn_logout = findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CovidGuidelineActivity.this);
                String authentication = sharedPreferences.getString("authentication", "");

                if(authentication.equals("facebook")){
                    FirebaseAuth.getInstance().signOut();
                    LoginManager.getInstance().logOut();
                    Toast.makeText(CovidGuidelineActivity.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(CovidGuidelineActivity.this, CovidWatcherActivity.class);
                    startActivity(intent);
                    Toast.makeText(CovidGuidelineActivity.this, "Logout successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}