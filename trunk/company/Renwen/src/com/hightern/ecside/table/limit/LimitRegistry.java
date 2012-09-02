package com.hightern.ecside.table.limit;

import java.util.Map;

import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.core.AbstractRegistry;
import com.hightern.ecside.table.state.State;

public final class LimitRegistry extends AbstractRegistry {

    public LimitRegistry(Context context, String tableId,
            String prefixWithTableId, String state, String stateAttr) {
        this.context = context;
        this.tableId = tableId;
        this.prefixWithTableId = prefixWithTableId;
        this.state = state;
        this.stateAttr = stateAttr;
        this.autoIncludeParameters = false;
        setParameterMap();
    }

    @SuppressWarnings("unchecked")
    protected void handleStateInternal(State state, Map tableParameterMap) {
    }
}
