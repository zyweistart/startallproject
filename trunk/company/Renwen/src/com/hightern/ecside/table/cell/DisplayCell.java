package com.hightern.ecside.table.cell;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;

public class DisplayCell extends AbstractCell {

    @Override
    public String getExportDisplay(TableModel model, Column column) {
        return column.getPropertyValueAsString();
    }

    protected String getCellValue(TableModel model, Column column) {
        return column.getValueAsString();
    }
}
