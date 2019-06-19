package com.syncinfo.android.coffeetown.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.syncinfo.android.coffeetown.R;
import com.syncinfo.android.mvc.view.ListUCAdapterAsBaseAdapter;
import com.syncinfo.android.mvc.view.fragment.ListViewFragment;
import com.syncinfo.coffeetown.model.Table;
import com.syncinfo.coffeetown.model.Waiter;
import com.syncinfo.coffeetown.uc.TableListUC;

import java.text.MessageFormat;

/**
 * Created by mmartins on 2018-01-21.
 */

public class TableListPerWaiterViewFragment extends ListViewFragment<Table, TableListUC> {

    public static Intent createInvocationIntent(Waiter waiter) {
        return new IntentHelper().serialize(waiter).setTargetFragment(TableListPerWaiterViewFragment.class).intent();
    }

    private Waiter waiter = null;

    public TableListPerWaiterViewFragment() {}

//    @Override
//    protected int getLayoutId() {
//        return R.layout.table_list_per_waiter_view;
//    }
//
//    /**
//     * @return Id of the Android ListView
//     */
//    @Override
//    protected int getListViewId() {
//        return R.id.lvTableListPerWaiter_Tables;
//    }
//
//    /**
//     * @return layout of the list item
//     */
//    @Override
//    protected int getListViewItemLayoutId() {
//        return R.layout.table_list_item_view;
//    }
//
//    /**
//     * @return id of the item inside the list item layout
//     */
//    @Override
//    protected int getListViewItemId() { return R.id.tvTableListItem_TableLabel; }

    @Override
    protected ListUCAdapterAsBaseAdapter.ViewFromResourceHandler<Table> getViewFromResourceHandler() {
        return (position, view, textView, isViewReused, table) -> {
            textView.setText(table.getLabel());
            ImageButton menu = view.findViewById(R.id.basicListItem_MenuButton);
            menu.setOnClickListener(v -> start(CommandMainViewFragment.createInvocationIntent(table)));
            return view;
        };
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setListUC(new TableListUC());
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();
        if (this.waiter == null) {
            this.waiter = new IntentHelper(activity.getIntent()).deserialize(Waiter.class);
        }

        getListUC().listByWaiter(this.waiter.getId());

        String label = getText(R.string.TableListPerWaiter_Title).toString();
        getLabel().setText(MessageFormat.format(label,new Object[] {waiter.getName()}));
    }
}
