package com.example.arishti;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class profile_page extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;
    private static final int CHATPAGE_REQUEST = 1;
    Button mCaptureBtn,nextbtn;
    ImageView mImageView;
    Uri image_uri;
    private String mImageFileLocation;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        mImageView = findViewById(R.id.profile_img);
        mCaptureBtn = findViewById(R.id.capture_btn);
        nextbtn=findViewById(R.id.next_button);

        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permission, PERMISSION_CODE);
                    } else {
                        for (i = 0; i < 5; i++) {
                            if(i==2)
                            {
                                Toast.makeText(profile_page.this,"We need 5 photos",Toast.LENGTH_SHORT).show();
                            }
                            openCamera();
                        }
                        nextbtn.setVisibility(View.VISIBLE);
                        nextbtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(profile_page.this,chat_page.class);
                                startActivityForResult(intent,CHATPAGE_REQUEST);
                            }
                        });
                    }
                } else {
                    for (i = 0; i < 5; i++) {
                        if(i==2)
                        {
                            Toast.makeText(profile_page.this,"We need 5 photos",Toast.LENGTH_SHORT).show();
                        }
                        openCamera();
                    }
                    nextbtn.setVisibility(View.VISIBLE);
                    nextbtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(profile_page.this,chat_page.class);
                            startActivityForResult(intent,CHATPAGE_REQUEST);
                        }
                    });
                }
            }
        });
    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File photofile = null;
        try {
            photofile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        //idhar se
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera();
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            //mImageView.setImageURI(image_uri);
            /*Bundle extras=data.getExtras();
            Bitmap photoCapturedBitmap=(Bitmap) extras.get("data");
            mImageView.setImageBitmap(photoCapturedBitmap);*/
            mImageView.setImageURI(image_uri);
        }
    }

    File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd:HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDirectory);

        mImageFileLocation = image.getAbsolutePath();
        return image;
    }
}
