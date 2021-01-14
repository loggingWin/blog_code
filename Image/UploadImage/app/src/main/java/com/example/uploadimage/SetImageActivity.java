package com.example.uploadimage;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class SetImageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_image);

        TextView textView = findViewById(R.id.tv_image_path);
        ImageView imageView = findViewById(R.id.iv_image);

        String imagePath = getIntent().getStringExtra("path");
        textView.setText(imagePath);
        Glide.with(this).load(imagePath).into(imageView);
    }
}