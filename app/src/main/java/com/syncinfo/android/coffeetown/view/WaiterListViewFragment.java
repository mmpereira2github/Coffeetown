package com.syncinfo.android.coffeetown.view;

/**
 * Created by mmartins on 2018-01-30.
 */

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.syncinfo.android.coffeetown.R;
import com.syncinfo.android.mvc.view.ListUCAdapterAsBaseAdapter;
import com.syncinfo.android.mvc.view.fragment.ListViewFragment;
import com.syncinfo.coffeetown.model.Waiter;
import com.syncinfo.coffeetown.uc.WaiterListUC;

public class WaiterListViewFragment extends ListViewFragment<Waiter, WaiterListUC> {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setListUC(new WaiterListUC());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = super.onCreateView(inflater, container, savedInstanceState);
        getLabel().setText(getText(R.string.WaiterListView_Title));
        return result;
    }

    @Override
    protected ListUCAdapterAsBaseAdapter.ViewFromResourceHandler<Waiter> getViewFromResourceHandler() {
        return (position, view, textView, isViewReused, waiter) -> {
            /*
             * Set the waiter name to be shown in the view
             */
            textView.setText(waiter.getName());
            /*
             * Here the image button is configured to show tables per waiter
             */
            ImageButton menu = view.findViewById(R.id.basicListItem_MenuButton);
            menu.setOnClickListener(v -> start(TableListPerWaiterViewFragment.createInvocationIntent(waiter)));
//            menu.setOnClickListener(v -> {
//                PopupMenu popupMenu = new PopupMenu(getActivity(), menu, Gravity.LEFT);
//                popupMenu.setOnMenuItemClickListener(
//                    menuItem -> {
//                        start(TableListPerWaiterViewFragment.createInvocationIntent(waiter));
//                        return true;
//                    });
//
//                popupMenu.getMenuInflater().inflate(R.menu.waiter_list_menu, popupMenu.getMenu());
//                popupMenu.show();
//            });

            return view;
        };
    }
}
