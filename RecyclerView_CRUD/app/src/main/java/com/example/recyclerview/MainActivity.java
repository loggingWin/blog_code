package com.example.recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //    SharedPreferences pref = getSharedPreferences("member", MODE_PRIVATE);
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

        members.add(new Member("Kim", 12, "student"));
        members.add(new Member("Jake", 20, "programmer"));
        members.add(new Member("Tom", 41, "baker"));
        members.add(new Member("Conan", 32, "teacher"));
        members.add(new Member("Chris", 26, "COO"));
        members.add(new Member("John", 43, "PD"));
        members.add(new Member("Kate", 56, "professor"));
        members.add(new Member("Shara", 30, "student"));
        members.add(new Member("Megan", 14, "soccer player"));
        members.add(new Member("Josh", 17, "student"));

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    void setContent() {
        btnCreate = findViewById(R.id.btn_create);
        recyclerView = findViewById(R.id.recyclerview);
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
        int action = 0;
        if (getIntent().getBooleanExtra("new", false)) action = 1;
        else if (getIntent().getBooleanExtra("edit", false)) action = 2;
        Log.d(TAG, "onStart: " + action);
        if (action > 0) {
            String name, job;
            int age;
            name = getIntent().getStringExtra("name");
            job = getIntent().getStringExtra("job");
            age = getIntent().getIntExtra("age", -1);
            if (action == 1) members.add(new Member(name, age, job));
            else { // when action == 2
                int position = getIntent().getIntExtra("position", -1);
                if (position != -1) {
                    members.get(position).setName(name);
                    members.get(position).setAge(age);
                    members.get(position).setJob(job);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        members = this.members;
    }
}