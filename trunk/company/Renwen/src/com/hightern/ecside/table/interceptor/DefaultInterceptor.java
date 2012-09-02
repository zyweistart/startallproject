package com.hightern.ecside.table.interceptor;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.bean.Row;
import com.hightern.ecside.table.bean.Table;
import com.hightern.ecside.table.core.TableModel;

public class DefaultInterceptor implements TableInterceptor, RowInterceptor,
        ColumnInterceptor, ExportInterceptor {

    public void addTableAttributes(TableModel tableModel, Table table) {
    }

    public void addRowAttributes(TableModel tableModel, Row row) {
    }

    public void modifyRowAttributes(TableModel model, Row row) {
    }

    public void addColumnAttributes(TableModel tableModel, Column column) {
    }

    public void modifyColumnAttributes(TableModel model, Column column) {
    }

    public void addExportAttributes(TableModel tableModel, Export export) {
    }
}
