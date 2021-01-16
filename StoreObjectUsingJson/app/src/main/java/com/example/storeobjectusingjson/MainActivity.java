package com.example.storeobjectusingjson;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    String TAG = "@@★@";
    Button btnCreate;
    RecyclerView recyclerView;
    ArrayList<Member> members = new ArrayList<>();

    CustomAdapter adapter = new CustomAdapter(members, this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setContent();
        setListener();

        getMemberList(members);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelperCallback(adapter));
        helper.attachToRecyclerView(recyclerView);
    }

    void setContent() {
        btnCreate = findViewById(R.id.btn_create);
        recyclerView = findViewById(R.id.recyclerview);

        if (getMemberSize() == 0) {
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

    void setListener() {
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddActivity.class));
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (getIntent().getBooleanExtra("refresh", false)) {
            members.clear();
            getMemberList(members);
            adapter.notifyDataSetChanged();
        }
        for (int i = 0; i < members.size(); i++) {
            Log.d(TAG, "onStart: " + i + ": name: " + members.get(i).getName());
        }
//        adapter.notifyDataSetChanged();
    }
}