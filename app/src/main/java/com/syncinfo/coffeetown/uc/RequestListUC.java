package com.syncinfo.coffeetown.uc;

import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.Request;
import com.syncinfo.mvc.uc.impl.FilterBasedListUC;

/**
 * Created by mmartins on 2018-02-26.
 */

public class RequestListUC extends FilterBasedListUC<Request> {

    public RequestListUC(int commandId) {
        super(result -> Model.getInstance().getRequestDAO().findByCommandId(commandId, result)); }

    public void listByCommand(int commandId) {
        this.currentFilter = result -> Model.getInstance().getRequestDAO().findByCommandId(commandId, result);
        resetAdapter();
    }
}
