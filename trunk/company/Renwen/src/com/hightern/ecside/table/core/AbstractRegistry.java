package com.hightern.ecside.table.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.hightern.ecside.table.context.Context;
import com.hightern.ecside.table.state.State;
import com.hightern.ecside.tree.TreeConstants;

public abstract class AbstractRegistry implements Registry {

    @SuppressWarnings("unchecked")
    protected Map parameterMap;
    protected Context context;
    protected String tableId;
    protected String prefixWithTableId;
    protected String state;
    protected String stateAttr;
    protected boolean autoIncludeParameters;

    @SuppressWarnings("unchecked")
    public void setParameterMap() {
        Map tableParameterMap = new HashMap();
        Map userDefinedParameterMap = new HashMap();

        Map params = context.getParameterMap();
        for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
            String paramName = (String) iter.next();
            if (paramName.equals(TableConstants.EXPORT_TABLE_ID)
                    || paramName.equals(TableConstants.EXTREME_COMPONENTS_INSTANCE)) {
                continue;
            }

            if (paramName.startsWith(prefixWithTableId + TableConstants.PAGE)
                    || paramName.startsWith(prefixWithTableId + TableConstants.CURRENT_ROWS_DISPLAYED)
                    || paramName.startsWith(prefixWithTableId + TableConstants.SORT)
                    || paramName.startsWith(prefixWithTableId + TableConstants.FILTER)
                    || paramName.startsWith(prefixWithTableId + TableConstants.EXPORT_VIEW)
                    || paramName.startsWith(prefixWithTableId + TableConstants.EXPORT_FILE_NAME)
                    || paramName.startsWith(prefixWithTableId + TableConstants.ALIAS)
                    || paramName.startsWith(prefixWithTableId + TreeConstants.OPEN)) {
                String paramValues[] = TableModelUtils.getValueAsArray(params.get(paramName));
                tableParameterMap.put(paramName, paramValues);
            } else {
                if (autoIncludeParameters) {
                    String paramValues[] = TableModelUtils.getValueAsArray(params.get(paramName));
                    userDefinedParameterMap.put(paramName, paramValues);
                }
            }
        }

        this.parameterMap = handleState(tableParameterMap);
        parameterMap.putAll(userDefinedParameterMap);
    }

    @SuppressWarnings("unchecked")
    public Map handleState(Map tableParameterMap) {
        State states = TableCache.getInstance().getState(this.state);

        if (tableParameterMap.isEmpty()) {
            Map stateParameters = states.getParameters(context, tableId, stateAttr);
            if (stateParameters != null) {
                tableParameterMap = stateParameters;
            }
        }

        handleStateInternal(states, tableParameterMap);

        return tableParameterMap;
    }

    @SuppressWarnings("unchecked")
    public void addParameter(String name, Object value) {
        String paramValues[] = TableModelUtils.getValueAsArray(value);
        parameterMap.put(name, paramValues);
    }

    public String getParameter(String parameter) {
        String[] values = (String[]) parameterMap.get(parameter);
        if (values != null && values.length > 0) {
            return values[0];
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Map getParameterMap() {
        return parameterMap;
    }

    public void removeParameter(String parameter) {
        parameterMap.remove(parameter);
    }

    @SuppressWarnings("unchecked")
    protected abstract void handleStateInternal(State state, Map tableParameterMap);
}
