package com.example.storeobjectbysplitstring;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

import static com.example.storeobjectbysplitstring.ApplicationClass.MEMBER;
import static com.example.storeobjectbysplitstring.ApplicationClass.SEP;
import static com.example.storeobjectbysplitstring.ApplicationClass.editor;
import static com.example.storeobjectbysplitstring.ApplicationClass.sharedPreferences;

public class BaseActivity extends AppCompatActivity {
    void addMember(String name, int age, String job) {
        int newIndex = getMemberSize() + 1;
        if (newIndex > 0) {
            String key = String.format(Locale.KOREA, "%s%03d", MEMBER, newIndex); // key = MEMBER001;
            String value = name + SEP + age + SEP + job;
            editor.putString(key, value); // 새로운 멤버 정보
            editor.apply();
        }
    }

    void editMember(String key, String name, int age, String job) {
        String value = name + SEP + age + SEP + job;
        editor.putString(key, value).apply();
    }

    void deleteMember(Member member) {
//        선택한 member 객체의 키부터 마지막 키까지 하나씩 데이터를 당기는 작업. 마지막 키는 삭제.
        int index = Integer.parseInt(member.getKey().replace(MEMBER, "")); // if member.key == MEMBER001 -> index == 1
        while (true) {
            String key = String.format(Locale.KOREA, "%s%03d", MEMBER, index); // 현재 키
            String nextKey = String.format(Locale.KOREA, "%s%03d", MEMBER, index + 1); // 다음 키
            String value = sharedPreferences.getString(nextKey, null); // 다음 키의 데이터

            if (value == null) { // 마지막 키인 경우 삭제하기
                editor.remove(key);
                break;
            } else // 다음 키가 있을 경우 현재 키에 다음 키의 값을 넣어주기
                editor.putString(key, value);
        }
        editor.apply();
    }

    Member getMember(int index) { // 특정 index 의 멤버 정보 가져오기
        String key = String.format(Locale.KOREA, "%s%03d", MEMBER, index + 1); // arraylist나 for문을 돌려 가지고 올 때의 index는 0부터 n-1까지이므로 key를 설정할 때에는 index+1해줌
        String value = sharedPreferences.getString(key, null); // 해당 키의 데이터 가져오기
        if (value == null) return null; // 키에 대한 데이터가 null 이면 null 리턴

        String[] saveData = value.split(SEP);
        String name = saveData[0];
        int age = Integer.parseInt(saveData[1]);
        String job = saveData[2];
        return new Member(key, name, age, job);
    }

    void getMemberList(ArrayList<Member> members) { // 저장된 모든 멤버 추가하기
        for (int i = 0; i < getMemberSize(); i++) {
            members.add(getMember(i));
        }
    }

    int getMemberSize() { // 저장된 멤버 아이템의 개수
        int i = 0;
        while (true) {
            if (getMember(i) == null) return i;
            i++;
        }
    }
}