package com.hightern.ecside.table.cell;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.MessagesConstants;
import com.hightern.ecside.table.core.PreferencesConstants;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.core.TableModelUtils;
import com.hightern.ecside.table.view.html.BuilderConstants;
import com.hightern.ecside.table.view.html.BuilderUtils;
import com.hightern.ecside.table.view.html.TableActions;
import com.hightern.ecside.util.HtmlBuilder;

public class HeaderCell implements Cell {

    public String getExportDisplay(TableModel model, Column column) {
        return column.getTitle();
    }

    public String getHtmlDisplay(TableModel model, Column column) {
        HtmlBuilder html = new HtmlBuilder();

        String headerClass = null;
        String sortImage = null;
        String sortOrder = null;

        headerClass = column.getHeaderClass();

        if (TableModelUtils.isSorted(model, column.getAlias())) {
            sortOrder = model.getLimit().getSort().getSortOrder();

            if (sortOrder.equals(TableConstants.SORT_DEFAULT)) {
                sortOrder = TableConstants.SORT_ASC;
            } else if (sortOrder.equals(TableConstants.SORT_ASC)) {
                headerClass = model.getPreferences().getPreference(PreferencesConstants.TABLE_HEADER_SORT_CLASS);
                sortImage = BuilderUtils.getImage(model, BuilderConstants.SORT_ASC_IMAGE);
                sortOrder = TableConstants.SORT_DESC;
            } else if (sortOrder.equals(TableConstants.SORT_DESC)) {
                headerClass = model.getPreferences().getPreference(PreferencesConstants.TABLE_HEADER_SORT_CLASS);
                sortImage = BuilderUtils.getImage(model, BuilderConstants.SORT_DESC_IMAGE);
                sortOrder = TableConstants.SORT_DEFAULT;
            }
        } else {
            sortOrder = TableConstants.SORT_ASC; // the default
        }

        buildHeaderHtml(html, model, column, headerClass, sortImage, sortOrder);

        return html.toString();
    }

    protected void buildHeaderHtml(HtmlBuilder html, TableModel model, Column column, String headerClass, String sortImage, String sortOrder) {
        html.td(2);

        if (StringUtils.isNotEmpty(headerClass)) {
            html.styleClass(headerClass);
        }

        if (StringUtils.isNotEmpty(column.getHeaderStyle())) {
            html.style(column.getHeaderStyle());
        }

        if (StringUtils.isNotEmpty(column.getWidth())) {
            html.width(column.getWidth());
        }

        if (column.isSortable()) {
            if (sortOrder.equals(TableConstants.SORT_ASC)) {
                html.onmouseover("this.className='" + BuilderConstants.TABLE_HEADER_SORT_CSS + "';this.style.cursor='pointer'");
                if (StringUtils.isNotEmpty(headerClass)) {
                    html.onmouseout("this.className='" + headerClass + "';this.style.cursor='default'");
                } else {
                    html.onmouseout("this.className='" + BuilderConstants.TABLE_HEADER_CSS + "';this.style.cursor='default'");
                }
            }

            if (sortOrder.equals(TableConstants.SORT_DEFAULT) || sortOrder.equals(TableConstants.SORT_DESC)) {
                html.onmouseover("this.style.cursor='pointer'");
                html.onmouseout("this.style.cursor='default'");
            }

            html.onclick(new TableActions(model).getSortAction(column, sortOrder));

            boolean showTooltips = model.getTableHandler().getTable().isShowTooltips();
            if (showTooltips) {
                String headercellTooltip = model.getMessages().getMessage(MessagesConstants.COLUMN_HEADERCELL_TOOLTIP_SORT);
                html.title(headercellTooltip + " " + column.getTitle());
            }
        }

        html.close();

        html.append(column.getTitle());

        if (column.isSortable()) {
            if (StringUtils.isNotEmpty(sortImage)) {
                html.nbsp();
                html.img();
                html.src(sortImage);
                html.style("border:0");
                html.alt("Arrow");
                html.xclose();
            }
        }

        html.tdEnd();
    }
}
