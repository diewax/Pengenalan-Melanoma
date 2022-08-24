package com.example.melanoma.View.Deteksi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.util.BuddhistCalendar;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.melanoma.Presenter.Deteksi.DeteksiPresenter;
import com.example.melanoma.R;
import com.example.melanoma.View.HasilTes.HasilTes;
import com.example.melanoma.View.Melanoma.WikiMelanoma;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DeteksiMelanoma extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {
    private static final String TAG="HomeActivity";

    private Mat mRgba;
    private Mat mGray;
    private CameraBridgeViewBase mOpenCvCameraView;
    private DeteksiPresenter deteksiPresenter;
    private Button buttonHasil;
    private String hasilDeteksi,hasilABCDE;

    private static final String[] REQUIRED_PERMISSION_LIST = new String[]{
            Manifest.permission.CAMERA
    };
    private static final int REQUEST_CODE = 1;
    private List<String> mMissPermissions = new ArrayList<>();



    private BaseLoaderCallback mLoaderCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface
                        .SUCCESS:{
                    Log.i(TAG,"Open CV is loaded");

                    mOpenCvCameraView.enableView();
                }
                default:{

                    super.onManagerConnected(status);
                }
                break;
            }
        }
    };

    public DeteksiMelanoma(){
        Log.i(TAG,"Instantiated new "+this.getClass());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deteksi_melanoma);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        hasilABCDE = getIntent().getStringExtra("Hasil_ABCDE");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Deteksi Melanoma");

        buttonHasil = findViewById(R.id.hasiltes_button);
        mOpenCvCameraView=(CameraBridgeViewBase) findViewById(R.id.camera_frame);
        checkAndRequestPermissions();




        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
//        mOpenCvCameraView.setMaxFrameSize(720, 480);
        mOpenCvCameraView.setCvCameraViewListener(this);

        try{
            int inputSize = 256;
            deteksiPresenter = new DeteksiPresenter(getAssets(),"model.tflite",inputSize);
            Log.d("DeteksiMelanoma","model is loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
        buttonHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent activityChangeIntent = new Intent(getApplicationContext(), HasilTes.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                Bundle bundlehasil = new Bundle();
                bundlehasil.putString("Hasil_Deteksi",hasilDeteksi);
                bundlehasil.putString("Hasil_ABCDE",hasilABCDE);

                activityChangeIntent.putExtras(bundlehasil);
                startActivity(activityChangeIntent);
            }
        });

    }

    private void checkAndRequestPermissions() {
        mMissPermissions.clear();
        for (String permission : REQUIRED_PERMISSION_LIST) {
            int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                mMissPermissions.add(permission);
            }
        }
        // check permissions has granted
        if (mMissPermissions.isEmpty()) {
            mOpenCvCameraView.setCameraPermissionGranted();
        } else {
            ActivityCompat.requestPermissions(this,
                    mMissPermissions.toArray(new String[mMissPermissions.size()]),
                    REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            for (int i = grantResults.length - 1; i >= 0; i--) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    mMissPermissions.remove(permissions[i]);
                }
            }
        }
        // Get permissions success or not
        if (mMissPermissions.isEmpty()) {
            mOpenCvCameraView.setCameraPermissionGranted();
        } else {
            Log.e("DeteksiMelanoma","get permissions failed");
//            Toast.makeText(MicroCameraActivity.this, "get permissions failed,exiting...",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            //if load success
            Log.d(TAG,"OpenCV initialization done~");
            mLoaderCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else {
            //if not loaded
            Log.d(TAG,"OpenCV is not loaded. try again");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION,this, mLoaderCallBack);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOpenCvCameraView != null){
            mOpenCvCameraView.disableView();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mOpenCvCameraView != null){
            mOpenCvCameraView.disableView();
        }
    }
    @Override
    public void onCameraViewStarted(int width ,int height){
        mRgba = new Mat(height, width, CvType.CV_8UC4);
        mGray = new Mat(height, width, CvType.CV_8UC1);
    }
    @Override
    public void onCameraViewStopped(){
        mRgba.release();
    }
    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        mRgba=inputFrame.rgba();
        mGray=inputFrame.gray();

        mRgba = deteksiPresenter.recognizeImage(mRgba);
        hasilDeteksi = deteksiPresenter.hasilDeteksi();

        return mRgba;

    }
}
