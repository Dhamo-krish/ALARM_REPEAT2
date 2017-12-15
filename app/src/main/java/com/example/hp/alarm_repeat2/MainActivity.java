package com.example.hp.alarm_repeat2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private Button button;

    EditText editText;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=(Button)findViewById(R.id.trigger);
        editText=(EditText) findViewById(R.id.input);
        alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        ContentResolver resolver=getContentResolver();
        String oldDefaultKeyboard = Settings.Secure.getString(resolver, Settings.Secure.DEFAULT_INPUT_METHOD);
        Toast.makeText(this, ""+oldDefaultKeyboard, Toast.LENGTH_SHORT).show();
        System.out.println("oldDefaultKeyboard="+oldDefaultKeyboard);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                       imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                       System.out.println("EditText Keyboard"+imm.getCurrentInputMethodSubtype());

            }
        });
//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                editText.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//                        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
//
////                        imm.getCurrentInputMethodSubtype();
////                        Toast.makeText(MainActivity.this, ""+imm.getCurrentInputMethodSubtype(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });

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
