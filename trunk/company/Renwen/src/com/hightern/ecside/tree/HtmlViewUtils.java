package com.hightern.ecside.tree;

import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.core.TableConstants;

public final class HtmlViewUtils {

    private HtmlViewUtils() {
    }

    public static String filterJavaScript(TableModel model) {
        return "javascript:document.forms." + model.getTableHandler().prefixWithTableId() + "filter." + model.getTableHandler().prefixWithTableId() + TableConstants.FILTER + TableConstants.ACTION
                + ".value='" + TableConstants.FILTER_ACTION + "';document.forms." + model.getTableHandler().prefixWithTableId() + "filter.submit()";
    }

    public static String paginationJavaScript(TableModel model) {
        return "javascript:document.forms." + model.getTableHandler().prefixWithTableId() + "toolbar.submit()";
    }
}
