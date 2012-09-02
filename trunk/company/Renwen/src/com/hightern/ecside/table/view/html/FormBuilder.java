package com.hightern.ecside.table.view.html;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.limit.Sort;
import com.hightern.ecside.util.HtmlBuilder;

public class FormBuilder {

    private HtmlBuilder html;
    private TableModel model;

    public FormBuilder(TableModel model) {
        this(new HtmlBuilder(), model);
    }

    public FormBuilder(HtmlBuilder html, TableModel model) {
        this.html = html;
        this.model = model;
    }

    public HtmlBuilder getHtmlBuilder() {
        return html;
    }

    protected TableModel getTableModel() {
        return model;
    }

    public void formStart() {
       // formAttributes();
        html.newline();
        html.div().close();
        instanceParameter();
        exportTableIdParameter();
        exportParameters();
        rowsDisplayedParameter();
        filterParameter();
        pageParameters();
        sortParameters();
        aliasParameters();
        userDefinedParameters();
        html.newline();
      //  html.divEnd();
    }

    public void formEnd() {
        String form = model.getTableHandler().getTable().getForm();
        if (StringUtils.isBlank(form)) {
            html.formEnd();
        }
    }

    public void formAttributes() {
        String form = model.getTableHandler().getTable().getForm();
        if (StringUtils.isBlank(form)) {
            html.form();
            html.id(model.getTableHandler().getTable().getTableId());
            html.action(model.getTableHandler().getTable().getAction());
            html.method(model.getTableHandler().getTable().getMethod());
            html.close();
        }
    }

    public void instanceParameter() {
        html.newline();
        html.input("hidden");
        html.name(TableConstants.EXTREME_COMPONENTS_INSTANCE);
        html.value(model.getTableHandler().getTable().getTableId());
        html.xclose();
    }

    public void filterParameter() {
        if (BuilderUtils.filterable(model)) {
            html.newline();
            html.input("hidden");
            html.name(model.getTableHandler().prefixWithTableId()
                    + TableConstants.FILTER + TableConstants.ACTION);
            if (model.getLimit().isFiltered()) {
                html.value(TableConstants.FILTER_ACTION);
            }
            html.xclose();
        }
    }

    public void rowsDisplayedParameter() {
        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId()
                + TableConstants.CURRENT_ROWS_DISPLAYED);
        int currentRowsDisplayed = model.getLimit().getCurrentRowsDisplayed();
        html.value(String.valueOf(currentRowsDisplayed));
        html.xclose();
    }

    public void pageParameters() {
        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId()
                + TableConstants.PAGE);
        int page = model.getLimit().getPage();
        if (page > 0) {
            html.value(String.valueOf(page));
        }
        html.xclose();
    }

    public void exportTableIdParameter() {
        if (!BuilderUtils.showExports(model)) {
            return;
        }

        String form = BuilderUtils.getForm(model);
        String existingForm = (String) model.getContext().getRequestAttribute(
                TableConstants.EXPORT_TABLE_ID);
        if (form.equals(existingForm)) {
            return;
        }
        html.newline();
        html.input("hidden");
        html.name(TableConstants.EXPORT_TABLE_ID);
        html.xclose();
        model.getContext().setRequestAttribute(TableConstants.EXPORT_TABLE_ID,
                form);
    }

    public void exportParameters() {
        if (!BuilderUtils.showExports(model)) {
            return;
        }

        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId()
                + TableConstants.EXPORT_VIEW);
        html.xclose();

        html.newline();
        html.input("hidden");
        html.name(model.getTableHandler().prefixWithTableId()
                + TableConstants.EXPORT_FILE_NAME);
        html.xclose();
    }

    @SuppressWarnings("unchecked")
    public void sortParameters() {
        List columns = model.getColumnHandler().getColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            if (column.isSortable()) {
                html.newline();
                html.input("hidden");
                html.name(model.getTableHandler().prefixWithTableId()
                        + TableConstants.SORT + column.getAlias());
                Sort sort = model.getLimit().getSort();
                if (sort.isSorted()
                        && sort.getAlias().equals(column.getAlias())) {
                    html.value(sort.getSortOrder());
                }
                html.xclose();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void userDefinedParameters() {
        Map parameterMap = model.getRegistry().getParameterMap();
        Set keys = parameterMap.keySet();
        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            String name = (String) iter.next();

            if (name.startsWith(model.getTableHandler().prefixWithTableId())) {
                continue;
            }

            String values[] = (String[]) parameterMap.get(name);
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

    @SuppressWarnings("unchecked")
    public void aliasParameters() {
        List columns = model.getColumnHandler().getColumns();
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            if (StringUtils.isNotBlank(column.getProperty())
                    && !column.getProperty().equals(column.getAlias())) {
                html.newline();
                html.input("hidden");
                html.name(model.getTableHandler().prefixWithTableId()
                        + TableConstants.ALIAS + column.getAlias());
                html.value(column.getProperty());
                html.xclose();
            }
        }
    }

    public String toString() {
        return html.toString();
    }
}
