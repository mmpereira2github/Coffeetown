package com.syncinfo.mvc.view.impl;

import com.syncinfo.mvc.view.ViewId;

/**
 * Created by mmartins on 2018-01-01.
 */

public class StringBasedViewId implements ViewId {
    private String id;

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (null != o) {
            if (o instanceof StringBasedViewId) {
                return this.id.equals(((StringBasedViewId)o).id);
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return id;
    }

    public StringBasedViewId(String id) {
        this.id = id;
    }
}
