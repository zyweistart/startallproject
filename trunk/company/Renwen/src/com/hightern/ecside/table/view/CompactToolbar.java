package com.hightern.ecside.table.view;

import java.util.Iterator;

import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.html.BuilderConstants;
import com.hightern.ecside.table.view.html.BuilderUtils;
import com.hightern.ecside.table.view.html.StatusBarBuilder;
import com.hightern.ecside.table.view.html.ToolbarBuilder;
import com.hightern.ecside.table.view.html.TwoColumnRowLayout;
import com.hightern.ecside.util.HtmlBuilder;

public class CompactToolbar extends TwoColumnRowLayout {

    public CompactToolbar(HtmlBuilder html, TableModel model) {
        super(html, model);
    }

    protected boolean showLayout(TableModel model) {
        boolean showStatusBar = BuilderUtils.showStatusBar(model);
        boolean filterable = BuilderUtils.filterable(model);
        boolean showExports = BuilderUtils.showExports(model);
        boolean showPagination = BuilderUtils.showPagination(model);
        boolean showTitle = BuilderUtils.showTitle(model);
        if (!showStatusBar && !filterable && !showExports && !showPagination
                && !showTitle) {
            return false;
        }

        return true;
    }

    protected void columnLeft(HtmlBuilder html, TableModel model) {
        boolean showStatusBar = BuilderUtils.showStatusBar(model);
        if (!showStatusBar) {
            return;
        }

        html.td(4).styleClass(BuilderConstants.STATUS_BAR_CSS).close();

        new StatusBarBuilder(html, model).statusMessage();

        html.tdEnd();
    }

    @SuppressWarnings("unchecked")
    protected void columnRight(HtmlBuilder html, TableModel model) {
        boolean filterable = BuilderUtils.filterable(model);
        boolean showPagination = BuilderUtils.showPagination(model);
        boolean showExports = BuilderUtils.showExports(model);

        ToolbarBuilder toolbarBuilder = new ToolbarBuilder(html, model);

        html.td(4).styleClass(BuilderConstants.COMPACT_TOOLBAR_CSS).align(
                "right").close();

        html.table(4).border("0").cellPadding("1").cellSpacing("2").close();
        html.tr(5).close();

        if (showPagination) {
            html.td(5).close();
            toolbarBuilder.firstPageItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.prevPageItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.nextPageItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.lastPageItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.separator();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.rowsDisplayedDroplist();
            html.tdEnd();

            if (showExports) {
                html.td(5).close();
                toolbarBuilder.separator();
                html.tdEnd();
            }
        }

        if (showExports) {
            Iterator iterator = model.getExportHandler().getExports().iterator();
            for (Iterator iter = iterator; iter.hasNext();) {
                html.td(5).close();
                Export export = (Export) iter.next();
                toolbarBuilder.exportItemAsImage(export);
                html.tdEnd();
            }
        }

        if (filterable) {
            if (showExports || showPagination) {
                html.td(5).close();
                toolbarBuilder.separator();
                html.tdEnd();
            }

            html.td(5).close();
            toolbarBuilder.filterItemAsImage();
            html.tdEnd();

            html.td(5).close();
            toolbarBuilder.clearItemAsImage();
            html.tdEnd();
        }

        html.trEnd(5);

        html.tableEnd(4);

        html.tdEnd();
    }
}
