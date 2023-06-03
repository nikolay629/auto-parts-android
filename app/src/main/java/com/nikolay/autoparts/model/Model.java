package com.nikolay.autoparts.model;

import androidx.annotation.NonNull;

public class Model {
    private int id;
    private Brand brand;
    private String name;
    private int restId;

    public Model() {}

    public Model(Brand brand, String name) {
        this.brand = brand;
        this.name = name;
    }

    public Model(int id, Brand brand, String name) {
        this.id = id;
        this.brand = brand;
        this.name = name;
    }

    public Model(int id, Brand brand, String name, int restId) {
        this.id = id;
        this.brand = brand;
        this.name = name;
        this.restId = restId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
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
        return getName();
    }
}
