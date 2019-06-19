package com.syncinfo.coffeetown.model.dao.impl;

import com.syncinfo.coffeetown.model.Reference;
import com.syncinfo.coffeetown.model.dao.DAO;

/**
 * Created by mmartins on 2018-02-12.
 */

public class BasicReference<DataType, IdType> implements Reference<DataType, IdType> {
    private IdType id = null;
    private DataType data = null;
    private DAO<DataType, IdType> dao = null;

    public BasicReference(DataType data, IdType id) {
        this.id = id;
        this.data = data;
    }

    public BasicReference(DAO<DataType, IdType> dao, IdType id) {
        this.id = id;
        this.dao = dao;
    }

    @Override
    public DataType get() {
        if (null == this.data) {
            return this.data = this.dao.findById(this.id);
        }
        else {
            return this.data;
        }
    }

    @Override
    public IdType getId() {
        return this.id;
    }
}
