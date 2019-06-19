package com.syncinfo.coffeetown.uc;

import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.UserView;
import com.syncinfo.mvc.uc.impl.FilterBasedListUC;

/**
 * Created by mmartins on 2018-02-27.
 */

public class MenuListUC extends FilterBasedListUC<UserView> {

    public MenuListUC() {
        super(result -> Model.getInstance().getUserViewDao().findPerParent(null, result));
    }

    public void setParentMenu(UserView parent) {
        this.currentFilter = result -> Model.getInstance().getUserViewDao().findPerParent(parent, result);
        resetAdapter();
    }
}
