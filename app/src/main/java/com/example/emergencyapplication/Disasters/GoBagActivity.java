package com.example.emergencyapplication.Disasters;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.emergencyapplication.Disasters.GoBagFragments.AlertReceiverGoBag;
import com.example.emergencyapplication.Disasters.GoBagFragments.ContentsFragment;
import com.example.emergencyapplication.Disasters.GoBagFragments.GoBagAdapter;
import com.example.emergencyapplication.Disasters.GoBagFragments.RemindersFragment;
import com.example.emergencyapplication.Disasters.StormFragments.AfterFragment;
import com.example.emergencyapplication.Disasters.StormFragments.BeforeFragment;
import com.example.emergencyapplication.Disasters.StormFragments.DuringFragment;
import com.example.emergencyapplication.Disasters.StormFragments.StormAdapter;
import com.example.emergencyapplication.DisastersActivity;
import com.example.emergencyapplication.R;
import com.example.emergencyapplication.databinding.ActivityGoBagBinding;
import com.example.emergencyapplication.databinding.ActivityMainBinding;
import com.google.android.material.tabs.TabLayout;

import java.util.Calendar;
import java.util.TimeZone;

public class GoBagActivity extends AppCompatActivity {
//
//    WebView webView;
//    private TabLayout tabLayout;
//    private ViewPager viewPager;
    private ActivityGoBagBinding binding;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

//    ImageView bt_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGoBagBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        binding.btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });

        String intro = getResources().getString(R.string.Intro_goBag);

        String webText = String.valueOf(Html.fromHtml(
                "<![CDATA]<body style=\"text-align:justify\">"
                        +intro + "</body>"
        ));

        binding.wvIntro.setBackgroundColor(Color.TRANSPARENT);
        binding.wvIntro.loadData(webText,"text/html;charset=utf-8","UTF-8");


        binding.btnNotify.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {


                    showTime();
            }
        });

//        tabLayout = findViewById(R.id.tab_layout);
//        viewPager = findViewById(R.id.view_pager);
//        tabLayout.setupWithViewPager(viewPager);

        binding.tabLayout.setupWithViewPager(binding.viewPager);

        GoBagAdapter goBagAdapter = new GoBagAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        goBagAdapter.addFragment(new RemindersFragment(),"Reminders");
        goBagAdapter.addFragment(new ContentsFragment(), "Contents");
        binding.viewPager.setAdapter(goBagAdapter);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showTime() {

//        Calendar calendar = Calendar.getInstance();
//        int currentMonth = calendar.get(Calendar.MONTH);
//
//        currentMonth = currentMonth+6;
//
//        Log.d("TEST4", String.valueOf(currentMonth > Calendar.DECEMBER));
//        if(currentMonth > Calendar.DECEMBER){
//            currentMonth = Calendar.JANUARY;
//            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR)+1);
//        }
//
//        Log.d("TEST5", ""+currentMonth);
//
//        calendar.set(Calendar.MONTH, currentMonth);
//        Log.d("TEST6", String.valueOf(Calendar.MONTH));
//
//        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//        calendar.set(Calendar.DAY_OF_MONTH, maxDay);
//        long sixthMonths = calendar.getTimeInMillis();

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        int addMonth = 4 ;

        Log.d("TEST4", "Month: "+month);

        for (int i = 0; i < addMonth; i++){

            month++;

            if(month >= 11){
                int remainingMonths = month - 11;
                calendar.set(Calendar.MONTH, remainingMonths);
                Log.d("TEST6", "Month Before Alert: "+remainingMonths);
            }
            else{
                calendar.set(Calendar.MONTH, month);
                Log.d("TEST5", "Month Before Alert: "+month);
            }

        }

         if (month >= 11){
             year++;
             calendar.set(Calendar.YEAR, year);
         }
        Log.d("TEST7", "Month Before Alert: "+calendar.get(Calendar.MONTH)+" Year: "+year);

//        calendar.set(Calendar.MONTH, 0);
//        calendar.set(Calendar.YEAR, 2022);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);

        Log.d("TEST7", "Month Before Alert: "+calendar.get(Calendar.MONTH)+" Year: "+calendar.get(Calendar.YEAR));

        Long calendarLong = calendar.getTimeInMillis();

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(GoBagActivity.this, AlertReceiverGoBag.class);

        pendingIntent = PendingIntent.getBroadcast(GoBagActivity.this, 0, intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarLong, pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarLong, pendingIntent);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarLong, pendingIntent);
        }
        else{
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendarLong, pendingIntent);
        }


        Log.d("Month", ""+calendarLong);
        Toast.makeText(GoBagActivity.this, "You will be notified in month before the expiration of contents", Toast.LENGTH_LONG).show();
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            CharSequence name = "gobagnotifChannel";
            String description = "Your GO BAG is almost out to date!";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("gobagnotif", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}