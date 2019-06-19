package com.syncinfo.android.coffeetown.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.syncinfo.android.coffeetown.R;
import com.syncinfo.android.mvc.view.fragment.ViewFragment;
import com.syncinfo.coffeetown.view.ProductCategoryMainView;

public class ProductCategoryMainViewFragment extends ViewFragment {
    public static final String PRODUCT_CATEGORY_ID = "ProductCategoryId";

    private ProductCategoryMainView productCategoryMainView = null;

    @Override
    protected int getLayoutId() {
        return R.layout.product_category_main_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        Intent intent = activity.getIntent();

        if (null != intent && intent.hasExtra(PRODUCT_CATEGORY_ID)) {
            int productCategoryId = intent.getIntExtra(PRODUCT_CATEGORY_ID, 0);
            if (productCategoryId <= 0) {
                getErrorHandler().onError("Invalid productCategoryId");
            }

            this.productCategoryMainView = new ProductCategoryMainView(getErrorHandler(), productCategoryId);
        }
        else {
            this.productCategoryMainView = new ProductCategoryMainView(getErrorHandler());
        }

        EditText etName = activity.findViewById(R.id.etProductCategoryMain_name);
        etName.setText(this.productCategoryMainView.getCategoryName());

        /*
         * Get fragment that manage the spinner with product categories
         */
        ProductCategorySpinnerFragment fragment =
                (ProductCategorySpinnerFragment) getChildFragmentManager().findFragmentById(R.id
                .spProductCategoryMain_parentCategory);
        /*
         * When spinner has an item selected then the ProductCategory selected must be assigned to
         * view used to create the new product category.
         */
//        fragment.getSyncListView().registerOnSelectedItemListener(
//                (index, adapter) -> this.productCategoryMainView.setParentProductCategory(adapter.getItem(index)));

        setSpinner(this.productCategoryMainView.getParentProductCategory() != null, fragment);

        CheckBox cbSubcategory = activity.findViewById(R.id.cbProductCategoryMain_isSubcategory);
        cbSubcategory.setOnClickListener(v -> setSpinner(cbSubcategory.isChecked(), fragment) );

        Button btSave = activity.findViewById(R.id.btProductCategoryMain_save);
        btSave.setOnClickListener(v -> {
            this.productCategoryMainView.setCategoryName(etName.getText().toString());
            this.productCategoryMainView.save();
            activity.finish();
        });
    }

    private void setSpinner(boolean visible, ProductCategorySpinnerFragment fragment) {
//        if (visible) {
//            fragment.getSpinner().setVisibility(View.VISIBLE);
//            if (null == this.productCategoryMainView.getParentProductCategory()) {
//                if (fragment.getSyncListView().getAdapter().getCount() > 0) {
//                    this.productCategoryMainView.setParentProductCategory(
//                            fragment.getSyncListView().getAdapter().getItem(0));
//                }
//            } else {
//                int index = fragment.getSyncListView().getAdapter().getPosition(
//                        this.productCategoryMainView.getParentProductCategory());
//                fragment.getSpinner().setSelection(index);
//            }
//        }
//        else {
//            fragment.getSpinner().setVisibility(View.INVISIBLE);
//        }
    }
}
