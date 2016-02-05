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

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i=new Intent(context,PhotoService.class);
        context.startService(i);
    }
}
