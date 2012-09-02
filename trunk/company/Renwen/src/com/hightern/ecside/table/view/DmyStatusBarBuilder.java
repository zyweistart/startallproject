package com.hightern.ecside.table.view;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public class DmyStatusBarBuilder {

    public DmyStatusBarBuilder(TableModel model) {
        this(new HtmlBuilder(), model);
    }

    public DmyStatusBarBuilder(HtmlBuilder html, TableModel model) {
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
            html.append(model.getMessages().getMessage(
                    "statusbar.noResultsFound"));
        } else {
            int currentRowsDisplayed = model.getLimit().getCurrentRowsDisplayed();
            if (currentRowsDisplayed == 0) {
                currentRowsDisplayed = model.getLimit().getTotalRows();
            }
            int totalRows = model.getLimit().getTotalRows();
            int totalPages = 1;
            if (currentRowsDisplayed != 0) {
                totalPages = totalRows / currentRowsDisplayed;
            }
            if (currentRowsDisplayed != 0
                    && totalRows % currentRowsDisplayed > 0) {
                totalPages++;
            }
            Integer totalPage = new Integer(totalPages);
            Integer currentPage = new Integer(model.getLimit().getPage());
            Integer total = new Integer(model.getLimit().getTotalRows());
            Integer from = new Integer(model.getLimit().getRowStart() + 1);
            Integer to = new Integer(model.getLimit().getRowEnd());
            Object messageArguments[] = {currentPage, totalPage, from, to,
                total};
            html.append(model.getMessages().getMessage("statusbar.pagesFound",
                    messageArguments));
        }
    }

    @Override
    public String toString() {
        return html.toString();
    }
    private HtmlBuilder html;
    private TableModel model;
}
