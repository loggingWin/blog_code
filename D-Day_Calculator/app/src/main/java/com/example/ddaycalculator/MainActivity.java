package com.example.ddaycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button button;
    TextView tvDate, tvDDay;

    final long ONE_DAY = 1000 * 60 * 60 * 24; // 하루
    final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM월 dd일 yyyy년", Locale.KOREA); // 날짜 형식

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        tvDate = findViewById(R.id.tv_date);
        tvDDay = findViewById(R.id.tv_dday);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this);
//                최소 선택 가능 날짜 설정
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        Calendar calendar = Calendar.getInstance(); // 날짜에 관한 추상 클래스
                        calendar.set(year, month, date); // datePicker 에서 선택한 날짜로 세팅
                        Date date1 = calendar.getTime();
                        tvDate.setText(DATE_FORMAT.format(date1)); // 날짜 형식대로 텍스트뷰 세팅

                        long selectDate = date1.getTime() / ONE_DAY; // 선택한 날짜 일수
                        long nowDate = System.currentTimeMillis() / ONE_DAY; // 현재 날짜 일수

                        long gap = selectDate - nowDate; // 선택 날짜와 현재 차이
                        tvDDay.setText(String.format(Locale.KOREA, "D-%d", gap));
                    }
                });
                datePickerDialog.show();
            }
        });
    }
}