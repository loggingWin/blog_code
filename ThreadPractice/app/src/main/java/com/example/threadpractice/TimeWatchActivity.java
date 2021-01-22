package com.example.threadpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TimeWatchActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnStart, btnStop;
    TextView tvCount, tvGoSendText, tvGoImage;
    Thread thread = null;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timewatch);

        btnStart = findViewById(R.id.btn_timewatch_start);
        btnStop = findViewById(R.id.btn_timewatch_stop);
        tvCount = findViewById(R.id.tv_timewatch_count);
        tvGoSendText = findViewById(R.id.tv_timewatch_sendtext);
        tvGoImage = findViewById(R.id.tv_timewatch_image);

        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        tvGoSendText.setOnClickListener(this);
        tvGoImage.setOnClickListener(this);

        tvCount.setText("0");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_timewatch_start:
                btnStart.setVisibility(View.GONE);
                btnStop.setVisibility(View.VISIBLE);
                if (thread == null)
                    thread = new Thread(new TimeWatchRunnable());
                thread.start();
                break;
            case R.id.btn_timewatch_stop:
                btnStart.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.GONE);
                thread.interrupt();
                thread = null;
                break;
            case R.id.tv_timewatch_sendtext:
                startActivity(new Intent(getApplicationContext(), SendTextActivity.class));
                finish();
                break;
            case R.id.tv_timewatch_image:
                startActivity(new Intent(getApplicationContext(), ImageActivity.class));
                finish();
                break;
        }
    }

    class TimeWatchRunnable implements Runnable {
        @Override
        public void run() {
            int value = 0;
            handler.post(new Runnable() {
                @Override
                public void run() {
                    tvCount.setText("0");
                }
            });
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("Thread", "status: interrupted");
                    return;
                }
                value += 1;
                Log.d("Thread", "value: " + value);
                int finalValue = value;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvCount.setText(String.valueOf(finalValue));
                    }
                });
            }
        }
    }
}