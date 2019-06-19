package com.syncinfo.mvc.view.impl;

import com.syncinfo.mvc.view.ViewId;

/**
 * Created by mmartins on 2018-01-01.
 */

public class IntBasedViewId implements ViewId {
    private Integer id;

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (null != o) {
            if (o instanceof StringBasedViewId) {
                return this.id.equals(((IntBasedViewId)o).id);
            }
        }

        return false;
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public IntBasedViewId(int id) {
        this.id = id;
    }
}
