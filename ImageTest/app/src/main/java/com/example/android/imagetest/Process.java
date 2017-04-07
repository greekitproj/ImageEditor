package com.example.android.imagetest;

import android.graphics.Bitmap;
import android.net.Uri;
import android.graphics.Canvas;

import jp.co.cyberagent.android.gpuimage.GPUImageView;

/**
 * Created by Stevens on 4/4/2017.
 */

public class Process {
    public static void showImage(GPUImageView gpuImageView, final Uri selectedImage) {
        gpuImageView.setImage(selectedImage);
    }

}
