package com.hightern.ecside.table.view;

import java.util.Iterator;

import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.html.BuilderUtils;
import com.hightern.ecside.table.view.html.TwoColumnRowLayout;
import com.hightern.ecside.util.HtmlBuilder;

public class DmyCompactToolbar extends TwoColumnRowLayout {

    public DmyCompactToolbar(HtmlBuilder html, TableModel model) {
        super(html, model);
    }

    protected boolean showLayout(TableModel model) {
        boolean showStatusBar = BuilderUtils.showStatusBar(model);
        boolean filterable = BuilderUtils.filterable(model);
        boolean showExports = BuilderUtils.showExports(model);
        boolean showPagination = BuilderUtils.showPagination(model);
        boolean showTitle = BuilderUtils.showTitle(model);
        return showStatusBar || filterable || showExports || showPagination || showTitle;
    }

    protected void columnLeft(HtmlBuilder html, TableModel model) {
        boolean showStatusBar = BuilderUtils.showStatusBar(model);
        if (!showStatusBar) {
            return;
        }
        String imgStr = model.getTableHandler().getTable().getTitle();
        if (imgStr != null && imgStr.length() > 0) {
            String imgStrArray[] = imgStr.split("&");
            html.td(4).styleClass("operateToolbar").align("left").close();
            html.table(4).border("0").cellPadding("0").cellSpacing("0").close();
            html.tr(5).close();
            for (int i = 0; i < imgStrArray.length; i++) {
                html.td(5).styleClass("tdSeparate").close();
                html.tdEnd();
                html.td(5).styleClass("tdMouseOut").onmouseover(
                        "this.className='tdMouseOver'").onmouseout(
                        "this.className='tdMouseOut'").close();
                html.append(imgStrArray[i]);
                html.tdEnd();
            }
            html.trEnd(5);
            html.tableEnd(4);
            html.tdEnd();
        } else {
            html.td(4).styleClass("operateToolbar").align("left").close();
            html.table(4).border("0").cellPadding("0").cellSpacing("0").close();
            html.tr(5).close();
            html.td(5).styleClass("tdSeparate").close();
            html.tdEnd();
            html.td(5).styleClass("tdMouseOut").onmouseover(
                    "this.className='tdMouseOver'").onmouseout(
                    "this.className='tdMouseOut'").close();
            html.tdEnd();
            html.trEnd(5);
            html.tableEnd(4);
            html.tdEnd();
        }
    }

    @SuppressWarnings("unchecked")
    protected void columnRight(HtmlBuilder html, TableModel model) {
        boolean filterable = BuilderUtils.filterable(model);
        boolean showPagination = BuilderUtils.showPagination(model);
        boolean showExports = BuilderUtils.showExports(model);
        DmyToolbarBuilder toolbarBuilder = new DmyToolbarBuilder(html, model);
        html.td(4).styleClass("compactToolbar").align("right").close();
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
            html.append(model.getMessages().getMessage("toolbar.text.pageSelect"));
            toolbarBuilder.pageSelectItemDroplist();
            html.tdEnd();
            html.td(5).close();
            html.append(model.getMessages().getMessage("toolbar.text.showNum"));
            toolbarBuilder.rowsDisplayedDroplist();
            html.tdEnd();
            html.td(5).close();
            (new DmyStatusBarBuilder(html, model)).statusMessage();
            html.tdEnd();
            if (showExports) {
                html.td(5).close();
                toolbarBuilder.separator();
                html.tdEnd();
            }
        }
        if (showExports) {
            Iterator iterator = model.getExportHandler().getExports().iterator();
            for (Iterator iter = iterator; iter.hasNext(); html.tdEnd()) {
                html.td(5).close();
                Export export = (Export) iter.next();
                toolbarBuilder.exportItemAsImage(export);
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
