package com.nikolay.autoparts.model;

import androidx.annotation.NonNull;

public class Category {

    private int id;
    private String name;
    private int restId;

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(int id, String name, int restId) {
        this.id = id;
        this.name = name;
        this.restId = restId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRestId() {
        return restId;
    }

    public void setRestId(int restId) {
        this.restId = restId;
    }

    @NonNull
    @Override
    public String toString() {
        return this.getName();
    }
}
