package com.hightern.ecside.table.callback;

import java.util.Collection;

import com.hightern.ecside.table.core.TableModel;

public interface FilterRowsCallback {

    @SuppressWarnings("unchecked")
    public Collection filterRows(TableModel model, Collection rows) throws
            Exception;
}
