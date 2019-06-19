package com.syncinfo.coffeetown.model.dao.impl;

import com.syncinfo.coffeetown.model.ProductCategory;
import com.syncinfo.coffeetown.model.Request;
import com.syncinfo.coffeetown.model.dao.RequestDAO;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mmartins on 2018-02-12.
 */

public class InMemoryRequestDAO extends InMemoryDao<Request, Integer> implements RequestDAO {
    private static AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Request create() {
        return new Request();
    }

    @Override
    protected void setId(Request request) {
        request.setId(nextId.getAndIncrement());
    }

    @Override
    protected Integer getId(Request request) {
        return request.getId();
    }

    @Override
    public <C extends Collection<Request>> C findByCommandId(int requestListId, C result) {
        return this.findAll(r -> r.getCommandId() == requestListId, result);
    }

    @Override
    public <C extends Collection<Request>> C findByCommandIdAndCategoryId(int commandId, int categoryId, C result) {
        return this.findAll(r -> {
            if (r.getCommandId() != commandId) {
                return false;
            }
            if (r.getOffering().getProduct().getCategories() != null) {
                for (ProductCategory c: r.getOffering().getProduct().getCategories()) {
                    if (c.getId() == categoryId) {
                        return true;
                    }
                }
            }
            return false;
        }, result);
    }
}
