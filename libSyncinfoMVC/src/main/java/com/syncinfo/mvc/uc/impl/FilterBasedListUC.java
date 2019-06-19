package com.syncinfo.mvc.uc.impl;

import com.syncinfo.util.Filter;
import com.syncinfo.util.Assert;

/**
 * Created by mmartins on 2018-02-26.
 */

public class FilterBasedListUC<T> extends BasicListUC<T> {

    protected Filter<T> currentFilter = null;

    public FilterBasedListUC () { this(Filter.Factory.createEmpty()); }

    public FilterBasedListUC (Filter<T> filter) {
        super();
        Assert.notNull(filter, "filter");
        setChoiceMode(getChoiceMode());
        this.currentFilter = filter;
        resetAdapter();
    }

    @Override
    public void resetAdapter() {
        this.setAdapter(new BasicListUCAdapter<T>(this.currentFilter));
    }
}
