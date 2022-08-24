package com.example.melanoma.Presenter.Deteksi;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.melanoma.Model.modelDeteksi;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;

public class DeteksiPresenter implements DeteksiPresenterInterface {
    private GpuDelegate gpuDelegate;
    private Interpreter interpreter;
    private modelDeteksi modelDeteksi;

    public DeteksiPresenter(AssetManager assetManager, String modelPath,int inputsize) throws IOException {
        modelDeteksi = new modelDeteksi(inputsize);
        Interpreter.Options options = new Interpreter.Options();
        gpuDelegate = new GpuDelegate();
        options.addDelegate(gpuDelegate);
        options.setNumThreads(4);
        interpreter = new Interpreter(loadModelFile(assetManager,modelPath),options);

    }

    @Override
    public MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException{
        AssetFileDescriptor assetFileDescriptor=assetManager.openFd(modelPath);
        FileInputStream inputStream=new FileInputStream(assetFileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startOffSet=assetFileDescriptor.getStartOffset();
        long declaredLength=assetFileDescriptor.getLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffSet,declaredLength);
    }

    @Override
    public Mat recognizeImage(Mat mat_image) {

        //karena defaultnya landscape hrs di puter jadi portrait
        Mat rotate_mat_image = new Mat();
        Core.flip(mat_image.t(),rotate_mat_image,1);

        //define height + width dari original bitmap
        modelDeteksi.setHeight(rotate_mat_image.height());
        modelDeteksi.setWidth(rotate_mat_image.width());

        //draw kotak 500x500
        Rect roi_cropped = new Rect((modelDeteksi.getWidth()-500)/2,(modelDeteksi.getHeight()-500)/2,500,500);

        Mat cropped_image = new Mat(rotate_mat_image,roi_cropped);



        //convert Mat -> bitmap
        Bitmap bitmap = null;
        bitmap = Bitmap.createBitmap(cropped_image.cols(),cropped_image.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(cropped_image,bitmap);



        //resize bitmap image to input size of model
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, modelDeteksi.getINPUT_SIZE(), modelDeteksi.getINPUT_SIZE(),false);

        //convert scaledBitmap to ByteBuffer
        ByteBuffer byteBuffer = createBitmapToByteBuffer(scaledBitmap);

        //create an input and output for interpreter
        float[][] output = new float[1][1];

        //now pass it to interpreter
        interpreter.run(byteBuffer,output);

        //check value output
//        Log.d("DeteksiPresenter","Out "+ Arrays.deepToString(output));

        //extract value from output
        float val_prediction = (float) Array.get(Array.get(output,0),0);

        //set threshold
        if (val_prediction>0.4){
            //put text melanoma terdeteksi
            Imgproc.rectangle(rotate_mat_image,new Point((modelDeteksi.getWidth()-500)/2,(modelDeteksi.getHeight()-500)/2),new Point((modelDeteksi.getWidth()+500)/2,(modelDeteksi.getHeight()+500)/2), modelDeteksi.getRed(),2);
            Imgproc.putText(rotate_mat_image,"Terdapat Kemungkinan" ,new Point(((modelDeteksi.getWidth()-380)/2),45),3,1, modelDeteksi.getRed(),2);
            Imgproc.putText(rotate_mat_image,"Kanker Melanoma" ,new Point(((modelDeteksi.getWidth()-300)/2),85),3,1, modelDeteksi.getRed(),2);
            modelDeteksi.setTextHasil("Terdapat Kemungkinan Kanker Melanoma");
        }
        else {
            Imgproc.rectangle(rotate_mat_image,new Point((modelDeteksi.getWidth()-500)/2,(modelDeteksi.getHeight()-500)/2),new Point((modelDeteksi.getWidth()+500)/2,(modelDeteksi.getHeight()+500)/2), modelDeteksi.getGreen(),2);
            Imgproc.putText(rotate_mat_image,"Tidak Terdapat Kemungkinan" ,new Point(((modelDeteksi.getWidth()-400)/2-40),45),3,1, modelDeteksi.getGreen(),2);
            Imgproc.putText(rotate_mat_image,"Kanker Melanoma" ,new Point(((modelDeteksi.getWidth()-300)/2),85),3,1, modelDeteksi.getGreen(),2);
            modelDeteksi.setTextHasil("Tidak Terdapat Kemungkinan Kanker Melanoma");
        }


        //return to landscape again
        Core.flip(rotate_mat_image.t(),rotate_mat_image,0);
        return rotate_mat_image;
    }

    @Override
    public ByteBuffer createBitmapToByteBuffer(Bitmap scaledBitmap) {
        ByteBuffer byteBuffer;
        byteBuffer = ByteBuffer.allocateDirect(4* modelDeteksi.getINPUT_SIZE()* modelDeteksi.getINPUT_SIZE()* modelDeteksi.getPIXEL_SIZE());
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[modelDeteksi.getINPUT_SIZE()* modelDeteksi.getINPUT_SIZE()];

        scaledBitmap.getPixels(intValues,0,scaledBitmap.getWidth(),0,0,scaledBitmap.getWidth(),scaledBitmap.getHeight());

        int pixel = 0;
        for (int i = 0; i < modelDeteksi.getINPUT_SIZE(); ++i) {
            for (int j = 0; j < modelDeteksi.getINPUT_SIZE(); ++j) {
                final int val = intValues[pixel++];
                //set value of byte buffer
                //image_mean and image_std is use to convert image pixel from 0-255 to 0-1 or 0-255 to -1 to 1
                byteBuffer.putFloat((((val>>16)& 0xFF)- modelDeteksi.getIMAGE_MEAN())/ modelDeteksi.getIMAGE_STD());//red
                byteBuffer.putFloat((((val>>8)& 0xFF)- modelDeteksi.getIMAGE_MEAN())/ modelDeteksi.getIMAGE_STD());//green
                byteBuffer.putFloat((((val)& 0xFF)- modelDeteksi.getIMAGE_MEAN())/ modelDeteksi.getIMAGE_STD());//blue
            }
        }
        return byteBuffer;
    }

    @Override
    public String hasilDeteksi() {
        return modelDeteksi.getTextHasil();
    }


}
