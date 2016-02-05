package com.android.myapplication;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.hardware.Camera;
import android.os.IBinder;
import android.view.SurfaceView;

import java.io.File;
import java.io.IOException;
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

    public PhotoService(){
        super("PhotoService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        writePhoto();

    }

    Camera camera;

    private void writePhoto() {

        int cameraID=findFrontCamera();
        safeCameraOpen(cameraID);

        PhotoHandler photoHandler=new PhotoHandler(getApplicationContext());
        try {
            camera.reconnect();
            SurfaceView mview = new SurfaceView(getBaseContext());
            camera.setPreviewDisplay(mview.getHolder());
            camera.startPreview();
            camera.takePicture(null, null, photoHandler);
            Thread.sleep(3000);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    private int findFrontCamera(){
        int cameraID=-1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for(int i=0;i<numberOfCameras;i++){
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i,info);
            if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                cameraID=i;
                break;
            }
        }
        return cameraID;
    }


    private void safeCameraOpen(int id) {

        try {
            releaseCameraAndPreview();
            camera = Camera.open(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void releaseCameraAndPreview() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }


}
