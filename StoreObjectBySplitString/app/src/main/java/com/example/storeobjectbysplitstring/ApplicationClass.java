package com.example.storeobjectbysplitstring;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class ApplicationClass extends Application {
    static SharedPreferences sharedPreferences = null;
    static SharedPreferences.Editor editor = null;
    static final String MEMBER_DATA = "Member";
    static final String MEMBER = "member";
    static final String SEP = "@#!~";

    @Override
    public void onCreate() {
        super.onCreate();
        if (sharedPreferences == null) {
            sharedPreferences = getApplicationContext().getSharedPreferences(MEMBER_DATA, Context.MODE_PRIVATE); // MEMBER_DATA : 데이터 저장할 xml 파일명
            editor = sharedPreferences.edit();
        }
    }
}
