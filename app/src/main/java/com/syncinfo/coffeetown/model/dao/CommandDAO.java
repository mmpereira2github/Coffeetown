package com.syncinfo.coffeetown.model.dao;

import com.syncinfo.coffeetown.model.Command;
import com.syncinfo.coffeetown.model.Offering;

import java.util.Collection;

/**
 * Created by mmartins on 2018-02-13.
 */

public interface CommandDAO extends DAO<Command, Integer> {

    <C extends Collection<Command>> C findByTableId(int tableId, C result);
}
