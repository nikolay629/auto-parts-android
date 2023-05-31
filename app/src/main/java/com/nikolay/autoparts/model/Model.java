package com.nikolay.autoparts.model;

import androidx.annotation.NonNull;

public class Model {
    private int id;
    private Brand brand;
    private String name;

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

    @NonNull
    @Override
    public String toString() {
        return getName();
    }
}
