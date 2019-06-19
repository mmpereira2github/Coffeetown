package com.syncinfo.android.mvc.view;

import android.content.Context;
import android.widget.ListAdapter;

import com.syncinfo.mvc.uc.ListUC;

/**
 * Created by mmartins on 2018-01-28.
 */

public class ListUCAdapterAsListAdapter<ListItemType> extends ListUCAdapterAsBaseAdapter<ListItemType>
        implements ListAdapter {

    public ListUCAdapterAsListAdapter(ListUC.Adapter<ListItemType> listViewAdapter, Context context, int resource,
                                      int textViewResourceId) {
        super(listViewAdapter, context, resource, textViewResourceId, null);
    }

    public ListUCAdapterAsListAdapter(ListUC.Adapter<ListItemType> listViewAdapter, Context context,
                                      int resource, int textViewResourceId, ToCharSequenceConverter<ListItemType>
                                              converter) {
        super(listViewAdapter, context, resource, textViewResourceId, converter);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }
}
