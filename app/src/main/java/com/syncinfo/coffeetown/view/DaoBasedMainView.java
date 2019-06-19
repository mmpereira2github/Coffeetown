package com.syncinfo.coffeetown.view;

import com.syncinfo.coffeetown.model.dao.DAO;
import com.syncinfo.util.Assert;

/**
 * Created by mmartins on 2018-02-15.
 */

public class DaoBasedMainView<TargetEntity, IdType, ImplDao extends DAO<TargetEntity, IdType>> extends
        MainView<TargetEntity> {
    private final ImplDao dao;

    public DaoBasedMainView(IdType id, ImplDao dao) {
        this(dao, Assert.notNull(dao, "DAO").getById(id));
    }

    public DaoBasedMainView(ImplDao dao, TargetEntity entity) {
        super(entity);
        this.dao = dao;
    }

    @Override
    protected boolean isNew(TargetEntity entity) { return this.dao.isNew(entity); }

    @Override
    protected void doCreate(Controller.Ref<TargetEntity> ref) { ref.result = this.dao.create(); }

    @Override
    protected void doSave(Controller.Ref<TargetEntity> ref) { ref.result = this.dao.save(ref.result); }

    @Override
    protected void doUpdate(Controller.Ref<TargetEntity> ref) {
        ref.result = this.dao.update(ref.result);
    }

    @Override
    protected void doRemove(Controller.Ref<TargetEntity> ref) { this.dao.remove(ref.result); }

    public ImplDao getDao() { return dao; }
}
