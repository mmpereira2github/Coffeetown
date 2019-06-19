package com.syncinfo.coffeetown.model.dao;

import com.syncinfo.coffeetown.model.UserView;

import java.util.Collection;

/**
 * Created by mmartins on 2018-01-27.
 */

public interface UserViewDao {

    UserView create();

    UserView save(UserView u);

    /**
     * Find all UserView instance whose parent is the one provided as input. The instances found are copied to the
     * collection passed as input and also returned.
     *
     * @param parent
     * @param result
     * @param <C>
     * @return
     */
    <C extends Collection<UserView>> C findPerParent(UserView parent, C result);

    UserView findByLabel(String label);
}
