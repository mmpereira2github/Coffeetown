package com.syncinfo.android.coffeetown.view;

import com.syncinfo.android.mvc.view.ListUCAdapterAsBaseAdapter;
import com.syncinfo.android.mvc.view.fragment.SpinnerViewFragment;
import com.syncinfo.coffeetown.model.Command;
import com.syncinfo.coffeetown.uc.CommandListUC;

/**
 * Created by mmartins on 2018-02-14.
 */

public class CommandSpinnerFragment extends SpinnerViewFragment<Command, CommandListUC> {

    public CommandSpinnerFragment() { super(); setListUC(new CommandListUC());}

    /**
     * Subclass overrides this method to provide a way to convert a ProductCategory instance into a string.
     * @return product category name
     */
    protected ListUCAdapterAsBaseAdapter.ToCharSequenceConverter<Command> getToCharSequenceConverter() {
        return c -> c.getLabel() != null? c.getLabel(): String.valueOf(c.getId());
    }
}
