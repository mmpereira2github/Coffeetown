package com.syncinfo.coffeetown.model.dao.impl;

import com.syncinfo.coffeetown.model.Waiter;
import com.syncinfo.coffeetown.model.dao.WaiterDao;

import java.util.Collection;

import java.util.regex.Pattern;

/**
 * Created by mmartins on 2017-12-30.
 */

public class InMemoryWaiterDao extends InMemoryDao<Waiter, Integer> implements WaiterDao {
    private static int nextId = 1;

    public InMemoryWaiterDao() {}

    @Override
    public Waiter findWaiterByName(String name) {
        return this.findSingle(w -> name.equalsIgnoreCase(w.getName()));
    }

    @Override
    public void remove(int waiterId) {
        this.remove(this.findSingle(w->waiterId == w.getId()));
    }

    @Override
    protected void setId(Waiter waiter) {
        waiter.setId(nextId++);
    }

    @Override
    protected Integer getId(Waiter waiter) {
        return waiter.getId();
    }

    public Waiter findWaiterById(int waiterId) {
        return this.findSingle((i) -> i.getId() == waiterId);
    }

    @Override
    public Waiter getWaiterById(int waiterId) {
        return this.get((w) -> waiterId == w.getId(),
                () -> { throw new IllegalArgumentException("Waiter whose id=[" + waiterId + "] does not exist"); });
    }

    @Override
    public <C extends Collection<Waiter>> C getAll(C result) {
        return this.findAll(w-> true, result);
    }

    @Override
    public <C extends Collection<Waiter>> C findByNamePattern(String namePattern, final C result) {
        Pattern m = Pattern.compile(namePattern);
        return this.findAll((w) -> m.matcher(w.getName()).matches(), result);
    }

    @Override
    public Waiter create() {
        return new Waiter();
    }
}
