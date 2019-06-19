package com.syncinfo.coffeetown.model.dao;

import java.util.Collection;

/**
 * Created by mmartins on 2018-02-09.
 */

public interface DAO<Data, IdType> {

    Data create();

    Data save(Data data);

    void remove(Data data);

    Data update(Data data);

    Data findById(IdType id);

    Data getById(IdType id);

    boolean isNew(Data data);

    <R extends Collection<Data>> R getAll(R result);
}
