 package com.example.gallerymanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

 public class FullView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_view);

        ImageView imageView = findViewById(R.id.img_full);
        String img_id = getIntent().getStringExtra("img_id");
        Glide.with(this)
                .load(img_id)
                .into(imageView);
//        imageView.setImageResource(img_id);

    }
}
