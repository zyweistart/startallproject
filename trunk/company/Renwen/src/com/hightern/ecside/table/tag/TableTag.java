package com.hightern.ecside.table.tag;

import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import com.hightern.ecside.table.bean.Table;
import com.hightern.ecside.table.context.JspPageContext;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.core.TableModelImpl;
import com.hightern.ecside.table.interceptor.TableInterceptor;
import com.hightern.ecside.util.ExceptionUtils;

public class TableTag extends TagSupport implements TryCatchFinally,
        TableInterceptor {

    private static final long serialVersionUID = 1L;
    private String action;
    private String autoIncludeParameters;
    private String border;
    private String bufferView;
    private String cellpadding;
    private String cellspacing;
    private String filterable;
    private String filterRowsCallback;
    private String form;
    private String imagePath;
    private String interceptor;
    private Object items;
    private String locale;
    private String method;
    private String onInvokeAction;
    private String retrieveRowsCallback;
    private String rowsDisplayed;
    private String scope;
    private String showExports;
    private String showPagination;
    private String showStatusBar;
    private String showTitle;
    private String showTooltips;
    private String sortRowsCallback;
    private String sortable;
    private String state;
    private String stateAttr;
    private String style;
    private String styleClass;
    private String tableId;
    private String theme;
    private String title;
    private String var;
    private String view;
    private String width;
    protected TableModel model;
    @SuppressWarnings("unchecked")
    private Iterator iterator;

    public TableModel getModel() {
        return model;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setAutoIncludeParameters(String autoIncludeParameters) {
        this.autoIncludeParameters = autoIncludeParameters;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public void setBufferView(String bufferView) {
        this.bufferView = bufferView;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public void setFilterable(String filterable) {
        this.filterable = filterable;
    }

    public void setFilterRowsCallback(String filterRowsCallback) {
        this.filterRowsCallback = filterRowsCallback;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setInterceptor(String interceptor) {
        this.interceptor = interceptor;
    }

    public void setItems(Object items) {
        this.items = items;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setOnInvokeAction(String onInvokeAction) {
        this.onInvokeAction = onInvokeAction;
    }

    public void setRetrieveRowsCallback(String retrieveRowsCallback) {
        this.retrieveRowsCallback = retrieveRowsCallback;
    }

    public void setRowsDisplayed(String rowsDisplayed) {
        this.rowsDisplayed = rowsDisplayed;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setShowPagination(String showPagination) {
        this.showPagination = showPagination;
    }

    public void setShowExports(String showExports) {
        this.showExports = showExports;
    }

    public void setShowStatusBar(String showStatusBar) {
        this.showStatusBar = showStatusBar;
    }

    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    public void setShowTooltips(String showTooltips) {
        this.showTooltips = showTooltips;
    }

    public void setSortRowsCallback(String sortRowsCallback) {
        this.sortRowsCallback = sortRowsCallback;
    }

    public void setSortable(String sortable) {
        this.sortable = sortable;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStateAttr(String stateAttr) {
        this.stateAttr = stateAttr;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVar(String var) {
        this.var = var;
    }

    public void setView(String view) {
        this.view = view;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            iterator = null;
            pageContext.setAttribute(TableConstants.ROWCOUNT, "0");
            model = new TableModelImpl(new JspPageContext(pageContext),
                    TagUtils.evaluateExpressionAsString("locale", this.locale,
                    this, pageContext));
            Table table = new Table(model);
            table.setAction(TagUtils.evaluateExpressionAsString("action",
                    action, this, pageContext));
            table.setAutoIncludeParameters(TagUtils.evaluateExpressionAsBoolean("autoIncludeParameters",
                    this.autoIncludeParameters, this, pageContext));
            table.setBorder(TagUtils.evaluateExpressionAsString("border",
                    this.border, this, pageContext));
            table.setBufferView(TagUtils.evaluateExpressionAsBoolean(
                    "bufferView", this.bufferView, this, pageContext));
            table.setCellpadding(TagUtils.evaluateExpressionAsString(
                    "cellpadding", this.cellpadding, this, pageContext));
            table.setCellspacing(TagUtils.evaluateExpressionAsString(
                    "cellspacing", this.cellspacing, this, pageContext));
            table.setFilterable(TagUtils.evaluateExpressionAsBoolean(
                    "filterable", this.filterable, this, pageContext));
            table.setFilterRowsCallback(TagUtils.evaluateExpressionAsString(
                    "filterRowsCallback", this.filterRowsCallback, this,
                    pageContext));
            table.setForm(TagUtils.evaluateExpressionAsString("form",
                    this.form, this, pageContext));
            table.setImagePath(TagUtils.evaluateExpressionAsString("imagePath",
                    this.imagePath, this, pageContext));
            table.setInterceptor(TagUtils.evaluateExpressionAsString(
                    "interceptor", this.interceptor, this, pageContext));
            table.setItems(TagUtils.evaluateExpressionAsObject("items",
                    this.items, this, pageContext));
            table.setLocale(TagUtils.evaluateExpressionAsString("locale",
                    this.locale, this, pageContext));
            table.setMethod(TagUtils.evaluateExpressionAsString("method",
                    this.method, this, pageContext));
            table.setOnInvokeAction(TagUtils.evaluateExpressionAsString(
                    "onInvokeAction", this.onInvokeAction, this, pageContext));
            table.setRetrieveRowsCallback(TagUtils.evaluateExpressionAsString(
                    "retrieveRowsCallback", this.retrieveRowsCallback, this,
                    pageContext));
            table.setRowsDisplayed(TagUtils.evaluateExpressionAsInt(
                    "rowsDisplayed", this.rowsDisplayed, this, pageContext));
            table.setScope(TagUtils.evaluateExpressionAsString("scope", scope,
                    this, pageContext));
            table.setShowExports(TagUtils.evaluateExpressionAsBoolean(
                    "showExports", this.showExports, this, pageContext));
            table.setShowPagination(TagUtils.evaluateExpressionAsBoolean(
                    "showPagination", this.showPagination, this, pageContext));
            table.setShowStatusBar(TagUtils.evaluateExpressionAsBoolean(
                    "showStatusBar", this.showStatusBar, this, pageContext));
            table.setShowTitle(TagUtils.evaluateExpressionAsBoolean(
                    "showTitle", this.showTitle, this, pageContext));
            table.setShowTooltips(TagUtils.evaluateExpressionAsBoolean(
                    "showTooltips", this.showTooltips, this, pageContext));
            table.setSortRowsCallback(TagUtils.evaluateExpressionAsString(
                    "sortRowsCallback", this.sortRowsCallback, this,
                    pageContext));
            table.setSortable(TagUtils.evaluateExpressionAsBoolean("sortable",
                    this.sortable, this, pageContext));
            table.setState(TagUtils.evaluateExpressionAsString("state",
                    this.state, this, pageContext));
            table.setStateAttr(TagUtils.evaluateExpressionAsString("stateAttr",
                    this.stateAttr, this, pageContext));
            table.setStyle(TagUtils.evaluateExpressionAsString("style", style,
                    this, pageContext));
            table.setStyleClass(TagUtils.evaluateExpressionAsString(
                    "styleClass", this.styleClass, this, pageContext));
            table.setTableId(TagUtils.evaluateExpressionAsString("tableId",
                    tableId, this, pageContext));
            table.setTheme(TagUtils.evaluateExpressionAsString("theme",
                    this.theme, this, pageContext));
            table.setTitle(TagUtils.evaluateExpressionAsString("title",
                    this.title, this, pageContext));
            table.setVar(TagUtils.evaluateExpressionAsString("var", this.var,
                    this, pageContext));
            table.setView(TagUtils.evaluateExpressionAsString("view",
                    this.view, this, pageContext));
            table.setWidth(TagUtils.evaluateExpressionAsString("width",
                    this.width, this, pageContext));

            addTableAttributes(model, table);
            model.addTable(table);
        } catch (Exception e) {
            throw new JspException("TableTag.doStartTag() Problem: "
                    + ExceptionUtils.formatStackTrace(e));
        }

        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doAfterBody() throws JspException {
        try {
            if (iterator == null) {
                iterator = model.execute().iterator();
            }

            if (iterator != null && iterator.hasNext()) {
                Object bean = iterator.next();
                model.setCurrentRowBean(bean);
                return EVAL_BODY_AGAIN;
            }
        } catch (Exception e) {
            throw new JspException("TableTag.doAfterBody() Problem: "
                    + ExceptionUtils.formatStackTrace(e));
        }

        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().println(model.getViewData());
        } catch (Exception e) {
            throw new JspException("TableTag.doEndTag() Problem: "
                    + ExceptionUtils.formatStackTrace(e));
        }

        return EVAL_PAGE;
    }

    public void addTableAttributes(TableModel model, Table table) {
    }

    public void doCatch(Throwable e) throws Throwable {
        throw new JspException("TableTag Problem: "
                + ExceptionUtils.formatStackTrace(e));
    }

    public void doFinally() {
        iterator = null;
        model = null;
    }

    @Override
    public void release() {
        action = null;
        autoIncludeParameters = null;
        border = null;
        cellpadding = null;
        cellspacing = null;
        filterable = null;
        filterRowsCallback = null;
        form = null;
        imagePath = null;
        interceptor = null;
        items = null;
        locale = null;
        method = null;
        onInvokeAction = null;
        retrieveRowsCallback = null;
        rowsDisplayed = null;
        scope = null;
        showExports = null;
        showPagination = null;
        showStatusBar = null;
        sortRowsCallback = null;
        sortable = null;
        state = null;
        stateAttr = null;
        style = null;
        styleClass = null;
        tableId = null;
        title = null;
        var = null;
        view = null;
        width = null;
        super.release();
    }
}
