package com.hightern.ecside.table.view.html;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public abstract class TwoColumnRowLayout {

    private HtmlBuilder html;
    private TableModel model;

    public TwoColumnRowLayout(HtmlBuilder html, TableModel model) {
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
        html.tr(1).style("padding: 0px;").close();
        html.td(2).colSpan(String.valueOf(model.getColumnHandler().columnCount())).close();
        html.table(2).border("0").cellPadding("0").cellSpacing("0").width("100%").close();
        html.tr(3).close();
        columnLeft(html, model);
        columnRight(html, model);
        html.trEnd(3);
        html.tableEnd(2);
        html.newline();
        html.tabs(2);
        html.tdEnd();
        html.trEnd(1);
        html.tabs(2);
        html.newline();
    }

    public String toString() {
        return html.toString();
    }

    protected abstract boolean showLayout(TableModel model);

    protected abstract void columnLeft(HtmlBuilder html, TableModel model);

    protected abstract void columnRight(HtmlBuilder html, TableModel model);
}
