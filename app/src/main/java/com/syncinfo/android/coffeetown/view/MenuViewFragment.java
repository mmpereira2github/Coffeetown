package com.syncinfo.android.coffeetown.view;

/**
 * Created by mmartins on 2018-01-30.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.syncinfo.android.coffeetown.R;
import com.syncinfo.android.mvc.view.ListUCAdapterAsBaseAdapter;
import com.syncinfo.android.mvc.view.fragment.ListViewFragment;
import com.syncinfo.coffeetown.model.UserView;
import com.syncinfo.coffeetown.uc.MenuListUC;

import java.util.HashMap;
import java.util.Map;

public class MenuViewFragment extends ListViewFragment<UserView, MenuListUC> {
    private Map<UserView, String> rootMenu = new HashMap<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setListUC(new MenuListUC());
        getListUC().registerOnItemSelectedListener(
                (index, selected, adapter) -> showActivity(adapter.getItem(index))
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View result = super.onCreateView(inflater, container, savedInstanceState);
        getLabel().setText("Menu");
        return result;
    }

    @Override
    protected ListUCAdapterAsBaseAdapter.ViewFromResourceHandler<UserView> getViewFromResourceHandler() {
        return (position, view, textView, isViewReused, userView) -> {
            /*
             * Set the waiter name to be shown in the view
             */
            textView.setText(getLocalizedLabel(userView));
            /*
             * Disable menu
             */
            ImageButton menu = view.findViewById(R.id.basicListItem_MenuButton);
            menu.setVisibility(View.INVISIBLE);
            menu.setEnabled(false);

            return view;
        };
    }

    private String getLocalizedLabel(UserView userView) {
        String result = null;
        if (null == userView.getParent()) {
            result = rootMenu.get(userView);
            if (null == result) {
                String packageName = "com.syncinfo.android.coffeetown";
                int id = getResources().getIdentifier(userView.getLabel(), "string", packageName);
                if (0 == id) {
                    result = userView.getLabel();
                }
                else {
                    result = getString(id);
                }
                rootMenu.put(userView, result);
            }
        }

        return result;
    }

    private void showActivity(UserView userView) {
        if (userView.isLauncherValid()) {
            Class<? extends Fragment> theClass = (Class<? extends Fragment>) userView.getLauncher();
            start(new IntentHelper().setTargetFragment(theClass).intent());
        }
        else {
            getErrorHandler().onError("UserView is invalid launcher=[" + userView.getLauncher() + "]");
        }
    }
}
