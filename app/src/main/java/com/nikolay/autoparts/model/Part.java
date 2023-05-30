package com.nikolay.autoparts.model;

public class Part {
    private int id;
    private Category category;
    private Model model;
    private String name;
    private int qty;
    private float price;

    public Part() {}

    public Part(String name, int qty, float price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public Part(Category category, Model model, String name, int qty, float price) {
        this.category = category;
        this.model = model;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public Part(int id, Category category, Model model, String name, int qty, float price) {
        this.id = id;
        this.category = category;
        this.model = model;
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
