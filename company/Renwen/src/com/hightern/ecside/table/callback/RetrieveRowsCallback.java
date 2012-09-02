package com.hightern.ecside.table.callback;

import java.util.Collection;

import com.hightern.ecside.table.core.TableModel;

public interface RetrieveRowsCallback {

    @SuppressWarnings("unchecked")
    public Collection retrieveRows(TableModel model) throws Exception;
}
