package com.example.threadpractice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendTextActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtMessage;
    Button btnSubmit;
    TextView tvResult, tvGoTimeWatch, tvGoImage;
    SendThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendtext);

        edtMessage = findViewById(R.id.edt_sendtext_message);
        btnSubmit = findViewById(R.id.btn_sendtext_submit);
        tvResult = findViewById(R.id.tv_sendtext_result);
        tvGoTimeWatch = findViewById(R.id.tv_sendtext_timewatch);
        tvGoImage = findViewById(R.id.tv_sendtext_image);

        btnSubmit.setOnClickListener(this);
        tvGoTimeWatch.setOnClickListener(this);
        tvGoImage.setOnClickListener(this);

        thread = new SendThread();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sendtext_submit:
                String input = edtMessage.getText().toString();
                if (input.length() > 0) {
                    Message message = Message.obtain();
                    message.obj = input;
//                thread.handler.sendMessage(message);
                    thread.handler.handleMessage(message);
                } else
                    Toast.makeText(getApplicationContext(), "no message", Toast.LENGTH_LONG).show();
                break;
            case R.id.tv_sendtext_timewatch:
                startActivity(new Intent(getApplicationContext(), TimeWatchActivity.class));
                finish();
                break;
            case R.id.tv_sendtext_image:
                startActivity(new Intent(getApplicationContext(), ImageActivity.class));
                finish();
                break;
        }
    }

    class SendThread extends Thread {
        SendHandler handler = new SendHandler();

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            Looper.loop();
        }

        class SendHandler extends Handler {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                final String output = msg.obj + "\nfrom thread";
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText(output);
                    }
                });
            }
        }
    }
}