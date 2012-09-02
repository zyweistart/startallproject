package com.hightern.ecside.table.view;

import java.util.Iterator;

import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.html.BuilderConstants;
import com.hightern.ecside.table.view.html.BuilderUtils;
import com.hightern.ecside.table.view.html.TableBuilder;
import com.hightern.ecside.table.view.html.ToolbarBuilder;
import com.hightern.ecside.table.view.html.TwoColumnTableLayout;
import com.hightern.ecside.util.HtmlBuilder;

public class LimitToolbar extends TwoColumnTableLayout {

    public LimitToolbar(HtmlBuilder html, TableModel model) {
        super(html, model);
    }

    protected boolean showLayout(TableModel model) {
        boolean showPagination = BuilderUtils.showPagination(model);
        boolean showExports = BuilderUtils.showExports(model);
        if (!showPagination && !showExports) {
            return false;
        }

        return true;
    }

    protected void columnLeft(HtmlBuilder html, TableModel model) {
        html.td(2).close();
        new TableBuilder(html, model).title();
        html.tdEnd();
    }

    @SuppressWarnings("unchecked")
    protected void columnRight(HtmlBuilder html, TableModel model) {
        boolean showPagination = BuilderUtils.showPagination(model);
        boolean showExports = BuilderUtils.showExports(model);

        ToolbarBuilder toolbarBuilder = new ToolbarBuilder(html, model);

        html.td(2).align("right").close();

        html.table(2).border("0").cellPadding("0").cellSpacing("1").styleClass(
                BuilderConstants.TOOLBAR_CSS).close();

        html.tr(3).close();

        if (showPagination) {

            html.td(4).close();
            toolbarBuilder.firstPageItemAsImage();
            html.tdEnd();

            html.td(4).close();
            toolbarBuilder.prevPageItemAsImage();
            html.tdEnd();

            html.td(4).close();
            toolbarBuilder.nextPageItemAsImage();
            html.tdEnd();

            html.td(4).close();
            toolbarBuilder.separator();
            html.tdEnd();

            html.td(4).style("width:20px").close();
            html.newline();
            html.tabs(4);
            toolbarBuilder.rowsDisplayedDroplist();
            html.img();
            html.src(BuilderUtils.getImage(model,
                    BuilderConstants.TOOLBAR_ROWS_DISPLAYED_IMAGE));
            html.style("border:0");
            html.alt("Rows Displayed");
            html.xclose();
            html.tdEnd();

            if (showExports) {
                html.td(4).close();
                toolbarBuilder.separator();
                html.tdEnd();
            }
        }

        if (showExports) {
            Iterator iterator = model.getExportHandler().getExports().iterator();
            for (Iterator iter = iterator; iter.hasNext();) {
                html.td(4).close();
                Export export = (Export) iter.next();
                toolbarBuilder.exportItemAsImage(export);
                html.tdEnd();
            }
        }

        html.trEnd(3);

        html.tableEnd(2);
        html.newline();
        html.tabs(2);

        html.tdEnd();
    }
}
