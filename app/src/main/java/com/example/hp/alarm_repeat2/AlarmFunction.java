package com.example.hp.alarm_repeat2;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.util.List;

/**
 * Created by HP on 11/10/2017.
 */

public class AlarmFunction extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm);
        ActivityManager am = (ActivityManager)getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        List<ActivityManager.RunningAppProcessInfo> pids = am.getRunningAppProcesses();
        System.out.println("&&&&&&&&&  BEFORE  &&&&&&&&");
        for(int i=0;i<pids.size();i++)
        {
            ActivityManager.RunningAppProcessInfo info = pids.get(i);
            System.out.println("Process  "+i+" --> "+info.processName);

            if (info.processName.equalsIgnoreCase("com.example.subash.keylogger"))
            {
                System.out.println("Killing "+info.processName);
                Toast.makeText(this, "Killing", Toast.LENGTH_SHORT).show();
                Process.sendSignal(info.pid,Process.SIGNAL_KILL);
            }
        }
        System.out.println("&&&&&&&&&  AFTER  &&&&&&&&");
        for(int i=0;i<pids.size();i++)
        {
            ActivityManager.RunningAppProcessInfo info = pids.get(i);
            System.out.println("Process  "+i+" --> "+info.processName);

        }
        NotificationCompat.Builder mNotifyBuilder = (NotificationCompat.Builder) new NotificationCompat
                .Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Notification")
                .setContentText("Alarm is trigger at 6")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent);


        notificationManager.notify(1, mNotifyBuilder.build());
    }
}
