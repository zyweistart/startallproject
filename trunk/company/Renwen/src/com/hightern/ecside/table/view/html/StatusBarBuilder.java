package com.hightern.ecside.table.view.html;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public class StatusBarBuilder {

    private HtmlBuilder html;
    private TableModel model;

    public StatusBarBuilder(TableModel model) {
        this(new HtmlBuilder(), model);
    }

    public StatusBarBuilder(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
    }

    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    public void statusMessage() {
        if (model.getLimit().getTotalRows() == 0) {
            html.append(model.getMessages().getMessage(BuilderConstants.STATUSBAR_NO_RESULTS_FOUND));
        } else {
            Integer total = new Integer(model.getLimit().getTotalRows());
            Integer from = new Integer(model.getLimit().getRowStart() + 1);
            Integer to = new Integer(model.getLimit().getRowEnd());
            Object[] messageArguments = {total, from, to};
            html.append(model.getMessages().getMessage(BuilderConstants.STATUSBAR_RESULTS_FOUND, messageArguments));
        }
    }

    @Override
    public String toString() {
        return html.toString();
    }
}
