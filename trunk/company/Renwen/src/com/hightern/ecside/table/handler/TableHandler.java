package com.hightern.ecside.table.handler;

import com.hightern.ecside.table.bean.Table;
import com.hightern.ecside.table.core.PreferencesConstants;
import com.hightern.ecside.table.core.TableCache;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.core.TableModelUtils;

public class TableHandler {

    protected TableModel model;
    private Table table;

    public TableHandler(TableModel model) {
        this.model = model;
    }

    public Table getTable() {
        return table;
    }

    public void addTable(Table table) {
        this.table = table;
        addTableAttributes();
        table.defaults();
    }

    public void addTableAttributes() {
        String interceptor = TableModelUtils.getInterceptPreference(model, table.getInterceptor(), PreferencesConstants.TABLE_INTERCEPTOR);
        table.setInterceptor(interceptor);
        TableCache.getInstance().getTableInterceptor(interceptor).addTableAttributes(model, table);
    }

    public String prefixWithTableId() {
        return table.getTableId() + "_";
    }

    public Integer getTotalRows() {
        return (Integer) table.getAttribute(TableConstants.TOTAL_ROWS);
    }

    public void setTotalRows(Integer totalRows) {
        table.addAttribute(TableConstants.TOTAL_ROWS, totalRows);
    }
}
