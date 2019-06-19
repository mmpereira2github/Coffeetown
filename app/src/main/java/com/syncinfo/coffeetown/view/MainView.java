package com.syncinfo.coffeetown.view;

import com.syncinfo.util.ListenerManager;
import com.syncinfo.util.Assert;

/**
 * Created by mmartins on 2018-02-15.
 */

public abstract class MainView<TargetEntity> {
    public interface Controller {
        class Ref<TargetEntity> {
            TargetEntity result = null;
            Ref(TargetEntity result) { this.result = result; }
        }

        interface OnCreateListener<TargetEntity> { void onCreate(Ref<TargetEntity> entity); }
        interface OnSaveListener<TargetEntity> { void onSave(Ref<TargetEntity> entity); }
        interface OnUpdateListener<TargetEntity> { void onUpdate(Ref<TargetEntity> entity); }
        interface OnRemoveListener<TargetEntity> { void onRemove(Ref<TargetEntity> entity); }
    }

    public interface Observer {
        interface OnCreatedListener<TargetEntity> { void onCreated(TargetEntity entity); }
        interface OnSavedListener<TargetEntity> { void onSaved(TargetEntity entity); }
        interface OnUpdatedListener<TargetEntity> { void onUpdated(TargetEntity entity); }
        interface OnRemovedListener<TargetEntity> { void onRemoved(TargetEntity entity); }
    }


    private TargetEntity entity;
    private boolean isNew;
    private ListenerManager<Controller.OnCreateListener<TargetEntity>> onCreateListeners = new ListenerManager<>();
    private ListenerManager<Controller.OnSaveListener<TargetEntity>> onSaveListeners = new ListenerManager<>();
    private ListenerManager<Controller.OnUpdateListener<TargetEntity>> onUpdateListeners = new ListenerManager<>();
    private ListenerManager<Controller.OnRemoveListener<TargetEntity>> onRemoveListeners = new ListenerManager<>();
    private ListenerManager<Observer.OnCreatedListener<TargetEntity>> onCreatedListeners = new ListenerManager<>();
    private ListenerManager<Observer.OnSavedListener<TargetEntity>> onSavedListeners = new ListenerManager<>();
    private ListenerManager<Observer.OnUpdatedListener<TargetEntity>> onUpdatedListeners = new ListenerManager<>();
    private ListenerManager<Observer.OnRemovedListener<TargetEntity>> onRemovedListeners = new ListenerManager<>();


    public MainView(TargetEntity entity) {
        setTargetInstance(entity);
    }

    protected abstract boolean isNew(TargetEntity entity);

    public void setTargetInstance(TargetEntity entity) {
        Assert.notNull(entity, "Entity");
        this.isNew = isNew(entity);
        this.entity = create();
    }

    protected TargetEntity create() {
        Controller.Ref<TargetEntity> ref = new Controller.Ref<>(entity);
        doCreate(ref);
        this.onCreateListeners.notify(l -> l.onCreate(ref));
        this.onCreatedListeners.notify(l -> l.onCreated(ref.result));
        return ref.result;
    }

    protected void doCreate(Controller.Ref<TargetEntity> ref) {}

    protected void doUpdate(Controller.Ref<TargetEntity> ref) {}

    protected void doSave(Controller.Ref<TargetEntity> ref) {}

    protected void doRemove(Controller.Ref<TargetEntity> ref) {}

    protected TargetEntity save() {
        Controller.Ref<TargetEntity> ref = new Controller.Ref<>(entity);
        this.onSaveListeners.notify(l -> l.onSave(ref));
        doSave(ref);
        this.onSavedListeners.notify(l -> l.onSaved(ref.result));
        return ref.result;
    }

    protected TargetEntity update() {
        Controller.Ref<TargetEntity> ref = new Controller.Ref<>(entity);
        this.onUpdateListeners.notify(l -> l.onUpdate(ref));
        doUpdate(ref);
        this.onUpdatedListeners.notify(l -> l.onUpdated(ref.result));
        return ref.result;
    }

    public TargetEntity remove() {
        Controller.Ref<TargetEntity> ref = new Controller.Ref<>(entity);
        this.onRemoveListeners.notify(l -> l.onRemove(ref));
        doUpdate(ref);
        this.onRemovedListeners.notify(l -> l.onRemoved(ref.result));
        return ref.result;
    }

    public TargetEntity saveOrUpdate() {
        Assert.notNull(this.entity, "entity");
        if (this.isNew) {
            return save();
        }
        else {
            return update();
        }
    }

    public TargetEntity getTargetInstance() {
        return entity;
    }
}
