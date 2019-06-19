package com.syncinfo.coffeetown.uc;

import com.syncinfo.coffeetown.model.Command;
import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.mvc.uc.impl.FilterBasedListUC;

/**
 * Created by mmartins on 2018-02-26.
 */

public class CommandListUC extends FilterBasedListUC<Command> {

    public CommandListUC() { super(); }

    public CommandListUC (int tableId) {
        super(result -> Model.getInstance().getCommandDAO().findByTableId(tableId, result));
    }

    public void listByTable(int tableId) {
        this.currentFilter = result -> Model.getInstance().getCommandDAO().findByTableId(tableId, result);
        resetAdapter();
    }
}
