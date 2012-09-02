package com.hightern.ecside.table.core;

import java.util.Map;

@SuppressWarnings("unchecked")
public interface Registry {

    public Map handleState(Map tableParameterMap);

    public void addParameter(String name, Object value);

    public String getParameter(String parameter);

    public void setParameterMap();

    public Map getParameterMap();

    public void removeParameter(String parameter);
}
