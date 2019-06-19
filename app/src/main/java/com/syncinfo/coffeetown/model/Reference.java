package com.syncinfo.coffeetown.model;

import com.syncinfo.coffeetown.model.dao.DAO;

/**
 * Created by mmartins on 2018-02-12.
 */

public interface Reference<DataType, IdType> {
    DataType get();
    IdType getId();
}
