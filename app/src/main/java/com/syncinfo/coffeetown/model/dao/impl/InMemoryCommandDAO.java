package com.syncinfo.coffeetown.model.dao.impl;

import com.syncinfo.coffeetown.model.Command;
import com.syncinfo.coffeetown.model.dao.CommandDAO;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mmartins on 2018-02-13.
 */

public class InMemoryCommandDAO extends InMemoryDao<Command, Integer> implements CommandDAO {
    private static final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Command create() {
        return new Command();
    }

    @Override
    protected void setId(Command command) {
        command.setId(nextId.getAndIncrement());
        if (null == command.getLabel()) {
            command.setLabel("Comanda " + command.getId());
        }
    }

    @Override
    protected Integer getId(Command command) {
        return command.getId();
    }

    @Override
    public <C extends Collection<Command>> C findByTableId(int tableId, C result) {
        return this.findAll(c -> c.getTableId() == tableId, result);
    }
}
