package com.hightern.ecside.table.view;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public class CompactView extends AbstractHtmlView {

    protected void beforeBodyInternal(TableModel model) {
        getTableBuilder().tableStart();

        getTableBuilder().theadStart();

        getTableBuilder().titleRowSpanColumns();

        toolbar(getHtmlBuilder(), getTableModel());

        getTableBuilder().filterRow();

        getTableBuilder().headerRow();

        getTableBuilder().theadEnd();

        getTableBuilder().tbodyStart();
    }

    protected void afterBodyInternal(TableModel model) {
        getCalcBuilder().defaultCalcLayout();

        getTableBuilder().tbodyEnd();

        getTableBuilder().tableEnd();
    }

    protected void toolbar(HtmlBuilder html, TableModel model) {
        new CompactToolbar(html, model).layout();
    }
}
