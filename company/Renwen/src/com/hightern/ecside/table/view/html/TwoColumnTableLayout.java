package com.hightern.ecside.table.view.html;

import com.hightern.ecside.table.bean.Table;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public abstract class TwoColumnTableLayout {

    private HtmlBuilder html;
    private TableModel model;

    public TwoColumnTableLayout(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
    }

    protected HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    public void layout() {
        if (!showLayout(model)) {
            return;
        }

        html.table(0).border("0").cellPadding("0").cellSpacing("0");
        Table table = model.getTableHandler().getTable();
        html.width(table.getWidth()).close();
        html.tr(1).close();
        columnLeft(html, model);
        columnRight(html, model);
        html.trEnd(1);

        html.tableEnd(0);
        html.newline();
    }

    public String toString() {
        return html.toString();
    }

    protected abstract boolean showLayout(TableModel model);

    protected abstract void columnLeft(HtmlBuilder html, TableModel model);

    protected abstract void columnRight(HtmlBuilder html, TableModel model);
}
