package com.syncinfo.android.mvc.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SpinnerAdapter;

import com.syncinfo.mvc.uc.ListUC;

/**
 * Created by mmartins on 2018-02-08.
 */

public class ListUCAdapterAsSpinnerAdapter<ListItemType> extends ListUCAdapterAsBaseAdapter<ListItemType>
        implements SpinnerAdapter {

    public ListUCAdapterAsSpinnerAdapter(ListUC.Adapter<ListItemType> listViewAdapter, Context context, int
            resource, int textViewResourceId) {
        super(listViewAdapter, context, resource, textViewResourceId, null);
    }

    public ListUCAdapterAsSpinnerAdapter(ListUC.Adapter<ListItemType> listViewAdapter, Context context,
                                         int resource, int textViewResourceId, ToCharSequenceConverter<ListItemType> converter) {
        super(listViewAdapter, context, resource, textViewResourceId, converter);
    }

    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getView(i, view, viewGroup);
    }
}
