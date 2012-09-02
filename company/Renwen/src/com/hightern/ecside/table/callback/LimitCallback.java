package com.hightern.ecside.table.callback;

import java.util.Collection;

import com.hightern.ecside.table.bean.Table;
import com.hightern.ecside.table.core.RetrievalUtils;
import com.hightern.ecside.table.core.TableConstants;
import com.hightern.ecside.table.core.TableModel;

public final class LimitCallback implements RetrieveRowsCallback,
        FilterRowsCallback, SortRowsCallback {

    public final static String TOTAL_ROWS = "totalRows";

    @SuppressWarnings("unchecked")
    public Collection retrieveRows(TableModel model) throws Exception {
        Table table = model.getTableHandler().getTable();
        Collection rows = RetrievalUtils.retrieveCollection(model.getContext(), table.getItems(), table.getScope());

        Object totalRows = RetrievalUtils.retrieve(model.getContext(), TableConstants.TOTAL_ROWS);
        if (totalRows == null) {
            totalRows = (Integer) RetrievalUtils.retrieve(model.getContext(), model.getTableHandler().prefixWithTableId() + TableConstants.TOTAL_ROWS);
        }

        if (totalRows instanceof Integer) {
            model.getTableHandler().setTotalRows((Integer) totalRows);
        } else {
            String message = "You need to specify the " + TableConstants.TOTAL_ROWS + " (as an Integer) to use the " + this.getClass().getName() + ".";
            throw new Exception(message);
        }

        return rows;
    }

    @SuppressWarnings("unchecked")
    public Collection filterRows(TableModel model, Collection rows) throws
            Exception {
        return rows;
    }

    @SuppressWarnings("unchecked")
    public Collection sortRows(TableModel model, Collection rows) throws
            Exception {
        return rows;
    }
}
