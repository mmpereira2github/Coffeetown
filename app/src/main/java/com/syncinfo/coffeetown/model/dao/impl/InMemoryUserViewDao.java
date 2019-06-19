package com.syncinfo.coffeetown.model.dao.impl;

import com.syncinfo.coffeetown.model.UserView;
import com.syncinfo.coffeetown.model.dao.UserViewDao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mmartins on 2018-01-27.
 */

public class InMemoryUserViewDao extends InMemoryDao<UserView, Integer> implements UserViewDao {
    private static AtomicInteger nextId = new AtomicInteger(1);

    @Override
    protected void setId(UserView userView) {
        userView.setId(nextId.getAndIncrement());
    }

    @Override
    protected Integer getId(UserView userView) {
        return userView.getId();
    }

    @Override
    public UserView create() {
        return new UserView();
    }

    @Override
    public <C extends Collection<UserView>> C findPerParent(UserView parent, C result) {
        return this.findAll((uv) -> uv.getParent() == null, result);
    }

    @Override
    public UserView findByLabel(String label) {
        if (null != label) {
            return this.findSingle(u -> label.equalsIgnoreCase(u.getLabel()));
        }
        else {
            return null;
        }
    }
}
