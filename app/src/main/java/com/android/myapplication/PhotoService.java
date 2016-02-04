package com.android.myapplication;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PhotoService extends IntentService{

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PhotoService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        writePhoto();

    }

    private void writePhoto() {
        String fileName=generationFileName();

        File file=new File(getApplicationContext().getFilesDir(),fileName);


    }

    private String generationFileName() {
        String fileName=new SimpleDateFormat("yyyyMMddhhmm'.jgp'").format(new Date());

        return fileName;
    }


}
