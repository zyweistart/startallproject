package com.hightern.ecside.table.core;

import java.util.Map;

import com.hightern.ecside.table.state.State;

public final class TableRegistry extends AbstractRegistry {

    public TableRegistry(TableModel model) {
        this.context = model.getContext();
        this.tableId = model.getTableHandler().getTable().getTableId();
        this.prefixWithTableId = model.getTableHandler().prefixWithTableId();
        this.state = model.getTableHandler().getTable().getState();
        this.stateAttr = model.getTableHandler().getTable().getStateAttr();
        this.autoIncludeParameters = model.getTableHandler().getTable().isAutoIncludeParameters();
        setParameterMap();
    }

    @SuppressWarnings("unchecked")
    protected void handleStateInternal(State state, Map tableParameterMap) {
        state.saveParameters(context, tableId, tableParameterMap);
    }
}
