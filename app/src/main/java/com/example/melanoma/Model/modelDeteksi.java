package com.example.melanoma.Model;

import android.content.res.AssetManager;

import org.opencv.core.Scalar;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.IOException;

public class modelDeteksi {

    private int INPUT_SIZE;
    private int PIXEL_SIZE=3; //for rgb
    private int IMAGE_MEAN=0;
    private float IMAGE_STD=255.0f; //scale image 0-255 -> 0-1

    private int height=0;
    private int width=0;

    private Scalar red=new Scalar(255, 0 ,0, 50);
    private Scalar green=new Scalar(0, 255, 0, 50);

    private String textHasil;

    public modelDeteksi(int inputsize){
        INPUT_SIZE = inputsize;
    }

    public int getINPUT_SIZE() {
        return INPUT_SIZE;
    }

    public void setINPUT_SIZE(int INPUT_SIZE) {
        this.INPUT_SIZE = INPUT_SIZE;
    }

    public int getPIXEL_SIZE() {
        return PIXEL_SIZE;
    }

    public void setPIXEL_SIZE(int PIXEL_SIZE) {
        this.PIXEL_SIZE = PIXEL_SIZE;
    }

    public int getIMAGE_MEAN() {
        return IMAGE_MEAN;
    }

    public void setIMAGE_MEAN(int IMAGE_MEAN) {
        this.IMAGE_MEAN = IMAGE_MEAN;
    }

    public float getIMAGE_STD() {
        return IMAGE_STD;
    }

    public void setIMAGE_STD(float IMAGE_STD) {
        this.IMAGE_STD = IMAGE_STD;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width= width;
    }

    public Scalar getRed() {
        return red;
    }

    public Scalar getGreen() {
        return green;
    }

    public String getTextHasil() {
        return textHasil;
    }

    public void setTextHasil(String textHasil) {
        this.textHasil = textHasil;
    }
}
