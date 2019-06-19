package com.syncinfo.android.mvc.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.syncinfo.android.R;
import com.syncinfo.android.mvc.view.ListUCAdapterAsBaseAdapter;
import com.syncinfo.android.mvc.view.ListUCAdapterAsSpinnerAdapter;
import com.syncinfo.android.mvc.view.fragment.ViewFragment;
import com.syncinfo.mvc.uc.ListUC;
import com.syncinfo.util.Matcher;

/**
 * Created by mmartins on 2018-02-26.
 */

public class SpinnerViewFragment <ListItemType, ListUCType extends ListUC<ListItemType>>
        extends ViewFragment implements AdapterView.OnItemSelectedListener {

    private AdapterView.OnItemClickListener onItemClickListener = null;
    private Spinner spinner = null;
    private ListUCType listUC = null;

    public Spinner getSpinner() { return this.spinner; }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public ListUCType getListUC() {
        return listUC;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.basic_spinner_fragment;
    }

    protected int getSpinnerId() {
        return R.id.spBasic;
    }

    protected int getSpinnerItemLayoutId() {
        return R.layout.basic_spinner_item;
    }

    protected int getSpinnerItemId() {
        return R.id.basicSpinnerItem_TextView;
    }

    public void setListUC(ListUCType listUC) {
        this.listUC = listUC;
        this.listUC.registerOnResetAdapterListener(adapter -> createAndroidAdapter());
        createAndroidAdapter();
    }

    protected ListUCAdapterAsBaseAdapter.ToCharSequenceConverter<ListItemType> getToCharSequenceConverter() {
        return null;
    }

    protected ListUCAdapterAsBaseAdapter.ViewFromResourceHandler<ListItemType> getViewFromResourceHandler() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = super.onCreateView(inflater, container, savedInstanceState);
        Log.i(ViewFragment.class.getSimpleName(), getClass().getSimpleName() + "::onCreateView");
        assert result != null;
        this.spinner = result.findViewById(getSpinnerId());
        createAndroidAdapter();
        this.spinner.setOnItemSelectedListener(this);

        this.getListUC().registerOnItemSelectedListener(
                (index, selected, adapter) -> this.spinner.setSelection(index, selected));
        if (this.getListUC().hasSelection()) {
            this.spinner.setSelection(this.getListUC().positionSelected());
        }
        else {
            Log.i(getClass().getSimpleName(), "No item selected in LIST UC");
        }

//        this.getListUC().registerOnResetAdapterListener(
//                (adapter) -> this.spinner.setSelection(this.spinner.getSelectedItemPosition(), false));
//
        return result;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        getListUC().setItemSelected(position, !getListUC().isSelected(position));
        Log.i("SpinnerFragment", "isSelected=" + getListUC().isSelected(position));
        if (null != this.onItemClickListener) {
            this.onItemClickListener.onItemClick(parent, view, position, id);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> var1) {
        Log.i("SpinnerFragment", "onNothingSelected");
        getListUC().unselectAllItems();
    }

    private void createAndroidAdapter() {
        Log.i(getClass().getSimpleName(), "createAndroidAdapter");
        if (null != this.listUC) {
            Log.i(getClass().getSimpleName(), "null != this.listUC");
            if (this.spinner != null) {
                Log.i(getClass().getSimpleName(), "this.spinner != null");
                ListUCAdapterAsSpinnerAdapter<ListItemType> listAdapter = new ListUCAdapterAsSpinnerAdapter<>(
                        getListUC().getAdapter(), getActivity(), getSpinnerItemLayoutId(),
                        getSpinnerItemId(), getToCharSequenceConverter());

                listAdapter.setViewFromResourceHandler(getViewFromResourceHandler());

                this.spinner.setAdapter(listAdapter);
            }
        }
    }

    public boolean setSpinnerSelection(Matcher<ListItemType> m) {
        ListUC.Adapter<ListItemType> adapter = getListUC().getAdapter();
        if (null != adapter) {
            int count = adapter.getCount();
            for (int i = 0; i < count; i++) {
                ListItemType item = adapter.getItem(i);
                if (m.matches(item)) {
                    getSpinner().setSelection(i);
                    return true;
                }
            }
        }

        return false;
    }

}
