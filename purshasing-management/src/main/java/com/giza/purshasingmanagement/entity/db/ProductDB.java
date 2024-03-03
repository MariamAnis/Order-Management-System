package com.giza.purshasingmanagement.entity.db;

import java.io.Serializable;

public class ProductDB implements Serializable {

    private String name;
    private int quantity;
    private float price;

    public ProductDB(){

    }

    public ProductDB(String name, int quantity, float price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
