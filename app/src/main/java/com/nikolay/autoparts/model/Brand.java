package com.nikolay.autoparts.model;

import androidx.annotation.NonNull;

public class Brand {

    private int id;
    private String name;
    private int restId;

    public Brand() {}

    public Brand(String name) {
        this.name = name;
    }

    public Brand(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Brand(int id, String name, int restId) {
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
        return getName() ;
    }
}
