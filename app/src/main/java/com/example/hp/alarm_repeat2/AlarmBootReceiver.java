package com.example.hp.alarm_repeat2;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by HP on 11/10/2017.
 */

public class AlarmBootReceiver extends BroadcastReceiver
{
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    Calendar calendar;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "Alarm Still works", Toast.LENGTH_SHORT).show();
//        ComponentName receiver = new ComponentName(context, AlarmBootReceiver.class);
//        PackageManager pm = context.getPackageManager();
//
//        pm.setComponentEnabledSetting(receiver,
//                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
//                PackageManager.DONT_KILL_APP);

//        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
//        {
//            Toast.makeText(context.getApplicationContext(), "BOW", Toast.LENGTH_SHORT).show();

            alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent intent1 = new Intent(context, AlarmReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(context, 0, intent1, 0);
        Calendar firingCal= Calendar.getInstance();
        Calendar currentCal = Calendar.getInstance();

        firingCal.set(Calendar.HOUR, 6); // At the hour you wanna fire
        firingCal.set(Calendar.MINUTE, 0); // Particular minute
        firingCal.set(Calendar.SECOND, 0);
        firingCal.set(Calendar.AM_PM,0);// particular second

        long intendedTime = firingCal.getTimeInMillis();
        long currentTime = currentCal.getTimeInMillis();
        if(intendedTime >= currentTime)
        {
            System.out.print("bow done"+intendedTime);
            alarmMgr.setRepeating(AlarmManager.RTC, (intendedTime-currentTime), AlarmManager.INTERVAL_DAY, alarmIntent);
        } else{

            firingCal.add(Calendar.DAY_OF_MONTH, 1);
            intendedTime = firingCal.getTimeInMillis();

            alarmMgr.setRepeating(AlarmManager.RTC, (intendedTime-currentTime), AlarmManager.INTERVAL_DAY, alarmIntent);
        }
        //}
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mNotifyBuilder = (NotificationCompat.Builder) new NotificationCompat
                .Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Notification")
                .setContentText("Alarm has been sheduled After BOOT COMPLETED! BOW !")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent);

        notificationManager.notify(1, mNotifyBuilder.build());

    }
}
