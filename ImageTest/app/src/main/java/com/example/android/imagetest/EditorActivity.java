package com.example.android.imagetest;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilterGroup;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageView;

public class EditorActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{

    private GPUImageView gpuImageView;
    private Button sepiaButton, textButton, drawButton;
    private Uri uri;
    GPUImageFilterGroup filterGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        sepiaButton = (Button) findViewById(R.id.sepiaButton);
        textButton = (Button) findViewById(R.id.textButton);
        drawButton = (Button) findViewById(R.id.drawButton);

        filterGroup = new GPUImageFilterGroup();

        Intent intent = getIntent();
        uri = (Uri) intent.getExtras().get("URI");
        gpuImageView = (GPUImageView) findViewById(R.id.gpuImageView);
        gpuImageView.setScaleType(GPUImage.ScaleType.CENTER_INSIDE);
        Process.showImage(gpuImageView,uri);

        View.OnClickListener sepiaButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri!=null){
                    filterGroup.addFilter(new GPUImageSepiaFilter());

                    gpuImageView.setFilter(filterGroup);
                    Log.w("EditorActivity","SepiaFilter");
                }
                else {
                    Toast.makeText(EditorActivity.this, "Select an image from the Gallery", Toast.LENGTH_LONG).show();
                }
            }
        };
        sepiaButton.setOnClickListener(sepiaButtonListener);

        View.OnClickListener textButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uri!=null){

                    Intent intent = new Intent(EditorActivity.this, CanvasActivity.class);

                    intent.putExtra(new String("URI"), uri);
                    startActivity(intent);

                    Log.w("EditorActivity","drawButtonListener");
                }
                else {
                    Toast.makeText(EditorActivity.this, "error on draw button", Toast.LENGTH_LONG).show();
                }
            }
        };
        textButton.setOnClickListener(textButtonListener);


    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
