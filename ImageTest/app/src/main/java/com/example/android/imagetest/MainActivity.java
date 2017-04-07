package com.example.android.imagetest;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.opengl.GLSurfaceView;
import android.provider.ContactsContract;
import android.provider.OpenableColumns;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final int IMAGE_PICKER = 10;
    static final int IMAGE_EDITOR = 20;

    private Button browseButton;
    private Uri selectedImageUri;
    private ImageView selectedImageView;

    //private Button abc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        browseButton = (Button) findViewById(R.id.browseButton);
        selectedImageView = (ImageView) findViewById(R.id.selectedImageView);





        View.OnClickListener browseButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryPickerIntent = new Intent();
                galleryPickerIntent.setType("image/*");
                galleryPickerIntent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(galleryPickerIntent, "Select an Image"), IMAGE_PICKER);
            }
        };
        browseButton.setOnClickListener(browseButtonListener);
    }


    /* Handle the results */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case IMAGE_PICKER:
                    selectedImageUri = data.getData();//<>

                    if (selectedImageUri != null) {
                        Intent intent = new Intent(MainActivity.this, EditorActivity.class);

                        intent.putExtra(new String("URI"), selectedImageUri);
                        startActivity(intent);

                        Log.w("MainActivity",selectedImageUri.toString());
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Select an image from the Gallery", Toast.LENGTH_LONG).show();
                    }




//
//                    GPUImage mGPUImage = new GPUImage(MainActivity.this);
//                    mGPUImage.setGLSurfaceView((GLSurfaceView) findViewById(R.id.surfaceView));
//                    mGPUImage.setImage(selectedImageUri); // this loads image on the current thread, should be run in a thread
//                    mGPUImage.setFilter(new GPUImageSepiaFilter());
//
//                    // Later when image should be saved saved:
//                    mGPUImage.saveToPictures("GPUImage", "ImageWithFilter.jpg", null);
                    Log.w("MainActivity",selectedImageUri.toString());

                    selectedImageView.setImageURI(selectedImageUri);
                    Log.w("ImagePicker",selectedImageUri.toString());

//                    nameTextView.setText(getFileName(selectedImageUri));
//                    File f = new File(selectedImageUri.toString());
//
//                    String imageName = f.getName();
//                    nameTextView.setText(imageName);
                    break;

                case IMAGE_EDITOR:

                    /* Set the image! */
                    //Uri editedImageUri = data.getParcelableExtra(AdobeImageIntent.EXTRA_OUTPUT_URI);
                    //mSelectedImageView.setImageURI(editedImageUri);
                    Log.w("IMAGE_EDITOR",selectedImageUri.toString());
                    break;

            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        Log.w("getfilename",result);
        return result;
    }

}
