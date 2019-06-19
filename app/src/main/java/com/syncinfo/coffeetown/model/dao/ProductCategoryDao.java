package com.syncinfo.coffeetown.model.dao;

import com.syncinfo.coffeetown.model.ProductCategory;

import java.util.Collection;

/**
 * Created by mmartins on 2018-02-08.
 */

public interface ProductCategoryDao extends DAO<ProductCategory, Integer> {
    void remove(int id);

    /**
     * Find all ProductCategory instances whose parent is the one provided as input. The instances found are copied to
     * the collection passed as input and also returned.
     *
     * @param parent
     * @param result
     * @param <C>
     * @return
     */
    <C extends Collection<ProductCategory>> C findPerParent(ProductCategory parent, C result);
}
