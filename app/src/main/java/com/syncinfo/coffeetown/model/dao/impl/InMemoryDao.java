package com.syncinfo.coffeetown.model.dao.impl;

import com.syncinfo.coffeetown.model.dao.DAO;
import com.syncinfo.util.Searcher;
import com.syncinfo.util.Assert;
import com.syncinfo.util.Matcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mmartins on 2018-01-27.
 */

public abstract class InMemoryDao<T, IdType> implements DAO<T, IdType> {
    private List<T> cache = new ArrayList<>();
    private Map<IdType, T> index = new HashMap<>();

    protected abstract void setId(T t);
    protected abstract IdType getId(T t);

    @Override
    public T save(T t) {
        setId(t);
        IdType id = getId(t);
        if (index.containsKey(id)) {
            throw new IllegalArgumentException("Duplicate instance=[" + id + "]");
        }

        index.put(id, t);
        Assert.isTrue(this.cache.add(t), ()->{throw new RuntimeException(t + " not added");} );
        return t;
    }

    @Override
    public void remove(T t) {
        this.cache.remove(t);
        this.index.remove(getId(t));
    }

    protected T findSingle(Matcher<T> m) {
        return Searcher.search(this.cache, Assert.notNull(m, "Matcher"));
    }

    protected <R extends Collection<T>> R findAll(Matcher<T> m, R result) {
        Assert.notNull(m, "Matcher");
        return Searcher.searchAll(this.cache, m, result);
    }

    protected <E extends Exception> T get(Matcher<T> m, Assert.Thrower<E> t) throws E {
        Assert.notNull(m, "Matcher");
        return Assert.notNull(findSingle(m), t );
    }

    @Override
    public <R extends Collection<T>> R getAll(R result) {
        result.addAll(this.cache);
        return result;
    }

    @Override
    public T update(T t) {
        return t;
    }

    @Override
    public T findById(IdType id) {
        return this.findSingle(t -> getId(t).equals(id));
    }

    @Override
    public T getById(IdType id) {
        return Assert.notNull(findById(id), "No instance found for id=[" + id + "]");
    }

    @Override
    public boolean isNew(T t) {
        return getId(t) == null;
    }
}
