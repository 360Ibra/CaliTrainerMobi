package com.example.calitrainermobi;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;




import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;

import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ResourceUtils;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mmdeploy.Context;
import mmdeploy.Device;
import mmdeploy.Model;
import mmdeploy.PoseTracker;

public class MainActivity2 extends CameraActivity implements CameraBridgeViewBase.CvCameraViewListener2  {

    CameraBridgeViewBase cameraBridgeViewBase;
    private PoseTracker poseTracker;

    private long stateHandle;


    static {
        System.loadLibrary("opencv_java4");
    }

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        stateHandle = initMMDeploy();
        setContentView(R.layout.activity_main_2);
        getPermission();

        cameraBridgeViewBase = findViewById(R.id.cameraView);
        cameraBridgeViewBase.setCvCameraViewListener(this);
        if (OpenCVLoader.initDebug()){
            cameraBridgeViewBase.enableView();
        }

    }

    private long initPoseTracker(String workDir)
    {
        String detModelPath=workDir + "/rtmdet-nano-ncnn-fp16";
        String poseModelPath=workDir + "/rtmpose-tiny-ncnn-fp16";
        Log.d("DETMODELPATH" ,detModelPath);
        Log.d("posemodelpath" ,poseModelPath);


        String deviceName="cpu";
        int deviceID = 0;
        Model detModel = new Model(detModelPath);
        Model poseModel = new Model(poseModelPath);
        Device device = new Device(deviceName, deviceID);
        Context context = new Context();
        context.add(device);
        this.poseTracker = new mmdeploy.PoseTracker(detModel, poseModel, context);
        mmdeploy.PoseTracker.Params params = this.poseTracker.initParams();
        params.detInterval = 3 ;
        params.poseMaxNumBboxes = 1;
        long stateHandle = this.poseTracker.createState(params);
        return stateHandle;
    }


    private long initMMDeploy() {
        String workDir = PathUtils.getExternalAppFilesPath() + File.separator
                + "file";
        if (ResourceUtils.copyFileFromAssets("models", workDir)){
            return initPoseTracker(workDir);
        }
        return -1;
    }


    @Override
    protected List<? extends CameraBridgeViewBase> getCameraViewList() {
        return Collections.singletonList(cameraBridgeViewBase);
    }

    void getPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        }
    }
}

@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        if(grantResults.length > 0 && grantResults[0]!= PackageManager.PERMISSION_GRANTED){
            getPermission();
        }
}

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        Mat frame = inputFrame.rgba();

        org.opencv.core.Mat cvMat = new org.opencv.core.Mat();
        Imgproc.cvtColor(frame, cvMat,Imgproc.COLOR_RGB2BGR);
        mmdeploy.Mat mat = Utils.cvMatToMat(cvMat);

        mmdeploy.PoseTracker.Result[] results = poseTracker.apply(stateHandle, mat, -1);
        DrawSkeleton_.drawPoseTrackerResult(frame,results);

        return frame;
    }
}