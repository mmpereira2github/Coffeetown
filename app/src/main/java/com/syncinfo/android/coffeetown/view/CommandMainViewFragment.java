package com.syncinfo.android.coffeetown.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.syncinfo.android.coffeetown.R;
import com.syncinfo.android.mvc.view.fragment.ViewFragment;
import com.syncinfo.coffeetown.model.Command;
import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.Offering;
import com.syncinfo.coffeetown.model.Table;
import com.syncinfo.coffeetown.uc.TableCommandsMainUC;

import java.text.MessageFormat;

/**
 * Created by mmartins on 2018-02-13.
 */

public class CommandMainViewFragment extends ViewFragment {

    public static Intent createInvocationIntent(Table table) {
        return new IntentHelper().serialize(table).setTargetFragment(CommandMainViewFragment.class).intent();
    }

    private Table table = null;
    private CommandSpinnerFragment commandSpinnerFragment = new CommandSpinnerFragment();
    private ProductCategorySpinnerFragment productCategorySpinnerFragment = new ProductCategorySpinnerFragment();
    private AggregatedRequestListViewFragment aggregatedRequestListViewFragment = new AggregatedRequestListViewFragment();
    private TableCommandsMainUC tableCommandsMainUC = null;

    public CommandMainViewFragment() {
        this.tableCommandsMainUC =
                new TableCommandsMainUC(commandSpinnerFragment.getListUC(),
                        aggregatedRequestListViewFragment.getListUC(),
                        this.productCategorySpinnerFragment.getListUC());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = getActivity();
        if (this.table == null) {
            this.table = new IntentHelper(activity.getIntent()).deserialize(Table.class);
        }

        String labelFormat = getText(R.string.CommandMainView_Label).toString();
        TextView label = activity.findViewById(R.id.commandMain_Table);
        label.setText(MessageFormat.format(labelFormat, new Object[] { table.getLabel()}));

        this.tableCommandsMainUC.setTable(this.table);
//        this.commandListUC.listByTable(this.table.getId());
//        this.commandSpinnerFragment.setListUC(this.commandListUC);
        this.commandSpinnerFragment = this.addFragment(R.id.commandMain_CommandBlock_List, this.commandSpinnerFragment);

        ImageButton btNewCommand = activity.findViewById(R.id.commandMain_CommandBlock_Plus);
        btNewCommand.setOnClickListener(v -> {
            Command newCommand = Model.getInstance().getCommandDAO().create();
            newCommand.setTableId(this.table.getId());
            Model.getInstance().getCommandDAO().save(newCommand);
            this.commandSpinnerFragment.getListUC().resetAdapter();
            this.commandSpinnerFragment.setSpinnerSelection(command -> command.getId() == newCommand.getId());
        });

        this.addFragment(R.id.commandMain_RequestList, this.aggregatedRequestListViewFragment);

//        this.commandSpinnerFragment.setOnItemClickListener(
//                (parent, view, position, id) -> {
//                    this.command = this.commandSpinnerFragment.getListUC().getAdapter().getItem(position);
//                    this.aggregatedRequestListViewFragment.setCommand(this.command);
//                });

        this.addFragment(R.id.commandMain_Categories, this.productCategorySpinnerFragment);
//        this.productCategorySpinnerFragment.setOnItemClickListener(
//                (parent, view, position, id) -> {
//                    this.selectedCategory = this.productCategorySpinnerFragment.getListUC().getAdapter().getItem(position);
//                    if (this.productCategorySpinnerFragment.isSelectionTheSpecialAllCategory()) {
//                        this.selectedCategory = null;
//                    }
//
//                    this.aggregatedRequestListViewFragment.getListUC().filterByCategory(this.selectedCategory);
//                });

        Button offerings = activity.findViewById(R.id.commandMain_Offering);
        offerings.setOnClickListener(v ->
                startForResult(OfferingSelectionViewFragment.createInvocationIntent(
                        new OfferingSelectionViewFragment.Input(true)),
                        output -> addOfferings(OfferingSelectionViewFragment.getOutput(output).offeringsSelected)));

    }

    protected void addOfferings(Offering[] offerings) {
        if (offerings !=null && offerings.length > 0) {
            this.tableCommandsMainUC.getCommandMainUC().addOfferings(offerings);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.command_main_view;
    }
}
