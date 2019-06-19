package com.syncinfo.coffeetown.model.dao;

import com.syncinfo.coffeetown.model.Offering;
import com.syncinfo.coffeetown.model.ProductCategory;

import java.util.Collection;

/**
 * Created by mmartins on 2018-02-09.
 */

public interface OfferingDAO extends DAO<Offering, Integer> {

    <C extends Collection<Offering>> C getAll(C result);
    <C extends Collection<Offering>> C findByCategory(ProductCategory productCategory, C result);
}
