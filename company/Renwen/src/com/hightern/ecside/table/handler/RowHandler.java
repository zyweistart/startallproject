package com.hightern.ecside.table.handler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.hightern.ecside.table.bean.Row;
import com.hightern.ecside.table.core.PreferencesConstants;
import com.hightern.ecside.table.core.TableCache;
import com.hightern.ecside.table.core.TableModel;
import com.hightern.ecside.table.core.TableModelUtils;

public class RowHandler {

    private static Log logger = LogFactory.getLog(RowHandler.class);
    private TableModel model;
    private Row row;

    public RowHandler(TableModel model) {
        this.model = model;
    }

    public TableModel getModel() {
        return model;
    }

    public Row getRow() {
        return row;
    }

    public void addRow(Row row) {
        this.row = row;
        addRowAttributes();
        row.defaults();
    }

    public void addRowAttributes() {
        String interceptor = TableModelUtils.getInterceptPreference(model, row.getInterceptor(), PreferencesConstants.ROW_INTERCEPTOR);
        row.setInterceptor(interceptor);
        TableCache.getInstance().getRowInterceptor(interceptor).addRowAttributes(model, row);
    }

    public void modifyRowAttributes() {
        TableCache.getInstance().getRowInterceptor(row.getInterceptor()).modifyRowAttributes(model, row);
    }

    /**
     * Find out if the column is sitting on an even row.
     */
    public boolean isRowEven() {
        if (row.getRowCount() != 0 && (row.getRowCount() % 2) == 0) {
            return true;
        }

        return false;
    }

    /**
     * Find out if the column is sitting on an odd row.
     */
    public boolean isRowOdd() {
        if (row.getRowCount() != 0 && (row.getRowCount() % 2) == 0) {
            return false;
        }

        return true;
    }

    public int increaseRowCount() {
        if (row == null) {
            String msg = "There is no row defined. The row (Row or RowTag) is now required "
                    + "and needs to be put around the columns.";
            logger.error(msg);
            throw new IllegalStateException(msg);
        }

        row.setRowCount(row.getRowCount() + 1);
        return row.getRowCount();
    }
}
