package com.syncinfo.coffeetown.model.dao;

import com.syncinfo.coffeetown.model.Product;
import com.syncinfo.coffeetown.model.ProductCategory;

/**
 * Created by mmartins on 2017-12-30.
 */

public interface ProductCatalogueDao {
    ProductCategory[] getRootCategories(int catalogueId);

    ProductCategory[] getSubCategories(int catalogueId, int categoryId);

    Product[] getProducts(int catalogueId, int categoryId);
}
