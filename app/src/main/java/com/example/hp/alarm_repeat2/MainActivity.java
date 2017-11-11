package com.example.hp.alarm_repeat2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Button button;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.trigger);

        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
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
                Toast.makeText(MainActivity.this, "Alarm scheduled "+(intendedTime-currentTime) , Toast.LENGTH_SHORT).show();
    System.out.println("Time remaining:"+(intendedTime-currentTime)+firingCal.getTime());
                }
        });

    }
}
