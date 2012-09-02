package com.hightern.ecside.table.callback;

import java.util.Collection;

import com.hightern.ecside.table.bean.Table;
import com.hightern.ecside.table.core.RetrievalUtils;
import com.hightern.ecside.table.core.TableModel;

public class CallbackUtils {

    private CallbackUtils() {
    }

    @SuppressWarnings("unchecked")
    public static Collection getItems(TableModel model, Table table) throws
            Exception {
        String items = String.valueOf(table.getItems());
        return RetrievalUtils.retrieveCollection(model.getContext(), items, table.getScope());
    }
}
