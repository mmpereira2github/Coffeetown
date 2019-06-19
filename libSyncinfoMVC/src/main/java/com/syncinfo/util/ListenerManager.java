package com.syncinfo.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by mmartins on 2018-01-28.
 */

public class ListenerManager<ListenerType> { //implements Iterable<ListenerType> {
    private List<ListenerType> listeners = new ArrayList<>();

    public void registerListener(ListenerType l) {
        this.listeners.add(l);
    }

    public void unregisterListener(ListenerType l) {
        this.listeners.remove(l);
    }

    public void notify(Notifier<? super ListenerType> n) {
        for (ListenerType l : this.listeners) n.notify(l);
    }

//    @Override
//    public Iterator<ListenerType> iterator() {
//        return this.listeners.iterator();
//    }
//
    public interface Notifier<ListenerType> {
        void notify(ListenerType l);
    }

    public int count() { return this.listeners.size(); }
}
