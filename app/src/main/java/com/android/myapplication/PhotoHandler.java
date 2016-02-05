package com.android.myapplication;

import android.content.Context;
import android.hardware.Camera;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoHandler implements Camera.PictureCallback{

    private String file;

    private final Context context;

    public PhotoHandler(Context context) {
        this.context = context;
    }


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        File file=new File(context.getFilesDir(),generationFileName());
        try {
            //camera.startPreview();
            FileOutputStream fos=new FileOutputStream(file);
            fos.write(data);
            fos.close();
            camera.stopPreview();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String generationFileName() {

        String fileName=new SimpleDateFormat("yyyyMMddhhmm'.jpg'").format(new Date());

        return fileName;
    }
}
