package com.example.storeobjectusingjson;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.storeobjectusingjson.ApplicationClass.MEMBER;
import static com.example.storeobjectusingjson.ApplicationClass.editor;
import static com.example.storeobjectusingjson.ApplicationClass.sharedPreferences;

public class BaseActivity extends AppCompatActivity {
    Gson gson = new GsonBuilder().create();

    void addMember(String name, int age, String job) {
        int newIndex = getMemberSize() + 1;
        if (newIndex > 0) {
            String key = String.format(Locale.KOREA, "%s%03d", MEMBER, newIndex); // key = MEMBER001;
            Member member = new Member(key, name, age, job);
            String value = gson.toJson(member);
            editor.putString(key, value); // 새로운 멤버 정보
            editor.apply();
        }
    }

    void editMember(Member member) {
        String value = gson.toJson(member);
        editor.putString(member.getKey(), value).apply();
    }

    void deleteMember(String memberKey) {
//        선택한 member 객체의 키부터 마지막 키까지 하나씩 데이터를 당기는 작업. 마지막 키는 삭제.
        int index = Integer.parseInt(memberKey.replace(MEMBER, "")); // if member.key == MEMBER001 -> index == 1
        while (true) {
            String key = String.format(Locale.KOREA, "%s%03d", MEMBER, index); // 현재 키
            String nextKey = String.format(Locale.KOREA, "%s%03d", MEMBER, index + 1); // 다음 키
            String value = sharedPreferences.getString(nextKey, null); // 다음 키의 데이터

            if (value == null) { // 마지막 키인 경우 삭제하기
                editor.remove(key);
                break;
            } else {// 다음 키가 있을 경우 현재 키에 다음 키의 값을 넣어주기
                Member member = gson.fromJson(value, Member.class);
                member.setKey(key);
                value = gson.toJson(member);
                editor.putString(key, value);
            }
            index++;
        }
        editor.apply();
    }

    Member getMember(int index) {
        String key = String.format(Locale.KOREA, "%s%03d", MEMBER, index + 1); // arraylist나 for문을 돌려 가지고 올 때의 index는 0부터 n-1까지이므로 key를 설정할 때에는 index+1해줌
        String value = sharedPreferences.getString(key, null); // 해당 키의 데이터 가져오기
        if (value == null) return null; // 키에 대한 데이터가 null 이면 null 리턴

        return gson.fromJson(value, Member.class);
    }

    void getMemberList(ArrayList<Member> members) {
        int i = 0;
        while (true) {
            if (getMember(i) == null) break;
            members.add(getMember(i));
            i++;
        }
    }

    int getMemberSize() {
        int i = 0;
        while (true) {
            if (getMember(i) == null) return i;
            i++;
        }
    }
}
