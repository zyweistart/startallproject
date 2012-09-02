package com.hightern.ecside.table.cell;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.cell.Cell;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public class SelectAllHeaderCell implements Cell {

    public String getExportDisplay(TableModel model, Column column) {
        return column.getTitle();
    }

    public String getHtmlDisplay(TableModel model, Column column) {
        HtmlBuilder html = new HtmlBuilder();

        html.td(2);

        if (StringUtils.isNotEmpty(column.getHeaderClass())) {
            html.styleClass(column.getHeaderClass());
        }

        if (StringUtils.isNotEmpty(column.getHeaderStyle())) {
            html.style(column.getHeaderStyle());
        }

        if (StringUtils.isNotEmpty(column.getWidth())) {
            html.width(column.getWidth());
        }

        html.close();

        String controlName = column.getAlias() + "_selector";
        String selectableControlName = column.getAlias();

        html.input("checkbox");
        html.id(controlName);
        html.name(controlName);
        html.title("(Un)Select All");

        html.onclick("for(i = 0; i < document.getElementsByName('" + selectableControlName
                + "').length; i++)document.getElementsByName('" + selectableControlName
                + "').item(i).checked=document.getElementById('" + controlName + "').checked;");
        html.close();

        html.tdEnd();

        return html.toString();
    }
}
