package com.syncinfo.coffeetown.model.dao.impl;

import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.Request;
import com.syncinfo.coffeetown.model.Command;
import com.syncinfo.coffeetown.model.Table;
import com.syncinfo.coffeetown.model.Waiter;
import com.syncinfo.coffeetown.model.dao.TableDao;
import com.syncinfo.util.Assert;

import java.util.Collection;

/**
 * Created by mmartins on 2017-12-30.
 */

public class InMemoryTableDao extends InMemoryDao<Table, Integer> implements TableDao {
    private static int nextId = 1;

    public InMemoryTableDao() {}

    @Override
    protected void setId(Table table) {
        table.setId(nextId++);
    }

    @Override
    protected Integer getId(Table table) {
        return table.getId();
    }

    @Override
    public <C extends Collection<Table>> C findTablesByWaiterId(int waiterId, C result) {
        Assert.isTrue(waiterId > 0, () -> {
            throw new IllegalArgumentException("Invalid waiter id=[" + waiterId + "]");
        });

        return this.findAll((Table t) -> null != t.getOwner() && t.getOwner().getId() == waiterId, result);
    }

    @Override
    public Command getRequestList(int tableId) {
        return null;
    }

    @Override
    public Command addRequest(int tableId, Request request) {
        return null;
    }

    @Override
    public Command changeStatus(int requestId, Request.Status status) {
        return null;
    }

    @Override
    public Command changeStatus(int requestListId) {
        return null;
    }

    @Override
    public Table changeWaiter(Table table, int waiterId, boolean refresh) {
        Assert.notNull(table, "Table cannot be null");
        Table result = this.get(t-> t.getId() == table.getId(),  ()->{
            throw new IllegalArgumentException("Table [" + table + "] is not found");});

        Waiter w = Model.getInstance().getWaiterDao().getWaiterById(waiterId);
        result.setOwner(w);
        return result;
    }

    @Override
    public Table removeWaiter(Table table, boolean refresh) {
        Assert.notNull(table, "Table cannot be null");
        Table result = this.get(t-> t.getId() == table.getId(),  ()->{
            throw new IllegalArgumentException("Table [" + table + "] is not found");});

        result.setOwner(null);
        return result;
    }

    @Override
    public Table findTableByLabel(String label) {
        Assert.notNull(label, "label");
        return this.findSingle(t -> label.equalsIgnoreCase(t.getLabel()));
    }

    @Override
    public Table getTableByLabel(String label) {
        return Assert.notNull(findTableByLabel(label), ()->{
            throw new IllegalArgumentException("No table found whose label is [" + label +"]");});
    }

    @Override
    public Table create() {
        return new Table();
    }
}
