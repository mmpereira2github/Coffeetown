package com.syncinfo.coffeetown.uc;

import com.syncinfo.coffeetown.model.Model;
import com.syncinfo.coffeetown.model.Offering;
import com.syncinfo.mvc.uc.impl.FilterBasedListUC;

/**
 * Created by mmartins on 2018-02-26.
 */

public class OfferingListUC extends FilterBasedListUC<Offering> {

    private final ChoiceMode choiceMode;

    public OfferingListUC() {
        this(ChoiceMode.SINGLE_SELECTION);
    }

    public OfferingListUC(ChoiceMode choiceMode) {
        super(result -> Model.getInstance().getOfferingDAO().getAll(result));
        this.choiceMode = choiceMode;
    }

    @Override
    public ChoiceMode getChoiceMode() { return this.choiceMode; }

}
