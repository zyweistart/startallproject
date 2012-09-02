package com.hightern.ecside.table.state;

import java.util.Map;

import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.core.TableConstants;

public class NotifyToPersistState extends AbstractState {

    @SuppressWarnings("unchecked")
    public Map getParameters(Context context, String tableId, String stateAttr) {
        String stateAttrValue = context.getParameter(stateAttr);
        if ("true".equalsIgnoreCase(stateAttrValue)) {
            return (Map) context.getSessionAttribute(TableConstants.STATE
                    + tableId);
        }

        return null;
    }
}
