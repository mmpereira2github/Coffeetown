package com.syncinfo.coffeetown.model.dao.impl;

import com.syncinfo.coffeetown.model.Product;
import com.syncinfo.coffeetown.model.dao.ProductDao;

import java.util.concurrent.atomic.AtomicInteger;

;

/**
 * Created by mmartins on 2018-02-09.
 */

public class InMemoryProductDAO extends InMemoryDao<Product, Integer> implements ProductDao {
    private static AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Product create() {
        return new Product();
    }

    @Override
    protected void setId(Product product) {
        product.setId(nextId.getAndIncrement());
    }

    @Override
    protected Integer getId(Product product) {
        return product.getId();
    }
}
