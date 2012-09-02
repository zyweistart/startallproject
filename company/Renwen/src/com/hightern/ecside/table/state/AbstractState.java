package com.hightern.ecside.table.state;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.core.TableConstants;

abstract class AbstractState implements State {

    @SuppressWarnings("unchecked")
    public void saveParameters(Context context, String tableId, Map parameterMap) {
        Map savedAttributes = new HashMap();

        Set keys = parameterMap.keySet();
        for (Iterator iter = keys.iterator(); iter.hasNext();) {
            String key = (String) iter.next();
            Object value = parameterMap.get(key);
            savedAttributes.put(key, value);
        }

        context.setSessionAttribute(TableConstants.STATE + tableId,
                savedAttributes);
    }
}
