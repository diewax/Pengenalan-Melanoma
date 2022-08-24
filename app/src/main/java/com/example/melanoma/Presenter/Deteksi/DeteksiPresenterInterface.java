package com.example.melanoma.Presenter.Deteksi;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

import org.opencv.core.Mat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;

public interface DeteksiPresenterInterface {
    MappedByteBuffer loadModelFile(AssetManager assetManager,String modelPath)throws IOException;

    Mat recognizeImage(Mat mat_image);

    ByteBuffer createBitmapToByteBuffer(Bitmap scaledBitmap);

    String hasilDeteksi();
}
