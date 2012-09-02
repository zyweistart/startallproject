package com.hightern.ecside.table.bean;

import com.hightern.ecside.table.core.TableModel;

public class Row extends Attributes {

    private TableModel model;
    private String highlightClass;
    private Boolean highlightRow;
    private String interceptor;
    private String onclick;
    private String onmouseout;
    private String onmouseover;
    private String style;
    private String styleClass;
    private int rowCount;

    public Row(TableModel model) {
        this.model = model;
    }

    public void defaults() {
        this.highlightClass = RowDefaults.getHighlightClass(model, highlightClass);
        this.highlightRow = RowDefaults.isHighlightRow(model, highlightRow);
    }

    public String getHighlightClass() {
        return highlightClass;
    }

    public void setHighlightClass(String highlightClass) {
        this.highlightClass = highlightClass;
    }

    public boolean isHighlightRow() {
        return highlightRow.booleanValue();
    }

    public void setHighlightRow(Boolean highlightRow) {
        this.highlightRow = highlightRow;
    }

    public String getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    public String getOnclick() {
        return onclick;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public String getOnmouseout() {
        return onmouseout;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public String getOnmouseover() {
        return onmouseover;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowcount) {
        this.rowCount = rowcount;
    }
}
