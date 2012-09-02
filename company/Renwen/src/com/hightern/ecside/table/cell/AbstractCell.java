package com.hightern.ecside.table.cell;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.html.ColumnBuilder;

public abstract class AbstractCell implements Cell {

    public String getExportDisplay(TableModel model, Column column) {
        return getCellValue(model, column);
    }

    public String getHtmlDisplay(TableModel model, Column column) {
        ColumnBuilder columnBuilder = new ColumnBuilder(column);
        columnBuilder.tdStart();
        columnBuilder.tdBody(getCellValue(model, column));
        columnBuilder.tdEnd();
        return columnBuilder.toString();
    }

    protected abstract String getCellValue(TableModel model, Column column);
}
