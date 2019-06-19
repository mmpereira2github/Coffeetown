package com.syncinfo.coffeetown.view;

import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.ProductCategory;
import com.syncinfo.mvc.view.ErrorHandler;
import com.syncinfo.mvc.view.impl.ThrowerErrorHandler;
import com.syncinfo.util.Assert;

/**
 * Created by mmartins on 2018-02-08.
 */

public class ProductCategoryMainView {
    private ErrorHandler errorHandler;
    private ProductCategory targetProductCategory;
    private String categoryName;
    private ProductCategory parentProductCategory;

    public ProductCategoryMainView(ErrorHandler errorHandler) {
        reset();
        this.errorHandler = null == errorHandler? new ThrowerErrorHandler(): errorHandler;
    }

    public ProductCategoryMainView(ErrorHandler errorHandler, int id) {
        this(errorHandler);
        this.targetProductCategory = Model.getInstance().getProductCategoryDao().getById(id);
        this.categoryName = this.targetProductCategory.getName();
        this.parentProductCategory = this.targetProductCategory.getParent();
    }

    public ProductCategoryMainView(ErrorHandler errorHandler, ProductCategory targetProductCategory) {
        this(errorHandler);
        this.targetProductCategory = targetProductCategory;
        this.categoryName = this.targetProductCategory.getName();
        this.parentProductCategory = this.targetProductCategory.getParent();
    }

    public void reset() {
        this.categoryName = null;
        this.parentProductCategory = null;
        this.targetProductCategory = null;
    }

    public ProductCategory save() {
        ProductCategory result = null;
        boolean isNew = false;
        if (null == this.targetProductCategory) {
            isNew = true;
            this.targetProductCategory = Model.getInstance().getProductCategoryDao().create();
        }

        this.targetProductCategory.setParent(this.parentProductCategory);
        this.targetProductCategory.setName(this.categoryName);
        if (isNew) {
            result = Model.getInstance().getProductCategoryDao().save(this.targetProductCategory);
        }

        return result;
    }

    public ProductCategory getTargetProductCategory() {
        return targetProductCategory;
    }

    public void setTargetProductCategory(ProductCategory targetProductCategory) {
        this.targetProductCategory = targetProductCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        Assert.notNull(categoryName, "Name cannot be null");
        this.categoryName = categoryName;
    }

    public ProductCategory getParentProductCategory() {
        return parentProductCategory;
    }

    public void setParentProductCategory(ProductCategory parentProductCategory) {
        this.parentProductCategory = parentProductCategory;
    }
}
