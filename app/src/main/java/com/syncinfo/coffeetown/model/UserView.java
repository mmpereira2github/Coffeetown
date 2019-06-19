package com.syncinfo.coffeetown.model;

import android.util.Log;

import com.syncinfo.util.Assert;

/**
 * Created by mmartins on 2018-01-27.
 */

public class UserView {
    private int id;
    private UserView parent = null;
    private String label = null;
    private Class<?> launcher = null;
    private boolean launcherValid = false;

    public String getLabel() { return this.label; }

    public void setLabel(String label) { this.label = label;  }

    public UserView getParent() {
        return parent;
    }

    public void setParent(UserView parent) {
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Class<?> getLauncher() {
        return launcher;
    }
    public boolean isLauncherValid() { return launcherValid; }

    public void setLauncher(Class<?> launcherClass) {
        Assert.notNull(launcherClass, "launcherClass");
        this.launcher = launcherClass;
        this.launcherValid = true;
    }

    public void setLauncher(String launcher) {
        this.launcher = null;
        this.launcherValid = false;
        try {
            setLauncher(Class.forName(launcher));
        } catch (ClassNotFoundException e) {
            Log.e("UserView", "Inflating " + launcher, e);
        }
    }
}
