package com.example.storeobjectbysplitstring;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    RecyclerView recyclerView;
    ArrayList<Member> members = new ArrayList<>();
    CustomAdapter adapter = new CustomAdapter(members, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContent();

        getMemberList(members); // sharedPreferences 에서 멤버 정보 가져오기
    }

    void setContent() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        helper.attachToRecyclerView(recyclerView);

        findViewById(R.id.btn_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });

        if (getMemberSize() == 0) { // 테스트를 위해 멤버 아이템이 없을 경우 초기값 넣어주기
            addMember("Kim", 12, "student");
            addMember("Jake", 20, "programmer");
            addMember("Tom", 41, "baker");
            addMember("Conan", 32, "teacher");
            addMember("Chris", 26, "COO");
            addMember("John", 43, "PD");
            addMember("Kate", 56, "professor");
            addMember("Shara", 30, "student");
            addMember("Megan", 14, "soccer player");
            addMember("Josh", 17, "student");
        }
    }

    @Override
    protected void onNewIntent(Intent intent) { // launchMode = "singleTask"
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getBooleanExtra("refresh", false)) {
            // 추가 또는 수정 작업이 있을 경우 멤버 아이템 다시 세팅하기
            members.clear();
            getMemberList(members);
            adapter.notifyDataSetChanged();
        }
    }
}