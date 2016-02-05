package com.android.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

/**
 * Created by stri0214 on 04.02.2016.
 */
public class MainActivity extends Activity {

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent myIntent = new Intent(this, PhotoServiceUpReciever.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 789987, myIntent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis(), 120000 ,pendingIntent);

    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
