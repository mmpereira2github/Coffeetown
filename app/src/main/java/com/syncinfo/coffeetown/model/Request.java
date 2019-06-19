package com.syncinfo.coffeetown.model;

/**
 * Created by mmartins on 2017-12-30.
 */

public class Request {
    public enum Status {NOT_DELIVERED, DELIVERED, CANCELLED};
    private Offering offering;
    private Status status = Status.NOT_DELIVERED;
    private int id;
    private Integer commandId = null;

    public Request() {}

    public Request(Offering offering) {
        this.offering = offering;
        this.status = Status.NOT_DELIVERED;
    }

    public void setCommandId(int commandId) {
        this.commandId = commandId;
    }

    public int getCommandId() {
        return commandId;
    }

    public Offering getOffering() {
        return this.offering;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOffering(Offering offering) {
        this.offering = offering;
    }

    public void setCommandId(Integer commandId) {
        this.commandId = commandId;
    }
}
