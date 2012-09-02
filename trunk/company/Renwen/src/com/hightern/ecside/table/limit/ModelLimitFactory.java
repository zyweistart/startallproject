package com.hightern.ecside.table.limit;

import com.hightern.ecside.table.core.TableModel;

public final class ModelLimitFactory extends AbstractLimitFactory {

    TableModel model;

    public ModelLimitFactory(TableModel model) {
        this.model = model;
        this.tableId = model.getTableHandler().getTable().getTableId();
        this.prefixWithTableId = model.getTableHandler().prefixWithTableId();
        this.context = model.getContext();
        this.registry = model.getRegistry();
        this.isExported = getExported();
    }

    protected boolean showPagination() {
        return model.getTableHandler().getTable().isShowPagination();
    }
}
