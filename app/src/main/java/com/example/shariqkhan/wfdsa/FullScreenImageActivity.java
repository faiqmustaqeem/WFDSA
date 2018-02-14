package com.example.shariqkhan.wfdsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class FullScreenImageActivity extends AppCompatActivity {

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);

        image = (ImageView) findViewById(R.id.photo_view);

        Glide.with(this).load(GlobalClass.image_link).into(image);
    }
}
