package com.hightern.ecside.table.handler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import com.hightern.ecside.table.bean.Column;
import com.hightern.ecside.table.calc.CalcResult;
import com.hightern.ecside.table.calc.CalcUtils;
import com.hightern.ecside.table.cell.Cell;
import com.hightern.ecside.table.core.PreferencesConstants;
import com.hightern.ecside.table.core.TableCache;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.core.TableModelUtils;

public class ColumnHandler {

    private TableModel model;
    @SuppressWarnings("unchecked")
    private List columns = new ArrayList();
    private Column firstColumn;
    private Column lastColumn;

    public ColumnHandler(TableModel model) {
        this.model = model;
    }

    public void addAutoGenerateColumn(Column column) {
        column.addAttribute(TableConstants.IS_AUTO_GENERATE_COLUMN, "true");
        addColumn(column);
    }

    @SuppressWarnings("unchecked")
    public void addColumn(Column column) {
        column.defaults();
        addColumnAttributes(column);

        if (!isViewAllowed(column)) {
            return;
        }

        if (firstColumn == null) {
            firstColumn = column;
            column.setFirstColumn(true);
        }

        if (lastColumn != null) {
            lastColumn.setLastColumn(false);
        }
        lastColumn = column;
        column.setLastColumn(true);

        columns.add(column);
    }

    public void addColumnAttributes(Column column) {
        String interceptor = TableModelUtils.getInterceptPreference(model,
                column.getInterceptor(),
                PreferencesConstants.COLUMN_INTERCEPTOR);
        column.setInterceptor(interceptor);
        TableCache.getInstance().getColumnInterceptor(interceptor).addColumnAttributes(model, column);
    }

    public void modifyColumnAttributes(Column column) {
        TableCache.getInstance().getColumnInterceptor(column.getInterceptor()).modifyColumnAttributes(model, column);
    }

    public int columnCount() {
        return columns.size();
    }

    @SuppressWarnings("unchecked")
    public List getColumns() {
        return columns;
    }

    @SuppressWarnings("unchecked")
    public Column getColumnByAlias(String alias) {
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            String columnAlias = column.getAlias();
            if (columnAlias != null && columnAlias.equals(alias)) {
                return column;
            }
        }

        return null;
    }

    public boolean hasMetatData() {
        return columnCount() > 0;
    }

    @SuppressWarnings("unchecked")
    public List getFilterColumns() {
        boolean cleared = model.getLimit().isCleared();

        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            String value = model.getLimit().getFilterSet().getFilterValue(
                    column.getAlias());
            if (cleared || StringUtils.isEmpty(value)) {
                value = "";
            }
            Cell cell = TableModelUtils.getFilterCell(column, value);
            column.setCellDisplay(cell.getHtmlDisplay(model, column));
        }

        return columns;
    }

    @SuppressWarnings("unchecked")
    public List getHeaderColumns() {
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            Cell cell = TableModelUtils.getHeaderCell(column, column.getTitle());
            boolean isExported = model.getLimit().isExported();
            if (!isExported) {
                column.setCellDisplay(cell.getHtmlDisplay(model, column));
            } else {
                column.setCellDisplay(cell.getExportDisplay(model, column));
            }
        }

        return columns;
    }

    private boolean isViewAllowed(Column column) {
        String view = model.getTableHandler().getTable().getView();

        boolean isExported = model.getLimit().isExported();
        if (isExported) {
            view = model.getExportHandler().getCurrentExport().getView();
        }

        boolean allowView = allowView(column, view);
        boolean denyView = denyView(column, view);

        if (allowView & !denyView) {
            return true;
        }

        return false;
    }

    private boolean allowView(Column column, String view) {
        String viewsAllowed[] = column.getViewsAllowed();
        if (viewsAllowed == null || viewsAllowed.length == 0) {
            return true;
        }

        for (int i = 0; i < viewsAllowed.length; i++) {
            if (view.equals(viewsAllowed[i])) {
                return true;
            }
        }

        return false;
    }

    private boolean denyView(Column column, String view) {
        String viewsDenied[] = column.getViewsDenied();
        if (viewsDenied == null || viewsDenied.length == 0) {
            return false;
        }

        for (int i = 0; i < viewsDenied.length; i++) {
            if (view.equals(viewsDenied[i])) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("unchecked")
    public Column getFirstCalcColumn() {
        for (Iterator iter = columns.iterator(); iter.hasNext();) {
            Column column = (Column) iter.next();
            if (column.isCalculated()) {
                return column;
            }
        }

        return null;
    }

    public CalcResult[] getCalcResults(Column column) {
        if (!column.isCalculated()) {
            return null;
        }

        return CalcUtils.getCalcResults(model, column);
    }
}
