package com.android.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by stri0214 on 04.02.2016.
 */
public class PhotoServiceUpReciever extends BroadcastReceiver {

    private static final long time=600000;

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent iStartService = new Intent(context, PhotoService.class);
        context.startService(iStartService);

        Intent i = new Intent(context, PhotoServiceUpReciever.class);
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, i,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager =(AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),600000,pending);


    }
}
