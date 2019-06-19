package com.syncinfo.coffeetown.model.dao;

import com.syncinfo.coffeetown.model.UserView;
import com.syncinfo.coffeetown.model.Waiter;

import java.util.Collection;

/**
 * Created by mmartins on 2017-12-30.
 */

public interface WaiterDao extends DAO<Waiter, Integer> {
    Waiter findWaiterByName(String name);
    void remove(int waiterId);

    Waiter findWaiterById(int waiterId);
    Waiter getWaiterById(int waiterId);

    <C extends Collection<Waiter>> C getAll(C result);

    <C extends Collection<Waiter>> C findByNamePattern(String namePattern, C result);
}
