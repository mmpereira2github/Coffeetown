package com.syncinfo.coffeetown.model;

import com.syncinfo.coffeetown.model.Product;

/**
 * Created by mmartins on 2018-02-09.
 */

public class Offering {
    private int id = 0;
    private Product product = null;
    private double price = 0.0;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
