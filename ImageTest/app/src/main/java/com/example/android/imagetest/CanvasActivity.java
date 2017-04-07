package com.example.android.imagetest;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;


public class CanvasActivity extends AppCompatActivity {

    private CanvasView canvasView;
    private EditText editText;
    private Uri uri;

    //private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        editText = (EditText) findViewById(R.id.editText);

        Log.w("CanvasActivity","onCreate");

        Intent intent = getIntent();
        uri = (Uri) intent.getExtras().get("URI");

        canvasView = (CanvasView)findViewById(R.id.canvasView);
        canvasView.setImageURI(uri);

        Log.w("CanvasActivity","after set canvasview");
        Bitmap picBitmap = ((BitmapDrawable)canvasView.getDrawable()).getBitmap();

        Log.w("CanvasActivity","before createBitmap text");

        editText.setDrawingCacheEnabled(true);
        editText.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        editText.layout(0, 0, editText.getMeasuredWidth(), editText.getMeasuredHeight());

        editText.setCursorVisible(false);
        editText.buildDrawingCache();
        //ImageView img = (ImageView)findViewById(R.id.editText);
        //img.setImageBitmap(editText.getDrawingCache());

        //Bitmap textBitmap = Bitmap.createBitmap(editText.getDrawingCache());

        Bitmap textBitmap = Bitmap.createBitmap(editText.getDrawingCache());

        Log.w("CanvasActivity","after createBitmap text");
        Bitmap combinedBitmap = combineImages(picBitmap,textBitmap);

        FileOutputStream out = null;
        try {
            out = new FileOutputStream("TEST_SAVE_BMP");
       //     combinedBitmap.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored

            Log.w("CanvasActivity","TEST_SAVE_BMP");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }





    public Bitmap combineImages(Bitmap background, Bitmap foreground) {

        int width = 0, height = 0;
        Bitmap cs;

        width = getWindowManager().getDefaultDisplay().getWidth();
        height = getWindowManager().getDefaultDisplay().getHeight();

        cs = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas comboImage = new Canvas(cs);
        background = Bitmap.createScaledBitmap(background, width, height, true);
        comboImage.drawBitmap(background, 0, 0, null);
        comboImage.drawBitmap(foreground, 5,2, null);

        return cs;
    }
}
