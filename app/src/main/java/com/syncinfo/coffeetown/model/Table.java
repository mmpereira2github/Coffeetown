package com.syncinfo.coffeetown.model;

/**
 * Place where people request product and services.
 * @startuml
 * class Table
 * Table --> "0..*" Command: has
 * Table -> "0..1" Waiter: serviced by
 * @enduml
 */
public class Table {
    private int id;
    private String label;
    private Waiter owner;
    private Command command;

    public Table() {}

    public Table(int id, String label, Waiter owner) {
        this.id = id;
        this.label = label;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public String getLabel() {
        return label;
    }

    public Waiter getOwner() {
        return owner;
    }

    public void setOwner(Waiter owner) {
        this.owner = owner;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("id=[%d],label[%s],owner[%s],command=[%s]", id, label, owner, command);
    }

}
