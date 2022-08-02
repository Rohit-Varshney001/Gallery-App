package com.example.gallerymanager;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GalleryAdapter galleryAdapter;
    List<String> imagesList;
    TextView galleryNumber;

    private final static int MY_READ_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        galleryNumber = findViewById(R.id.gallery_number);
        recyclerView = findViewById(R.id.recyclerview_gallery_images);


        //Check For Permission
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, MY_READ_PERMISSION_CODE);

        }else {
            loadImages();
        }


    }

    private void loadImages(){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        imagesList = ImagesGallery.listOfImages(this);
        galleryAdapter = new GalleryAdapter(this, imagesList, new GalleryAdapter.PhotoListener() {
            @Override
            public void onPhotoClick(String path) {
                //Do something with photo

                Toast.makeText(MainActivity.this, "" +path, Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.setAdapter(galleryAdapter);

        galleryNumber.setText("Photos("+ imagesList.size()+")");

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_READ_PERMISSION_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Read External Storage Permission granted", Toast.LENGTH_SHORT).show();
                loadImages();
            }else {
                Toast.makeText(this, "Read External Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
