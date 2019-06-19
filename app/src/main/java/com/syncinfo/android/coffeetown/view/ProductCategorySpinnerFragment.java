package com.syncinfo.android.coffeetown.view;

import com.syncinfo.android.mvc.view.ListUCAdapterAsBaseAdapter;
import com.syncinfo.android.mvc.view.fragment.SpinnerViewFragment;
import com.syncinfo.coffeetown.model.ProductCategory;
import com.syncinfo.coffeetown.uc.ProductCategoryListUC;

public class ProductCategorySpinnerFragment extends SpinnerViewFragment<ProductCategory, ProductCategoryListUC> {

    private final ProductCategory specialAllCategories = new ProductCategory();
    private final boolean useSpecialAllCategories;

    public ProductCategorySpinnerFragment() {
        this(false);
    }

    public ProductCategorySpinnerFragment(boolean useSpecialAllCategories) {
        this.specialAllCategories.setName("TODAS");
        this.useSpecialAllCategories = useSpecialAllCategories;
        setListUC(new ProductCategoryListUC(this.specialAllCategories));
    }

    /**
     * Subclass overrides this method to provide a way to convert a ProductCategory instance into a string.
     * @return product category name
     */
    protected ListUCAdapterAsBaseAdapter.ToCharSequenceConverter<ProductCategory> getToCharSequenceConverter() {
        return ProductCategory::getName;
    }
}