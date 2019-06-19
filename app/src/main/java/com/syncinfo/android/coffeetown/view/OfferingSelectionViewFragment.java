package com.syncinfo.android.coffeetown.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.syncinfo.android.coffeetown.R;
import com.syncinfo.android.mvc.view.fragment.ViewFragment;
import com.syncinfo.coffeetown.model.Offering;

/**
 * Created by mmartins on 2018-02-10.
 */

public class OfferingSelectionViewFragment extends ViewFragment {
    public static class Input {
        final boolean selectionRequired;

        public Input(boolean selectionRequired) {
            this.selectionRequired = selectionRequired;
        }
    }

    public static class Output {
        final Offering[] offeringsSelected;

        public Output(Offering[] offeringsSelected) {
            this.offeringsSelected = offeringsSelected;
        }
    }

    public static Intent createInvocationIntent(Input input) {
        return new IntentHelper().serialize(input).setTargetFragment(OfferingSelectionViewFragment.class).intent();
    }

    public static Output getOutput(Intent output) {
        return new IntentHelper(output).deserialize(Output.class);
    }

    private ProductCategorySpinnerFragment productCategorySpinnerFragment = new ProductCategorySpinnerFragment();
    private OfferingListViewFragment offeringListViewFragment = new OfferingListViewFragment();
    private Button btSelect = null;
    private Input input;

    @Override
    protected int getLayoutId() {
        return R.layout.offering_selection_view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /*
         * The layout is created having two place holders for the next two fragments to be used. The
         * place holders are marked using FrameLayout.
         */
        View result = super.onCreateView(inflater, container, savedInstanceState);
        /*
         * Reuse the fragment that shows the product categories as spinner
         */
        addFragment(R.id.offeringSelectionView_Categories, this.productCategorySpinnerFragment);
        /*
         * Reuse the fragment that shows the offerings as a list view
         */
        addFragment(R.id.offeringSelectionView_OfferingList, this.offeringListViewFragment);

        this.btSelect = result.findViewById(R.id.offeringSelectionView_Select);
        this.btSelect.setOnClickListener(view -> returnResult());
        return result;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentActivity activity = getActivity();

        this.input = null;
        Intent intent = activity.getIntent();
        if (null != intent) {
            this.input = new IntentHelper(intent).deserialize(Input.class);
        }

        if (null == this.input) {
            this.input = new Input(false);
        }

        if (input.selectionRequired == false) {
            this.btSelect.setEnabled(false);
            this.btSelect.setVisibility(View.INVISIBLE);
        }

        activity.setTitle(activity.getTitle() + "- Cardápio");
    }

    protected void returnResult() {
        if (this.input.selectionRequired) {
            Output output = new Output(this.offeringListViewFragment.getListUC().getItemsSelected(new Offering[0]));
            Log.i(getClass().getSimpleName(), "output.offeringsSelected.length=" + output.offeringsSelected.length);
            this.returnResult(new IntentHelper().serialize(output).intent());
        }

        getActivity().finish();
    }
}
