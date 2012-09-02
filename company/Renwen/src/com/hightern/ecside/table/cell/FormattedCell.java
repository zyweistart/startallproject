package com.hightern.ecside.table.cell;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;

public abstract class FormattedCell extends AbstractCell {

    protected String getCellValue(TableModel model, Column column) {
        Object value = column.getValue();

        if (value == null) {
            return "";
        }

        String result = null;

        if (value instanceof String) {
            result = value.toString();
        } else {
            result = formatColumnValue(model, column);
        }

        return result;
    }

    protected abstract String formatColumnValue(TableModel model, Column column);
}
