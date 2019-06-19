package com.syncinfo.coffeetown.model.dao.impl;

import com.syncinfo.coffeetown.model.ProductCategory;
import com.syncinfo.coffeetown.model.dao.ProductCategoryDao;
import com.syncinfo.util.Assert;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mmartins on 2018-02-08.
 */

public class InMemoryProductCategoryDao extends InMemoryDao<ProductCategory, Integer> implements ProductCategoryDao {
    private static AtomicInteger nextId = new AtomicInteger(0);

    @Override
    public ProductCategory create() {
        return new ProductCategory();
    }

    @Override
    public void remove(int id) {
        ProductCategory productCategory = this.findSingle(c -> c.getId() == id);
        if (null != productCategory) this.remove(productCategory);
    }

    @Override
    public <C extends Collection<ProductCategory>> C findPerParent(ProductCategory parent, C result) {
        return this.findAll(c -> (null != c.getParent() && (c.getParent().equals(parent)) ||
                (null == parent && c.getParent() == null)), result);
    }

    @Override
    protected void setId(ProductCategory productCategory) {
        productCategory.setId(nextId.incrementAndGet());
    }

    @Override
    protected Integer getId(ProductCategory productCategory) {
        return productCategory.getId();
    }
}
