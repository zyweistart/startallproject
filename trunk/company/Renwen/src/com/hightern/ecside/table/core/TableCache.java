package com.hightern.ecside.table.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.calc.Calc;
import com.hightern.ecside.table.callback.FilterRowsCallback;
import com.hightern.ecside.table.callback.RetrieveRowsCallback;
import com.hightern.ecside.table.callback.SortRowsCallback;
import com.hightern.ecside.table.cell.Cell;
import com.hightern.ecside.table.interceptor.ColumnInterceptor;
import com.hightern.ecside.table.interceptor.ExportInterceptor;
import com.hightern.ecside.table.interceptor.RowInterceptor;
import com.hightern.ecside.table.interceptor.TableInterceptor;
import com.hightern.ecside.table.state.State;

@SuppressWarnings("unchecked")
public class TableCache {

    private static Log logger = LogFactory.getLog(TableCache.class);
    public static TableCache tableCache = new TableCache();
    private Map cache = Collections.synchronizedMap(new HashMap());

    private TableCache() {
    }

    public static TableCache getInstance() {
        return tableCache;
    }

    private Object getCachedObject(String className) {
        Object cachedObject = null;

        try {
            Class classForName = Class.forName(className);
            cachedObject = cache.get(classForName);
            if (cachedObject == null) {
                cachedObject = classForName.newInstance();
                cache.put(classForName, cachedObject);
            }

        } catch (Exception e) {
            String msg = "Could not create the object [" + className
                    + "]. The class was not found or does not exist.";
            logger.error(msg, e);
            throw new IllegalStateException(msg);
        }

        return cachedObject;
    }

    public Cell getCell(String cell) {
        return (Cell) getCachedObject(cell);
    }

    public State getState(String state) {
        return (State) getCachedObject(state);
    }

    public RetrieveRowsCallback getRetrieveRowsCallback(TableModel model) {
        String retrieveRowsCallback = model.getTableHandler().getTable().getRetrieveRowsCallback();
        return (RetrieveRowsCallback) getCachedObject(retrieveRowsCallback);
    }

    public FilterRowsCallback getFilterRowsCallback(TableModel model) {
        String filterRowsCallback = model.getTableHandler().getTable().getFilterRowsCallback();
        return (FilterRowsCallback) getCachedObject(filterRowsCallback);
    }

    public SortRowsCallback getSortRowsCallback(TableModel model) {
        String sortRowsCallback = model.getTableHandler().getTable().getSortRowsCallback();
        return (SortRowsCallback) getCachedObject(sortRowsCallback);
    }

    public Calc getCalc(String calc) {
        return (Calc) getCachedObject(calc);
    }

    public TableInterceptor getTableInterceptor(String tableInterceptor) {
        return (TableInterceptor) getCachedObject(tableInterceptor);
    }

    public RowInterceptor getRowInterceptor(String rowInterceptor) {
        return (RowInterceptor) getCachedObject(rowInterceptor);
    }

    public ColumnInterceptor getColumnInterceptor(String columnInterceptor) {
        return (ColumnInterceptor) getCachedObject(columnInterceptor);
    }

    public ExportInterceptor getExportInterceptor(String exportInterceptor) {
        return (ExportInterceptor) getCachedObject(exportInterceptor);
    }

    public AutoGenerateColumns getAutoGenerateColumns(String autoGenerateColumns) {
        return (AutoGenerateColumns) getCachedObject(autoGenerateColumns);
    }
}
