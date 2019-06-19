package com.syncinfo.android.coffeetown.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.syncinfo.android.mvc.view.ListUCAdapterAsBaseAdapter;
import com.syncinfo.android.mvc.view.fragment.ListViewFragment;
import com.syncinfo.coffeetown.model.Command;
import com.syncinfo.coffeetown.uc.AggregatedRequestListUC;

/**
 * Created by mmartins on 2018-02-26.
 */

public class AggregatedRequestListViewFragment
        extends ListViewFragment<AggregatedRequestListUC.AggregatedRequest, AggregatedRequestListUC> {

    public AggregatedRequestListViewFragment() {
        super();
        setListUC(new AggregatedRequestListUC());
        getListUC().registerOnCommandSetListener(command -> setLabel());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setLabel();
    }

    private void setLabel() {
        Command command = getListUC().getCommand();
        if (getLabel() != null) {
            getLabel().setText("Pedidos " + (command != null ? command.getLabel() : ""));
        }
    }

    @Override
    protected ListUCAdapterAsBaseAdapter.ViewFromResourceHandler<AggregatedRequestListUC.AggregatedRequest> getViewFromResourceHandler() {
        return (position, view, textView, isViewReused, aggregatedRequest) -> {
            /*
             * Set the waiter name to be shown in the view
             */
            textView.setText(aggregatedRequest.offering.getProduct().getName() + "\n(" + aggregatedRequest.getStatus() + ") R$" + aggregatedRequest.getTotal());
            return view;
        };
    }
}
