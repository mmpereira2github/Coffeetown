package com.syncinfo.util;

import java.util.Collection;
import java.util.Collections;

/**
 * Functional interface to be used by list views for filter data
 */
public interface Filter<Data> {
    Collection<Data> filter(Collection<Data> result);

    class Factory {
        public static <Data> Filter<Data> createEmpty() {
            return result -> Collections.EMPTY_LIST;
        }
    }
}
