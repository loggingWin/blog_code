package com.example.storeobjectusingjson;

import java.io.Serializable;

public class Member implements Serializable {
    String key;
    String name;
    int age;
    String job;

    public Member(String name, int age, String job) {
        this.key = "";
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public Member(String key, String name, int age, String job) {
        this.key = key;
        this.name = name;
        this.age = age;
        this.job = job;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}
