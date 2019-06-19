package com.syncinfo.android.mvc.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.syncinfo.android.R;
import com.syncinfo.android.mvc.view.ListUCAdapterAsBaseAdapter;
import com.syncinfo.android.mvc.view.ListUCAdapterAsListAdapter;
import com.syncinfo.mvc.uc.ListUC;

/**
 * ListViewFragment exposes a com.syncinfo.mvc.view.ListView instance as an android.widget.ListView UI through an
 * Fragment. ListViewFragment also creates a ListViewAdapterListAdapter instance to expose the underneath
 * com.syncinfo.mvc.view.ListView.Adapter as an android.widget.ListAdapter to android.widget.ListView.
 *
 * @startuml
 * class android.widget.ListView
 * class com.syncinfo.mvc.view.ListView
 * class ListViewFragment
 * ViewFragment <|- ListViewFragment
 * android.widget.ListView "listViewUI"  <-- ListViewFragment
 * com.syncinfo.mvc.view.ListView "listViewCore" <-- ListViewFragment
 * ListViewFragment .> ListViewAdapterListAdapter : creates
 * @enduml
 *
 * @param <ListItemType>
 */
public abstract class ListViewFragment<ListItemType, ListUCType extends ListUC<ListItemType>> extends ViewFragment {
    private AdapterView.OnItemClickListener onItemClickListener = null;

    private android.widget.ListView listView = null;
    private TextView listViewLabel = null;

    private ListUCType listUC = null;

    public ListUCType getListUC() {
        return listUC;
    }

    public android.widget.ListView getListView() { return this.listView; }

    @Override
    protected int getLayoutId() {
        return R.layout.basic_list_view_fragment;
    }

    protected int getListViewId() {
        return R.id.basicListViewFragment_ListView;
    }

    protected int getListViewItemLayoutId() {
        return R.layout.basic_list_item;
    }

    protected int getListViewItemId() {
        return R.id.basicListItem_TextView;
    }

    protected int getListViewLabelId() { return R.id.basicListViewFragment_Label; }

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
        assert result != null;
        this.listView = result.findViewById(getListViewId());
        createAndroidAdapter();
        this.listView.setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) -> {
                    getListUC().setItemSelected(position, !getListUC().isSelected(position));
                    if (null != this.onItemClickListener) {
                        this.onItemClickListener.onItemClick(parent, view, position, id);
                    }
                }
        );

        int labelId = getListViewLabelId();
        if (labelId > 0) {
            View labelView = result.findViewById(labelId);
            if (labelView instanceof TextView) {
                this.listViewLabel = (TextView) labelView;
            }
        }
        Log.i(getClass().getSimpleName(), "onCreateView, this.listViewLabel=" + this.listViewLabel);
        return result;
    }

    protected TextView getLabel() { return this.listViewLabel; }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void createAndroidAdapter() {
        Log.i(getClass().getSimpleName(), "createAndroidAdapter");
        if (null != this.listView) {
            Log.i(getClass().getSimpleName(), "createAndroidAdapter=true!!!");
            ListUCAdapterAsListAdapter<ListItemType> listAdapter = new ListUCAdapterAsListAdapter<>(
                    getListUC().getAdapter(), getActivity(), getListViewItemLayoutId(),
                    getListViewItemId(), getToCharSequenceConverter());

            listAdapter.setViewFromResourceHandler(getViewFromResourceHandler());

            this.listView.setAdapter(listAdapter);
        }
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}