package com.hightern.ecside.table.cell;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.view.html.TableActions;
import com.hightern.ecside.util.HtmlBuilder;

public class FilterCell implements Cell {

    public String getExportDisplay(TableModel model, Column column) {
        return null;
    }

    public String getHtmlDisplay(TableModel model, Column column) {
        HtmlBuilder html = new HtmlBuilder();

        html.td(2);

        if (StringUtils.isNotEmpty(column.getFilterClass())) {
            html.styleClass(column.getFilterClass());
        }

        if (StringUtils.isNotEmpty(column.getFilterStyle())) {
            html.style(column.getFilterStyle());
        }

        if (StringUtils.isNotEmpty(column.getWidth())) {
            html.width(column.getWidth());
        }

        html.close();

        if (!column.isFilterable()) {
            html.append("");
        } else {
            html.append(input(model, column));
        }

        html.tdEnd();

        return html.toString();
    }

    /**
     * If the filter is specified the default is to use a <input type=text> tag.
     * 
     * Modified 16 AUG 2007 - Todd Fredrich - added 'return false;' to 'if(event.keycode == 13)' 
     *                                        clause when a user-set onInvokeAction is present. 
     */
    private String input(TableModel model, Column column) {
        HtmlBuilder html = new HtmlBuilder();

        html.input("text");
        html.name(model.getTableHandler().prefixWithTableId() + TableConstants.FILTER + column.getAlias());

        String value = column.getValueAsString();
        if (StringUtils.isNotBlank(value)) {
            html.value(StringEscapeUtils.escapeHtml(value));
        }

        StringBuffer onkeypress = new StringBuffer();
        onkeypress.append("if (event.keyCode == 13) {");
        TableActions ta = new TableActions(model);
        onkeypress.append(ta.getFilterAction());

        if (ta.hasOnInvokeAction()) {
            onkeypress.append("return false;");
        }

        onkeypress.append("}");
        html.onkeypress(onkeypress.toString());

        html.xclose();

        return html.toString();
    }
}
