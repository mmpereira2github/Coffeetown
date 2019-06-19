package com.syncinfo.coffeetown.model;

/**
 * Created by mmartins on 2017-12-30.
 */

public class ProductCategory {
    private ProductCategory parent = null;
    private int id = 0;
    private String name = null;

    public ProductCategory() {}

    public ProductCategory(ProductCategory parent, int id, String name) {
        this.parent = parent;
        this.id = id;
        this.name = name;
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

    public ProductCategory getParent() {
        return parent;
    }

    public void setParent(ProductCategory parent) {
        this.parent = parent;
    }
}
