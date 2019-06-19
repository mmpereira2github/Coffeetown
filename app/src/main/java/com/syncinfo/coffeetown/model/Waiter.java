package com.syncinfo.coffeetown.model;

/**
 * Created by mmartins on 2017-12-30.
 */

public class Waiter {
    private int id;
    private String name;

    public Waiter() {}

    public Waiter(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
