package com.syncinfo.coffeetown.model.dao;

import com.syncinfo.coffeetown.model.Command;
import com.syncinfo.coffeetown.model.Request;
import com.syncinfo.coffeetown.model.Table;

import java.util.Collection;

/**
 * Created by mmartins on 2017-12-30.
 */

public interface TableDao extends DAO<Table, Integer> {

    <C extends Collection<Table>> C findTablesByWaiterId(int waiterId, C result);

    Command getRequestList(int tableId);

    Command addRequest(int tableId, Request request);

    Command changeStatus(int requestId, Request.Status status);

    Command changeStatus(int requestListId);

    Table changeWaiter(Table table, int waiterId, boolean refresh);
    Table removeWaiter(Table table, boolean refresh);

    Table findTableByLabel(String label);
    Table getTableByLabel(String label);
}
