package com.syncinfo.coffeetown.model.dao.impl;

import com.syncinfo.coffeetown.model.Offering;
import com.syncinfo.coffeetown.model.ProductCategory;
import com.syncinfo.coffeetown.model.dao.OfferingDAO;
import com.syncinfo.util.Assert;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mmartins on 2018-02-09.
 */

public class InMemoryOfferingDAO extends InMemoryDao<Offering, Integer> implements OfferingDAO {
    private static AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Offering create() {
        return new Offering();
    }

    @Override
    protected void setId(Offering offering) {
        offering.setId(nextId.getAndIncrement());
    }

    @Override
    protected Integer getId(Offering offering) {
        return offering.getId();
    }

    @Override
    public <C extends Collection<Offering>> C findByCategory(ProductCategory productCategory, C result) {
        if (null == productCategory) {
            return this.findAll(o -> {
                Collection<ProductCategory> categories = o.getProduct().getCategories();
                if (null == categories || categories.isEmpty()) {
                    return true;
                }
                else {
                    return false;
                }
            }, result);
        }
        else {
            return this.findAll(o -> {
                Collection<ProductCategory> categories = o.getProduct().getCategories();
                if (null == categories || categories.isEmpty()) {
                    return false;
                }
                else {
                    for (ProductCategory category: categories) {
                        if (category.getId() == productCategory.getId()) {
                            return true;
                        }
                    }

                    return false;
                }
            }, result);
        }
    }
}
