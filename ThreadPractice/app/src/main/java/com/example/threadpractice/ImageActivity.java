package com.example.threadpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ivImage;
    Button btnStart, btnStop;
    TextView tvGoSendText, tvGoTimeWatch;
    Thread thread = null;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        ivImage = findViewById(R.id.iv_image);
        btnStart = findViewById(R.id.btn_image_start);
        btnStop = findViewById(R.id.btn_image_stop);
        tvGoSendText = findViewById(R.id.tv_image_sendtext);
        tvGoTimeWatch = findViewById(R.id.tv_image_timewatch);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        tvGoSendText.setOnClickListener(this);
        tvGoTimeWatch.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_image_start:
                btnStart.setVisibility(View.GONE);
                btnStop.setVisibility(View.VISIBLE);
                if (thread == null)
                    thread = new Thread(new ImageRunnable());
                thread.start();
                break;
            case R.id.btn_image_stop:
                btnStart.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.GONE);
                thread.interrupt();
                thread = null;
                break;
            case R.id.tv_image_sendtext:
                startActivity(new Intent(getApplicationContext(), SendTextActivity.class));
                finish();
                break;
            case R.id.tv_image_timewatch:
                startActivity(new Intent(getApplicationContext(), TimeWatchActivity.class));
                finish();
                break;
        }
    }

    class ImageRunnable implements Runnable {
        int[] images = {R.drawable.ic_twotone_ac_unit_24, R.drawable.ic_twotone_brightness_4_24, R.drawable.ic_twotone_bug_report_24,
                R.drawable.ic_twotone_catching_pokemon_24, R.drawable.ic_twotone_policy_24};
        Handler handler = new Handler();
        boolean hasImageAlready = (ivImage.getDrawable() != null);

        @Override
        public void run() {
            while (true) {
                if (hasImageAlready) {
                    try {
                        Thread.sleep(1000 * 2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    hasImageAlready = false;
                }
                Log.d("image runnable", "image index: " + index);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ivImage.setImageResource(images[index]);
                    }
                });
                index++;
                if (index == images.length) index = 0;
                try {
                    Thread.sleep(1000 * 2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}