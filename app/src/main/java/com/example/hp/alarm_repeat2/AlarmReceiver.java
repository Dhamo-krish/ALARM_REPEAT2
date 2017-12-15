package com.example.hp.alarm_repeat2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;

/**
 * Created by HP on 11/10/2017.
 */

public class AlarmReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
     Intent intent2=new Intent(context,AlarmFunction.class);
        intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
     context.startActivity(intent2);
//        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Intent notificationIntent = new Intent(context, MainActivity.class);
//        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        NotificationCompat.Builder mNotifyBuilder = (NotificationCompat.Builder) new NotificationCompat
//                .Builder(context)
//                .setSmallIcon(R.drawable.ic_launcher_background)
//                .setWhen(System.currentTimeMillis())
//                .setContentTitle("Notification")
//                .setContentText("Alarm is trigger !")
//                .setWhen(System.currentTimeMillis())
//                .setContentIntent(pendingIntent);
//
//        notificationManager.notify(1, mNotifyBuilder.build());

    }
}
