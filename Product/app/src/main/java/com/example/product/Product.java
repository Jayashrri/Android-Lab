package com.example.product;

public class Product {
    private int id;
    private String name;
    private float mrp;
    private float price;

    public Product(int id, String name, float mrp, float price) {
        this.id = id;
        this.name = name;
        this.mrp = mrp;
        this.price = price;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public float getMrp() { return mrp; }
    public float getPrice() { return price; }
}