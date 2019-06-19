package com.syncinfo.android.mvc.view;

import android.database.DataSetObserver;
import android.widget.Adapter;

import com.syncinfo.mvc.uc.ListUC;
import com.syncinfo.util.ListenerManager;

/**
 * ListViewAdapterAndroidAdapter is an implementation of android.widget.Adapter that uses a
 * com.syncinfo.mvc.view.ListView.Adapter as source.
 *
 * @param <ListItemType>
 */
public abstract class ListUCAdapterAsAndroidAdapter<ListItemType> implements Adapter {
    private final ListUC.Adapter<ListItemType> listViewAdapter;
    private final ListenerManager<DataSetObserver> dataSetObserverListenerManager = new ListenerManager<>();

    public ListUCAdapterAsAndroidAdapter(ListUC.Adapter<ListItemType> listViewAdapter) {
        this.listViewAdapter = listViewAdapter;
        this.listViewAdapter.registerObserver(
                () -> this.dataSetObserverListenerManager.notify((l)-> l.onChanged()));
    }

    protected ListUC.Adapter<ListItemType> getListViewAdapter() {
        return this.listViewAdapter;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
        this.dataSetObserverListenerManager.registerListener(dataSetObserver);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
        this.dataSetObserverListenerManager.unregisterListener(dataSetObserver);
    }

    @Override
    public int getCount() {
        return this.listViewAdapter.getCount();
    }

    @Override
    public ListItemType getItem(int i) {
        return this.listViewAdapter.getItem(i);
    }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return this.listViewAdapter.isEmpty();
    }
}
