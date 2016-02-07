package com.android.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PhotoService extends IntentService {

    private SurfaceHolder sHolder;
    private Camera mCamera;
    private boolean flag = false;

    public PhotoService(String name) {
        super(name);
    }

    public PhotoService() {
        super("PhotoService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        writePhoto();
    }

    SurfaceHolder.Callback sfHolder = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCamera.startPreview();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            /*try {
                //mCamera.setPreviewDisplay(holder);
               // mCamera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
            flag = true;*/
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    };

    private void writePhoto() {
        int cameraID = findFrontCamera();
        safeCameraOpen(cameraID);
        SurfaceView sv = new SurfaceView(this);
        sHolder = sv.getHolder();
        sHolder.addCallback(sfHolder);
        mCamera.takePicture(null, null, photoHandler);
    }


    private int findFrontCamera() {
        int cameraID = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraID = i;
                break;
            }
        }
        return cameraID;
    }

    Camera.PictureCallback photoHandler = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File file = new File(getApplicationContext().getFilesDir(), generationFileName());
            try {
                mCamera.startPreview();
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String generationFileName() {
            String fileName = new SimpleDateFormat("yyyyMMddhhmm'.jpg'").format(new Date());
            return fileName;
        }
    };


    private void safeCameraOpen(int id) {
        try {
            releaseCameraAndPreview();
            mCamera = Camera.open(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void releaseCameraAndPreview() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }


}
