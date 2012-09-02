package com.hightern.ecside.table.view;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public class HtmlView extends AbstractHtmlView {

    protected void beforeBodyInternal(TableModel model) {
        toolbar(getHtmlBuilder(), getTableModel());
        getTableBuilder().tableStart();
        getTableBuilder().theadStart();
        statusBar(getHtmlBuilder(), getTableModel());
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
        new DefaultToolbar(html, model).layout();
    }

    protected void statusBar(HtmlBuilder html, TableModel model) {
        new DefaultStatusBar(html, model).layout();
    }
}
