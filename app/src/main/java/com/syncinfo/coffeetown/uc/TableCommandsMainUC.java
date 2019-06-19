package com.syncinfo.coffeetown.uc;

import com.syncinfo.coffeetown.model.Command;
import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.Offering;
import com.syncinfo.coffeetown.model.Table;
import com.syncinfo.util.Assert;

/**
 * Created by mmartins on 2018-02-27.
 */

public class TableCommandsMainUC {

    private Table table = null;
    private CommandListUC commandListUC = null;
    private AggregatedRequestListUC aggregatedRequestListUC = null;
    private ProductCategoryListUC productCategoryListUC = null;
    private CommandMainUC commandMainUC = new CommandMainUC();

    public TableCommandsMainUC(CommandListUC commandListUC, AggregatedRequestListUC aggregatedRequestListUC, ProductCategoryListUC productCategoryListUC) {
        super();
        this.commandListUC = commandListUC;
        this.aggregatedRequestListUC = aggregatedRequestListUC;
        this.productCategoryListUC = productCategoryListUC;

        this.commandListUC.registerOnItemSelectedListener(
                (index, selected, adapter) -> {
                    this.aggregatedRequestListUC.listByCommand(adapter.getItem(index));
                    this.commandMainUC.setCommand(this.aggregatedRequestListUC.getCommand());
                }
        );

        this.productCategoryListUC.registerOnItemSelectedListener(
                (index, selected, adapter) -> {
                    if (this.productCategoryListUC.isSelectionTheSpecialAllCategory()) {
                        this.aggregatedRequestListUC.filterByCategory(null);
                    }
                    else {
                        this.aggregatedRequestListUC.filterByCategory(adapter.getItem(index));
                    }
                }
        );

        this.commandMainUC.registerOnCommandChangeListener(command -> this.aggregatedRequestListUC.resetAdapter());
    }

//    public Command getCurrentCommand() {
//        return this.aggregatedRequestListUC.getCommand();
//    }

    public Table getTable() {
        return table;
    }

    public void setTable(int tableId) {
        setTable(Model.getInstance().getTableDao().getById(tableId));
    }

    public void setTable(Table table) {
        this.table = Assert.notNull(table, "table");
        this.commandListUC.listByTable(table.getId());
        if (this.commandListUC.getAdapter().getCount() > 0) {
            this.commandListUC.setItemSelected(0, true);
        }
    }

    public CommandMainUC getCommandMainUC() {
        return commandMainUC;
    }
}
