package com.example.emergencyapplication.Disasters.GoBagFragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.emergencyapplication.Disasters.GoBagActivity;
import com.example.emergencyapplication.R;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class AlertReceiverGoBag extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent intentAlert = new Intent(context, GoBagActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentAlert, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "gobagnotif")
                .setSmallIcon(R.drawable.greenarcher3)
                .setContentTitle("Green Archers Emergency Application")
                .setContentText("Your GO BAG is almost out to date!")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123,builder.build());
    }
}
