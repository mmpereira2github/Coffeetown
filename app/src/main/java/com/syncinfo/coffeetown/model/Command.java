package com.syncinfo.coffeetown.model;

import com.syncinfo.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmartins on 2017-12-30.
 */

public class Command {
    public enum Status {DETACHED, OPENED, CLOSED}

    private int id;
    private Status status;
    private int tableId;
    private String label = null;

    public Command() {
        this.id = 0;
        this.status = Status.DETACHED;
        this.tableId = 0;
    }

    public Status getStatus() {
        return status;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        Assert.isTrue(tableId > 0,
                () -> { throw new IllegalArgumentException("tableId must be greater than zero"); } );
        this.tableId = tableId;
        if (Status.DETACHED == this.status) {
            this.status = Status.OPENED;
        }
    }

    public void setStatus(Status status) { this.status = status; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
