package com.example.storeobjectbysplitstring;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class EditActivity extends BaseActivity {
    EditText edtName, edtAge, edtJob;
    Button btnSubmit;
    String name, job;
    int age;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        edtName = findViewById(R.id.edt_name);
        edtAge = findViewById(R.id.edt_age);
        edtJob = findViewById(R.id.edt_job);

        Member member = (Member) getIntent().getSerializableExtra("member");

        name = member.getName();
        job = member.getJob();
        age = member.getAge();
        edtName.setText(name);
        edtAge.setText(String.valueOf(age));
        edtJob.setText(job);

        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = edtName.getText().toString();
                job = edtJob.getText().toString();
                age = Integer.parseInt(edtAge.getText().toString());

                if (name.length() > 0 && job.length() > 0 && age > 0) {
                    editMember(member.getKey(), name, age, job);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("refresh", true);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
}
