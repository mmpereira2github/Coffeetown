package com.syncinfo.util;

import com.syncinfo.util.Matcher;

import java.util.Collection;

/**
 * Created by mmartins on 2018-01-21.
 */

public class Searcher {

    public static <CollectionItem> CollectionItem search(Collection<CollectionItem> c, Matcher<CollectionItem> m) {
        if (null != m && null != c) {
            for (CollectionItem i : c) {
                if (null != i) {
                    if (m.matches(i)) {
                        return i;
                    }
                }
            }
        }
        return null;
    }

    public static <CollectionItem, R extends Collection<CollectionItem>> R searchAll(Collection<CollectionItem> c, Matcher<CollectionItem> m, R result) {
        if (null != m && null != c) {
            for (CollectionItem i : c) {
                if (null != i) {
                    if (m.matches(i)) {
                        result.add(i);
                    }
                }
            }
        }
        return result;
    }
}
