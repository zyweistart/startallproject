package com.hightern.ecside.table.state;

import java.util.Map;

import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.core.TableConstants;

public class PersistState extends AbstractState {

    @SuppressWarnings("unchecked")
    public Map getParameters(Context context, String tableId, String stateAttr) {
        return (Map) context.getSessionAttribute(TableConstants.STATE + tableId);
    }
}
