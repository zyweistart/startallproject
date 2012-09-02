package com.hightern.ecside.table.callback;

import java.util.Collection;

import com.hightern.ecside.table.core.TableModel;

public interface SortRowsCallback {

    @SuppressWarnings("unchecked")
    public Collection sortRows(TableModel model, Collection rows) throws
            Exception;
}
