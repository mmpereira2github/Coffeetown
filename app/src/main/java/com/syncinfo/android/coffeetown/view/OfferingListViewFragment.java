package com.syncinfo.android.coffeetown.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

import com.syncinfo.android.coffeetown.R;
import com.syncinfo.android.mvc.view.ListUCAdapterAsBaseAdapter;
import com.syncinfo.android.mvc.view.fragment.ListViewFragment;
import com.syncinfo.coffeetown.model.Offering;
import com.syncinfo.coffeetown.uc.OfferingListUC;

public class OfferingListViewFragment extends ListViewFragment<Offering, OfferingListUC> {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (null == getListUC()) {
            setListUC(new OfferingListUC());
        }
    }

    @Override
    protected ListUCAdapterAsBaseAdapter.ViewFromResourceHandler<Offering> getViewFromResourceHandler() {
        return (position, view, textView, isViewReused, offering) -> {
            setBackgroundColor(view, this.getListUC().isSelected(position));
            textView.setText(offering.getProduct().getName());
            if (isViewReused == false) {
                /*
                 * Disable menu
                 */
                ImageButton menu = view.findViewById(R.id.basicListItem_MenuButton);
                menu.setEnabled(false);
                menu.setVisibility(View.INVISIBLE);
            }

            return view;
        };
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLabel().setText(getText(R.string.OfferingListView_Label));
        setOnItemClickListener(
                (AdapterView<?> parent, View view, int position, long id) ->
                        setBackgroundColor(view, this.getListUC().isSelected(position))
        );
    }

    private void setBackgroundColor(View view, boolean selected) {
        if (selected) {
            view.setBackgroundColor(Color.CYAN);
        }
        else {
            view.setBackgroundColor(Color.TRANSPARENT);
        }
    }
}
