package com.hightern.ecside.table.view;

import com.hightern.ecside.table.bean.Export;
import com.hightern.ecside.table.core.Messages;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.html.BuilderUtils;
import com.hightern.ecside.table.view.html.TableActions;
import com.hightern.ecside.table.view.html.toolbar.ButtonItem;
import com.hightern.ecside.table.view.html.toolbar.ImageItem;
import com.hightern.ecside.table.view.html.toolbar.TextItem;
import com.hightern.ecside.table.view.html.toolbar.ToolbarItemUtils;
import com.hightern.ecside.util.HtmlBuilder;

public class DmyToolbarBuilder {

    public DmyToolbarBuilder(TableModel model) {
        this(new HtmlBuilder(), model);
    }

    public DmyToolbarBuilder(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
        messages = model.getMessages();
    }

    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    protected Messages getMessages() {
        return messages;
    }

    public void firstPageItemAsButton() {
        ButtonItem item = new ButtonItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.firstPage"));
        item.setContents(messages.getMessage("toolbar.text.firstPage"));
        ToolbarItemUtils.buildFirstPage(html, model, item);
    }

    public void firstPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.firstPage"));
        item.setDisabledImage(BuilderUtils.getImage(model, "firstPageDisabled"));
        item.setImage(BuilderUtils.getImage(model, "firstPage"));
        item.setAlt(messages.getMessage("toolbar.text.firstPage"));
        item.setStyle("border:0");
        ToolbarItemUtils.buildFirstPage(html, model, item);
    }

    public void firstPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.firstPage"));
        item.setText(messages.getMessage("toolbar.text.firstPage"));
        ToolbarItemUtils.buildFirstPage(html, model, item);
    }

    public void prevPageItemAsButton() {
        ButtonItem item = new ButtonItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.prevPage"));
        item.setContents(messages.getMessage("toolbar.text.prevPage"));
        ToolbarItemUtils.buildPrevPage(html, model, item);
    }

    public void prevPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.prevPage"));
        item.setDisabledImage(BuilderUtils.getImage(model, "prevPageDisabled"));
        item.setImage(BuilderUtils.getImage(model, "prevPage"));
        item.setAlt(messages.getMessage("toolbar.text.prevPage"));
        item.setStyle("border:0");
        ToolbarItemUtils.buildPrevPage(html, model, item);
    }

    public void prevPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.prevPage"));
        item.setText(messages.getMessage("toolbar.text.prevPage"));
        ToolbarItemUtils.buildPrevPage(html, model, item);
    }

    public void nextPageItemAsButton() {
        ButtonItem item = new ButtonItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.nextPage"));
        item.setContents(messages.getMessage("toolbar.text.nextPage"));
        ToolbarItemUtils.buildNextPage(html, model, item);
    }

    public void nextPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.nextPage"));
        item.setDisabledImage(BuilderUtils.getImage(model, "nextPageDisabled"));
        item.setImage(BuilderUtils.getImage(model, "nextPage"));
        item.setAlt(messages.getMessage("toolbar.text.nextPage"));
        item.setStyle("border:0");
        ToolbarItemUtils.buildNextPage(html, model, item);
    }

    public void nextPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.nextPage"));
        item.setText(messages.getMessage("toolbar.text.nextPage"));
        ToolbarItemUtils.buildNextPage(html, model, item);
    }

    public void lastPageItemAsButton() {
        ButtonItem item = new ButtonItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.lastPage"));
        item.setContents(messages.getMessage("toolbar.text.lastPage"));
        ToolbarItemUtils.buildLastPage(html, model, item);
    }

    public void lastPageItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.lastPage"));
        item.setDisabledImage(BuilderUtils.getImage(model, "lastPageDisabled"));
        item.setImage(BuilderUtils.getImage(model, "lastPage"));
        item.setAlt(messages.getMessage("toolbar.text.lastPage"));
        item.setStyle("border:0");
        ToolbarItemUtils.buildLastPage(html, model, item);
    }

    public void lastPageItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.lastPage"));
        item.setText(messages.getMessage("toolbar.text.lastPage"));
        ToolbarItemUtils.buildLastPage(html, model, item);
    }

    public void filterItemAsButton() {
        ButtonItem item = new ButtonItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.filter"));
        item.setContents(messages.getMessage("toolbar.text.filter"));
        ToolbarItemUtils.buildFilter(html, model, item);
    }

    public void filterItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.filter"));
        item.setImage(BuilderUtils.getImage(model, "filter"));
        item.setAlt(messages.getMessage("toolbar.text.filter"));
        item.setStyle("border:0");
        ToolbarItemUtils.buildFilter(html, model, item);
    }

    public void filterItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.filter"));
        item.setText(messages.getMessage("toolbar.text.filter"));
        ToolbarItemUtils.buildFilter(html, model, item);
    }

    public void clearItemAsButton() {
        ButtonItem item = new ButtonItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.clear"));
        item.setContents(messages.getMessage("toolbar.text.clear"));
        ToolbarItemUtils.buildClear(html, model, item);
    }

    public void clearItemAsImage() {
        ImageItem item = new ImageItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.clear"));
        item.setImage(BuilderUtils.getImage(model, "clear"));
        item.setAlt(messages.getMessage("toolbar.text.clear"));
        item.setStyle("border:0");
        ToolbarItemUtils.buildClear(html, model, item);
    }

    public void clearItemAsText() {
        TextItem item = new TextItem();
        item.setTooltip(messages.getMessage("toolbar.tooltip.clear"));
        item.setText(messages.getMessage("toolbar.text.clear"));
        ToolbarItemUtils.buildClear(html, model, item);
    }

    public void exportItemAsButton(Export export) {
        ButtonItem item = new ButtonItem();
        item.setTooltip(export.getTooltip());
        item.setContents(export.getText());
        ToolbarItemUtils.buildExport(html, model, item, export);
    }

    public void exportItemAsImage(Export export) {
        ImageItem item = new ImageItem();
        item.setTooltip(export.getTooltip());
        item.setImage(BuilderUtils.getImage(model, export.getImageName()));
        item.setAlt(export.getText());
        item.setStyle("border:0");
        ToolbarItemUtils.buildExport(html, model, item, export);
    }

    public void exportItemAsText(Export export) {
        TextItem item = new TextItem();
        item.setTooltip(export.getTooltip());
        item.setText(export.getText());
        ToolbarItemUtils.buildExport(html, model, item, export);
    }

    public void pageSelectItemDroplist() {
        int nowPage = model.getLimit().getPage();
        int totalPage = getTotalPages(model);
        html.select().name((new StringBuilder(String.valueOf(model.getTableHandler().prefixWithTableId()))).append("pd").toString());
        StringBuffer onchange = new StringBuffer();
        onchange.append((new DmyTableActions(model)).getPageSelectItemAction());
        html.onchange(onchange.toString());
        html.close();
        html.newline();
        html.tabs(4);
        for (int i = 0; i < totalPage; i++) {
            html.option().value(String.valueOf(i + 1));
            if (nowPage == i + 1) {
                html.selected();
            }
            html.close();
            html.append(String.valueOf(i + 1));
            html.optionEnd();
        }

        html.newline();
        html.tabs(4);
        html.selectEnd();
    }

    public void rowsDisplayedDroplist() {
        int rowsDisplayed = model.getTableHandler().getTable().getRowsDisplayed();
        int medianRowsDisplayed = model.getTableHandler().getTable().getMedianRowsDisplayed();
        int maxRowsDisplayed = model.getTableHandler().getTable().getMaxRowsDisplayed();
        int currentRowsDisplayed = model.getLimit().getCurrentRowsDisplayed();
        html.select().name(
                (new StringBuilder(String.valueOf(model.getTableHandler().prefixWithTableId()))).append("rd").toString());
        StringBuffer onchange = new StringBuffer();
        onchange.append((new TableActions(model)).getRowsDisplayedAction());
        html.onchange(onchange.toString());
        html.close();
        html.newline();
        html.tabs(4);
        html.option().value(String.valueOf(rowsDisplayed));
        if (currentRowsDisplayed == rowsDisplayed) {
            html.selected();
        }
        html.close();
        html.append(String.valueOf(rowsDisplayed));
        html.optionEnd();
        html.option().value(String.valueOf(medianRowsDisplayed));
        if (currentRowsDisplayed == medianRowsDisplayed) {
            html.selected();
        }
        html.close();
        html.append(String.valueOf(medianRowsDisplayed));
        html.optionEnd();
        html.option().value(String.valueOf(maxRowsDisplayed));
        if (currentRowsDisplayed == maxRowsDisplayed) {
            html.selected();
        }
        html.close();
        html.append(String.valueOf(maxRowsDisplayed));
        html.optionEnd();
        html.newline();
        html.tabs(4);
        html.selectEnd();
    }

    public void separator() {
        html.img();
        html.src(BuilderUtils.getImage(model, "separator"));
        html.style("border:0");
        html.alt("Separator");
        html.xclose();
    }

    public String toString() {
        return html.toString();
    }

    public int getTotalPages(TableModel model) {
        int currentRowsDisplayed = model.getLimit().getCurrentRowsDisplayed();
        if (currentRowsDisplayed == 0) {
            currentRowsDisplayed = model.getLimit().getTotalRows();
        }
        int totalRows = model.getLimit().getTotalRows();
        int totalPages = 1;
        if (currentRowsDisplayed != 0) {
            totalPages = totalRows / currentRowsDisplayed;
        }
        if (currentRowsDisplayed != 0 && totalRows % currentRowsDisplayed > 0) {
            totalPages++;
        }
        return totalPages;
    }
    private HtmlBuilder html;
    private TableModel model;
    private Messages messages;
}
