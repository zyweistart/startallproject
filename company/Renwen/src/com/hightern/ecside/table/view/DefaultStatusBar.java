package com.hightern.ecside.table.view;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.html.BuilderConstants;
import com.hightern.ecside.table.view.html.BuilderUtils;
import com.hightern.ecside.table.view.html.StatusBarBuilder;
import com.hightern.ecside.table.view.html.ToolbarBuilder;
import com.hightern.ecside.table.view.html.TwoColumnRowLayout;
import com.hightern.ecside.util.HtmlBuilder;

public class DefaultStatusBar extends TwoColumnRowLayout {

    public DefaultStatusBar(HtmlBuilder html, TableModel model) {
        super(html, model);
    }

    protected boolean showLayout(TableModel model) {
        boolean showStatusBar = BuilderUtils.showStatusBar(model);
        boolean filterable = BuilderUtils.filterable(model);
        if (!showStatusBar && !filterable) {
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

    protected void columnRight(HtmlBuilder html, TableModel model) {
        boolean filterable = BuilderUtils.filterable(model);
        if (!filterable) {
            return;
        }

        html.td(4).styleClass(BuilderConstants.FILTER_BUTTONS_CSS).close();

        html.img();
        html.src(BuilderUtils.getImage(model, BuilderConstants.TOOLBAR_FILTER_ARROW_IMAGE));
        html.style("border:0");
        html.alt("Arrow");
        html.xclose();

        html.nbsp();

        ToolbarBuilder toolbarBuilder = new ToolbarBuilder(html, model);
        toolbarBuilder.filterItemAsImage();

        html.nbsp();

        toolbarBuilder.clearItemAsImage();

        html.tdEnd();
    }
}
