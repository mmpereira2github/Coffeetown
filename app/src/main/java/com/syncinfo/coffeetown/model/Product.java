package com.syncinfo.coffeetown.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mmartins on 2017-12-30.
 */

public class Product {
    private Set<ProductCategory> categories = null;
    private int id;
    private String name = null;

    public Product() {
    }

    public Collection<ProductCategory> getCategories() {
        return this.categories;
    }

    public void addCategory(ProductCategory category) {
        if (null == this.categories) {
            this.categories = new HashSet<>();
        }

        this.categories.add(category);
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
}
