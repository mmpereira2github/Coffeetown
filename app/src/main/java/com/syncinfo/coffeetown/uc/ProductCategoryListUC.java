package com.syncinfo.coffeetown.uc;

import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.ProductCategory;
import com.syncinfo.mvc.uc.impl.FilterBasedListUC;

import java.util.Collection;

/**
 * Created by mmartins on 2018-02-26.
 */

public class ProductCategoryListUC extends FilterBasedListUC<ProductCategory> {

    private ProductCategory allCategories = null;

    public ProductCategoryListUC() { super(result -> Model.getInstance().getProductCategoryDao().getAll(result)); }

    public ProductCategoryListUC(ProductCategory allCategories) {
        super(result -> withSpecialAllCategories(allCategories, result));
        this.allCategories = allCategories;
    }

    private static <R extends Collection<ProductCategory>> R withSpecialAllCategories(ProductCategory allCategories, R result) {
        result.add(allCategories);
        Model.getInstance().getProductCategoryDao().getAll(result);
        return result;
    }

    public boolean isSelectionTheSpecialAllCategory() {
        boolean result = false;
        if (hasSelection()) {
            ProductCategory selected = getItemSelected();
            result = selected == this.allCategories;
        }

        return result;
    }

}
