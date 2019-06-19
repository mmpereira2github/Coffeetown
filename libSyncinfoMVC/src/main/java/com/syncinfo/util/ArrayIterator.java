package com.syncinfo.util;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Created by mmartins on 2018-01-28.
 */

public class ArrayIterator<T> implements Iterator<T> {
    private T[] array = null;
    private int nextItem = 0;

    public ArrayIterator (T...items) {
        this.array = items;
    }

    @Override
    public boolean hasNext() {
        return null != this.array && this.nextItem < this.array.length;
    }

    @Override
    public T next() {
        return null != this.array && this.nextItem < this.array.length? this.array[this.nextItem++]: null;
    }
}
