package com.hightern.ecside.table.view.html;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.limit.Sort;

public class TableActions {

    private TableModel model;

    public TableActions(TableModel model) {
        this.model = model;
    }

    protected TableModel getTableModel() {
        return model;
    }

    public boolean hasOnInvokeAction() {
        return StringUtils.isNotBlank(getOnInvokeAction());
    }

    public String getOnInvokeAction() {
        return model.getTableHandler().getTable().getOnInvokeAction();
    }

    public String getOnInvokeOrSubmitAction() {
        String onInvokeAction = getOnInvokeAction();
        if (StringUtils.isNotBlank(onInvokeAction)) {
            return onInvokeAction;
        }

        return getSubmitAction();
    }

    public String getSubmitAction() {
        StringBuffer result = new StringBuffer();

        String form = BuilderUtils.getForm(model);
        String action = model.getTableHandler().getTable().getAction();
        result.append("document.forms.").append(form).append(".setAttribute('action','").append(action).append("');");

        String method = model.getTableHandler().getTable().getMethod();
        result.append("document.forms.").append(form).append(".setAttribute('method','").append(method).append("');");

        result.append("document.forms.").append(form).append(".submit()");

        return result.toString();
    }

    public String getFormParameter(String name, String value) {
        StringBuffer result = new StringBuffer();

        String form = BuilderUtils.getForm(model);
        result.append("document.forms.").append(form).append(".");
        result.append(model.getTableHandler().prefixWithTableId()).append(name);
        result.append(".value='").append(value).append("';");

        return result.toString();
    }

    public String getExportTableIdParameter(String value) {
        StringBuffer result = new StringBuffer();

        String form = BuilderUtils.getForm(model);

        result.append("document.forms.").append(form).append(".");
        result.append(TableConstants.EXPORT_TABLE_ID);
        result.append(".value='").append(value).append("';");

        return result.toString();
    }

    public String getExportAction(String exportView, String exportFileName) {
        StringBuffer action = new StringBuffer("javascript:");

        action.append(getExportTableIdParameter(model.getTableHandler().getTable().getTableId()));
        action.append(getFormParameter(TableConstants.EXPORT_VIEW, exportView));
        action.append(getFormParameter(TableConstants.EXPORT_FILE_NAME, exportFileName));

        action.append(getSubmitAction());

        return action.toString();
    }

    public String getPageAction(int page) {
        StringBuffer action = new StringBuffer("javascript:");

        action.append(getClearedExportTableIdParameters());

        action.append(getFormParameter(TableConstants.PAGE, "" + page));
        action.append(getOnInvokeOrSubmitAction());

        return action.toString();
    }

    public String getFilterAction() {
        StringBuffer action = new StringBuffer("javascript:");

        action.append(getClearedExportTableIdParameters());

        action.append(getFormParameter(TableConstants.FILTER + TableConstants.ACTION, TableConstants.FILTER_ACTION));
        action.append(getFormParameter(TableConstants.PAGE, "1"));
        action.append(getOnInvokeOrSubmitAction());

        return action.toString();
    }

    public String getClearAction() {
        StringBuffer action = new StringBuffer("javascript:");

        action.append(getClearedExportTableIdParameters());

        action.append(getFormParameter(TableConstants.FILTER + TableConstants.ACTION, TableConstants.CLEAR_ACTION));
        action.append(getFormParameter(TableConstants.PAGE, "1"));
        action.append(getOnInvokeOrSubmitAction());

        return action.toString();
    }

    public String getSortAction(Column column, String sortOrder) {
        StringBuffer action = new StringBuffer("javascript:");

        Sort sort = model.getLimit().getSort();
        if (sort.isSorted()) {
            action.append(getFormParameter(TableConstants.SORT + sort.getAlias(), ""));
        }
        action.append(getClearedExportTableIdParameters());
        action.append(getFormParameter(TableConstants.SORT + column.getAlias(), sortOrder));
        action.append(getFormParameter(TableConstants.PAGE, "1"));
        action.append(getOnInvokeOrSubmitAction());

        return action.toString();
    }

    public String getRowsDisplayedAction() {
        StringBuffer action = new StringBuffer("javascript:");

        action.append(getClearedExportTableIdParameters());

        action.append(getRowsDisplayedFormParameter(TableConstants.CURRENT_ROWS_DISPLAYED));
        action.append(getFormParameter(TableConstants.PAGE, "1"));
        action.append(getOnInvokeOrSubmitAction());

        return action.toString();
    }

    public String getClearedExportTableIdParameters() {
        if (BuilderUtils.showExports(model)) {
            return getExportTableIdParameter("");
        }
        return "";
    }

    protected String getRowsDisplayedFormParameter(String name) {
        StringBuffer result = new StringBuffer();
        String form = BuilderUtils.getForm(model);
        String selectedOption = "this.options[this.selectedIndex].value";
        result.append("document.forms.").append(form).append(".");
        result.append(model.getTableHandler().prefixWithTableId()).append(name);
        result.append(".value=").append(selectedOption).append(";");
        return result.toString();
    }
}
