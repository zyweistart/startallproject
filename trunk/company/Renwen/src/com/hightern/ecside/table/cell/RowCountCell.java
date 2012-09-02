package com.hightern.ecside.table.cell;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;

public class RowCountCell extends AbstractCell {

    protected String getCellValue(TableModel model, Column column) {
        int rowcount = ((model.getLimit().getPage() - 1)
                * model.getLimit().getCurrentRowsDisplayed())
                + model.getRowHandler().getRow().getRowCount();
        return String.valueOf(rowcount);
    }
}
