package com.syncinfo.mvc.uc.impl;

import com.syncinfo.mvc.uc.ListUC;
import com.syncinfo.util.ListenerManager;
import com.syncinfo.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mmartins on 2018-01-19.
 */

public abstract class BasicListUC<ListItemType> implements ListUC<ListItemType> {
    private ChoiceMode choiceMode = ChoiceMode.SINGLE_SELECTION;
    private Adapter<ListItemType> adapter = null;
    private Set<Integer> selected = new HashSet<>();
    private ListenerManager<Observer.OnItemSelectedListener<ListItemType>> onItemSelectedListeners = new
            ListenerManager<>();
    private ListenerManager<Observer.OnResetAdapterListener<ListItemType>> onResetAdapterListeners = new
            ListenerManager<>();
    private ListenerManager<Observer.OnUnselectedAllItemsListener<ListItemType>> onUnselectedAllItemsListeners = new
            ListenerManager<>();

    public BasicListUC() {}

    @Override
    public Adapter<ListItemType> getAdapter() {
        return this.adapter;
    }

    @Override
    public ChoiceMode getChoiceMode() { return ChoiceMode.SINGLE_SELECTION; }

    protected BasicListUC<ListItemType> setChoiceMode(ChoiceMode choiceMode) {
        this.choiceMode = choiceMode;
        return this;
    }

    protected BasicListUC<ListItemType> setAdapter(Adapter<ListItemType> adapter) {
        if (null != this.adapter) {
            this.unselectAllItems();
        }

        this.adapter = adapter;
        this.onResetAdapterListeners.notify(l -> l.onResetAdapter(this.adapter));
        this.adapter.registerObserver(() -> this.unselectAllItems());
        return this;
    }

    @Override
    public void setItemSelected(final int index, boolean state) {
        Assert.notNull(this.adapter, () -> {
            throw new IllegalStateException("Adapter not set");
        });
        Assert.isTrue(index < this.adapter.getCount(), () -> {
            throw new IllegalArgumentException("index=[" + index +
                    "] > this.adapter.getCount()=[" + this.adapter.getCount() + "]");
        });

        if (state) {
            if (ChoiceMode.SINGLE_SELECTION == this.choiceMode) {
                for (int pos : this.selected) setItemSelected(pos, false);
            }

            this.selected.add(index);
        } else {
            this.selected.remove(index);
        }

        this.onItemSelectedListeners.notify(l -> l.onItemSelected(index, state, this.adapter));
    }

    @Override
    public void unselectAllItems() {
        if (this.selected.isEmpty()) {
            return;
        } else {
            Set<Integer> unselectedItems = new HashSet<>(this.selected);
            this.selected.clear();
            if (this.onUnselectedAllItemsListeners.count() == 0) {
                if (this.onItemSelectedListeners.count() > 0) {
                    for (Integer i : unselectedItems) {
                        this.onItemSelectedListeners.notify(l -> l.onItemSelected(i, false, this.adapter));
                    }
                }
            } else {
                this.onUnselectedAllItemsListeners.notify(l -> l.onUnselectedAllItems(unselectedItems, this.adapter));
            }
        }
    }

    @Override
    public boolean isSelected(int index) {
        return this.selected.contains(index);
    }

    @Override
    public Collection<Integer> positionsSelected() {
        return Collections.unmodifiableCollection(this.selected);
    }

    @Override
    public int positionSelected() {
        Assert.isTrue(this.selected.isEmpty() == false,
                () -> { throw new IllegalStateException("No item selected");} );
        Assert.isTrue(this.selected.size() == 1,
                () -> { throw new IllegalStateException("More than one item selected (count=" + this.selected.size()+")");} );
        return this.selected.iterator().next();
    }

    @Override
    public boolean hasSelection() {
        return selected.isEmpty() == false;
    }

    @Override
    public ListItemType getItemSelected() {
        Assert.isTrue(this.hasSelection(), () -> {
            throw new IllegalStateException("No selection done");
        });
        for (Integer i: this.positionsSelected()) {
            return Assert.notNull(this.adapter.getItem(i), "item selected");
        }

        throw new IllegalStateException("No selection done");
    }

    @Override
    public <S extends Collection<ListItemType>> S getItemsSelected(S result) {
        for (Integer i: this.positionsSelected()) {
            result.add(Assert.notNull(this.adapter.getItem(i), "item selected, position=" + i));
        }

        return result;
    }

    @Override
    public ListItemType[] getItemsSelected(ListItemType[] array) {
        return getItemsSelected(new ArrayList<>()).toArray(array);
    }

    @Override
    public void registerOnItemSelectedListener(Observer.OnItemSelectedListener<ListItemType> listener) {
        this.onItemSelectedListeners.registerListener(listener);
    }

    @Override
    public void registerOnUnselectedAllItemsListener(Observer.OnUnselectedAllItemsListener<ListItemType> listener) {
        this.onUnselectedAllItemsListeners.registerListener(listener);
    }

    @Override
    public void unregisterOnItemSelectedListener(Observer.OnItemSelectedListener<ListItemType> listener) {
        this.onItemSelectedListeners.registerListener(listener);
    }

    @Override
    public void unregisterOnUnselectedAllItemsListener(Observer.OnUnselectedAllItemsListener<ListItemType> listener) {
        this.onUnselectedAllItemsListeners.unregisterListener(listener);
    }

    @Override
    public void registerOnResetAdapterListener(Observer.OnResetAdapterListener<ListItemType> listener) {
        this.onResetAdapterListeners.registerListener(listener);
    }

    @Override
    public void unregisterOnResetAdapterListener(Observer.OnResetAdapterListener<ListItemType> listener) {
        this.onResetAdapterListeners.unregisterListener(listener);
    }
}
