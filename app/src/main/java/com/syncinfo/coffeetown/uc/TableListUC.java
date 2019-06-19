package com.syncinfo.coffeetown.uc;

import android.util.Log;

import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.Table;
import com.syncinfo.coffeetown.model.Waiter;
import com.syncinfo.mvc.uc.impl.BasicListUC;
import com.syncinfo.mvc.uc.impl.BasicListUCAdapter;
import com.syncinfo.util.Filter;
import com.syncinfo.util.Assert;

/**
 * Created by mmartins on 2018-01-19.
 */

public class TableListUC extends BasicListUC<Table> {

    private Filter<Table> currentFilter = null;

    public TableListUC() {
        this(Filter.Factory.createEmpty());
    }

    public TableListUC(Waiter waiter) {
        this(result -> Model.getInstance().getTableDao().findTablesByWaiterId(waiter.getId(), result));
    }

    public TableListUC(Filter<Table> filter) {
        super();
        Assert.notNull(filter, "filter");
        setChoiceMode(ChoiceMode.SINGLE_SELECTION);
        this.currentFilter = filter;
        resetAdapter();
    }

    @Override
    public void resetAdapter() {
        this.setAdapter(new BasicListUCAdapter<Table>(this.currentFilter));
    }

    public void listByWaiter(int waiterId) {
        Log.i("TableListUC", "waiterId=" + waiterId);
        this.currentFilter = result -> Model.getInstance().getTableDao().findTablesByWaiterId(waiterId, result);
        resetAdapter();
    }
}
