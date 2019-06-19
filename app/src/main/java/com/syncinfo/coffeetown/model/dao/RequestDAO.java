package com.syncinfo.coffeetown.model.dao;

import com.syncinfo.coffeetown.model.Request;

import java.util.Collection;

/**
 * Created by mmartins on 2018-02-12.
 */

public interface RequestDAO extends DAO<Request, Integer> {

    <C extends Collection<Request>> C findByCommandId(int requestListId, C result);

    <C extends Collection<Request>> C findByCommandIdAndCategoryId(int commandId, int categoryId, C result);
}
