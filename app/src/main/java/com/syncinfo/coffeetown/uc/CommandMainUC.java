package com.syncinfo.coffeetown.uc;

import com.syncinfo.coffeetown.model.Command;
import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.Offering;
import com.syncinfo.coffeetown.model.Request;
import com.syncinfo.util.ListenerManager;
import com.syncinfo.util.Assert;

/**
 * Created by mmartins on 2018-02-27.
 */

public class CommandMainUC {
    public interface OnCommandChangeListener {
        void onChange(Command command);
    }

    private Command command = null;
    private ListenerManager<OnCommandChangeListener> onChangeListenerListenerManager = new ListenerManager<>();

    public void setCommand(Command command) {
        this.command = command;
    }

    public void addOfferings(Offering...offerings) {
        for (Offering o: offerings) addOffering(o);
    }

    public void addOffering(Offering offering) {
        Assert.notNull(this.command, "command");
        reread();
        if (this.command.getStatus() == Command.Status.OPENED) {
            Request request = Model.getInstance().getRequestDAO().create();
            request.setOffering(offering);
            request.setCommandId(this.command.getId());
            request.setStatus(Request.Status.NOT_DELIVERED);
            Model.getInstance().getRequestDAO().save(request);
            this.onChangeListenerListenerManager.notify(l -> l.onChange(this.command));
        }
        else {
            throw new IllegalStateException("Command=[" + this.command.getLabel() +
                    "] in status=[" + this.command.getStatus() + "]");
        }
    }

    public void changeLabel(String newLabel) {
        reread();
        if (null != command.getLabel()) {
            if (command.getLabel().equals(newLabel) == false) {
                command.setLabel(newLabel);
                Model.getInstance().getCommandDAO().save(this.command);
                this.onChangeListenerListenerManager.notify(l -> l.onChange(this.command));
            }
        }
    }

    private void reread() {
        this.command = Model.getInstance().getCommandDAO().getById(this.command.getId());
    }

    public void registerOnCommandChangeListener(OnCommandChangeListener listener) {
        this.onChangeListenerListenerManager.registerListener(listener);
    }
}
