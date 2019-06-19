package com.syncinfo.util;

import java.util.Iterator;

/**
 * Created by mmartins on 2018-01-28.
 */
public class SingletonIterator<T> implements Iterator<T> {
    boolean hasNext = true;
    T item = null;

    SingletonIterator() {
        hasNext = false;
    }

    SingletonIterator(T item) {
        this.item = item;
    }

    @Override
    public boolean hasNext() {
        return this.hasNext;
    }

    @Override
    public T next() {
        T result = this.item;
        this.item = null;
        this.hasNext = false;
        return result;
    }
}
