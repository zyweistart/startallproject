package com.hightern.ecside.table.state;

import java.util.Map;

import com.hightern.ecside.table.context.Context;

public interface State {

    @SuppressWarnings("unchecked")
    public void saveParameters(Context context, String tableId, Map parameterMap);

    @SuppressWarnings("unchecked")
    public Map getParameters(Context context, String tableId, String stateAttr);
}
