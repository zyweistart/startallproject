package com.hightern.ecside.tree;

import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Set;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.util.HtmlBuilder;

public class TreeRegistryUtils {

    @SuppressWarnings("unchecked")
    public static String getHiddenFields(TableModel model, String parameter) {
        HtmlBuilder html = new HtmlBuilder();

        Set keys = model.getRegistry().getParameterMap().keySet();

        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            String name = (String) iter.next();

            if (((parameter == null) && !name.startsWith(model.getTableHandler().prefixWithTableId()))
                    || (name.startsWith(model.getTableHandler().prefixWithTableId()
                    + parameter))) {
                String values[] = (String[]) model.getRegistry().getParameterMap().get(name);
                if (values == null || values.length == 0) {
                    html.newline();
                    html.input("hidden").name(name).xclose();
                } else {
                    for (int i = 0; i < values.length; i++) {
                        html.newline();
                        html.input("hidden").name(name).value(values[i]).xclose();
                    }
                }
            }
        }

        return html.toString();
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public static String getParameterString(TableModel model, String parameter) {
        HtmlBuilder html = new HtmlBuilder();

        Set keys = model.getRegistry().getParameterMap().keySet();

        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            String name = (String) iter.next();

            if (((parameter == null) && !name.startsWith(model.getTableHandler().prefixWithTableId()))
                    || (name.startsWith(model.getTableHandler().prefixWithTableId()
                    + parameter))) {
                String values[] = (String[]) model.getRegistry().getParameterMap().get(name);
                if (values == null || values.length == 0) {
                    html.ampersand().append(name).equals();
                } else {
                    for (int i = 0; i < values.length; i++) {
                        String encodedValue = URLEncoder.encode(values[i]);
                        html.append("&amp;").append(name).equals().append(
                                encodedValue);
                    }
                }

            }
        }

        return html.toString();
    }

    public static String getURLParameterString(TableModel model,
            boolean filter, boolean sort, boolean page, boolean rowsDisplayed) {
        StringBuffer sb = new StringBuffer();

        if (filter) {
            sb.append(getParameterString(model, TableConstants.FILTER));
        }

        if (sort) {
            sb.append(getParameterString(model, TableConstants.SORT));
        }

        if (page) {
            sb.append(getParameterString(model, TableConstants.PAGE));
        }

        if (rowsDisplayed) {
            sb.append(getParameterString(model,
                    TableConstants.CURRENT_ROWS_DISPLAYED));
        }

        sb.append(getParameterString(model, null));

        return sb.toString();
    }

    public static String getFormHiddenFields(TableModel model, boolean filter,
            boolean sort, boolean page, boolean rowsDisplayed) {
        StringBuffer sb = new StringBuffer();

        if (filter) {
            sb.append(getHiddenFields(model, TableConstants.FILTER));
        }

        if (sort) {
            sb.append(getHiddenFields(model, TableConstants.SORT));
        }

        if (page) {
            sb.append(getHiddenFields(model, TableConstants.PAGE));
        }

        if (rowsDisplayed) {
            sb.append(getHiddenFields(model,
                    TableConstants.CURRENT_ROWS_DISPLAYED));
        }

        sb.append(getHiddenFields(model, null));

        return sb.toString();
    }
}
