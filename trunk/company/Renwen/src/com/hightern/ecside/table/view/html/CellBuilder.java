package com.hightern.ecside.table.view.html;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.util.HtmlBuilder;

public class CellBuilder {

    private CellBuilder() {
    }

    public static void tdStart(HtmlBuilder html, Column column) {
        html.td(2);
        styleClass(html, column);
        style(html, column);
        width(html, column);
        html.close();
    }

    public static void tdEnd(HtmlBuilder html) {
        html.tdEnd();
    }

    public static void style(HtmlBuilder html, Column column) {
        String style = column.getStyle();
        html.style(style);
    }

    public static void styleClass(HtmlBuilder html, Column column) {
        String styleClass = column.getStyleClass();
        html.styleClass(styleClass);
    }

    public static void width(HtmlBuilder html, Column column) {
        String width = column.getWidth();
        html.width(width);
    }

    public static void tdBody(HtmlBuilder html, String value) {
        if (StringUtils.isNotBlank(value)) {
            html.append(value);
        } else {
            html.nbsp();
        }
    }
}
