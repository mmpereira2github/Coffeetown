package com.syncinfo.mvc.uc.impl;

import com.syncinfo.mvc.uc.ListUC;
import com.syncinfo.util.Filter;
import com.syncinfo.util.ListenerManager;
import com.syncinfo.util.Matcher;
import com.syncinfo.util.Searcher;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by mmartins on 2018-01-28.
 */

public class BasicListUCAdapter<ListItemType> implements ListUC.Adapter<ListItemType> {
    /**
     * Functional interface to retrieve the id of a ListItemType instance.
     * @param <ListItemType>
     * @param <IdType>
     */
    public interface IdExtractor<ListItemType, IdType> { IdType getId(ListItemType instance); }

    protected List<ListItemType> list = null;
    protected ListenerManager<Observer> onObservers = new ListenerManager<>();

    public BasicListUCAdapter(int size) {
        this.list = new ArrayList<>(size);
    }

    public BasicListUCAdapter(ListItemType...items) {
        this.list = new ArrayList<>(items.length);
        for (ListItemType i: items) if (null != i) this.list.add(i);
    }

    public BasicListUCAdapter(Collection<ListItemType> items) {
        this.list = new ArrayList<>(items);
    }

    public BasicListUCAdapter(Filter<ListItemType> filter) {
        this.list = (List<ListItemType>) filter.filter(new ArrayList<>());
    }

    public BasicListUCAdapter(List<ListItemType> items) {
        this.list = items;
    }

    @Override
    public <C extends Collection<ListItemType>> C findAll(Matcher<ListItemType> matcher, C result) {
        return Searcher.searchAll(this.list, matcher, result);
    }

    @Override
    public boolean hasAllInstances() {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public ListItemType getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public int getPosition(ListItemType item) {
        return this.list.indexOf(item);
    }

    @Override
    public void registerObserver(Observer observer) {
        this.onObservers.registerListener(observer);
    }

    @Override
    public void unregisterObserver(Observer observer) {
        this.onObservers.unregisterListener(observer);
    }
}
