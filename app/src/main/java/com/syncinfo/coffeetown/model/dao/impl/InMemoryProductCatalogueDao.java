package com.syncinfo.coffeetown.model.dao.impl;

import com.syncinfo.coffeetown.model.Product;
import com.syncinfo.coffeetown.model.ProductCategory;
import com.syncinfo.coffeetown.model.dao.ProductCatalogueDao;

/**
 * Created by mmartins on 2017-12-30.
 */

public class InMemoryProductCatalogueDao implements ProductCatalogueDao {
    @Override
    public ProductCategory[] getRootCategories(int catalogueId) {
        return null;
    }

    @Override
    public ProductCategory[] getSubCategories(int catalogueId, int categoryId) {
        return null;
    }

    @Override
    public Product[] getProducts(int catalogueId, int categoryId) {
        return null;
    }

}
