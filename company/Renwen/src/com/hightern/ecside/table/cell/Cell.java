package com.hightern.ecside.table.cell;

import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableModel;

public interface Cell {

    public String getExportDisplay(TableModel model, Column column);

    public String getHtmlDisplay(TableModel model, Column column);
}
